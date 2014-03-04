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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public class CompositeResourceBundle extends ResourceBundle {

	public CompositeResourceBundle(ResourceBundle ... delegateResourceBundles) {
		_delegateResourceBundles = delegateResourceBundles;
		_keySet = new HashSet<String>();

		for (ResourceBundle delegateResourceBundle : _delegateResourceBundles) {
			Set<String> delegateKeySet = delegateResourceBundle.keySet();

			_keySet.addAll(delegateKeySet);
		}
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(keySet());
	}

	@Override
	protected Object handleGetObject(String key) {
		for (ResourceBundle delegateResourceBundle : _delegateResourceBundles) {
			Object object = null;

			try {
				object = delegateResourceBundle.getObject(key);
			}
			catch (MissingResourceException e) {
				continue;
			}

			if (object != null) {
				return object;
			}
		}

		throw new MissingResourceException(
			"Could not find resource", CompositeResourceBundle.class.getName(),
			key);
	}

	@Override
	protected Set<String> handleKeySet() {
		return _keySet;
	}

	private ResourceBundle[] _delegateResourceBundles;
	private final HashSet<String> _keySet;

}