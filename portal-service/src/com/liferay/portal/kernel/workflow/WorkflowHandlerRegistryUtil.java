/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class WorkflowHandlerRegistryUtil {

	public static List<WorkflowHandler> getScopeableWorkflowHandlers() {
		return getWorkflowHandlerRegistry().getScopeableWorkflowHandlers();
	}

	public static WorkflowHandler getWorkflowHandler(String className) {
		return getWorkflowHandlerRegistry().getWorkflowHandler(className);
	}

	public static WorkflowHandlerRegistry getWorkflowHandlerRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			WorkflowHandlerRegistryUtil.class);

		return _workflowHandlerRegistry;
	}

	public static List<WorkflowHandler> getWorkflowHandlers() {
		return getWorkflowHandlerRegistry().getWorkflowHandlers();
	}

	public static void register(List<WorkflowHandler> workflowHandlers) {
		for (WorkflowHandler workflowHandler : workflowHandlers) {
			register(workflowHandler);
		}
	}

	public static void register(WorkflowHandler workflowHandler) {
		getWorkflowHandlerRegistry().register(workflowHandler);
	}

	public static void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, Object model, ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public static void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, Object model, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		if (serviceContext.getWorkflowAction() !=
				WorkflowConstants.ACTION_PUBLISH) {

			return;
		}

		WorkflowInstanceLink workflowInstanceLink =
			WorkflowInstanceLinkLocalServiceUtil.fetchWorkflowInstanceLink(
				companyId, groupId, className, classPK);

		if (workflowInstanceLink != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Workflow already started for class " + className +
						" with primary key " + classPK + " in group " +
							groupId);
			}

			return;
		}

		WorkflowHandler workflowHandler = getWorkflowHandler(className);

		if (workflowHandler == null) {
			throw new WorkflowException(
				"No workflow handler found for " + className);
		}

		WorkflowDefinitionLink workflowDefinitionLink = null;

		if (WorkflowThreadLocal.isEnabled() &&
			WorkflowEngineManagerUtil.isDeployed()) {

			try {
				workflowDefinitionLink =
					workflowHandler.getWorkflowDefinitionLink(
						companyId, groupId, classPK);
			}
			catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			}
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

		workflowHandler.updateStatus(status, workflowContext);

		if (workflowDefinitionLink != null) {
			workflowHandler.startWorkflowInstance(
				companyId, groupId, userId, classPK, model, workflowContext);
		}
	}

	public static void startWorkflowInstance(
			long companyId, long userId, String className, long classPK,
			Object model, ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public static void startWorkflowInstance(
			long companyId, long userId, String className, long classPK,
			Object model, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		startWorkflowInstance(
			companyId, WorkflowConstants.DEFAULT_GROUP_ID, userId, className,
			classPK, model, serviceContext, workflowContext);
	}

	public static void unregister(List<WorkflowHandler> workflowHandlers) {
		for (WorkflowHandler workflowHandler : workflowHandlers) {
			unregister(workflowHandler);
		}
	}

	public static void unregister(WorkflowHandler workflowHandler) {
		getWorkflowHandlerRegistry().unregister(workflowHandler);
	}

	public static Object updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		String className = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		WorkflowHandler workflowHandler = getWorkflowHandler(className);

		if (workflowHandler != null) {
			return workflowHandler.updateStatus(status, workflowContext);
		}

		return null;
	}

	public void setWorkflowHandlerRegistry(
		WorkflowHandlerRegistry workflowHandlerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_workflowHandlerRegistry = workflowHandlerRegistry;
	}

	private static Log _log = LogFactoryUtil.getLog(
		WorkflowHandlerRegistryUtil.class);

	private static WorkflowHandlerRegistry _workflowHandlerRegistry;

}