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

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionTerm;
import com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.ShippingFixedOptionTermDTOConverter;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionTermResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/shipping-fixed-option-term.properties",
	scope = ServiceScope.PROTOTYPE,
	service = ShippingFixedOptionTermResource.class
)
public class ShippingFixedOptionTermResourceImpl
	extends BaseShippingFixedOptionTermResourceImpl {

	@Override
	public void deleteShippingFixedOptionTerm(Long id) throws Exception {
		_commerceShippingFixedOptionQualifierService.
			deleteCommerceShippingFixedOptionQualifier(id);
	}

	@Override
	public Page<ShippingFixedOptionTerm>
			getShippingFixedOptionIdShippingFixedOptionTermsPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionService.
				fetchCommerceShippingFixedOption(id);

		if (commerceShippingFixedOption == null) {
			throw new NoSuchShippingFixedOptionException(
				"Unable to find shipping fixed option with ID " + id);
		}

		return Page.of(
			TransformUtil.transform(
				_commerceShippingFixedOptionQualifierService.
					getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition()),
				corEntryRel -> _toShippingFixedOptionTerm(corEntryRel)),
			pagination,
			_commerceShippingFixedOptionQualifierService.
				getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
					id, search));
	}

	@Override
	public ShippingFixedOptionTerm
			postShippingFixedOptionIdShippingFixedOptionTerm(
				Long id, ShippingFixedOptionTerm shippingFixedOptionTerm)
		throws Exception {

		CommerceTermEntry commerceTermEntry = _getCommerceTermEntry(
			shippingFixedOptionTerm);

		return _toShippingFixedOptionTerm(
			_commerceShippingFixedOptionQualifierService.
				addCommerceShippingFixedOptionQualifier(
					CommerceTermEntry.class.getName(),
					commerceTermEntry.getCommerceTermEntryId(), id));
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commerceShippingFixedOptionQualifier.
					getCommerceShippingFixedOptionQualifierId(),
				"deleteShippingFixedOptionTerm",
				_commerceShippingFixedOptionQualifierModelResourcePermission)
		).build();
	}

	private CommerceTermEntry _getCommerceTermEntry(
			ShippingFixedOptionTerm shippingFixedOptionTerm)
		throws Exception {

		CommerceTermEntry commerceTerm = null;

		if (shippingFixedOptionTerm.getTermId() > 0) {
			commerceTerm = _commerceTermEntryService.getCommerceTermEntry(
				shippingFixedOptionTerm.getTermId());
		}
		else {
			commerceTerm =
				_commerceTermEntryService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					shippingFixedOptionTerm.getTermExternalReferenceCode());
		}

		return commerceTerm;
	}

	private ShippingFixedOptionTerm _toShippingFixedOptionTerm(
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier)
		throws Exception {

		return _shippingFixedOptionTermDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceShippingFixedOptionQualifier),
				_dtoConverterRegistry,
				commerceShippingFixedOptionQualifier.
					getCommerceShippingFixedOptionQualifierId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier)"
	)
	private ModelResourcePermission<CommerceShippingFixedOptionQualifier>
		_commerceShippingFixedOptionQualifierModelResourcePermission;

	@Reference
	private CommerceShippingFixedOptionQualifierService
		_commerceShippingFixedOptionQualifierService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ShippingFixedOptionTermDTOConverter
		_shippingFixedOptionTermDTOConverter;

}