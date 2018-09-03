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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.util.Collection;
import java.util.Hashtable;

/**
 * @author Neil Griffin
 */
public class PortletDictionary extends Hashtable<String, Object> {

	public void put(String name, String value, String defaultValue) {
		if (value != null) {
			put(name, value);
		}
		else {
			put(name, defaultValue);
		}
	}

	public void putIfNotEmpty(String name, Collection<?> collection) {
		if ((collection != null) && !collection.isEmpty()) {
			put(name, collection);
		}
	}

	public void putIfNotEmpty(String name, String value) {
		if ((value != null) && (value.length() > 0)) {
			put(name, value);
		}
	}

	public void putIfNotNull(String name, String value) {
		if (value != null) {
			put(name, value);
		}
	}

}