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

package com.liferay.portal.kernel.portlet.bundle.resourcebundletracker;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestPortlet.PORTLET_NAME, "language.id=es_ES",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = ResourceBundle.class
)
public class TestResourceBundle extends ResourceBundle {

	public TestResourceBundle() {
		_map = new HashMap<>();

		_map.put("this", "esto");
		_map.put("is", "es");
		_map.put("a", "un");
		_map.put("test", "prueba");
		_map.put("resourcebundle", "paquete de recursos");
	}

	@Override
	public Enumeration<String> getKeys() {
		Set<String> keys = _map.keySet();

		return Collections.enumeration(keys);
	}

	@Override
	protected Object handleGetObject(String key) {
		if (key == null) {
			throw new IllegalArgumentException(
				"The key does not exist in this resource bundle");
		}

		return _map.get(key);
	}

	private final Map<String, String> _map;

}