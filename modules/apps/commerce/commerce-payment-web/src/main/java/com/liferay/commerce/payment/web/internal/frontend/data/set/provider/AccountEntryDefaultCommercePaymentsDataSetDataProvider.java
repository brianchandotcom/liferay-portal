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

package com.liferay.commerce.payment.web.internal.frontend.data.set.provider;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.payment.web.internal.constants.CommercePaymentMethodGroupRelFDSNames;
import com.liferay.commerce.payment.web.internal.model.PaymentMethod;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
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
 * @author Crescenzo Rega
 */
@Component(
	enabled = false, immediate = true,
	property = "fds.data.provider.key=" + CommercePaymentMethodGroupRelFDSNames.ACCOUNT_ENTRY_DEFAULT_PAYMENTS,
	service = FDSDataProvider.class
)
public class AccountEntryDefaultCommercePaymentsDataSetDataProvider
	implements FDSDataProvider<PaymentMethod> {

	@Override
	public List<PaymentMethod> getItems(
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

				String paymentMethodName = _language.get(
					httpServletRequest, "use-priority-settings");

				CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
					_commerceChannelAccountEntryRelService.
						fetchCommerceChannelAccountEntryRel(
							accountEntryId,
							commerceChannel.getCommerceChannelId(),
							CommerceChannelAccountEntryRelConstants.
								TYPE_PAYMENT);

				if (commerceChannelAccountEntryRel != null) {
					CommercePaymentMethodGroupRel
						commercePaymentMethodGroupRel =
							_commercePaymentMethodGroupRelService.
								getCommercePaymentMethodGroupRel(
									commerceChannelAccountEntryRel.
										getClassPK());

					if (commercePaymentMethodGroupRel != null) {
						paymentMethodName =
							commercePaymentMethodGroupRel.getName(locale);

						if (commercePaymentMethodGroupRel.isActive()) {
							active = _language.get(locale, "yes");
						}
						else {
							active = _language.get(locale, "no");
						}
					}
				}

				return new PaymentMethod(
					accountEntryId, active, commerceChannel.getName(),
					commerceChannel.getCommerceChannelId(), paymentMethodName);
			});
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRelsCount(
				accountEntryId,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
	}

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}