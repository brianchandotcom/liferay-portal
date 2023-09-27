/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender;

/**
 * @author Bruno Farache
 */
public interface Response {

	public String getId();

	public String getPayload();

	public String getPlatform();

	public String getStatus();

	public String getToken();

	public boolean isSucceeded();

}