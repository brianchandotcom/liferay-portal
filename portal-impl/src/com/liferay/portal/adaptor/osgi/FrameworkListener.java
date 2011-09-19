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

package com.liferay.portal.adaptor.osgi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;

/**
 * @author Raymond Augé
 */
public class FrameworkListener extends BaseListener
	implements org.osgi.framework.FrameworkListener {

	public FrameworkListener(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void frameworkEvent(FrameworkEvent frameworkEvent) {
		int type = frameworkEvent.getType();

		try {
			if (type == FrameworkEvent.ERROR) {
				doError(frameworkEvent);
			}
			else if (type == FrameworkEvent.INFO) {
				doInfo(frameworkEvent);
			}
			else if (type == FrameworkEvent.PACKAGES_REFRESHED) {
				doPackagesRefreshed(frameworkEvent);
			}
			else if (type == FrameworkEvent.STARTED) {
				doStarted(frameworkEvent);
			}
			else if (type == FrameworkEvent.STARTLEVEL_CHANGED) {
				doStartLevelChanged(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED) {
				doStopped(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED) {
				doStoppedBootClasspathModified(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED_UPDATE) {
				doStoppedUpdate(frameworkEvent);
			}
			else if (type == FrameworkEvent.WAIT_TIMEDOUT) {
				doWaitTimedout(frameworkEvent);
			}
			else if (type == FrameworkEvent.WARNING) {
				doWarning(frameworkEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void doError(FrameworkEvent frameworkEvent) throws Exception {
		Object source = frameworkEvent.getSource();

		Throwable throwable = frameworkEvent.getThrowable();

		_log.error(
			stateMessage("[ERROR]", String.valueOf(source)), throwable);
	}

	protected void doInfo(FrameworkEvent frameworkEvent) throws Exception {
		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[INFO]", String.valueOf(source)));
	}

	protected void doPackagesRefreshed(FrameworkEvent frameworkEvent)
		throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[PACKAGES_REFRESHED]", String.valueOf(source)));
	}

	protected void doStarted(FrameworkEvent frameworkEvent) throws Exception {
		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[STARTED]", String.valueOf(source)));
	}

	protected void doStartLevelChanged(FrameworkEvent frameworkEvent)
		throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[STARTLEVEL_CHANGED]", String.valueOf(source)));
	}

	protected void doStopped(FrameworkEvent frameworkEvent) throws Exception {
		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[STOPPED]", String.valueOf(source)));
	}

	protected void doStoppedBootClasspathModified(FrameworkEvent frameworkEvent)
		throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(
			stateMessage(
				"[STOPPED_BOOTCLASSPATH_MODIFIED]", String.valueOf(source)));
	}

	protected void doStoppedUpdate(FrameworkEvent frameworkEvent)
		throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[STOPPED_UPDATE]", String.valueOf(source)));
	}

	protected void doWaitTimedout(FrameworkEvent frameworkEvent)
		throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[WAIT_TIMEDOUT]", String.valueOf(source)));
	}

	protected void doWarning(FrameworkEvent frameworkEvent) throws Exception {

		Object source = frameworkEvent.getSource();

		_log.info(stateMessage("[WARNING]", String.valueOf(source)));
	}

	private static Log _log = LogFactoryUtil.getLog(FrameworkListener.class);

	private BundleContext _bundleContext;

}