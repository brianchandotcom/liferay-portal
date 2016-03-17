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

package com.liferay.portal.portlet.bridge.soy.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Bruno Basto
 */
public class SoyPortletControllersManager {

	public SoyPortletControllersManager(Bundle bundle) {
		_bundle = bundle;
		_controllersMap = new HashMap<>();
	}

	public String getController(String path) {
		final String controller;

		if (_hasController(path)) {
			controller = path.concat(".es");
		}
		else {
			controller = path.concat(".soy");
		}

		return controller;
	}

	private boolean _hasController(String path) {
		if (!_controllersMap.containsKey(path)) {
			String filePath = "/META-INF/resources/".concat(path).concat(
				".es.js");

			Boolean hasController = false;

			if (_bundle.getEntry(filePath) != null) {
				hasController = true;
			}

			_controllersMap.put(path, hasController);
		}

		return _controllersMap.get(path);
	}

	private final Bundle _bundle;
	private final Map<String, Boolean> _controllersMap;

}