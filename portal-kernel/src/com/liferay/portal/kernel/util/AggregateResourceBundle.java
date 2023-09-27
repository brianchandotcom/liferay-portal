/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateResourceBundle extends ResourceBundle {

	public AggregateResourceBundle(ResourceBundle... resourceBundles) {
		_resourceBundles = resourceBundles;
	}

	@Override
	public boolean containsKey(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		for (ResourceBundle resourceBundle : _resourceBundles) {
			if (resourceBundle.containsKey(key)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(handleKeySet());
	}

	@Override
	public Locale getLocale() {
		for (ResourceBundle resourceBundle : _resourceBundles) {
			Locale locale = resourceBundle.getLocale();

			if (locale != null) {
				return locale;
			}
		}

		return super.getLocale();
	}

	@Override
	protected Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		for (ResourceBundle resourceBundle : _resourceBundles) {
			if (!resourceBundle.containsKey(key)) {
				continue;
			}

			try {
				return resourceBundle.getObject(key);
			}
			catch (MissingResourceException missingResourceException) {
			}
		}

		return null;
	}

	@Override
	protected Set<String> handleKeySet() {
		if (_keys == null) {
			Set<String> keys = new HashSet<>();

			for (ResourceBundle resourceBundle : _resourceBundles) {
				keys.addAll(resourceBundle.keySet());
			}

			_keys = keys;
		}

		return _keys;
	}

	private volatile Set<String> _keys;
	private final ResourceBundle[] _resourceBundles;

}