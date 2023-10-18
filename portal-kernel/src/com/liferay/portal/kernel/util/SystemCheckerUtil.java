/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Preston Crary
 */
public class SystemCheckerUtil {

	public static void runSystemCheckers(
		Consumer<String> infoConsumer, Consumer<String> warnConsumer) {

		ServiceTracker<Object, Object> serviceTracker = new ServiceTracker<>(
			SystemBundleUtil.getBundleContext(),
			"com.liferay.portal.osgi.debug.SystemChecker", null);

		serviceTracker.open(true);

		Map<?, Object> trackedMap = serviceTracker.getTracked();

		Collection<Object> systemCheckers = trackedMap.values();

		infoConsumer.accept("Available checkers: " + systemCheckers);

		try {
			for (Object systemChecker : systemCheckers) {
				StringBundler sb = new StringBundler(5);

				sb.append("Running \"");

				Class<?> clazz = systemChecker.getClass();

				try {
					Method method = clazz.getMethod("getName");

					method.setAccessible(true);

					String name = (String)method.invoke(systemChecker);

					sb.append(name);

					sb.append("\". You can run this by itself with command \"");

					method = clazz.getMethod("getOSGiCommand");

					method.setAccessible(true);

					sb.append(method.invoke(systemChecker));

					sb.append("\" in gogo shell.");

					infoConsumer.accept(sb.toString());

					method = clazz.getMethod("check");

					method.setAccessible(true);

					String result = (String)method.invoke(systemChecker);

					if (Validator.isNull(result)) {
						infoConsumer.accept(
							name + " check result: No issues were found.");
					}
					else {
						warnConsumer.accept(name + " check result: " + result);
					}
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					_log.error(reflectiveOperationException);
				}
			}
		}
		finally {
			serviceTracker.close();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemCheckerUtil.class);

}