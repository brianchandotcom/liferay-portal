/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return _tableNameCTModelRegistrationMap.get(tableName);
	}

	public static Set<String> getTableNames() {
		return _tableNameCTModelRegistrationMap.keySet();
	}

	public static void registerCTModel(
		CTModelRegistration ctModelRegistration) {

		_tableNameCTModelRegistrationMap.put(
			ctModelRegistration.getTableName(), ctModelRegistration);
	}

	public static void unregisterCTModel(String tableName) {
		_tableNameCTModelRegistrationMap.remove(tableName);
	}

	private CTModelRegistry() {
	}

	private static final Map<String, CTModelRegistration>
		_tableNameCTModelRegistrationMap = new ConcurrentHashMap<>();

}