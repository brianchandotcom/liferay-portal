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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.CommercePaymentMethodIdentifier;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommercePaymentMethodNestedCollectionResource
	implements NestedCollectionResource<CommercePaymentMethod, Long,
		CommercePaymentMethodIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePaymentMethod, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePaymentMethod, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "commerce-payment-method";
	}

	@Override
	public ItemRoutes<CommercePaymentMethod, Long> itemRoutes(
		ItemRoutes.Builder<CommercePaymentMethod, Long> builder) {

		return builder.addGetter(
			this::_getCommercePaymentMethod
		).build();
	}

	@Override
	public Representor<CommercePaymentMethod> representor(
		Representor.Builder<CommercePaymentMethod, Long> builder) {

		return builder.types(
			"CommercePaymentMethod"
		).identifier(
			CommercePaymentMethod::getCommercePaymentMethodId
		).addBidirectionalModel(
			"webSite", "commercePaymentMethods", WebSiteIdentifier.class,
			CommercePaymentMethod::getGroupId
		).addLocalizedStringByLocale(
			"name", CommercePaymentMethod::getName
		).addLocalizedStringByLocale(
			"description", CommercePaymentMethod::getDescription
		).addBoolean(
			"active", CommercePaymentMethod::getActive
		).addDate(
			"dateCreated", CommercePaymentMethod::getCreateDate
		).addDate(
			"dateModified", CommercePaymentMethod::getModifiedDate
		).addLinkedModel(
			"author", PersonIdentifier.class, CommercePaymentMethod::getUserId
		).build();
	}

	private CommercePaymentMethod _getCommercePaymentMethod(
		Long commercePaymentMethodId) throws PortalException {

		return _commercePaymentMethodService.getCommercePaymentMethod(
			commercePaymentMethodId);
	}

	private PageItems<CommercePaymentMethod> _getPageItems(
		Pagination pagination, Long webSiteId) {

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentMethodService.getCommercePaymentMethods(
				webSiteId, false);

		int total =
			_commercePaymentMethodService.getCommercePaymentMethodsCount(
				webSiteId, false);

		return new PageItems<>(commercePaymentMethods, total);
	}

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

}