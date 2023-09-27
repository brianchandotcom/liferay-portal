/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.security.access.control;

/**
 * @author Carlos Sierra
 * @author Mariano Álvaro Sáiz
 */
public interface AllowedIPAddressesValidator {

	public boolean isAllowedIPAddress(String ipAddress);

}