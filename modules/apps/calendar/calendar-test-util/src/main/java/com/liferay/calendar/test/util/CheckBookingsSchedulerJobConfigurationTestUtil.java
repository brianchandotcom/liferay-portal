/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Adam Brandizzi
 */
public class CheckBookingsSchedulerJobConfigurationTestUtil {

	public static void setUp() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			CheckBookingsSchedulerJobConfigurationTestUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?>[] serviceReferences =
			bundleContext.getAllServiceReferences(
				SchedulerJobConfiguration.class.getName(),
				StringBundler.concat(
					"(&(objectClass=",
					SchedulerJobConfiguration.class.getName(),
					")(component.name=com.liferay.calendar.web.internal.",
					"scheduler.CheckBookingsSchedulerJobConfiguration))"));

		_checkBookingsSchedulerJobConfiguration = bundleContext.getService(
			serviceReferences[0]);

		ReflectionTestUtil.setFieldValue(
			_checkBookingsSchedulerJobConfiguration,
			"_calendarBookingLocalService",
			ProxyUtil.newProxyInstance(
				CalendarBookingLocalService.class.getClassLoader(),
				new Class<?>[] {CalendarBookingLocalService.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						if (Objects.equals(
								method.getName(), "checkCalendarBookings")) {

							return null;
						}

						return method.invoke(
							CalendarBookingLocalServiceUtil.getService(), args);
					}

				}));
	}

	public static void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_checkBookingsSchedulerJobConfiguration,
			"_calendarBookingLocalService",
			CalendarBookingLocalServiceUtil.getService());
	}

	private static Object _checkBookingsSchedulerJobConfiguration;

}