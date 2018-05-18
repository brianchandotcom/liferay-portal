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
				_commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
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