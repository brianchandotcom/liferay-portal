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

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.PricingClassCPDefinitionRel;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
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
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES_PRODUCT_DEFINITIONS,
	service = ClayDataSetDataProvider.class
)
public class CommercePricingClassCPDefinitionRelDataSetDataProvider
	implements ClayDataSetDataProvider<PricingClassCPDefinitionRel> {

	@Override
	public List<PricingClassCPDefinitionRel> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<PricingClassCPDefinitionRel> pricingClasseCPDefinitionRels =
			new ArrayList<>();

		try {
			Locale locale = _portal.getLocale(httpServletRequest);

			long commercePricingClassId = ParamUtil.getLong(
				httpServletRequest, "commercePricingClassId");

			List<CommercePricingClassCPDefinitionRel>
				commercePricingClassCPDefinitionRels =
					_commercePricingClassCPDefinitionRelService.
						searchByCommercePricingClassId(
							commercePricingClassId, filter.getKeywords(),
							LanguageUtil.getLanguageId(locale),
							pagination.getStartPosition(),
							pagination.getEndPosition());

			for (CommercePricingClassCPDefinitionRel
					commercePricingClassCPDefinitionRel :
						commercePricingClassCPDefinitionRels) {

				CPDefinition cpDefinition =
					_cpDefinitionService.getCPDefinition(
						commercePricingClassCPDefinitionRel.
							getCPDefinitionId());

				pricingClasseCPDefinitionRels.add(
					new PricingClassCPDefinitionRel(
						commercePricingClassCPDefinitionRel.
							getCommercePricingClassCPDefinitionRelId(),
						cpDefinition.getCPDefinitionId(),
						cpDefinition.getName(),
						_getSku(
							cpDefinition,
							_portal.getLocale(httpServletRequest)),
						new ImageField(
							cpDefinition.getName(
								LanguageUtil.getLanguageId(locale)),
							"rounded", "lg",
							cpDefinition.getDefaultImageThumbnailSrc())));
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return pricingClasseCPDefinitionRels;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commercePricingClassId = ParamUtil.getLong(
			httpServletRequest, "commercePricingClassId");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commercePricingClassCPDefinitionRelService.
			getCommercePricingClassCPDefinitionRelsCount(
				commercePricingClassId, filter.getKeywords(),
				themeDisplay.getLanguageId());
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
		CommercePricingClassCPDefinitionRelDataSetDataProvider.class);

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

}