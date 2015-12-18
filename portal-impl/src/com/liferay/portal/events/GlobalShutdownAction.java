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

import com.liferay.portal.deploy.RequiredPluginsUtil;
import com.liferay.portal.fabric.server.FabricServerUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployDir;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployUtil;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.javadoc.JavadocManagerUtil;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.AuthPublicPathRegistry;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.zip.TrueZIPHelperUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.util.ThirdPartyThreadLocalRegistry;

import java.sql.Connection;
import java.sql.Statement;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Brian Wing Shun Chan
 */
public class GlobalShutdownAction extends SimpleAction {

	@Override
	public void run(String[] ids) {

		// Lower shutdown levels have dependences on higher levels, therefore
		// lower ones need to shutdown before higher ones. Components within the
		// same shutdown level should not depend on each other.

		shutdownLevel1();
		shutdownLevel2();
		shutdownLevel3();
		shutdownLevel4();
		shutdownLevel5();
		shutdownLevel6();
		shutdownLevel7();
	}

	protected ThreadGroup getThreadGroup() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup();

		for (int i = 0; i < 10; i++) {
			if (threadGroup.getParent() == null) {
				break;
			}
			else {
				threadGroup = threadGroup.getParent();
			}
		}

		return threadGroup;
	}

	protected Thread[] getThreads(ThreadGroup threadGroup) {
		Thread[] threads = new Thread[threadGroup.activeCount() * 2];

		threadGroup.enumerate(threads);

		return threads;
	}

	protected void shutdownLevel1() {

		// Authentication

		AuthPublicPathRegistry.unregister(PropsValues.AUTH_PUBLIC_PATHS);

		// Javadoc

		JavadocManagerUtil.unload(StringPool.BLANK);

		// OpenOffice

		DocumentConversionUtil.disconnect();

		// Plugins

		RequiredPluginsUtil.stopCheckingRequiredPlugins();
	}

	protected void shutdownLevel2() {

		// Auto deploy

		AutoDeployUtil.unregisterDir(AutoDeployDir.DEFAULT_NAME);

		// Hot deploy

		HotDeployUtil.unregisterListeners();

		// Sandbox deploy

		SandboxDeployUtil.unregisterDir(SandboxDeployDir.DEFAULT_NAME);
	}

	protected void shutdownLevel3() {

		// Messaging

		MessageBusUtil.shutdown(true);

		// Portal fabric

		if (PropsValues.PORTAL_FABRIC_ENABLED) {
			try {
				Future<?> future = FabricServerUtil.stop();

				future.get(
					PropsValues.PORTAL_FABRIC_SHUTDOWN_TIMEOUT,
					TimeUnit.MILLISECONDS);
			}
			catch (Exception e) {
				_log.error("Unable to stop fabric server", e);
			}
		}
	}

	protected void shutdownLevel4() {

		// Hypersonic

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.HYPERSONIC) {
			Connection connection = null;
			Statement statement = null;

			try {
				connection = DataAccess.getConnection();

				statement = connection.createStatement();

				statement.executeUpdate("SHUTDOWN");
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			finally {
				DataAccess.cleanUp(connection, statement);
			}
		}

		// Portal Resiliency

		MPIHelperUtil.shutdown();
	}

	protected void shutdownLevel5() {

		// Portal executors

		PortalExecutorManagerUtil.shutdown(true);

		// TrueZip

		TrueZIPHelperUtil.shutdown();
	}

	protected void shutdownLevel6() {

		// Reset log to default JDK 1.4 logger. This will allow WARs dependent
		// on the portal to still log events after the portal WAR has been
		// destroyed.

		try {
			LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());
		}
		catch (Exception e) {
		}

		// Thread local registry

		ThirdPartyThreadLocalRegistry.resetThreadLocals();
		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	protected void shutdownLevel7() {

		// Programmatically exit

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SHUTDOWN_PROGRAMMATICALLY_EXIT))) {

			Thread currentThread = Thread.currentThread();

			ThreadGroup threadGroup = getThreadGroup();

			Thread[] threads = getThreads(threadGroup);

			for (Thread thread : threads) {
				if ((thread == null) || (thread == currentThread)) {
					continue;
				}

				try {
					thread.interrupt();
				}
				catch (Exception e) {
				}
			}

			threadGroup.destroy();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GlobalShutdownAction.class);

}