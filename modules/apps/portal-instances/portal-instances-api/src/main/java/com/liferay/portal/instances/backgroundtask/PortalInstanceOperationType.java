/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.backgroundtask;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luis Ortiz
 */
public enum PortalInstanceOperationType {

	ADD("add"), DELETE("delete");

	public static PortalInstanceOperationType parse(String value) {
		for (PortalInstanceOperationType portalInstanceOperationType :
				values()) {

			if (StringUtil.equals(
					value, portalInstanceOperationType.getValue())) {

				return portalInstanceOperationType;
			}
		}

		throw new IllegalArgumentException(
			"Unknown portal instances operation type \"" + value + "\"");
	}

	public String getValue() {
		return _value;
	}

	private PortalInstanceOperationType(String value) {
		_value = value;
	}

	private final String _value;

}