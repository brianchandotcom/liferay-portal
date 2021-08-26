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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0;

import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.DiscountAccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountAccountGroupResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/discount-account-group.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountAccountGroupResource.class
)
public class DiscountAccountGroupResourceImpl
	extends BaseDiscountAccountGroupResourceImpl {

	@Override
	public Response deleteDiscountAccountGroup(Long id) throws Exception {
		_commerceDiscountCommerceAccountGroupRelService.
			deleteCommerceDiscountCommerceAccountGroupRel(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<DiscountAccountGroup>
			getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRels(
						commerceDiscount.getCommerceDiscountId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountCommerceAccountGroupRelService.
				getCommerceDiscountCommerceAccountGroupRelsCount(
					commerceDiscount.getCommerceDiscountId());

		return Page.of(
			_toDiscountAccountGroups(commerceDiscountCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public Page<DiscountAccountGroup> getDiscountIdDiscountAccountGroupsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRels(
						id, pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountCommerceAccountGroupRelService.
				getCommerceDiscountCommerceAccountGroupRelsCount(id);

		return Page.of(
			_toDiscountAccountGroups(commerceDiscountCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public DiscountAccountGroup
			postDiscountByExternalReferenceCodeDiscountAccountGroup(
				String externalReferenceCode,
				DiscountAccountGroup discountAccountGroup)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				DiscountAccountGroupUtil.
					addCommerceDiscountCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commerceDiscountCommerceAccountGroupRelService,
						discountAccountGroup, commerceDiscount,
						_serviceContextHelper.getServiceContext());

		return _toDiscountAccountGroup(
			commerceDiscountCommerceAccountGroupRel.
				getCommerceDiscountCommerceAccountGroupRelId());
	}

	@Override
	public DiscountAccountGroup postDiscountIdDiscountAccountGroup(
			Long id, DiscountAccountGroup discountAccountGroup)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				DiscountAccountGroupUtil.
					addCommerceDiscountCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commerceDiscountCommerceAccountGroupRelService,
						discountAccountGroup,
						_commerceDiscountService.getCommerceDiscount(id),
						_serviceContextHelper.getServiceContext());

		return _toDiscountAccountGroup(
			commerceDiscountCommerceAccountGroupRel.
				getCommerceDiscountCommerceAccountGroupRelId());
	}

	private DiscountAccountGroup _toDiscountAccountGroup(
			Long commerceDiscountCommerceAccountGroupRelId)
		throws Exception {

		return _discountAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountCommerceAccountGroupRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<DiscountAccountGroup> _toDiscountAccountGroups(
			List<CommerceDiscountCommerceAccountGroupRel>
				commerceDiscountCommerceAccountGroupRels)
		throws Exception {

		List<DiscountAccountGroup> discountAccountGroups = new ArrayList<>();

		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					commerceDiscountCommerceAccountGroupRels) {

			discountAccountGroups.add(
				_toDiscountAccountGroup(
					commerceDiscountCommerceAccountGroupRel.
						getCommerceDiscountCommerceAccountGroupRelId()));
		}

		return discountAccountGroups;
	}

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountAccountGroupDTOConverter _discountAccountGroupDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}