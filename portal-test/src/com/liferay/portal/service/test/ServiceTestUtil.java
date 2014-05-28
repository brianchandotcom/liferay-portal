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

import com.liferay.portal.events.SettingsFactoryStartupAction;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Alexander Chow
 * @author Manuel de la Peña
 */
public class ServiceTestUtil {

	public static final int THREAD_COUNT = 25;

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

	public static void destroyServices() {
		_deleteDirectories();
	}

	public static void initPermissions() {
		try {
			PortalInstances.addCompanyId(TestPropsValues.getCompanyId());

			setUser(TestPropsValues.getUser());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initServices() {

		// JCR

		try {
			JCRFactoryUtil.prepare();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Template manager

		try {
			TemplateManagerUtil.init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Indexers

		PortalRegisterTestUtil.registerIndexers();

		// Upgrade

		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Messaging

		MessageBus messageBus = (MessageBus)PortalBeanLocatorUtil.locate(
			MessageBus.class.getName());
		MessageSender messageSender =
			(MessageSender)PortalBeanLocatorUtil.locate(
				MessageSender.class.getName());
		SynchronousMessageSender synchronousMessageSender =
			(SynchronousMessageSender)PortalBeanLocatorUtil.locate(
				SynchronousMessageSender.class.getName());

		MessageBusUtil.init(
			DoPrivilegedUtil.wrap(messageBus),
			DoPrivilegedUtil.wrap(messageSender),
			DoPrivilegedUtil.wrap(synchronousMessageSender));

		if (TestPropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
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

		// Scheduler

		try {
			SchedulerEngineHelperUtil.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Verify

		try {
			DBUpgrader.verify();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Class names

		_checkClassNames();

		// Resource actions

		try {
			_checkResourceActions();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Settings

		SettingsFactoryStartupAction settingsFactoryStartupAction =
			new SettingsFactoryStartupAction();

		settingsFactoryStartupAction.run(
			new String[] {TestPropsValues.COMPANY_WEB_ID});

		// Trash

		PortalRegisterTestUtil.registerTrashHandlers();

		// Workflow

		PortalRegisterTestUtil.registerWorkflowHandlers();

		// Asset renderers

		PortalRegisterTestUtil.registerAssetRendererFactories();

		// Company

		try {
			CompanyLocalServiceUtil.checkCompany(
				TestPropsValues.COMPANY_WEB_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Directories

		_deleteDirectories();

		// Lucene

		try {
			FileUtil.mkdirs(
				PropsValues.LUCENE_DIR + TestPropsValues.getCompanyId());

			LuceneHelperUtil.startup(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			e.printStackTrace();
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

	private static void _deleteDirectories() {
		FileUtil.deltree(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

		FileUtil.deltree(
			PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT));

		try {
			FileUtil.deltree(
				PropsValues.LUCENE_DIR + TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _replaceWithSynchronousDestination(String name) {
		BaseDestination baseDestination = new SynchronousDestination();

		baseDestination.setName(name);

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		messageBus.replace(baseDestination);
	}

}