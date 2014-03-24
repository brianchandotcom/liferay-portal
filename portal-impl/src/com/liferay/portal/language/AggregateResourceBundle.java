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

import com.liferay.portal.kernel.util.EnumerationUtil;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateResourceBundle extends ResourceBundle {

	public AggregateResourceBundle(ResourceBundle... resourceBundles) {
		_resourceBundles = resourceBundles;
	}

	@Override
	public Enumeration<String> getKeys() {
		Enumeration<String>[] enumerations =
			new Enumeration[_resourceBundles.length];

		for (int i = 0; i < _resourceBundles.length; i++) {
			enumerations[i] = _resourceBundles[i].getKeys();
		}

		return EnumerationUtil.<String>compose(enumerations);
	}

	@Override
	protected Object handleGetObject(String key) {
		for (ResourceBundle resourceBundle : _resourceBundles) {
			Object object = null;

			try {
				object = resourceBundle.getObject(key);
			}
			catch (MissingResourceException mre) {
				continue;
			}

			if (object != null) {
				return object;
			}
		}

		return null;
	}

	private ResourceBundle[] _resourceBundles;

}