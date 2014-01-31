/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ResourceBundleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
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

		_map = new HashMap<String, String>();

		Properties properties = PropertiesUtil.load(inputStream, charsetName);

		LanguageResources.fixValues(_map, properties);
	}

	public LiferayResourceBundle(String string) throws IOException {
		_map = new HashMap<String, String>();

		Properties properties = PropertiesUtil.load(string);

		LanguageResources.fixValues(_map, properties);
	}

	@Override
	public Enumeration<String> getKeys() {
		final Set<String> keys = _map.keySet();

		final Enumeration<String> parentKeys =
			(parent == null) ? null : parent.getKeys();

		final Iterator<String> itr = keys.iterator();

		return new Enumeration<String>() {
			String next = null;

			@Override
			public boolean hasMoreElements() {
				if (next == null) {
					if (itr.hasNext()) {
						next = itr.next();
					}
					else if (parentKeys != null) {
						while ((next == null) && parentKeys.hasMoreElements()) {
							next = parentKeys.nextElement();

							if (keys.contains(next)) {
								next = null;
							}
						}
					}
				}

				if (next != null) {
					return true;
				}
				else {
					return false;
				}
			}

			@Override
			public String nextElement() {
				if (hasMoreElements()) {
					String result = next;

					next = null;

					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}

		};
	}

	@Override
	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		String value = _map.get(key);

		if ((value == null) && ResourceBundleThreadLocal.isReplace()) {
			if (parent != null) {
				try {
					value = parent.getString(key);
				}
				catch (MissingResourceException mre) {
				}
			}

			if (value == null) {
				value = ResourceBundleUtil.NULL_VALUE;
			}
		}

		return value;
	}

	private Map<String, String> _map;

}