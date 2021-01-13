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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListChannelDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListChannelUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PriceListChannelResource.class}
)
public class PriceListChannelResourceImpl
	extends BasePriceListChannelResourceImpl implements NestedFieldSupport {

	@Override
	public void deletePriceListChannel(Long id) throws Exception {
		_commercePriceListChannelRelService.deleteCommercePriceListChannelRel(
			id);
	}

	@Override
	public Page<PriceListChannel>
			getPriceListByExternalReferenceCodePriceListChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommercePriceListChannelRel> commercePriceListChannelRels =
			_commercePriceListChannelRelService.getCommercePriceListChannelRels(
				commercePriceList.getCommercePriceListId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceListChannelRelService.
				getCommercePriceListChannelRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListChannels(commercePriceListChannelRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = PriceList.class, value = "priceListChannels")
	@Override
	public Page<PriceListChannel> getPriceListIdPriceListChannelsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommercePriceListChannelRel> commercePriceListChannelRels =
			_commercePriceListChannelRelService.getCommercePriceListChannelRels(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commercePriceListChannelRelService.
				getCommercePriceListChannelRelsCount(id, search);

		return Page.of(
			_toPriceListChannels(commercePriceListChannelRels), pagination,
			totalItems);
	}

	@Override
	public PriceListChannel
			postPriceListByExternalReferenceCodePriceListChannel(
				String externalReferenceCode, PriceListChannel priceListChannel)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommercePriceListChannelRel commercePriceListChannelRel =
			PriceListChannelUtil.addCommercePriceListChannelRel(
				_commerceChannelService, _commercePriceListChannelRelService,
				priceListChannel, commercePriceList, _serviceContextHelper);

		return _toPriceListChannel(
			commercePriceListChannelRel.getCommercePriceListChannelRelId());
	}

	@Override
	public PriceListChannel postPriceListIdPriceListChannel(
			Long id, PriceListChannel priceListChannel)
		throws Exception {

		CommercePriceListChannelRel commercePriceListChannelRel =
			PriceListChannelUtil.addCommercePriceListChannelRel(
				_commerceChannelService, _commercePriceListChannelRelService,
				priceListChannel,
				_commercePriceListService.getCommercePriceList(id),
				_serviceContextHelper);

		return _toPriceListChannel(
			commercePriceListChannelRel.getCommercePriceListChannelRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceListChannelRel commercePriceListChannelRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commercePriceListChannelRel.getCommercePriceListChannelRelId(),
				"deletePriceListChannel",
				_commercePriceListChannelRelModelResourcePermission)
		).build();
	}

	private PriceListChannel _toPriceListChannel(
			Long commercePriceListChannelRelId)
		throws Exception {

		CommercePriceListChannelRel commercePriceListChannelRel =
			_commercePriceListChannelRelService.getCommercePriceListChannelRel(
				commercePriceListChannelRelId);

		return _priceListChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceListChannelRel), _dtoConverterRegistry,
				commercePriceListChannelRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<PriceListChannel> _toPriceListChannels(
			List<CommercePriceListChannelRel> commercePriceListChannelRels)
		throws Exception {

		List<PriceListChannel> priceListChannels = new ArrayList<>();

		for (CommercePriceListChannelRel commercePriceListChannelRel :
				commercePriceListChannelRels) {

			priceListChannels.add(
				_toPriceListChannel(
					commercePriceListChannelRel.
						getCommercePriceListChannelRelId()));
		}

		return priceListChannels;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceListChannelRel)"
	)
	private ModelResourcePermission<CommercePriceListChannelRel>
		_commercePriceListChannelRelModelResourcePermission;

	@Reference
	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PriceListChannelDTOConverter _priceListChannelDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}