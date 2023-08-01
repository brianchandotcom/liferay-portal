/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.lang.reflect.Method;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Preston Crary
 */
public class SystemCheckerUtil {

	public static void runSystemCheckers(Log log) {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		try {
			ServiceReference<?>[] serviceReferences =
				bundleContext.getAllServiceReferences(
					"com.liferay.portal.osgi.debug.SystemChecker", null);

			if (serviceReferences == null) {
				if (log.isWarnEnabled()) {
					log.warn("No system checkers available");
				}

				return;
			}

			for (ServiceReference<?> serviceReference : serviceReferences) {
				Object systemChecker = bundleContext.getService(
					serviceReference);

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

				bundleContext.ungetService(serviceReference);
			}
		}
		catch (Exception exception) {
			log.error(exception, exception);
		}
	}

	private SystemCheckerUtil() {
	}

}