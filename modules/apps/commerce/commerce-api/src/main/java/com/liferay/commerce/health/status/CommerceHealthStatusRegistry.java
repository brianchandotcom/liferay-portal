/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.health.status;

import java.util.List;

/**
 * @author Marco Leo
 */
public interface CommerceHealthStatusRegistry {

	public List<CommerceHealthStatus> getActiveCommerceHealthStatuses(int type);

	public CommerceHealthStatus getCommerceHealthStatus(String key);

	public List<CommerceHealthStatus> getCommerceHealthStatuses(int type);

}