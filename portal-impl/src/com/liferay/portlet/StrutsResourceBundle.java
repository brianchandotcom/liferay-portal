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

import com.liferay.portal.kernel.util.EnumerationUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageResources;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
		if (_keys.contains(key)) {
			key = key.concat(StringPool.PERIOD).concat(_portletName);

			return parent.containsKey(key);
		}

		return parent.containsKey(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		Enumeration<String> enumeration = Collections.enumeration(_keys);

		return EnumerationUtil.compose(enumeration, parent.getKeys());
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
			key = key.concat(StringPool.PERIOD).concat(_portletName);

			if (parent.containsKey(key)) {
				try {
					return parent.getObject(key);
				}
				catch (MissingResourceException mre) {
					return null;
				}
			}
		}

		return null;
	}

	private static List<String> _keys = Arrays.asList(
		new String[] {
			JavaConstants.JAVAX_PORTLET_DESCRIPTION,
			JavaConstants.JAVAX_PORTLET_KEYWORDS,
			JavaConstants.JAVAX_PORTLET_LONG_TITLE,
			JavaConstants.JAVAX_PORTLET_SHORT_TITLE,
			JavaConstants.JAVAX_PORTLET_TITLE
		});

	private Locale _locale;
	private String _portletName;

}