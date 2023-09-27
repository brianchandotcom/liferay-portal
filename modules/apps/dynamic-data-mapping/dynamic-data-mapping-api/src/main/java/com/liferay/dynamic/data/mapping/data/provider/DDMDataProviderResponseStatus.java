/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

/**
 * @author Leonardo Barros
 */
public enum DDMDataProviderResponseStatus {

	COMMAND_EXCEPTION, OK, SERVICE_UNAVAILABLE, SHORT_CIRCUIT, TIMEOUT,
	UNAUTHORIZED, UNKNOWN_ERROR

}