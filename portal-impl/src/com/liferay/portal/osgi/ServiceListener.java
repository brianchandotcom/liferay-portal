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
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

/**
 * @author Raymond Augé
 */
public class ServiceListener
	extends BaseListener implements org.osgi.framework.ServiceListener {

	public void serviceChanged(ServiceEvent serviceEvent) {
		try {
			int type = serviceEvent.getType();

			if (type == ServiceEvent.MODIFIED) {
				serviceEventModified(serviceEvent);
			}
			else if (type == ServiceEvent.REGISTERED) {
				serviceEventRegistered(serviceEvent);
			}
			else if (type == ServiceEvent.UNREGISTERING) {
				serviceEventUnregistering(serviceEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getLogMessage(
		String state, ServiceReference<?> serviceReference) {

		String message = StringUtil.merge(
			(String[])serviceReference.getProperty(Constants.OBJECTCLASS));

		return getLogMessage(state, message);
	}

	protected void serviceEventModified(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[MODIFIED]", serviceReference));
		}
	}

	protected void serviceEventRegistered(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[REGISTERED]", serviceReference));
		}
	}

	protected void serviceEventUnregistering(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[UNREGISTERING]", serviceReference));
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceListener.class);

}