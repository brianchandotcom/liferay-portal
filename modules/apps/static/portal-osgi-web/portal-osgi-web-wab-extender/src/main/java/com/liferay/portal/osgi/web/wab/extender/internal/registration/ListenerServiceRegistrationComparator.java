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

package com.liferay.portal.osgi.web.wab.extender.internal.registration;

import java.util.Comparator;

import javax.servlet.ServletContextListener;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Juan González
 */
public class ListenerServiceRegistrationComparator
	implements Comparator<ServiceRegistration<?>> {

	@Override
	public int compare(
		ServiceRegistration<?> serviceRegistration1,
		ServiceRegistration<?> serviceRegistration2) {

		Integer servletContextListenerCount1 = 0;

		ServiceReference<?> serviceReference1 =
			serviceRegistration1.getReference();

		String[] objectClassNames1 = (String[])serviceReference1.getProperty(
			Constants.OBJECTCLASS);

		for (String objectClassName : objectClassNames1) {
			if (objectClassName.equals(
					ServletContextListener.class.getName())) {

				servletContextListenerCount1++;
			}
		}

		Integer servletContextListenerCount2 = 0;

		ServiceReference<?> serviceReference2 =
			serviceRegistration2.getReference();

		String[] objectClassNames2 = (String[])serviceReference2.getProperty(
			Constants.OBJECTCLASS);

		for (String objectClassName : objectClassNames2) {
			if (objectClassName.equals(
					ServletContextListener.class.getName())) {

				servletContextListenerCount2++;
			}
		}

		if (servletContextListenerCount1.equals(servletContextListenerCount2)) {
			servletContextListenerCount2++;
		}

		return servletContextListenerCount1.compareTo(
			servletContextListenerCount2);
	}

}