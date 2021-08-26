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
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.PriceListAccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.PriceListAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.PriceListAccountGroupResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
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
	properties = "OSGI-INF/liferay/rest/v1_0/price-list-account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = PriceListAccountGroupResource.class
)
public class PriceListAccountGroupResourceImpl
	extends BasePriceListAccountGroupResourceImpl
	implements NestedFieldSupport {

	@Override
	public Response deletePriceListAccountGroup(Long id) throws Exception {
		_commercePriceListCommerceAccountGroupRelService.
			deleteCommercePriceListCommerceAccountGroupRel(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<PriceListAccountGroup>
			getPriceListByExternalReferenceCodePriceListAccountGroupPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRels(
						commercePriceList.getCommercePriceListId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceListCommerceAccountGroupRelService.
				getCommercePriceListCommerceAccountGroupRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListAccountGroups(
				commercePriceListCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public Page<PriceListAccountGroup> getPriceListIdPriceListAccountGroupsPage(
			Long id, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchCommercePriceList(id);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with id: " + id);
		}

		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRels(
						id, pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceListCommerceAccountGroupRelService.
				getCommercePriceListCommerceAccountGroupRelsCount(id);

		return Page.of(
			_toPriceListAccountGroups(
				commercePriceListCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public PriceListAccountGroup
			postPriceListByExternalReferenceCodePriceListAccountGroup(
				String externalReferenceCode,
				PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup, commercePriceList,
						_serviceContextHelper.getServiceContext(
							commercePriceList.getGroupId()));

		return _toPriceListAccountGroup(
			commercePriceListCommerceAccountGroupRel.
				getCommercePriceListCommerceAccountGroupRelId());
	}

	@Override
	public PriceListAccountGroup postPriceListIdPriceListAccountGroup(
			Long id, PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(id);

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup, commercePriceList,
						_serviceContextHelper.getServiceContext(
							commercePriceList.getGroupId()));

		return _toPriceListAccountGroup(
			commercePriceListCommerceAccountGroupRel.
				getCommercePriceListCommerceAccountGroupRelId());
	}

	private PriceListAccountGroup _toPriceListAccountGroup(
			Long commercePriceListCommerceAccountGroupRelId)
		throws Exception {

		return _priceListAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListCommerceAccountGroupRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<PriceListAccountGroup> _toPriceListAccountGroups(
			List<CommercePriceListCommerceAccountGroupRel>
				commercePriceListCommerceAccountGroupRels)
		throws Exception {

		List<PriceListAccountGroup> priceListAccountGroups = new ArrayList<>();

		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					commercePriceListCommerceAccountGroupRels) {

			priceListAccountGroups.add(
				_toPriceListAccountGroup(
					commercePriceListCommerceAccountGroupRel.
						getCommercePriceListCommerceAccountGroupRelId()));
		}

		return priceListAccountGroups;
	}

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private PriceListAccountGroupDTOConverter
		_priceListAccountGroupDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}