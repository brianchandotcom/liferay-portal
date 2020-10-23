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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductOption;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductOptionDataSetDataProvider
	implements ClayDataSetDataProvider<ProductOption> {

	@Override
	public List<ProductOption> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductOption> productOptions = new ArrayList<>();

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		Locale locale = _portal.getLocale(httpServletRequest);

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_getCPDefinitionOptionRels(
				cpDefinitionId, filter.getKeywords(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				sort);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			productOptions.add(
				new ProductOption(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					_getDDMFormFieldTypeLabel(
						cpDefinitionOptionRel.getDDMFormFieldTypeName(),
						locale),
					cpDefinitionOptionRel.getName(
						LanguageUtil.getLanguageId(locale)),
					cpDefinitionOptionRel.getPriority(),
					LanguageUtil.get(
						locale,
						cpDefinitionOptionRel.isRequired() ? "yes" : "no"),
					LanguageUtil.get(
						locale,
						cpDefinitionOptionRel.isSkuContributor() ? "yes" :
							"no"),
					cpDefinitionOptionRel.
						getCPDefinitionOptionValueRelsCount()));
		}

		return productOptions;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		return _cpDefinitionOptionRelService.searchCPDefinitionOptionRelsCount(
			cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
			cpDefinition.getCPDefinitionId(), filter.getKeywords());
	}

	private BaseModelSearchResult<CPDefinitionOptionRel>
			_getBaseModelSearchResult(
				long cpDefinitionId, String keywords, int start, int end,
				Sort sort)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		return _cpDefinitionOptionRelService.searchCPDefinitionOptionRels(
			cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
			cpDefinitionId, keywords, start, end, new Sort[] {sort});
	}

	private List<CPDefinitionOptionRel> _getCPDefinitionOptionRels(
			long cpDefinitionId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<CPDefinitionOptionRel> baseModelSearchResult =
			_getBaseModelSearchResult(
				cpDefinitionId, keywords, start, end, sort);

		return baseModelSearchResult.getBaseModels();
	}

	private String _getDDMFormFieldTypeLabel(
		String ddmFormFieldTypeName, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormFieldTypeName);

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldType.getName());

		String label = MapUtil.getString(
			ddmFormFieldTypeProperties, "ddm.form.field.type.label");

		try {
			if (Validator.isNotNull(label)) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", locale, ddmFormFieldType.getClass());

				return LanguageUtil.get(resourceBundle, label);
			}
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isWarnEnabled()) {
				_log.warn(missingResourceException, missingResourceException);
			}
		}

		return ddmFormFieldType.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductOptionDataSetDataProvider.class);

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private Portal _portal;

}