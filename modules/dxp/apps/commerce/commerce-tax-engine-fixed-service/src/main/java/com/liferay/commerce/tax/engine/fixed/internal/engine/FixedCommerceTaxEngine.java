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

package com.liferay.commerce.tax.engine.fixed.internal.engine;

import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.model.CommerceTaxCalculateRequest;
import com.liferay.commerce.model.CommerceTaxEngine;
import com.liferay.commerce.model.CommerceTaxRate;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.tax.engine.key=" + FixedCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class FixedCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "fixed-tax";

	@Override
	public List<CommerceTaxRate> getCommerceTaxRates(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {

		List<CommerceTaxRate> commerceTaxRates = new ArrayList<>();

		try {
			CommerceTaxFixedRate commerceTaxFixedRate =
				_commerceTaxFixedRateLocalService.
					getCommerceTaxFixedRateByCPTC_CTM(
						commerceTaxCalculateRequest.getTaxCategoryId(),
						commerceTaxCalculateRequest.getCommerceTaxMethodId());

			CommerceTaxRate commerceTaxRate = new CommerceTaxRate(
				KEY, KEY, commerceTaxFixedRate.getRate());

			commerceTaxRates.add(commerceTaxRate);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			throw new CommerceTaxEngineException(pe);
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

		return LanguageUtil.get(resourceBundle, KEY);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FixedCommerceTaxEngine.class);

	@Reference
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

}