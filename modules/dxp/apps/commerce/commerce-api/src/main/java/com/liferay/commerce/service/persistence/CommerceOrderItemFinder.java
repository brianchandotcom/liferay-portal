/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceOrderItemFinder {
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> findByC_C(
		long commerceWarehouseId, long commerceAddressId);

	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> findByC_C(
		long commerceWarehouseId, long commerceAddressId, int start, int end);

	public int getCommerceOrderItemsQuantity(long commerceOrderId);

	public int getCPInstanceQuantity(long cpInstanceId, int status);
}