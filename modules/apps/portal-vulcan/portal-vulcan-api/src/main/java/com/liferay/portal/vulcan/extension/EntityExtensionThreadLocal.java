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
		_extendedProperties.remove();
	}

	public static Map<String, Serializable> getExtendedProperties() {
		return _extendedProperties.get();
	}

	public static void setExtendedProperties(
		Map<String, Serializable> extendedProperties) {

		_extendedProperties.set(extendedProperties);
	}

	private static final ThreadLocal<Map<String, Serializable>>
		_extendedProperties = new CentralizedThreadLocal<>(
			EntityExtensionThreadLocal.class + "._extendedProperties");

}