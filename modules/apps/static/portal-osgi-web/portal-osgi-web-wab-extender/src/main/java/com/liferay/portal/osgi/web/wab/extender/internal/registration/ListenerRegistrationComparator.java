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
 * @author Juan Gonzalez
 */
public class ListenerRegistrationComparator
	implements Comparator<ServiceRegistration<?>> {

	@Override
	public int compare(
		ServiceRegistration<?> serviceRegistration1,
		ServiceRegistration<?> serviceRegistration2) {

		ServiceReference<?> serviceReference1 =
			serviceRegistration1.getReference();
		ServiceReference<?> serviceReference2 =
			serviceRegistration2.getReference();

		String[] objectClass1 = (String[])serviceReference1.getProperty(
			Constants.OBJECTCLASS);

		String[] objectClass2 = (String[])serviceReference2.getProperty(
			Constants.OBJECTCLASS);

		Integer servletContextListener1 = 0;

		for (String objectClass : objectClass1) {
			if (objectClass.equals(ServletContextListener.class.getName())) {
				servletContextListener1++;
			}
		}

		Integer servletContextListener2 = 0;

		for (String objectClass : objectClass2) {
			if (objectClass.equals(ServletContextListener.class.getName())) {
				servletContextListener2++;
			}
		}

		if (servletContextListener1.equals(servletContextListener2)) {
			servletContextListener2++;
		}

		return servletContextListener1.compareTo(servletContextListener2);
	}

}