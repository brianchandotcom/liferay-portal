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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

/**
 * @author Raymond Augé
 */
public class ServiceListener implements org.osgi.framework.ServiceListener {

	public void serviceChanged(ServiceEvent serviceEvent) {
		try {
			ServiceReference<?> serviceReference =
				serviceEvent.getServiceReference();

			Bundle bundle = serviceReference.getBundle();

			Log log = LogFactoryUtil.getLog(bundle.getSymbolicName());

			if (!log.isInfoEnabled()) {
				return;
			}

			int type = serviceEvent.getType();

			if (type == ServiceEvent.MODIFIED) {
				log.info("[MODIFIED] " + serviceReference.toString());
			}
			else if (type == ServiceEvent.REGISTERED) {
				log.info("[REGISTERED] " + serviceReference.toString());
			}
			else if (type == ServiceEvent.UNREGISTERING) {
				log.info("[UNREGISTERING] " + serviceReference.toString());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceListener.class);

}