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

package com.liferay.commerce.pricing.web.internal.frontend.data.set.provider;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.commerce.pricing.web.internal.model.PricingEntry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + CommercePricingFDSNames.ACCOUNT_ENTRY_PRICE_LISTS,
	service = FDSDataProvider.class
)
public class PriceListPricingEntryCommerceChannelAccountEntryRelFDSDataProvider
	implements FDSDataProvider<PricingEntry> {

	@Override
	public List<PricingEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");
		int type = ParamUtil.getInteger(httpServletRequest, "type");

		return TransformUtil.transform(
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRels(
					accountEntryId, type, fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition(), null),
			commerceChannelAccountEntryRel -> {
				CommercePriceList commercePriceList =
					_commercePriceListService.getCommercePriceList(
						commerceChannelAccountEntryRel.getClassPK());

				return new PricingEntry(
					commerceChannelAccountEntryRel.getAccountEntryId(),
					commercePriceList.isApproved(),
					_getChannelName(
						accountEntryId,
						commerceChannelAccountEntryRel.getCommerceChannelId(),
						httpServletRequest, type),
					commerceChannelAccountEntryRel.
						getCommerceChannelAccountEntryRelId(),
					commercePriceList.getName(),
					commerceChannelAccountEntryRel.isOverrideEligibility(),
					commerceChannelAccountEntryRel.getPriority(),
					commerceChannelAccountEntryRel.getType());
			});
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRelsCount(
				ParamUtil.getLong(httpServletRequest, "accountEntryId"),
				ParamUtil.getInteger(httpServletRequest, "type"));
	}

	private String _getChannelName(
			long accountEntryId, long commerceChannelId,
			HttpServletRequest httpServletRequest, int type)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelService.fetchCommerceChannel(commerceChannelId);

		if (commerceChannel == null) {
			List<CommerceChannelAccountEntryRel>
				commerceChannelAccountEntryRels =
					_commerceChannelAccountEntryRelService.
						getCommerceChannelAccountEntryRels(
							accountEntryId, type, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS, null);

			if (commerceChannelAccountEntryRels.size() == 1) {
				CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
					commerceChannelAccountEntryRels.get(0);

				if (commerceChannelAccountEntryRel.getCommerceChannelId() ==
						0) {

					return _language.get(httpServletRequest, "all-channels");
				}
			}
			else if (!commerceChannelAccountEntryRels.isEmpty()) {
				return _language.get(httpServletRequest, "all-other-channels");
			}

			return _language.get(httpServletRequest, "all-channels");
		}

		return commerceChannel.getName();
	}

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private Language _language;

}