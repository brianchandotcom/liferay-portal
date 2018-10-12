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

package com.liferay.talend.properties;

import com.liferay.talend.utils.StringUtils;

import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Zoltán Takács
 */
public abstract class BaseContextAwareStringProperty extends StringProperty {

	public BaseContextAwareStringProperty(String name) {
		super(name);
	}

	public String getHost() {
		if (_host == null) {
			throw new IllegalArgumentException("Host is not set");
		}

		return _host;
	}

	public void setHost(String host) {
		host = StringUtils.removeQuotes(host);
	}

	private String _host;

}