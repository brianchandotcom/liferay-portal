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

package com.liferay.portal.security.service.access.quota.metric.impl;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.quota.SAQMetricProvider;

import java.lang.reflect.Method;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(service = SAQMetricProvider.class)
public class ServiceSAQMetricProvider implements SAQMetricProvider {

	@Override
	public String getMetricName() {
		return "service";
	}

	@Override
	public String getMetricValue(
		AccessControlContext accessControlContext, Method method) {

		Class<?> declaringClass = method.getDeclaringClass();

		return declaringClass.getName() + "#" + method.getName();
	}

	@Override
	public boolean matches(String metricValue, String metricFilter) {
		String[] parts = metricValue.split("#");

		if (parts.length < 2) {
			return false;
		}

		return matches(parts[0], parts[1], metricFilter);
	}

	protected boolean matches(
		String className, String methodName, String serviceSignaturePattern) {

		String allowedClassName = null;
		String allowedMethodName = null;

		int index = serviceSignaturePattern.indexOf(CharPool.POUND);

		if (index > -1) {
			allowedClassName = serviceSignaturePattern.substring(0, index);
			allowedMethodName = serviceSignaturePattern.substring(index + 1);
		}
		else {
			allowedClassName = serviceSignaturePattern;
		}

		boolean wildcardMatchClass = false;

		if (Validator.isNotNull(allowedClassName) &&
			allowedClassName.endsWith(StringPool.STAR)) {

			allowedClassName = allowedClassName.substring(
				0, allowedClassName.length() - 1);
			wildcardMatchClass = true;
		}

		boolean wildcardMatchMethod = false;

		if (Validator.isNotNull(allowedMethodName) &&
			allowedMethodName.endsWith(StringPool.STAR)) {

			allowedMethodName = allowedMethodName.substring(
				0, allowedMethodName.length() - 1);
			wildcardMatchMethod = true;
		}

		if (Validator.isNotNull(allowedClassName) &&
			Validator.isNotNull(allowedMethodName)) {

			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedClassName)) {
			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedMethodName)) {
			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (wildcardMatchClass && Validator.isNull(allowedClassName)) {
			return true;
		}

		return false;
	}

}