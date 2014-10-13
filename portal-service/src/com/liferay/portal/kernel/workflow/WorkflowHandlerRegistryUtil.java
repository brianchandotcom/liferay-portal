/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.workflow;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
@ProviderType
public class WorkflowHandlerRegistryUtil {

	public static List<WorkflowHandler<?>> getScopeableWorkflowHandlers() {
		return _instance._getScopeableWorkflowHandlers();
	}

	public static <T> WorkflowHandler<T> getWorkflowHandler(String className) {
		return (WorkflowHandler<T>)_instance._getWorkflowHandler(className);
	}

	public static List<WorkflowHandler<?>> getWorkflowHandlers() {
		return _instance._getWorkflowHandlers();
	}

	public static void register(List<WorkflowHandler<?>> workflowHandlers) {
		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			register(workflowHandler);
		}
	}

	public static void register(WorkflowHandler<?> workflowHandler) {
		_instance._register(workflowHandler);
	}

	public static <T> void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, T model, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			(Map<String, Serializable>)serviceContext.removeAttribute(
				"workflowContext");

		if (workflowContext == null) {
			workflowContext = Collections.emptyMap();
		}

		startWorkflowInstance(
			companyId, groupId, userId, className, classPK, model,
			serviceContext, workflowContext);
	}

	public static <T> T startWorkflowInstance(
			final long companyId, final long groupId, final long userId,
			String className, final long classPK, final T model,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		if (serviceContext.getWorkflowAction() !=
				WorkflowConstants.ACTION_PUBLISH) {

			return model;
		}

		final WorkflowHandler<T> workflowHandler = getWorkflowHandler(
			className);

		if (workflowHandler == null) {
			if (WorkflowThreadLocal.isEnabled()) {
				throw new WorkflowException(
					"No workflow handler found for " + className);
			}

			return model;
		}

		boolean hasWorkflowInstanceInProgress =
			_instance._hasWorkflowInstanceInProgress(
				companyId, groupId, className, classPK);

		if (hasWorkflowInstanceInProgress) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Workflow already started for class " + className +
						" with primary key " + classPK + " in group " +
							groupId);
			}

			return model;
		}

		WorkflowDefinitionLink workflowDefinitionLink = null;

		if (WorkflowThreadLocal.isEnabled() &&
			WorkflowEngineManagerUtil.isDeployed()) {

			workflowDefinitionLink = workflowHandler.getWorkflowDefinitionLink(
				companyId, groupId, classPK);
		}

		int status = WorkflowConstants.STATUS_PENDING;

		if (workflowDefinitionLink == null) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		workflowContext = new HashMap<String, Serializable>(workflowContext);

		workflowContext.put(
			WorkflowConstants.CONTEXT_COMPANY_ID, String.valueOf(companyId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_GROUP_ID, String.valueOf(groupId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_ID, String.valueOf(userId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME, className);
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK, String.valueOf(classPK));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_TYPE,
			workflowHandler.getType(LocaleUtil.getDefault()));
		workflowContext.put(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT, serviceContext);
		workflowContext.put(
			WorkflowConstants.CONTEXT_TASK_COMMENTS,
			GetterUtil.getString(serviceContext.getAttribute("comments")));

		T updatedModel = workflowHandler.updateStatus(status, workflowContext);

		if (workflowDefinitionLink != null) {
			final Map<String, Serializable> tempWorkflowContext =
				workflowContext;

			TransactionCommitCallbackRegistryUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						workflowHandler.startWorkflowInstance(
							companyId, groupId, userId, classPK, model,
							tempWorkflowContext);

						return null;
					}

				});
		}

		return updatedModel;
	}

	public static <T> void startWorkflowInstance(
			long companyId, long userId, String className, long classPK,
			T model, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			(Map<String, Serializable>)serviceContext.removeAttribute(
				"workflowContext");

		if (workflowContext == null) {
			workflowContext = Collections.emptyMap();
		}

		startWorkflowInstance(
			companyId, WorkflowConstants.DEFAULT_GROUP_ID, userId, className,
			classPK, model, serviceContext, workflowContext);
	}

	public static <T> void startWorkflowInstance(
			long companyId, long userId, String className, long classPK,
			T model, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		startWorkflowInstance(
			companyId, WorkflowConstants.DEFAULT_GROUP_ID, userId, className,
			classPK, model, serviceContext, workflowContext);
	}

	public static void unregister(List<WorkflowHandler<?>> workflowHandlers) {
		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			unregister(workflowHandler);
		}
	}

	public static void unregister(WorkflowHandler<?> workflowHandler) {
		_instance._unregister(workflowHandler);
	}

	public static <T> T updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		String className = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		WorkflowHandler<T> workflowHandler = getWorkflowHandler(className);

		if (workflowHandler != null) {
			return workflowHandler.updateStatus(status, workflowContext);
		}

		return null;
	}

	private WorkflowHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
			new WorkflowHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private List<WorkflowHandler<?>> _getScopeableWorkflowHandlers() {
		return ListUtil.fromMapValues(_scopeableWorkflowHandlerMap);
	}

	private WorkflowHandler<?> _getWorkflowHandler(String className) {
		return _workflowHandlerMap.get(className);
	}

	private List<WorkflowHandler<?>> _getWorkflowHandlers() {
		return ListUtil.fromMapValues(_workflowHandlerMap);
	}

	private boolean _hasWorkflowInstanceInProgress(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink =
			WorkflowInstanceLinkLocalServiceUtil.fetchWorkflowInstanceLink(
				companyId, groupId, className, classPK);

		if (workflowInstanceLink == null) {
			return false;
		}

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		if (!workflowInstance.isComplete()) {
			return true;
		}

		return false;
	}

	private void _register(WorkflowHandler<?> workflowHandler) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<WorkflowHandler<?>> serviceRegistration =
			registry.registerService(
				(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
				workflowHandler);

		_serviceRegistrations.put(workflowHandler, serviceRegistration);
	}

	private void _unregister(WorkflowHandler<?> workflowHandler) {
		ServiceRegistration<WorkflowHandler<?>> serviceRegistration =
			_serviceRegistrations.remove(workflowHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowHandlerRegistryUtil.class);

	private static final WorkflowHandlerRegistryUtil _instance =
		new WorkflowHandlerRegistryUtil();

	private final Map<String, WorkflowHandler<?>> _scopeableWorkflowHandlerMap =
		new ConcurrentSkipListMap<String, WorkflowHandler<?>>();
	private final ServiceRegistrationMap<WorkflowHandler<?>>
		_serviceRegistrations =
			new ServiceRegistrationMap<WorkflowHandler<?>>();
	private final ServiceTracker<WorkflowHandler<?>, WorkflowHandler<?>>
		_serviceTracker;
	private final Map<String, WorkflowHandler<?>> _workflowHandlerMap =
		new TreeMap<String, WorkflowHandler<?>>();

	private class WorkflowHandlerServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer <WorkflowHandler<?>, WorkflowHandler<?>> {

		@Override
		public WorkflowHandler<?> addingService(
			ServiceReference<WorkflowHandler<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			WorkflowHandler<?> workflowHandler = registry.getService(
				serviceReference);

			_workflowHandlerMap.put(
				workflowHandler.getClassName(), workflowHandler);

			if (workflowHandler.isScopeable()) {
				_scopeableWorkflowHandlerMap.put(
					workflowHandler.getClassName(), workflowHandler);
			}

			return workflowHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<WorkflowHandler<?>> serviceReference,
			WorkflowHandler<?> workflowHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<WorkflowHandler<?>> serviceReference,
			WorkflowHandler<?> workflowHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_workflowHandlerMap.remove(workflowHandler.getClassName());

			if (workflowHandler.isScopeable()) {
				_scopeableWorkflowHandlerMap.remove(
					workflowHandler.getClassName());
			}
		}

	}

}