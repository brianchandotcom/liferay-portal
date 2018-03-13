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

package com.liferay.commerce.tax.engine.fixed.web.internal;

import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.CommerceTaxEngine;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.model.CommerceTaxRate;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.tax.engine.key=" + FixedCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class FixedCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "fixed";

	@Override
	public List<CommerceTaxRate> getCommerceTaxRates(
			CPDefinition cpDefinition, CommerceAddress commerceAddress,
			Locale locale)
		throws CommerceTaxEngineException {

		List<CommerceTaxRate> commerceTaxRates = new ArrayList<>();

		try {
			commerceTaxRates = _getCommerceTaxRates(cpDefinition, locale);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return commerceTaxRates;
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "fixed-tax-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "fixed");
	}

	@Override
	public boolean isPercentage(long groupId) {
		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodService.fetchCommerceTaxMethod(groupId, KEY);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod.getPercentage();
		}

		return true;
	}

	private List<CommerceTaxFixedRate> _getCommerceTaxFixedRates(long groupId) {
		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodService.fetchCommerceTaxMethod(groupId, KEY);

		if (commerceTaxMethod == null) {
			return Collections.emptyList();
		}

		return _commerceTaxFixedRateService.getCommerceTaxFixedRates(
			commerceTaxMethod.getCommerceTaxMethodId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	private List<CommerceTaxRate> _getCommerceTaxRates(
			CPDefinition cpDefinition, Locale locale)
		throws PortalException {

		List<CommerceTaxRate> commerceTaxRates = new ArrayList<>();

		List<CommerceTaxFixedRate> commerceTaxFixedRates =
			_getCommerceTaxFixedRates(cpDefinition.getGroupId());

		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			CommerceTaxCategory commerceTaxCategory =
				commerceTaxFixedRate.getCommerceTaxCategory();

			String name = commerceTaxCategory.getName(locale);

			double rate = commerceTaxFixedRate.getRate();

			commerceTaxRates.add(new CommerceTaxRate(name, name, rate));
		}

		return commerceTaxRates;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FixedCommerceTaxEngine.class);

	@Reference
	private CommerceTaxFixedRateService _commerceTaxFixedRateService;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

}