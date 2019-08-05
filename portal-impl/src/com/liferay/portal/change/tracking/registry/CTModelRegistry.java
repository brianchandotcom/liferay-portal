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

package com.liferay.portal.change.tracking.registry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class CTModelRegistry {

	public static CTModelRegistration getCTModelRegistration(String tableName) {
		return _ctModelRegistrations.get(tableName);
	}

	public static Set<String> getTableNames() {
		return _ctModelRegistrations.keySet();
	}

	public static void registerCTModel(
		String tableName, CTModelRegistration ctModelRegistration) {

		_ctModelRegistrations.put(tableName, ctModelRegistration);
	}

	public static void unregisterCTModel(String tableName) {
		_ctModelRegistrations.remove(tableName);
	}

	private CTModelRegistry() {
	}

	private static final Map<String, CTModelRegistration>
		_ctModelRegistrations = new ConcurrentHashMap<>();

}