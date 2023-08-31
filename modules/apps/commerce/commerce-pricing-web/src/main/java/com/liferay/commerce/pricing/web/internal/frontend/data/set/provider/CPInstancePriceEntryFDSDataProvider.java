/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.commerce.pricing.web.internal.model.InstancePriceEntry;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

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
	property = "fds.data.provider.key=" + CommercePricingFDSNames.INSTANCE_PRICE_ENTRIES,
	service = FDSDataProvider.class
)
public class CPInstancePriceEntryFDSDataProvider
	implements FDSDataProvider<InstancePriceEntry> {

	@Override
	public List<InstancePriceEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<InstancePriceEntry> instancePriceEntries = new ArrayList<>();

		long cpInstanceId = ParamUtil.getLong(
			httpServletRequest, "cpInstanceId");

		List<CommercePriceEntry> commercePriceEntries =
			_commercePriceEntryService.getInstanceCommercePriceEntries(
				cpInstanceId, fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition());

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			CommercePriceList commercePriceList =
				commercePriceEntry.getCommercePriceList();

			CommerceMoney priceCommerceMoney =
				commercePriceEntry.getPriceCommerceMoney(
					commercePriceList.getCommerceCurrencyId());

			Date createDate = commercePriceEntry.getCreateDate();

			String createDateDescription = _language.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - createDate.getTime(), true);

			instancePriceEntries.add(
				new InstancePriceEntry(
					commercePriceEntry.getCommercePriceEntryId(),
					_language.format(
						httpServletRequest, "x-ago", createDateDescription,
						false),
					commercePriceList.getName(),
					commercePriceEntry.isPriceOnApplication(),
					_getQuantity(commercePriceEntry),
					commercePriceEntry.getUnitOfMeasureKey(),
					HtmlUtil.escape(
						priceCommerceMoney.format(
							_portal.getLocale(httpServletRequest)))));
		}

		return instancePriceEntries;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long cpInstanceId = ParamUtil.getLong(
			httpServletRequest, "cpInstanceId");

		return _commercePriceEntryService.getInstanceCommercePriceEntriesCount(
			cpInstanceId);
	}

	private String _getQuantity(CommercePriceEntry commercePriceEntry) {
		BigDecimal quantity = commercePriceEntry.getQuantity();

		if (quantity == null) {
			return StringPool.BLANK;
		}

		quantity = quantity.stripTrailingZeros();

		return quantity.toString();
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}