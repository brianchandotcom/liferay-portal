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

package com.liferay.commerce.shipping.web.internal.display.context;

import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.web.internal.display.context.util.CommerceShippingMethodRequestHelper;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingMethodsDisplayContext {

	public CommerceShippingMethodsDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		CommerceShippingFixedOptionService commerceShippingFixedOptionService,
		CommerceShippingMethodService commerceShippingMethodService,
		CountryService countryService, HttpServletRequest httpServletRequest) {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_commerceShippingFixedOptionService =
			commerceShippingFixedOptionService;
		_commerceShippingMethodService = commerceShippingMethodService;
		_countryService = countryService;

		_commerceShippingMethodRequestHelper =
			new CommerceShippingMethodRequestHelper(httpServletRequest);
	}

	public long getCommerceChannelId() throws PortalException {
		if (_commerceShippingMethod != null) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByGroupId(
					_commerceShippingMethod.getGroupId());

			return commerceChannel.getCommerceChannelId();
		}

		return ParamUtil.getLong(
			_commerceShippingMethodRequestHelper.getRequest(),
			"commerceChannelId");
	}

	public CommerceShippingMethod getCommerceShippingMethod()
		throws PortalException {

		if (_commerceShippingMethod != null) {
			return _commerceShippingMethod;
		}

		long commerceShippingMethodId = ParamUtil.getLong(
			_commerceShippingMethodRequestHelper.getRequest(),
			"commerceShippingMethodId");

		if (commerceShippingMethodId != 0) {
			return _commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		_commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(),
				getCommerceShippingMethodEngineKey());

		return _commerceShippingMethod;
	}

	public String getCommerceShippingMethodEngineDescription(Locale locale) {
		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				getCommerceShippingMethodEngineKey());

		return commerceShippingEngine.getDescription(locale);
	}

	public String getCommerceShippingMethodEngineKey() {
		if (_commerceShippingMethod != null) {
			return _commerceShippingMethod.getEngineKey();
		}

		return ParamUtil.getString(
			_commerceShippingMethodRequestHelper.getRequest(),
			"commerceShippingMethodEngineKey");
	}

	public String getCommerceShippingMethodEngineName(Locale locale) {
		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				getCommerceShippingMethodEngineKey());

		return commerceShippingEngine.getName(locale);
	}

	public int getCommerceShippingOptionsCount() throws PortalException {
		CommerceShippingMethod commerceShippingMethod =
			getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return 0;
		}

		return _commerceShippingFixedOptionService.
			getCommerceShippingFixedOptionsCount(
				commerceShippingMethod.getCommerceShippingMethodId());
	}

	public int getCountriesCount() throws PortalException {
		return _countryService.getCompanyCountriesCount(
			_commerceShippingMethodRequestHelper.getCompanyId());
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private final CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;
	private CommerceShippingMethod _commerceShippingMethod;
	private final CommerceShippingMethodRequestHelper
		_commerceShippingMethodRequestHelper;
	private final CommerceShippingMethodService _commerceShippingMethodService;
	private final CountryService _countryService;

}