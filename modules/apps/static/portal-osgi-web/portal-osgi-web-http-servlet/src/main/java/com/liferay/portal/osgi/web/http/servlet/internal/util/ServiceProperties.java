/*******************************************************************************
 * Copyright (c) 2014, 2015 Liferay, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial
 *                    documentation
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceReference;

/**
 * @author Liferay, Inc.
 */
public class ServiceProperties {

	public static Map<String, String> parseInitParams(
		ServiceReference<?> serviceReference, String prefix) {

		return parseInitParams(serviceReference, prefix, null);
	}

	public static Map<String, String> parseInitParams(
		ServiceReference<?> serviceReference, String prefix,
		ServletContext parentContext) {

		Map<String, String> initParams = new HashMap<>();

		if (parentContext != null) {
			for (Enumeration<String> initParamNamesEnumeration =
					parentContext.getInitParameterNames();
				 initParamNamesEnumeration.hasMoreElements();) {

				String key = initParamNamesEnumeration.nextElement();

				initParams.put(key, parentContext.getInitParameter(key));
			}
		}

		for (String key : serviceReference.getPropertyKeys()) {
			if (key.startsWith(prefix)) {
				initParams.put(
					key.substring(prefix.length()),
					String.valueOf(serviceReference.getProperty(key)));
			}
		}

		return Collections.unmodifiableMap(initParams);
	}

	public static String parseName(Object property, Object object) {
		if (property == null) {
			Class<?> clazz = object.getClass();

			return clazz.getName();
		}

		return String.valueOf(property);
	}

}