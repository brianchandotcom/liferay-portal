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

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateResourceBundleLoader implements ResourceBundleLoader {

	public AggregateResourceBundleLoader(
		ResourceBundleLoader... resourceBundleLoaders) {

		_resourceBundleLoaders = resourceBundleLoaders;
	}

	@Override
	public ResourceBundle loadResourceBundle(String languageId) {
		ArrayList<ResourceBundle> resourceBundles = new ArrayList<>();

		for (ResourceBundleLoader resourceBundleLoader :
				_resourceBundleLoaders) {

			try {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(languageId);

				if (resourceBundle != null) {
					resourceBundles.add(resourceBundle);
				}
			}
			catch (Exception e) {
			}
		}

		if (resourceBundles.isEmpty()) {
			throw new MissingResourceException(
				"ResourceBundleLoader " + this + " failed to load " +
					"ResourceBundle for " + languageId,
				"", languageId);
		}

		if (resourceBundles.size() == 1) {
			return resourceBundles.get(0);
		}

		return new AggregateResourceBundle(
			resourceBundles.toArray(new ResourceBundle[0]));
	}

	private final ResourceBundleLoader[] _resourceBundleLoaders;

}