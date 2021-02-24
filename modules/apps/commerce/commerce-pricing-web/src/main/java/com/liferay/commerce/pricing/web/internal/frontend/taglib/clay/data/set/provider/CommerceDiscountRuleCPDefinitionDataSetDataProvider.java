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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.DiscountRuleCPDefinition;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_DISCOUNT_RULE_PRODUCT_DEFINITIONS,
	service = ClayDataSetDataProvider.class
)
public class CommerceDiscountRuleCPDefinitionDataSetDataProvider
	implements ClayDataSetDataProvider<DiscountRuleCPDefinition> {

	@Override
	public List<DiscountRuleCPDefinition> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		try {
			return _getDiscountRuleCPDefinitions(httpServletRequest, filter);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Collections.emptyList();
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		try {
			List<DiscountRuleCPDefinition> discountRuleCPDefinitions =
				_getDiscountRuleCPDefinitions(httpServletRequest, filter);

			return discountRuleCPDefinitions.size();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return 0;
	}

	private List<DiscountRuleCPDefinition> _getDiscountRuleCPDefinitions(
			HttpServletRequest httpServletRequest, Filter filter)
		throws Exception {

		long commerceDiscountRuleId = ParamUtil.getLong(
			httpServletRequest, "commerceDiscountRuleId");

		CommerceDiscountRule commerceDiscountRule =
			_commerceDiscountRuleService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		String settingsProperty = commerceDiscountRule.getSettingsProperty(
			commerceDiscountRule.getType());

		long[] cpDefinitionIds = StringUtil.split(settingsProperty, 0L);

		if (cpDefinitionIds == null) {
			return Collections.emptyList();
		}

		List<DiscountRuleCPDefinition> discountRuleCPDefinitions =
			new ArrayList<>();

		Locale locale = _portal.getLocale(httpServletRequest);

		String languageId = LanguageUtil.getLanguageId(locale);

		String keywordsLowerCase = StringUtil.toLowerCase(filter.getKeywords());

		for (long cpDefinitionId : cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			String cpDefinitionName = cpDefinition.getName(languageId);

			String cpDefinitionNameLowerCase = StringUtil.toLowerCase(
				cpDefinitionName);

			if (!cpDefinitionNameLowerCase.contains(keywordsLowerCase)) {
				continue;
			}

			discountRuleCPDefinitions.add(
				new DiscountRuleCPDefinition(
					commerceDiscountRule.getCommerceDiscountRuleId(),
					cpDefinition.getCPDefinitionId(), cpDefinitionName,
					_getSku(cpDefinition, locale),
					new ImageField(
						cpDefinitionName, "rounded", "lg",
						cpDefinition.getDefaultImageThumbnailSrc())));
		}

		return discountRuleCPDefinitions;
	}

	private String _getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.isEmpty()) {
			return StringPool.BLANK;
		}

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRuleCPDefinitionDataSetDataProvider.class);

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

}