/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.bookmarks.util.BookmarksIndexer;
import com.liferay.portlet.directory.workflow.UserWorkflowHandler;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.documentlibrary.workflow.DLFileEntryWorkflowHandler;
import com.liferay.portlet.journal.workflow.JournalArticleWorkflowHandler;
import com.liferay.portlet.messageboards.util.MBIndexer;
import com.liferay.portlet.messageboards.workflow.MBDiscussionWorkflowHandler;
import com.liferay.portlet.messageboards.workflow.MBMessageWorkflowHandler;
import com.liferay.portlet.usersadmin.util.UserIndexer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Alexander Chow
 */
public class ServiceTestUtil {

	public static void destroyServices() {
		LuceneHelperUtil.shutdown();

		FileUtil.deltree(PropsValues.LIFERAY_HOME + "/data");
	}

	public static void initPermissions() {
		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();

		PortalInstances.addCompanyId(TestPropsValues.COMPANY_ID);

		PrincipalThreadLocal.setName(TestPropsValues.USER_ID);

		try {
			User user = UserLocalServiceUtil.getUserById(
				TestPropsValues.USER_ID);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initServices() {
		InitUtil.initWithSpring();

		FileUtil.deltree(PropsValues.LIFERAY_HOME + "/data");

		// JCR

		try {
			JCRFactoryUtil.prepare();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Indexers

		IndexerRegistryUtil.register(new UserIndexer());
		IndexerRegistryUtil.register(new BookmarksIndexer());
		IndexerRegistryUtil.register(new DLIndexer());
		IndexerRegistryUtil.register(new MBIndexer());

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
			messageBus, messageSender, synchronousMessageSender);

		// Scheduler

		try {
			SchedulerEngineUtil.start();
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

		// Resource actions

		try {
			_checkResourceActions();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Workflow

		WorkflowHandlerRegistryUtil.register(new DLFileEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(
			new JournalArticleWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBDiscussionWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBMessageWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());

		// Company

		try {
			CompanyLocalServiceUtil.checkCompany(
				TestPropsValues.COMPANY_WEB_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _checkResourceActions() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		WebAppPool.put(
			TestPropsValues.COMPANY_ID,	WebKeys.PORTLET_CATEGORY,
			new PortletCategory());

		for (int i = 0; i < 200; i++) {
			String portletId = String.valueOf(i);

			Portlet portlet = new PortletImpl();

			portlet.setPortletId(portletId);
			portlet.setCompanyId(TestPropsValues.COMPANY_ID);
			portlet.setPortletModes(new HashMap<String, Set<String>>());

			PortletLocalServiceUtil.deployRemotePortlet(
				portlet, "category.test");

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

}