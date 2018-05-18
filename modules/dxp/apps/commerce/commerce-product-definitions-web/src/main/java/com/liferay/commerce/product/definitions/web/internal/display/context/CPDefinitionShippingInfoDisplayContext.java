/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionShippingInfoDisplayContext
	extends CPDefinitionsDisplayContext {

	public CPDefinitionShippingInfoDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceCurrencyService commerceCurrencyService,
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionService cpDefinitionService, ItemSelector itemSelector,
			CPMeasurementUnitService cpMeasurementUnitService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, cpDefinitionHelper,
			cpDefinitionService, itemSelector);

		_commerceCurrencyService = commerceCurrencyService;
		_cpMeasurementUnitService = cpMeasurementUnitService;
	}

	public String getCommerceCurrencyCode() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				themeDisplay.getScopeGroupId());

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public String getCPMeasurementUnitName(int type) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
				themeDisplay.getScopeGroupId(), type);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit.getName(themeDisplay.getLanguageId());
		}

		return StringPool.BLANK;
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private final CPMeasurementUnitService _cpMeasurementUnitService;

}