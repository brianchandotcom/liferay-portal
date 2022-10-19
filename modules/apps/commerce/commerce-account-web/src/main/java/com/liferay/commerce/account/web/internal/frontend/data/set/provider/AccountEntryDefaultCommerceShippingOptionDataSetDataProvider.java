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

package com.liferay.commerce.account.web.internal.frontend.data.set.provider;

import com.liferay.commerce.account.web.internal.constants.CommerceAccountFDSNames;
import com.liferay.commerce.account.web.internal.model.ShippingOption;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + CommerceAccountFDSNames.ACCOUNT_ENTRY_DEFAULT_SHIPPING_OPTIONS,
	service = FDSDataProvider.class
)
public class AccountEntryDefaultCommerceShippingOptionDataSetDataProvider
	implements FDSDataProvider<ShippingOption> {

	@Override
	public List<ShippingOption> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");
		long companyId = _portal.getCompanyId(httpServletRequest);
		Locale locale = _portal.getLocale(httpServletRequest);

		return TransformUtil.transform(
			_commerceChannelService.search(
				companyId, fdsKeywords.getKeywords(),
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), sort),
			commerceChannel -> {
				String active = StringPool.BLANK;
				String commerceShippingMethodName = _language.get(
					httpServletRequest, "use-priority-settings");
				String commerceShippingOptionName = _language.get(
					httpServletRequest, "use-priority-settings");

				CommerceShippingOptionAccountEntryRel
					commerceShippingOptionAccountEntryRel =
						_commerceShippingOptionAccountEntryRelService.
							fetchCommerceShippingOptionAccountEntryRel(
								accountEntryId,
								commerceChannel.getCommerceChannelId());

				if (commerceShippingOptionAccountEntryRel != null) {
					CommerceShippingMethod commerceShippingMethod =
						_commerceShippingMethodService.
							fetchCommerceShippingMethod(
								commerceChannel.getGroupId(),
								commerceShippingOptionAccountEntryRel.
									getCommerceShippingMethodKey());

					if (commerceShippingMethod != null) {
						commerceShippingMethodName =
							commerceShippingMethod.getName(locale);

						if (commerceShippingMethod.isActive()) {
							active = _language.get(locale, "yes");
						}
						else {
							active = _language.get(locale, "no");
						}
					}

					CommerceShippingFixedOption commerceShippingFixedOption =
						_commerceShippingFixedOptionService.
							fetchCommerceShippingFixedOption(
								companyId,
								commerceShippingOptionAccountEntryRel.
									getCommerceShippingOptionKey());

					if (commerceShippingFixedOption != null) {
						commerceShippingOptionName =
							commerceShippingFixedOption.getName(locale);
					}
				}

				return new ShippingOption(
					accountEntryId, active, commerceChannel.getName(),
					commerceChannel.getCommerceChannelId(),
					commerceShippingMethodName, commerceShippingOptionName);
			});
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		return _commerceChannelService.searchCommerceChannelsCount(
			_portal.getCompanyId(httpServletRequest),
			fdsKeywords.getKeywords());
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}