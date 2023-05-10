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