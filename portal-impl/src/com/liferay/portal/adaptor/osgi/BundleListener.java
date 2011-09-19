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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

/**
 * @author Raymond Augé
 */
public class BundleListener extends BaseListener
	implements org.osgi.framework.BundleListener {

	public BundleListener(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void bundleChanged(BundleEvent bundleEvent) {
		int type = bundleEvent.getType();

		try {
			if (type == BundleEvent.INSTALLED) {
				doInstalled(bundleEvent);
			}
			else if (type == BundleEvent.LAZY_ACTIVATION) {
				doLazyActivation(bundleEvent);
			}
			else if (type == BundleEvent.RESOLVED) {
				doResolved(bundleEvent);
			}
			else if (type == BundleEvent.STARTED) {
				doStarted(bundleEvent);
			}
			else if (type == BundleEvent.STARTING) {
				doStarting(bundleEvent);
			}
			else if (type == BundleEvent.STOPPED) {
				doStopped(bundleEvent);
			}
			else if (type == BundleEvent.STOPPING) {
				doStopped(bundleEvent);
			}
			else if (type == BundleEvent.UNINSTALLED) {
				doUninstalled(bundleEvent);
			}
			else if (type == BundleEvent.UNRESOLVED) {
				doUnresolved(bundleEvent);
			}
			else if (type == BundleEvent.UPDATED) {
				doUpdated(bundleEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void doInstalled(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[INSTALLED]", bundle.getSymbolicName()));
	}

	protected void doLazyActivation(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[LAZY_ACTIVATION]", bundle.getSymbolicName()));
	}

	protected void doResolved(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[RESOLVED]", bundle.getSymbolicName()));
	}

	protected void doStarted(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[STARTED]", bundle.getSymbolicName()));
	}

	protected void doStarting(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[STARTING]", bundle.getSymbolicName()));
	}

	protected void doStopped(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[STOPPED]", bundle.getSymbolicName()));
	}

	protected void doUninstalled(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[UNINSTALLED]", bundle.getSymbolicName()));
	}

	protected void doUnresolved(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[UNRESOLVED]", bundle.getSymbolicName()));
	}

	protected void doUpdated(BundleEvent bundleEvent) throws Exception {
		Bundle bundle = bundleEvent.getBundle();

		_log.info(stateMessage("[UPDATED]", bundle.getSymbolicName()));
	}

	private static Log _log = LogFactoryUtil.getLog(BundleListener.class);

	private BundleContext _bundleContext;

}