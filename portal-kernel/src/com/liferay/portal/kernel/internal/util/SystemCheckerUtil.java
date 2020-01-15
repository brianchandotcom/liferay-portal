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

package com.liferay.portal.kernel.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.lang.reflect.Method;

/**
 * @author Preston Crary
 */
public class SystemCheckerUtil {

	public static void runSystemCheckers(Log log) {
		Registry registry = RegistryUtil.getRegistry();

		try {
			ServiceReference<?>[] serviceReferences =
				registry.getAllServiceReferences(
					"com.liferay.portal.osgi.debug.SystemChecker", null);

			if (serviceReferences == null) {
				if (log.isWarnEnabled()) {
					log.warn("No system checkers available");
				}

				return;
			}

			for (ServiceReference<?> serviceReference : serviceReferences) {
				Object systemChecker = registry.getService(serviceReference);

				StringBundler sb = new StringBundler(4);

				sb.append("Running \"");
				sb.append(systemChecker);
				sb.append("\" check result: ");

				Class<?> clazz = systemChecker.getClass();

				Method method = clazz.getMethod("check");

				Object result = method.invoke(systemChecker);

				if (Validator.isNull(result)) {
					sb.append("No issues were found.");

					if (log.isInfoEnabled()) {
						log.info(sb.toString());
					}
				}
				else if (log.isWarnEnabled()) {
					sb.append(result);

					log.warn(sb.toString());
				}

				registry.ungetService(serviceReference);
			}
		}
		catch (Exception exception) {
			log.error(exception, exception);
		}
	}

	private SystemCheckerUtil() {
	}

}