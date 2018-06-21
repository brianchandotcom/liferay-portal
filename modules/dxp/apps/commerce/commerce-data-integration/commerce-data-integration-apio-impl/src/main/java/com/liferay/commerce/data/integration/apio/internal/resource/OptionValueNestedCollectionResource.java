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

package com.liferay.commerce.data.integration.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.OptionIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.OptionValueIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.OptionValueForm;
import com.liferay.commerce.data.integration.apio.internal.util.OptionValueHelper;
import com.liferay.commerce.product.exception.CPOptionValueKeyException;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.BadRequestException;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class OptionValueNestedCollectionResource
	implements
		NestedCollectionResource<CPOptionValue, Long, OptionValueIdentifier, Long,
			OptionIdentifier> {

	@Override
	public NestedCollectionRoutes<CPOptionValue, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPOptionValue, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPOptionValue,
			_hasPermission.forAddingIn(OptionValueIdentifier.class),
			OptionValueForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "option-values";
	}

	@Override
	public ItemRoutes<CPOptionValue, Long> itemRoutes(
		ItemRoutes.Builder<CPOptionValue, Long> builder) {

		return builder.addGetter(
			_cpOptionValueService::getCPOptionValue
		).addRemover(
			idempotent(_cpOptionValueService::deleteCPOptionValue),
			_hasPermission::forDeleting
		).addUpdater(
			this::_updateCPOptionValue,
			_hasPermission::forUpdating,
			OptionValueForm::buildForm
		).build();
	}

	@Override
	public Representor<CPOptionValue> representor(
		Representor.Builder<CPOptionValue, Long> builder) {

		return builder.types(
			"OptionValue"
		).identifier(
			CPOptionValue::getCPOptionValueId
		).addBidirectionalModel(
			"option", "values", OptionIdentifier.class,
			CPOptionValue::getCPOptionId
		).addString(
			"name", CPOptionValue::getName
		).addString(
			"key", CPOptionValue::getKey
		).build();
	}

	private CPOptionValue _addCPOptionValue(
		Long cpOptionId, OptionValueForm optionValueForm) throws PortalException {

		try {
			return _optionValueHelper.createCPOptionValue(
				cpOptionId, optionValueForm.getNameMap(),
				optionValueForm.getKey());
		}
		catch (CPOptionValueKeyException cpovke) {
			throw new BadRequestException(
				String.format(
					"An option value with key '%s' already exists",
					optionValueForm.getKey()),
				cpovke);
		}
	}

	private PageItems<CPOptionValue> _getPageItems(
		Pagination pagination, Long cpOptionId) throws PortalException {

	    List<CPOptionValue> cpOptionValues = _cpOptionValueService.getCPOptionValues(cpOptionId, pagination.getStartPosition(), pagination.getEndPosition());

	    int total = _cpOptionValueService.getCPOptionValuesCount(cpOptionId);

        return new PageItems<>(cpOptionValues, total);
	}

	private CPOptionValue _updateCPOptionValue(
		Long cpOptionValueId, OptionValueForm optionValueForm) throws PortalException {

        return _optionValueHelper.updateCPOptionValue(
            cpOptionValueId, optionValueForm.getNameMap(),
            optionValueForm.getKey());
	}

	@Reference
	private CPOptionValueService _cpOptionValueService;

	@Reference
	private OptionValueHelper _optionValueHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOptionValue)"
	)
	private HasPermission<Long> _hasPermission;

}