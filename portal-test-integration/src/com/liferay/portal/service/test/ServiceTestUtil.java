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

package com.liferay.portal.service.test;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PortalInstances;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Alexander Chow
 * @author Manuel de la Peña
 */
public class ServiceTestUtil {

	public static final int RETRY_COUNT = 10;

	public static final int THREAD_COUNT = 10;

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static void addResourcePermission(
			Role role, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		RoleTestUtil.addResourcePermission(
			role, resourceName, scope, primKey, actionId);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static void addResourcePermission(
			String roleName, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		RoleTestUtil.addResourcePermission(
			roleName, resourceName, scope, primKey, actionId);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static Role addRole(String roleName, int roleType) throws Exception {
		return RoleTestUtil.addRole(roleName, roleType);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static Role addRole(
			String roleName, int roleType, String resourceName, int scope,
			String primKey, String actionId)
		throws Exception {

		return RoleTestUtil.addRole(
			roleName, roleType, resourceName, scope, primKey, actionId);
	}

	public static void initMainServletServices() {

		// Upgrade

		try {
			DBUpgrader.upgrade();
		}
		catch (Throwable t) {
			_log.error(t, t);
		}

		// Messaging

		MessageBusUtil messageBusUtil = new MessageBusUtil();

		messageBusUtil.setSynchronousMessageSenderMode(
			SynchronousMessageSender.Mode.DEFAULT);

		// Scheduler

		ServiceDependencyManager schedulerServiceDependencyManager =
			new ServiceDependencyManager();

		schedulerServiceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					try {
						SchedulerEngineHelperUtil.start();
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

				@Override
				public void destroy() {
				}

			});

		final Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=com.liferay.portal.scheduler.quartz.internal." +
				"QuartzSchemaManager)");

		schedulerServiceDependencyManager.registerDependencies(
			new Class[] {SchedulerEngineHelper.class}, new Filter[] {filter});

		// Verify

		try {
			DBUpgrader.verify();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void initPermissions() {
		try {
			PortalInstances.addCompanyId(TestPropsValues.getCompanyId());

			setUser(TestPropsValues.getUser());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void initServices() {

		// Thread locals

		_setThreadLocals();

		// Search engine

		try {
			SearchEngineHelperUtil.initialize(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void initStaticServices() {

		// Indexers

		PortalRegisterTestUtil.registerIndexers();

		// Messaging

		if (TestPropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
			ServiceDependencyManager serviceDependencyManager =
				new ServiceDependencyManager();

			Filter audioProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR);
			Filter imageProcessFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR);
			Filter pdfProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			Filter rawMetaDataProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			Filter videoProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR);

			serviceDependencyManager.registerDependencies(
				audioProcessorFilter, imageProcessFilter, pdfProcessorFilter,
				rawMetaDataProcessorFilter, videoProcessorFilter);

			serviceDependencyManager.waitForDependencies();

			_replaceWithSynchronousDestination(
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR);
			_replaceWithSynchronousDestination(
				DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR);
			_replaceWithSynchronousDestination(
				DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			_replaceWithSynchronousDestination(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			_replaceWithSynchronousDestination(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR);
		}

		// Class names

		_checkClassNames();

		// Resource actions

		try {
			_checkResourceActions();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Workflow

		PortalRegisterTestUtil.registerWorkflowHandlers();

		// Company

		if (!ArrayUtil.contains(
				PortalInstances.getWebIds(), TestPropsValues.COMPANY_WEB_ID)) {

			try {
				CompanyLocalServiceUtil.checkCompany(
					TestPropsValues.COMPANY_WEB_ID);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public static Date newDate() throws Exception {
		return new Date();
	}

	public static Date newDate(int month, int day, int year) throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, year);

		return calendar.getTime();
	}

	public static void setUser(User user) throws Exception {
		if (user == null) {
			return;
		}

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	private static void _checkClassNames() {
		PortalUtil.getClassNameId(LiferayRepository.class.getName());
	}

	private static void _checkResourceActions() throws Exception {
		for (int i = 0; i < 200; i++) {
			String portletId = String.valueOf(i);

			Portlet portlet = new PortletImpl();

			portlet.setPortletId(portletId);
			portlet.setPortletModes(new HashMap<String, Set<String>>());

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portletId);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portletId, portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(portletId);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	private static Filter _registerDestinationFilter(String destinationName) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getFilter(
			"(&(destination.name=" + destinationName + ")(objectClass=" +
				Destination.class.getName() + "))");
	}

	private static void _replaceWithSynchronousDestination(String name) {
		BaseDestination baseDestination = new SynchronousDestination();

		baseDestination.setName(name);

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination oldDestination = messageBus.getDestination(name);

		messageBus.replace(baseDestination, false);

		ThreadPoolExecutor threadPoolExecutor =
			PortalExecutorManagerUtil.getPortalExecutor(
				oldDestination.getName(), false);

		if (threadPoolExecutor == null) {
			return;
		}

		threadPoolExecutor.shutdown();

		try {
			if (!threadPoolExecutor.awaitTermination(
					TestPropsValues.CI_TEST_TIMEOUT_TIME,
					TimeUnit.MILLISECONDS)) {

				throw new IllegalStateException(
					"Destination " + oldDestination.getName() +
						" shutdown waiting timeout!");
			}
		}
		catch (InterruptedException ie) {
			ReflectionUtil.throwException(ie);
		}
	}

	private static void _setThreadLocals() {
		LocaleThreadLocal.setThemeDisplayLocale(new Locale("en", "US"));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setPathMain("path");
		serviceContext.setPortalURL("http://tests:8080");

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceTestUtil.class);

}