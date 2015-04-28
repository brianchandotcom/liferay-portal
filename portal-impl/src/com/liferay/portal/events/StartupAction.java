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

package com.liferay.portal.events;

import com.liferay.portal.cache.bootstrap.ClusterLinkBootstrapLoaderHelperUtil;
import com.liferay.portal.fabric.server.FabricServerUtil;
import com.liferay.portal.jericho.CachedLoggerProvider;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.messaging.MessageDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxyDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCDatagramReceiveHandler;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.plugin.PluginPackageIndexer;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.taglib.servlet.JspFactorySwapper;

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

		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<MessageBus, MessageBus> messageBusServiceTracker =
			registry.trackServices(
				MessageBus.class, new MessageBusServiceTrackerCustomizer());

		messageBusServiceTracker.open();

		// Shutdown hook

		if (_log.isDebugEnabled()) {
			_log.debug("Add shutdown hook");
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(new Thread(new ShutdownHook()));

		// Indexers

		IndexerRegistryUtil.register(new MBMessageIndexer());
		IndexerRegistryUtil.register(new PluginPackageIndexer());

		// Upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrade database");
		}

		DBUpgrader.upgrade();

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

		// Ehache bootstrap

		ClusterLinkBootstrapLoaderHelperUtil.start();

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

		// Background tasks

		ServiceTracker<ClusterMasterExecutor, ClusterMasterExecutor>
			clusterMasterExecutorServiceTracker = registry.trackServices(
				ClusterMasterExecutor.class,
				new ClusterMasterExecutorServiceTrackerCustomizer());

		clusterMasterExecutorServiceTracker.open();

		// Liferay JspFactory

		JspFactorySwapper.swap();

		// Jericho

		CachedLoggerProvider.install();
	}

	protected void initializePortalResiliency(MessageBus messageBus) {
		try {
			DistributedRegistry.registerDistributed(
				ComponentConstants.COMPONENT_CONTEXT, Direction.DUPLEX,
				MatchType.POSTFIX);
			DistributedRegistry.registerDistributed(
				MimeResponse.MARKUP_HEAD_ELEMENT, Direction.DUPLEX,
				MatchType.EXACT);
			DistributedRegistry.registerDistributed(
				PortletRequest.LIFECYCLE_PHASE, Direction.DUPLEX,
				MatchType.EXACT);
			DistributedRegistry.registerDistributed(WebKeys.class);

			Intraband intraband = MPIHelperUtil.getIntraband();

			intraband.registerDatagramReceiveHandler(
				SystemDataType.MAILBOX.getValue(),
				new MailboxDatagramReceiveHandler());

			intraband.registerDatagramReceiveHandler(
				SystemDataType.MESSAGE.getValue(),
				new MessageDatagramReceiveHandler(messageBus));

			intraband.registerDatagramReceiveHandler(
				SystemDataType.PROXY.getValue(),
				new IntrabandProxyDatagramReceiveHandler());

			intraband.registerDatagramReceiveHandler(
				SystemDataType.RPC.getValue(), new RPCDatagramReceiveHandler());

			if (PropsValues.PORTAL_FABRIC_ENABLED) {
				FabricServerUtil.start();
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to initialize portal resiliency", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StartupAction.class);

	private class ClusterMasterExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ClusterMasterExecutor, ClusterMasterExecutor> {

		@Override
		public ClusterMasterExecutor addingService(
			ServiceReference<ClusterMasterExecutor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ClusterMasterExecutor clusterMasterExecutor = registry.getService(
				serviceReference);

			if (!clusterMasterExecutor.isEnabled()) {
				BackgroundTaskLocalServiceUtil.cleanUpBackgroundTasks();
			}

			return clusterMasterExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ClusterMasterExecutor> serviceReference,
			ClusterMasterExecutor clusterMasterExecutor) {
		}

		@Override
		public void removedService(
			ServiceReference<ClusterMasterExecutor> serviceReference,
			ClusterMasterExecutor clusterMasterExecutor) {
		}

	}

	private class MessageBusServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MessageBus, MessageBus> {

		@Override
		public MessageBus addingService(
			ServiceReference<MessageBus> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MessageBus messageBus = registry.getService(serviceReference);

			initializePortalResiliency(messageBus);

			return messageBus;
		}

		@Override
		public void modifiedService(
			ServiceReference<MessageBus> serviceReference,
			MessageBus messageBus) {
		}

		@Override
		public void removedService(
			ServiceReference<MessageBus> serviceReference,
			MessageBus messageBus) {
		}

	}

}