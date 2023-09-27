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
public interface CommerceOrderFinder {

	public com.liferay.commerce.model.CommerceOrder fetchByG_U_C_O_S_First(
		long groupId, long userId, long commerceAccountId, int orderStatus);

	public java.util.List<com.liferay.commerce.model.CommerceOrder> findByG_O(
		long groupId, int[] orderStatuses);

	public java.util.List<com.liferay.commerce.model.CommerceOrder> findByG_O(
		long groupId, int[] orderStatuses, int start, int end);

	public java.util.List<com.liferay.commerce.model.CommerceOrder>
		getShippedCommerceOrdersByCommerceShipmentId(
			long shipmentId, int start, int end);

}