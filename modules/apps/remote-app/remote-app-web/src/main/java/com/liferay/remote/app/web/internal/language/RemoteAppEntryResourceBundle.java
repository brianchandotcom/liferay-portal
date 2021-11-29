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

package com.liferay.remote.app.web.internal.language;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryResourceBundle extends ResourceBundle {

	public RemoteAppEntryResourceBundle(
		Locale locale, String portletId, RemoteAppEntry remoteAppEntry) {

		_map = HashMapBuilder.put(
			"javax.portlet.title." + portletId, remoteAppEntry.getName(locale)
		).build();
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(_map.keySet());
	}

	@Override
	protected Object handleGetObject(String key) {
		return _map.get(key);
	}

	private final Map<String, String> _map;

}