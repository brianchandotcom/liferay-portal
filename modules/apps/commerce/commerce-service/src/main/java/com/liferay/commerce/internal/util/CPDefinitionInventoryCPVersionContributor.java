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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.product.util.CPVersionContributor;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ethan Bustad
 */
@Component(immediate = true, service = CPVersionContributor.class)
public class CPDefinitionInventoryCPVersionContributor
	implements CPVersionContributor {

	@Override
	public void onDelete(long cpDefinitionId) {
		_cpDefinitionInventoryLocalService.
			deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public void onUpdate(long oldCPDefinitionId, long newCPDefinitionId) {
		_cpDefinitionInventoryLocalService.cloneCPDefinitionInventory(
			oldCPDefinitionId, newCPDefinitionId);
	}

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}