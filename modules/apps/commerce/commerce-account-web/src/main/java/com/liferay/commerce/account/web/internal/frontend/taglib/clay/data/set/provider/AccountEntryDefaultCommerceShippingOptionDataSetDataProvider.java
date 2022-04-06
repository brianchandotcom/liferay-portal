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

package com.liferay.commerce.account.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.account.web.internal.frontend.constants.CommerceAccountDataSetConstants;
import com.liferay.commerce.account.web.internal.model.ShippingOption;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceAccountDataSetConstants.COMMERCE_DATA_SET_KEY_ACCOUNT_ENTRY_DEFAULT_SHIPPING_OPTIONS,
	service = ClayDataSetDataProvider.class
)
public class AccountEntryDefaultCommerceShippingOptionDataSetDataProvider
	implements ClayDataSetDataProvider<ShippingOption> {

	@Override
	public List<ShippingOption> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");
		long companyId = _portal.getCompanyId(httpServletRequest);
		Locale locale = _portal.getLocale(httpServletRequest);

		return TransformUtil.transform(
			_commerceChannelService.search(
				companyId, filter.getKeywords(), pagination.getStartPosition(),
				pagination.getEndPosition(), sort),
			commerceChannel -> {
				String active = StringPool.BLANK;
				String commerceShippingMethodName = LanguageUtil.get(
					httpServletRequest, "use-priority-settings");
				String commerceShippingOptionName = LanguageUtil.get(
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
							active = LanguageUtil.get(locale, "yes");
						}
						else {
							active = LanguageUtil.get(locale, "no");
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
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		return _commerceChannelService.searchCommerceChannelsCount(
			_portal.getCompanyId(httpServletRequest), filter.getKeywords());
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
	private Portal _portal;

}