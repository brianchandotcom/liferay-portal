/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceOrderItemFinder {

	public int countByG_A_O(
		long groupId, long commerceAccountId, int[] orderStatuses);

	public java.util.List<com.liferay.commerce.model.CommerceOrderItem>
		findByAvailableQuantity(long commerceOrderId);

	public java.util.List<com.liferay.commerce.model.CommerceOrderItem>
		findByAvailableQuantity(long commerceOrderId, int start, int end);

	public java.util.List<com.liferay.commerce.model.CommerceOrderItem>
		findByG_A_O(
			long groupId, long commerceAccountId, int[] orderStatuses,
			int start, int end);

	public java.math.BigDecimal getCommerceOrderItemsQuantity(
		long commerceOrderId);

}