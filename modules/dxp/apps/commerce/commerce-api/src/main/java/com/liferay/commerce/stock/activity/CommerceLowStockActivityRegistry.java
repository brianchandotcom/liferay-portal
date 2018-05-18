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

package com.liferay.commerce.stock.activity;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CPDefinitionInventory;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceLowStockActivityRegistry {

	public List<CommerceLowStockActivity> getCommerceLowStockActivities();

	public CommerceLowStockActivity getCommerceLowStockActivity(
		CPDefinitionInventory cpDefinitionInventory);

	public CommerceLowStockActivity getCommerceLowStockActivity(String key);

}