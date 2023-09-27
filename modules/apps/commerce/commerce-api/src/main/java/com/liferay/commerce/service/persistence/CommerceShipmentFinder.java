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
public interface CommerceShipmentFinder {

	public int countByCommerceOrderId(long commerceOrderId);

	public java.util.List<com.liferay.commerce.model.CommerceShipment>
		findByCommerceOrderId(long commerceOrderId, int start, int end);

	public int[] findCommerceShipmentStatusesByCommerceOrderId(
		long commerceOrderId);

}