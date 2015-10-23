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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Brian Wing Shun Chan
 */
public class AggregateResourceBundle extends ResourceBundle {

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(handleKeySet());
	}

	public List<ResourceBundle> getResourceBundles() {
		return _resourceBundles;
	}

	@Override
	protected Object handleGetObject(String key) {
		for (int i = _resourceBundles.size() - 1; i >= 0; i--) {
			ResourceBundle resourceBundle = _resourceBundles.get(i);

			if (resourceBundle.containsKey(key)) {
				return resourceBundle.getObject(key);
			}
		}

		return null;
	}

	@Override
	protected Set<String> handleKeySet() {
		Set<String> keySet = new HashSet<>();

		for (int i = _resourceBundles.size() - 1; i >= 0; i--) {
			ResourceBundle resourceBundle = _resourceBundles.get(i);

			keySet.addAll(resourceBundle.keySet());
		}

		return keySet;
	}

	private final List<ResourceBundle> _resourceBundles = new ArrayList<>();

}