/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.notifications;

/**
 * @author Luis Ortiz
 */
public enum PortalInstancesOperationType {

	ADD("add"), COPY("copy"), DELETE("delete"), EXPORT("export"),
	IMPORT("import");

	public static PortalInstancesOperationType parse(String value) {
		for (PortalInstancesOperationType portalInstancesOperationType :
				values()) {

			if (value.equals(portalInstancesOperationType.getValue())) {
				return portalInstancesOperationType;
			}
		}

		throw new IllegalArgumentException(
			"Unknown portal instances operation type \"" + value + "\"");
	}

	public String getValue() {
		return _value;
	}

	private PortalInstancesOperationType(String value) {
		_value = value;
	}

	private final String _value;

}