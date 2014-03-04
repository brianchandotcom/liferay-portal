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
 *
 * ResourceBundle that looks for the keys in the resource bundles passed to it
 * in the constructor. It queries the bundles in order until someone returns
 * a non null value.
 *
 * If none of the bundles return a non null value it throws a
 * MissingResourceException
 *
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

	/**
	 *
	 * @returns an Enumeration containing the keys of all the wrapped bundles.
	 */
	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(keySet());
	}

	/**
	 * Looks for #{key} in all the wrapped bundles, in order, until one of them
	 * returns a non null value for the key. If none of the bundles returns
	 * a non value for the key it throws a MissingResourceException.
	 *
	 * @param key the key to look for in all the wrapped bundles.
	 * @return the first non null value queried from the wrapped bundles in
	 * order.
	 * @throws java.util.MissingResourceException when none of the wrapped
	 * bundles has a non null value for the key.
	 *
	 */
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