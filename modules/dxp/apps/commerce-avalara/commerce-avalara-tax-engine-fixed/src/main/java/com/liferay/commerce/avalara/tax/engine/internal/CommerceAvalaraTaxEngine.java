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

package com.liferay.commerce.avalara.tax.engine.internal;

import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculateRequest;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Calvin Keum
 */
@Component(
	property = "commerce.tax.engine.key=" + CommerceAvalaraTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class CommerceAvalaraTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "avalara";

	@Override
	public CommerceTaxValue getCommerceTaxValue(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {

		if (commerceTaxCalculateRequest.isShipping()) {
			return _commerceTaxEngine.getCommerceTaxValue(
				commerceTaxCalculateRequest);
		}

		commerceTaxCalculateRequest.setTaxCategoryId(
			_getTangiblePersonalPropertyTaxCategoryId());

		return _commerceTaxEngine.getCommerceTaxValue(
			commerceTaxCalculateRequest);
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			_getResourceBundle(locale), "avalara-tax-rate-description");
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(_getResourceBundle(locale), KEY);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private long _getTangiblePersonalPropertyTaxCategoryId() {
		if (_tangiblePersonalPropertyTaxCategoryId == 0) {
			CPTaxCategory cpTaxCategory =
				_cpTaxCategoryLocalService.fetchCPTaxCategoryByReferenceCode(
					CompanyThreadLocal.getCompanyId(),
					CommerceAvalaraConstants.
						CP_TAX_CATEGORY_ERC_TANGIBLE_PERSONAL_PROPERTY);

			if (cpTaxCategory == null) {
				_tangiblePersonalPropertyTaxCategoryId = 0;
			}
			else {
				_tangiblePersonalPropertyTaxCategoryId =
					cpTaxCategory.getCPTaxCategoryId();
			}
		}

		return _tangiblePersonalPropertyTaxCategoryId;
	}

	@Reference(target = "(commerce.tax.engine.key=by-address)")
	private CommerceTaxEngine _commerceTaxEngine;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference
	private Language _language;

	private long _tangiblePersonalPropertyTaxCategoryId;

}