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

package com.liferay.portal.language;

import com.liferay.portal.kernel.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class LiferayResourceBundle extends ResourceBundle {

	public LiferayResourceBundle(InputStream inputStream, String charsetName)
		throws IOException {

		this(null, inputStream, charsetName);
	}

	public LiferayResourceBundle(
			ResourceBundle parentResourceBundle, InputStream inputStream,
			String charsetName)
		throws IOException {

		setParent(parentResourceBundle);

		_properties = PropertiesUtil.load(inputStream, charsetName);
	}

	public LiferayResourceBundle(String string) throws IOException {
		_properties = PropertiesUtil.load(string);
	}

	@Override
	public boolean containsKey(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		if (_properties.containsKey(key)) {
			return true;
		}

		if (parent != null) {
			return parent.containsKey(key);
		}

		return false;
	}

	@Override
	public Enumeration<String> getKeys() {
		Set<String> keys = _properties.stringPropertyNames();

		if (parent == null) {
			return Collections.enumeration(keys);
		}

		return new ResourceBundleEnumeration(keys, parent.getKeys());
	}

	@Override
	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		return _properties.get(key);
	}

	@Override
	protected Set<String> handleKeySet() {
		return _properties.stringPropertyNames();
	}

	private final Properties _properties;

}