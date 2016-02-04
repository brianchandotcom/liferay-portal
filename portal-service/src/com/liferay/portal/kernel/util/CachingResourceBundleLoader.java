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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Carlos Sierra Andrés
 */
public class CachingResourceBundleLoader implements ResourceBundleLoader {

	public CachingResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public ResourceBundle loadResourceBundle(String languageId) {
		if (_resourceBundles.containsKey(languageId)) {
			return _resourceBundles.get(languageId);
		}
		else {
			synchronized (_resourceBundles) {
				if (_resourceBundles.containsKey(languageId)) {
					return _resourceBundles.get(languageId);
				}

				ResourceBundle resourceBundle;

				try {
					resourceBundle = _resourceBundleLoader.loadResourceBundle(
						languageId);
				}
				catch (Exception e) {
					resourceBundle = null;
				}

				_resourceBundles.put(languageId, resourceBundle);

				return resourceBundle;
			}
		}
	}

	private final ResourceBundleLoader _resourceBundleLoader;
	private final Map<String, ResourceBundle> _resourceBundles =
		new HashMap<>();

}