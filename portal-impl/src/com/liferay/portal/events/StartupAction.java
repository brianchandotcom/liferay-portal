/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.jericho.CachedLoggerProvider;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.JspFactorySwapper;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.plugin.PluginPackageIndexer;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(ids);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(String[] ids) throws Exception {

		// Print release information

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo());

		// Clear locks

		if (_log.isDebugEnabled()) {
			_log.debug("Clear locks");
		}

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			LockLocalServiceUtil.clear();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clear locks because Lock table does not exist");
			}
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}

		// Shutdown hook

		if (_log.isDebugEnabled()) {
			_log.debug("Add shutdown hook");
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(new Thread(new ShutdownHook()));

		// Security manager

		String portalSecurityManagerStrategy =
			PropsValues.PORTAL_SECURITY_MANAGER_STRATEGY;

		if (portalSecurityManagerStrategy.equals("smart")) {
			if (ServerDetector.isWebSphere()) {
				portalSecurityManagerStrategy = "none";
			}
			else {
				portalSecurityManagerStrategy = "default";
			}
		}

		if (portalSecurityManagerStrategy.equals("liferay")) {
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new PortalSecurityManager());
			}
		}
		else if (portalSecurityManagerStrategy.equals("none")) {
			System.setSecurityManager(null);
		}

		// Template manager

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize template manager");
		}

		TemplateManagerUtil.init();

		// Indexers

		IndexerRegistryUtil.register(new MBMessageIndexer());
		IndexerRegistryUtil.register(new PluginPackageIndexer());

		// Upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrade database");
		}

		enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			DBUpgrader.upgrade();
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}

		// Messaging

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize message bus");
		}

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

		// Cluster executor

		ClusterExecutorUtil.initialize();

		// Scheduler

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize scheduler engine lifecycle");
		}

		SchedulerEngineHelperUtil.initialize();

		// Verify

		if (_log.isDebugEnabled()) {
			_log.debug("Verify database");
		}

		DBUpgrader.verify();

		// Liferay JspFactory

		JspFactorySwapper.swap();

		// Jericho

		CachedLoggerProvider.install();
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}