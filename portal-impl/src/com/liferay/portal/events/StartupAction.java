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

package com.liferay.portal.events;

import com.liferay.portal.cache.ehcache.EhcacheStreamBootstrapCacheLoader;
import com.liferay.portal.jericho.CachedLoggerProvider;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.cache.PortalCacheDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.messaging.MessageDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCDatagramReceiveHandler;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.JspFactorySwapper;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.plugin.PluginPackageIndexer;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import org.apache.struts.taglib.tiles.ComponentConstants;

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

		// Portal resiliency

		DistributedRegistry.registerDistributed(
			ComponentConstants.COMPONENT_CONTEXT, Direction.DUPLEX,
			MatchType.POSTFIX);
		DistributedRegistry.registerDistributed(
			MimeResponse.MARKUP_HEAD_ELEMENT, Direction.DUPLEX,
			MatchType.EXACT);
		DistributedRegistry.registerDistributed(
			PortletRequest.LIFECYCLE_PHASE, Direction.DUPLEX, MatchType.EXACT);
		DistributedRegistry.registerDistributed(WebKeys.class);

		Intraband intraband = MPIHelperUtil.getIntraband();

		intraband.registerDatagramReceiveHandler(
			SystemDataType.MAILBOX.getValue(),
			new MailboxDatagramReceiveHandler());

		MessageBus messageBus = (MessageBus)PortalBeanLocatorUtil.locate(
			MessageBus.class.getName());

		intraband.registerDatagramReceiveHandler(
			SystemDataType.MESSAGE.getValue(),
			new MessageDatagramReceiveHandler(messageBus));

		intraband.registerDatagramReceiveHandler(
			SystemDataType.PORTAL_CACHE.getValue(),
			new PortalCacheDatagramReceiveHandler());
		intraband.registerDatagramReceiveHandler(
			SystemDataType.RPC.getValue(), new RPCDatagramReceiveHandler());

		// Clear locks

		if (_log.isDebugEnabled()) {
			_log.debug("Clear locks");
		}

		try {
			LockLocalServiceUtil.clear();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clear locks because Lock table does not exist");
			}
		}

		// Shutdown hook

		if (_log.isDebugEnabled()) {
			_log.debug("Add shutdown hook");
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(new Thread(new ShutdownHook()));

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

		DBUpgrader.upgrade();

		// Messaging

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize message bus");
		}

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

		// Cluster executor

		ClusterExecutorUtil.initialize();

		if (!SPIUtil.isSPI()) {
			ClusterMasterExecutorUtil.initialize();
		}

		// Ehache bootstrap

		EhcacheStreamBootstrapCacheLoader.start();

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

		// Background tasks

		if (!ClusterMasterExecutorUtil.isEnabled()) {
			BackgroundTaskLocalServiceUtil.cleanUpBackgroundTasks();
		}

		// Jericho

		CachedLoggerProvider.install();
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}