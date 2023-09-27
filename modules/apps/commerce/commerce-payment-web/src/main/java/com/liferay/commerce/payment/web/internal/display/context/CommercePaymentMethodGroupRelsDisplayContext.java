/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.display.context;

import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.payment.web.internal.display.context.helper.CommercePaymentMethodRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommercePaymentMethodGroupRelsDisplayContext {

	public CommercePaymentMethodGroupRelsDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		CommercePaymentMethodGroupRelService
			commercePaymentMethodGroupRelService,
		CommercePaymentMethodRegistry commercePaymentMethodRegistry,
		CountryService countryService, HttpServletRequest httpServletRequest) {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
		_commercePaymentMethodRegistry = commercePaymentMethodRegistry;
		_countryService = countryService;

		commercePaymentMethodRequestHelper =
			new CommercePaymentMethodRequestHelper(httpServletRequest);
	}

	public long getCommerceChannelId() throws PortalException {
		if (_commercePaymentMethodGroupRel != null) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByGroupId(
					_commercePaymentMethodGroupRel.getGroupId());

			return commerceChannel.getCommerceChannelId();
		}

		return ParamUtil.getLong(
			commercePaymentMethodRequestHelper.getRequest(),
			"commerceChannelId");
	}

	public String getCommercePaymentMethodEngineDescription(Locale locale) {
		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodRegistry.getCommercePaymentMethod(
				getCommercePaymentMethodEngineKey());

		return commercePaymentMethod.getDescription(locale);
	}

	public String getCommercePaymentMethodEngineKey() {
		if (_commercePaymentMethodGroupRel != null) {
			return _commercePaymentMethodGroupRel.getPaymentIntegrationKey();
		}

		return ParamUtil.getString(
			commercePaymentMethodRequestHelper.getRequest(),
			"commercePaymentMethodEngineKey");
	}

	public String getCommercePaymentMethodEngineName(Locale locale) {
		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodRegistry.getCommercePaymentMethod(
				getCommercePaymentMethodEngineKey());

		return commercePaymentMethod.getName(locale);
	}

	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel()
		throws PortalException {

		if (_commercePaymentMethodGroupRel != null) {
			return _commercePaymentMethodGroupRel;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		_commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelService.
				fetchCommercePaymentMethodGroupRel(
					commerceChannel.getGroupId(),
					getCommercePaymentMethodEngineKey());

		return _commercePaymentMethodGroupRel;
	}

	public long getCommercePaymentMethodGroupRelId() {
		if (_commercePaymentMethodGroupRel != null) {
			return _commercePaymentMethodGroupRel.
				getCommercePaymentMethodGroupRelId();
		}

		return 0;
	}

	public int getCountriesCount() throws PortalException {
		return _countryService.getCompanyCountriesCount(
			commercePaymentMethodRequestHelper.getCompanyId());
	}

	protected final CommercePaymentMethodRequestHelper
		commercePaymentMethodRequestHelper;

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;
	private final CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;
	private final CommercePaymentMethodRegistry _commercePaymentMethodRegistry;
	private final CountryService _countryService;

}