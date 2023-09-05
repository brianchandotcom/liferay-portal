/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.commerce.pricing.web.internal.model.InstanceTierPriceEntry;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommercePricingFDSNames.INSTANCE_TIER_PRICE_ENTRIES,
	service = FDSDataProvider.class
)
public class CPInstanceTierPriceEntryFDSDataProvider
	implements FDSDataProvider<InstanceTierPriceEntry> {

	@Override
	public List<InstanceTierPriceEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<InstanceTierPriceEntry> instanceTierPriceEntries =
			new ArrayList<>();

		long commercePriceEntryId = ParamUtil.getLong(
			httpServletRequest, "commercePriceEntryId");

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			_commerceTierPriceEntryService.getCommerceTierPriceEntries(
				commercePriceEntryId, fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition());

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			CommercePriceEntry commercePriceEntry =
				commerceTierPriceEntry.getCommercePriceEntry();

			CommercePriceList commercePriceList =
				commercePriceEntry.getCommercePriceList();

			CommerceMoney priceCommerceMoney =
				commerceTierPriceEntry.getPriceCommerceMoney(
					commercePriceList.getCommerceCurrencyId());

			Date createDate = commerceTierPriceEntry.getCreateDate();

			String createDateDescription = _language.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - createDate.getTime(), true);

			instanceTierPriceEntries.add(
				new InstanceTierPriceEntry(
					commerceTierPriceEntry.getCommerceTierPriceEntryId(),
					_language.format(
						httpServletRequest, "x-ago", createDateDescription,
						false),
					_commerceQuantityFormatter.format(
						commercePriceEntry.getCPInstance(),
						commerceTierPriceEntry.getMinQuantity(),
						commercePriceEntry.getUnitOfMeasureKey()),
					HtmlUtil.escape(
						priceCommerceMoney.format(
							_portal.getLocale(httpServletRequest)))));
		}

		return instanceTierPriceEntries;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commercePriceEntryId = ParamUtil.getLong(
			httpServletRequest, "commercePriceEntryId");

		return _commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
			commercePriceEntryId);
	}

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}