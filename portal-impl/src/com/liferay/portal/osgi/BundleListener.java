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
public class BundleListener implements org.osgi.framework.BundleListener {

	public void bundleChanged(BundleEvent bundleEvent) {
		try {
			int type = bundleEvent.getType();

			Bundle bundle = bundleEvent.getBundle();

			Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

			if (!log.isInfoEnabled()) {
				return;
			}

			if (type == BundleEvent.INSTALLED) {
				log.info("[INSTALLED]");
			}
			else if (type == BundleEvent.LAZY_ACTIVATION) {
				log.info("[LAZY_ACTIVATION]");
			}
			else if (type == BundleEvent.RESOLVED) {
				log.info("[RESOLVED]");
			}
			else if (type == BundleEvent.STARTED) {
				log.info("[STARTED]");
			}
			else if (type == BundleEvent.STARTING) {
				log.info("[STARTING]");
			}
			else if (type == BundleEvent.STOPPED) {
				log.info("[STOPPED]");
			}
			else if (type == BundleEvent.STOPPING) {
				log.info("[STOPPING]");
			}
			else if (type == BundleEvent.UNINSTALLED) {
				log.info("[UNINSTALLED]");
			}
			else if (type == BundleEvent.UNRESOLVED) {
				log.info("[UNRESOLVED]");
			}
			else if (type == BundleEvent.UPDATED) {
				log.info("[UPDATED]");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BundleListener.class);

}