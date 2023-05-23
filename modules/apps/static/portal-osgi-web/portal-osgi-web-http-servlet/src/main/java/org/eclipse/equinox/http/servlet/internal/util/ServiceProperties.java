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

package org.eclipse.equinox.http.servlet.internal.util;

import java.util.*;
import javax.servlet.ServletContext;
import org.osgi.framework.ServiceReference;

public class ServiceProperties {
	static public boolean parseBoolean(
		ServiceReference<?> serviceReference, String property) {

		Object value = serviceReference.getProperty(property);

		if (Boolean.class.isInstance(value)) {
			return ((Boolean)value).booleanValue();
		}
		if (String.class.isInstance(value)) {
			return Boolean.valueOf((String)value);
		}

		return false;
	}

	static public Map<String, String> parseInitParams(
		ServiceReference<?> serviceReference, String prefix, ServletContext parentContext) {

		Map<String, String> initParams = new HashMap<String, String>();

		if (parentContext != null) {
			// use the parent context init params;
			// but allow them to be overriden below by service properties
			for (Enumeration<String> initParamNames = parentContext.getInitParameterNames(); initParamNames.hasMoreElements();) {
				String key = initParamNames.nextElement();
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

	static public Map<String, String> parseInitParams(
		ServiceReference<?> serviceReference, String prefix) {
		return parseInitParams(serviceReference, prefix, null);
	}

	static public String parseName(Object property, Object object) {
		if (property == null) {
			return object.getClass().getName();
		}

		return String.valueOf(property);
	}

}
