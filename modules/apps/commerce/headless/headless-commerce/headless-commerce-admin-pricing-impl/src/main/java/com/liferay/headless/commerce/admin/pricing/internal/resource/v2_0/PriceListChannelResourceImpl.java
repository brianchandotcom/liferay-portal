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
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListChannelDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListChannelUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-channel.properties",
	scope = ServiceScope.PROTOTYPE, service = PriceListChannelResource.class
)
public class PriceListChannelResourceImpl
	extends BasePriceListChannelResourceImpl {

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

	@Override
	public Page<PriceListChannel> getPriceListIdPriceListChannelsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommercePriceListChannelRel> commercePriceListChannelRels =
			_commercePriceListChannelRelService.getCommercePriceListChannelRels(
				id, pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceListChannelRelService.
				getCommercePriceListChannelRelsCount(id);

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

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(id);

		CommercePriceListChannelRel commercePriceListChannelRel =
			PriceListChannelUtil.addCommercePriceListChannelRel(
				_commerceChannelService, _commercePriceListChannelRelService,
				priceListChannel, commercePriceList, _serviceContextHelper);

		return _toPriceListChannel(
			commercePriceListChannelRel.getCommercePriceListChannelRelId());
	}

	private PriceListChannel _toPriceListChannel(
			Long commercePriceListChannelRelId)
		throws Exception {

		return _priceListChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListChannelRelId,
				contextAcceptLanguage.getPreferredLocale()));
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

	@Reference
	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private PriceListChannelDTOConverter _priceListChannelDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}