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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * @author Raymond Augé
 */
public class BundleListener extends BaseListener
	implements org.osgi.framework.BundleListener {

	public void bundleChanged(BundleEvent bundleEvent) {
		try {
			int type = bundleEvent.getType();

			if (type == BundleEvent.INSTALLED) {
				bundleEventInstalled(bundleEvent);
			}
			else if (type == BundleEvent.LAZY_ACTIVATION) {
				bundleEventLazyActivation(bundleEvent);
			}
			else if (type == BundleEvent.RESOLVED) {
				bundleEventResolved(bundleEvent);
			}
			else if (type == BundleEvent.STARTED) {
				bundleEventStarted(bundleEvent);
			}
			else if (type == BundleEvent.STARTING) {
				bundleEventStarting(bundleEvent);
			}
			else if (type == BundleEvent.STOPPED) {
				bundleEventStopped(bundleEvent);
			}
			else if (type == BundleEvent.STOPPING) {
				bundleEventStopped(bundleEvent);
			}
			else if (type == BundleEvent.UNINSTALLED) {
				bundleEventUninstalled(bundleEvent);
			}
			else if (type == BundleEvent.UNRESOLVED) {
				bundleEventUnresolved(bundleEvent);
			}
			else if (type == BundleEvent.UPDATED) {
				bundleEventUpdated(bundleEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void bundleEventInstalled(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[INSTALLED]", bundleEvent));
	}

	protected void bundleEventLazyActivation(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[LAZY_ACTIVATION]", bundleEvent));
	}

	protected void bundleEventResolved(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[RESOLVED]", bundleEvent));
	}

	protected void bundleEventStarted(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[STARTED]", bundleEvent));
	}

	protected void bundleEventStarting(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[STARTING]", bundleEvent));
	}

	protected void bundleEventStopped(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[STOPPED]", bundleEvent));
	}

	protected void bundleEventUninstalled(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[UNINSTALLED]", bundleEvent));
	}

	protected void bundleEventUnresolved(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[UNRESOLVED]", bundleEvent));
	}

	protected void bundleEventUpdated(BundleEvent bundleEvent)
		throws Exception {

		if (!_log.isInfoEnabled()) {
			return;
		}

		_log.info(getLogMessage("[UPDATED]", bundleEvent));
	}

	protected String getLogMessage(String state, BundleEvent bundleEvent) {
		Bundle bundle = bundleEvent.getBundle();

		return getLogMessage(state, bundle.getSymbolicName());
	}

	private static Log _log = LogFactoryUtil.getLog(BundleListener.class);

}