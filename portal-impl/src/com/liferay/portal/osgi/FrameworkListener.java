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

package com.liferay.portal.osgi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkEvent;

/**
 * @author Raymond Augé
 */
public class FrameworkListener implements org.osgi.framework.FrameworkListener {

	public void frameworkEvent(FrameworkEvent frameworkEvent) {
		try {
			int type = frameworkEvent.getType();

			if (type == FrameworkEvent.ERROR) {
				frameworkEventError(frameworkEvent);
			}
			else if (type == FrameworkEvent.INFO) {
				frameworkEventInfo(frameworkEvent);
			}
			else if (type == FrameworkEvent.PACKAGES_REFRESHED) {
				frameworkEventPackagesRefreshed(frameworkEvent);
			}
			else if (type == FrameworkEvent.STARTED) {
				frameworkEventStarted(frameworkEvent);
			}
			else if (type == FrameworkEvent.STARTLEVEL_CHANGED) {
				frameworkEventStartLevelChanged(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED) {
				frameworkEventStopped(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED) {
				frameworkEventStoppedBootClasspathModified(frameworkEvent);
			}
			else if (type == FrameworkEvent.STOPPED_UPDATE) {
				frameworkEventStoppedUpdate(frameworkEvent);
			}
			else if (type == FrameworkEvent.WAIT_TIMEDOUT) {
				frameworkEventWaitTimedout(frameworkEvent);
			}
			else if (type == FrameworkEvent.WARNING) {
				frameworkEventWarning(frameworkEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void frameworkEventError(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isErrorEnabled()) {
			return;
		}

		log.error("[ERROR]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventInfo(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[INFO]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventPackagesRefreshed(
			FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[PACKAGES_REFRESHED]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventStarted(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[STARTED]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventStartLevelChanged(
			FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[STARTLEVEL_CHANGED]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventStopped(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[STOPPED]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventStoppedBootClasspathModified(
			FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info(
			"[STOPPED_BOOTCLASSPATH_MODIFIED]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventStoppedUpdate(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[STOPPED_UPDATE]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventWaitTimedout(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info("[WAIT_TIMEDOUT]", frameworkEvent.getThrowable());
	}

	protected void frameworkEventWarning(FrameworkEvent frameworkEvent)
		throws Exception {

		Bundle bundle = frameworkEvent.getBundle();

		Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

		if (!log.isWarnEnabled()) {
			return;
		}

		log.warn("[WARNING]", frameworkEvent.getThrowable());
	}

	private static Log _log = LogFactoryUtil.getLog(FrameworkListener.class);

}