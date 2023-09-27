/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceVirtualOrderItemFinder {

	public int countByG_C(long groupId, long commerceAccountId);

	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem> findByEndDate(java.util.Date endDate);

	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem> findByG_C(
				long groupId, long commerceAccountId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.type.virtual.order.model.
						CommerceVirtualOrderItem> orderByComparator);

}