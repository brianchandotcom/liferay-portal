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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.ResourceBundleEnumeration;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class StrutsResourceBundle extends ResourceBundle {

	public StrutsResourceBundle(String portletName, Locale locale) {
		_portletName = portletName;
		_locale = locale;

		setParent(LanguageResources.getResourceBundle(locale));
	}

	@Override
	public boolean containsKey(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		if (_keys.contains(key)) {
			key = _buildKey(key);
		}

		return parent.containsKey(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		Set<String> keys = new HashSet<String>();

		for (String key : _keys) {
			if (parent.containsKey(_buildKey(key))) {
				keys.add(key);
			}
		}

		return new ResourceBundleEnumeration(keys, parent.getKeys());
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	protected Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		if (_keys.contains(key)) {
			key = _buildKey(key);
		}

		if (parent.containsKey(key)) {
			try {
				return parent.getObject(key);
			}
			catch (MissingResourceException mre) {
				return null;
			}
		}

		return null;
	}

	private String _buildKey(String key) {
		return key.concat(StringPool.PERIOD).concat(_portletName);
	}

	private static final Set<String> _keys = SetUtil.fromArray(
		new String[] {
			JavaConstants.JAVAX_PORTLET_DESCRIPTION,
			JavaConstants.JAVAX_PORTLET_KEYWORDS,
			JavaConstants.JAVAX_PORTLET_LONG_TITLE,
			JavaConstants.JAVAX_PORTLET_SHORT_TITLE,
			JavaConstants.JAVAX_PORTLET_TITLE
		});

	private final Locale _locale;
	private final String _portletName;

}