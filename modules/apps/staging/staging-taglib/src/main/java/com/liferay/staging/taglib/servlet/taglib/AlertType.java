/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.taglib.servlet.taglib;

/**
 * @author Péter Alius
 */
public enum AlertType {

	ERROR("error"), INFO("info"), SUCCESS("success"), WARNING("warning");

	public String getAlertCode() {
		return _alertCode;
	}

	private AlertType(String alertCode) {
		_alertCode = alertCode;
	}

	private final String _alertCode;

}