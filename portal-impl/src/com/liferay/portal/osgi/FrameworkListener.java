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

package com.liferay.portal.osgi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.FrameworkEvent;

/**
 * @author Raymond Augé
 */
public class FrameworkListener extends BaseListener
	implements org.osgi.framework.FrameworkListener {

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

		_log.error(
			getLogMessage("[ERROR]", frameworkEvent.getSource()),
			frameworkEvent.getThrowable());
	}

	protected void frameworkEventInfo(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[INFO]", frameworkEvent.getSource()));
	}

	protected void frameworkEventPackagesRefreshed(
			FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(
			getLogMessage("[PACKAGES_REFRESHED]", frameworkEvent.getSource()));
	}

	protected void frameworkEventStarted(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[STARTED]", frameworkEvent.getSource()));
	}

	protected void frameworkEventStartLevelChanged(
			FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(
			getLogMessage("[STARTLEVEL_CHANGED]", frameworkEvent.getSource()));
	}

	protected void frameworkEventStopped(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[STOPPED]", frameworkEvent.getSource()));
	}

	protected void frameworkEventStoppedBootClasspathModified(
			FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(
			getLogMessage(
				"[STOPPED_BOOTCLASSPATH_MODIFIED]",
				frameworkEvent.getSource()));
	}

	protected void frameworkEventStoppedUpdate(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(
			getLogMessage("[STOPPED_UPDATE]", frameworkEvent.getSource()));
	}

	protected void frameworkEventWaitTimedout(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[WAIT_TIMEDOUT]", frameworkEvent.getSource()));
	}

	protected void frameworkEventWarning(FrameworkEvent frameworkEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[WARNING]", frameworkEvent.getSource()));
	}

	private static Log _log = LogFactoryUtil.getLog(FrameworkListener.class);

}