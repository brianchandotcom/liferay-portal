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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onBeforeRemove(CPDefinition cpDefinition) {
		long cpDefinitionId = cpDefinition.getCPDefinitionId();

		_cpDefinitionInventoryLocalService.
			deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}