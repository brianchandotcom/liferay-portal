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

package com.liferay.portal.kernel.deploy;

import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-39277.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class RequiredPluginsUtil {

	public static synchronized void shutdown() {
		unschedule(true);
	}

	public static synchronized void startUpCheckRequiredPlugins() {
		schedule();
	}

	public static synchronized void undeployCheckRequiredPlugins() {

		// On undeployment, we do a synchronously check to bring back missing
		// required plugin asap. Restart the periodical scanning to prevent
		// failure deployment.

		unschedule(false);

		doCheckRequiredPlugins();

		schedule();
	}

	protected static void schedule() {
		_scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor(
				new NamedThreadFactory(
					RequiredPluginsUtil.class.getName(), Thread.NORM_PRIORITY,
					null));

		_scheduledExecutorService.scheduleWithFixedDelay(
			new Runnable() {

				@Override
				public void run() {
					doCheckRequiredPlugins();
				}
			}, _DELAYED_TIME, _DELAYED_TIME, TimeUnit.MILLISECONDS);
	}

	protected static void unschedule(boolean awaitTermination) {
		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdownNow();

			if (awaitTermination) {
				try {
					_scheduledExecutorService.awaitTermination(
						1, TimeUnit.MINUTES);
				}
				catch (InterruptedException ie) {
					_log.error(ie, ie);
				}
			}

			_scheduledExecutorService = null;
		}
	}

	protected synchronized static void doCheckRequiredPlugins() {
		List<String[]> levelsRequiredDeploymentContexts =
			DeployManagerUtil.getLevelsRequiredDeploymentContexts();
		List<String[]> levelsRequiredDeploymentWARFileNames =
			DeployManagerUtil.getLevelsRequiredDeploymentWARFileNames();

		boolean deployed = false;

		for (int i = 0; i < levelsRequiredDeploymentContexts.size(); i++) {
			String[] levelRequiredDeploymentContexts =
				levelsRequiredDeploymentContexts.get(i);
			String[] levelRequiredDeploymentWARFileNames =
				levelsRequiredDeploymentWARFileNames.get(i);

			for (int j = 0; j < levelRequiredDeploymentContexts.length; j++) {
				String levelRequiredDeploymentContext =
					levelRequiredDeploymentContexts[j];

				if (DeployManagerUtil.isDeployed(
						levelRequiredDeploymentContext)) {

					continue;
				}

				deployed = true;

				String levelRequiredDeploymentWARFileName =
					levelRequiredDeploymentWARFileNames[j];

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Automatically deploying the required plugin " +
							levelRequiredDeploymentWARFileName);
				}

				ClassLoader classLoader =
					PortalClassLoaderUtil.getClassLoader();

				InputStream inputStream = classLoader.getResourceAsStream(
					"com/liferay/portal/deploy/dependencies/plugins" +
						(i + 1) + "/" + levelRequiredDeploymentWARFileNames[j]);

				AutoDeployDir autoDeployDir = AutoDeployUtil.getDir(
					AutoDeployDir.DEFAULT_NAME);

				if (autoDeployDir == null) {

					// This should never happen except during shutdown

					if (_log.isDebugEnabled()) {
						_log.debug(
							"The autodeploy directory " +
								AutoDeployDir.DEFAULT_NAME + " is null");
					}

					continue;
				}

				try {
					StreamUtil.transfer(
						inputStream,
						new FileOutputStream(
							autoDeployDir.getDeployDir() + "/" +
								levelRequiredDeploymentWARFileNames[j]));
				}
				catch (IOException ioe) {
					_log.error("Unable to write file", ioe);
				}
			}
		}

		if (!deployed) {

			// On an unchanged scan, we know for sure all required plugins are
			// in place. Any further plugin removal must trigger the
			// undeployment process which will restart the periodical scanning.
			// So it is safe to stop scanning here to safe some cpu power.

			unschedule(false);
		}
	}

	private static final long _DELAYED_TIME = Time.MINUTE;

	private static Log _log = LogFactoryUtil.getLog(RequiredPluginsUtil.class);

	private static ScheduledExecutorService _scheduledExecutorService;

}