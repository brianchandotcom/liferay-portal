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

package com.liferay.commerce.product.internal.option;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceOptionTypeImpl implements CommerceOptionType {

	@Override
	public boolean isPriceContributor(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isPriceContributor();
	}

	@Override
	public boolean isRequired(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isRequired();
	}

	@Override
	public boolean isSKUContributor(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isSkuContributor();
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

}