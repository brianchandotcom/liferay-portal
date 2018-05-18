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
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceTaxCalculateRequest;
import com.liferay.commerce.model.CommerceTaxEngine;
import com.liferay.commerce.model.CommerceTaxRate;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
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
	property = "commerce.tax.engine.key=" + ByAddressCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class ByAddressCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "by-address";

	@Override
	public List<CommerceTaxRate> getCommerceTaxRates(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {

		List<CommerceTaxRate> commerceTaxRates = new ArrayList<>();

		try {
			CommerceAddress commerceAddress =
				_commerceAddressLocalService.getCommerceAddress(
					commerceTaxCalculateRequest.getCommerceAddressId());

			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
				_commerceTaxFixedRateAddressRelLocalService.
					fetchCommerceTaxFixedRateAddressRel(
						commerceTaxCalculateRequest.getCommerceTaxMethodId(),
						commerceAddress.getCommerceCountryId(),
						commerceAddress.getCommerceRegionId(),
						commerceAddress.getZip());

			if (commerceTaxFixedRateAddressRel == null) {
				return null;
			}
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

		return LanguageUtil.get(
			resourceBundle, "by-address-tax-rate-description");
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
		ByAddressCommerceTaxEngine.class);

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

}