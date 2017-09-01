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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstancePricingInfoDisplayContext
	extends CPDefinitionsDisplayContext {

	public CPInstancePricingInfoDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionService cpDefinitionService, ItemSelector itemSelector,
			CommerceCurrencyService commerceCurrencyService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, cpDefinitionService,
			itemSelector);

		_commerceCurrencyService = commerceCurrencyService;
	}

	public String getCommerceCurrencyCode() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				themeDisplay.getScopeGroupId());

		if (commerceCurrency != null) {
			return commerceCurrency.getCode(themeDisplay.getLanguageId());
		}

		return StringPool.BLANK;
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPInstanceId();
	}

	@Override
	public String getScreenNavigationCategoryKey() throws PortalException {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS;
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private CPInstance _cpInstance;

}