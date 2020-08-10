/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.inventory.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Luca Pellizzon
 * @generated
 */
@ProviderType
public interface CommerceInventoryWarehouseFinder {

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
			findByG_S(long groupId, String sku);

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
			findByC_G_A(long companyId, long groupId, boolean active);

}