/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory;

import com.liferay.commerce.model.CPDefinitionInventory;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public interface CPDefinitionInventoryEngineRegistry {

	public CPDefinitionInventoryEngine getCPDefinitionInventoryEngine(
		CPDefinitionInventory cpDefinitionInventory);

	public CPDefinitionInventoryEngine getCPDefinitionInventoryEngine(
		String key);

	public List<CPDefinitionInventoryEngine> getCPDefinitionInventoryEngines();

}