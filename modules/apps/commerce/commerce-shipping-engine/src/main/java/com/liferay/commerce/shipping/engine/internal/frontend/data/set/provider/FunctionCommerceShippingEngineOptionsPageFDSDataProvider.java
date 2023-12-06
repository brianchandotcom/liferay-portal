/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.internal.frontend.data.set.provider;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.shipping.engine.internal.constants.FunctionCommerceShippingEngineFDSNames;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "fds.data.provider.key=" + FunctionCommerceShippingEngineFDSNames.FUNCTION_COMMERCE_SHIPPING_ENGINE_OPTIONS,
	service = FDSDataProvider.class
)
public class FunctionCommerceShippingEngineOptionsPageFDSDataProvider
	implements FDSDataProvider<CommerceShippingOption> {

	@Override
	public List<CommerceShippingOption> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String commerceShippingMethodEngineKey = ParamUtil.getString(
			httpServletRequest, "commerceShippingMethodEngineKey");

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethodEngineKey);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(), commerceChannelId,
			themeDisplay.getUserId(), 0, 0);

		return commerceShippingEngine.getCommerceShippingOptions(
			commerceContext, null, themeDisplay.getLocale());
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String commerceShippingMethodEngineKey = ParamUtil.getString(
			httpServletRequest, "commerceShippingMethodEngineKey");

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethodEngineKey);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(), commerceChannelId,
			themeDisplay.getUserId(), 0, 0);

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, null, themeDisplay.getLocale());

		return commerceShippingOptions.size();
	}

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

}