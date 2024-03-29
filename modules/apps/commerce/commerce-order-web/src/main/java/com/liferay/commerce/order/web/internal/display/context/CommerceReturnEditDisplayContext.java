/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceReturn;
import com.liferay.commerce.order.web.internal.display.context.helper.CommerceReturnRequestHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.Format;

import java.util.Date;

import javax.portlet.RenderRequest;

/**
 * @author Stefano Motta
 */
public class CommerceReturnEditDisplayContext {

	public CommerceReturnEditDisplayContext(
			AccountEntryLocalService accountEntryLocalService,
			CommerceOrderLocalService commerceOrderLocalService,
			CommercePriceFormatter commercePriceFormatter,
			ObjectEntryService objectEntryService, RenderRequest renderRequest)
		throws PortalException {

		_accountEntryLocalService = accountEntryLocalService;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commercePriceFormatter = commercePriceFormatter;

		long commerceReturnId = ParamUtil.getLong(
			renderRequest, "commerceReturnId");

		if (commerceReturnId > 0) {
			_commerceReturn = new CommerceReturn(
				objectEntryService.getObjectEntry(commerceReturnId));
		}
		else {
			_commerceReturn = null;
		}

		_commerceReturnRequestHelper = new CommerceReturnRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceReturnRequestHelper.getThemeDisplay();

		_commerceDateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.SHORT, DateFormat.SHORT, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	public String getAmountFormatted(BigDecimal amount) throws PortalException {
		CommerceOrder commerceOrder = getCommerceReturnCommerceOrder();

		return _commercePriceFormatter.format(
			commerceOrder.getCommerceCurrency(), amount,
			_commerceReturnRequestHelper.getLocale());
	}

	public CommerceReturn getCommerceReturn() {
		return _commerceReturn;
	}

	public AccountEntry getCommerceReturnAccountEntry() throws PortalException {
		if (_commerceReturn == null) {
			return null;
		}

		if (_accountEntry != null) {
			return _accountEntry;
		}

		_accountEntry = _accountEntryLocalService.getAccountEntry(
			_commerceReturn.getAccountId());

		return _accountEntry;
	}

	public String getCommerceReturnAccountEntryThumbnailURL()
		throws PortalException {

		if (_commerceReturn == null) {
			return StringPool.BLANK;
		}

		AccountEntry accountEntry = getCommerceReturnAccountEntry();

		ThemeDisplay themeDisplay =
			_commerceReturnRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/organization_logo?img_id=");
		sb.append(accountEntry.getLogoId());

		if (accountEntry.getLogoId() > 0) {
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(accountEntry.getLogoId()));
		}

		return sb.toString();
	}

	public CommerceOrder getCommerceReturnCommerceOrder()
		throws PortalException {

		if (_commerceReturn == null) {
			return null;
		}

		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		_commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			_commerceReturn.getOrderId());

		return _commerceOrder;
	}

	public String getDateTimeFormatted(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		return _commerceDateTimeFormat.format(date);
	}

	public String getDescriptiveAddress(CommerceAddress commerceAddress) {
		StringBundler sb = new StringBundler(5);

		sb.append(HtmlUtil.escape(commerceAddress.getCity()));
		sb.append(StringPool.COMMA_AND_SPACE);

		try {
			Region region = commerceAddress.getRegion();

			if (region != null) {
				sb.append(HtmlUtil.escape(region.getName()));
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		sb.append(HtmlUtil.escape(commerceAddress.getZip()));

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnEditDisplayContext.class);

	private AccountEntry _accountEntry;
	private final AccountEntryLocalService _accountEntryLocalService;
	private final Format _commerceDateTimeFormat;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceReturn _commerceReturn;
	private final CommerceReturnRequestHelper _commerceReturnRequestHelper;

}