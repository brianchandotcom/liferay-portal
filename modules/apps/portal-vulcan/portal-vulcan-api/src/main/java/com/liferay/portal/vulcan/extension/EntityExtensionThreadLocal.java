/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.extension;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Javier de Arcos
 */
public class EntityExtensionThreadLocal {

	public static void clearExtendedProperties() {
		_extendedPropertiesThreadLocal.remove();
	}

	public static Map<String, Serializable> getExtendedProperties() {
		return _extendedPropertiesThreadLocal.get();
	}

	public static void setExtendedProperties(
		Map<String, Serializable> extendedProperties) {

		_extendedPropertiesThreadLocal.set(extendedProperties);
	}

	private static final ThreadLocal<Map<String, Serializable>>
		_extendedPropertiesThreadLocal = new CentralizedThreadLocal<>(
			EntityExtensionThreadLocal.class +
				"._extendedPropertiesThreadLocal");

}