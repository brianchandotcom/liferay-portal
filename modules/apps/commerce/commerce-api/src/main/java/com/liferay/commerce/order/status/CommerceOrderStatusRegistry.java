/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.status;

import com.liferay.commerce.model.CommerceOrder;

import java.util.List;

/**
 * @author Alec Sloan
 */
public interface CommerceOrderStatusRegistry {

	public CommerceOrderStatus getCommerceOrderStatus(int key);

	public List<CommerceOrderStatus> getCommerceOrderStatuses();

	public List<CommerceOrderStatus> getCommerceOrderStatuses(
		CommerceOrder commerceOrder);

}