/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

/**
 * @author Bruno Farache
 */
public interface UserIdStrategy {

	public static final String ALWAYS_CURRENT_USER_ID =
		"ALWAYS_CURRENT_USER_ID";

	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

	public long getUserId(String userUuid);

}