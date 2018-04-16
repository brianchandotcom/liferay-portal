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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.product.content.web.configuration.CPCompareContentPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPMeasurementUnitConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.util.CPCompareUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareContentDisplayContext {

	public CPCompareContentDisplayContext(
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionService cpDefinitionService,
			CPDefinitionSpecificationOptionValueService
				cpDefinitionSpecificationOptionValueService,
			CPMeasurementUnitService cpMeasurementUnitService,
			CPOptionCategoryService cpOptionCategoryService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_cpDefinitionHelper = cpDefinitionHelper;
		_cpDefinitionService = cpDefinitionService;
		_cpDefinitionSpecificationOptionValueService =
			cpDefinitionSpecificationOptionValueService;
		_cpMeasurementUnitService = cpMeasurementUnitService;
		_cpOptionCategoryService = cpOptionCategoryService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCompareContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCompareContentPortletInstanceConfiguration.class);

		_cpDefinitionIds = CPCompareUtil.getCPDefinitionIds(httpServletRequest);
	}

	public Set<CPSpecificationOption> getCategorizedCPSpecificationOptions(
			List<CPDefinition> cpDefinitions)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPDefinition cpDefinition : cpDefinitions) {
			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpDefinition, true));
		}

		return cpSpecificationOptions;
	}

	public Set<String> getCPDefinitionOptionRelTitles(
		List<CPDefinition> cpDefinitions) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Set<String> cpDefinitionOptionRelTitles = new HashSet<>();

		for (CPDefinition cpDefinition : cpDefinitions) {
			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				getMultiValueCPDefinitionOptionRels(cpDefinition);

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				String cpDefinitionOptionRelTitle =
					cpDefinitionOptionRel.getTitle(
						themeDisplay.getLanguageId());

				cpDefinitionOptionRelTitles.add(cpDefinitionOptionRelTitle);
			}
		}

		return cpDefinitionOptionRelTitles;
	}

	public String getCPDefinitionOptionValueRels(
		CPDefinition cpDefinition, String cpDefinitionOptionRelTitle) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			if (cpDefinitionOptionRelTitle.equals(
					cpDefinitionOptionRel.getTitle(languageId))) {

				return StringUtil.merge(
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCPDefinitionOptionValueRels(),
						languageId));
			}
		}

		return StringPool.BLANK;
	}

	public List<CPDefinition> getCPDefinitions() throws PortalException {
		List<CPDefinition> cpDefinitions = new ArrayList<>();

		for (Long cpDefinitionId : _cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.fetchCPDefinition(
				cpDefinitionId);

			if (cpDefinition != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		if (cpDefinitions.size() > getProductsLimit()) {
			return cpDefinitions.subList(0, getProductsLimit());
		}

		return cpDefinitions;
	}

	public String getCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueService.
					fetchCPDefinitionSpecificationOptionValue(
						cpDefinitionId, cpSpecificationOptionId);

		if (cpDefinitionSpecificationOptionValue == null) {
			return StringPool.BLANK;
		}

		return cpDefinitionSpecificationOptionValue.getValue(
			themeDisplay.getLanguageId());
	}

	public List<CPOptionCategory> getCPOptionCategories() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpOptionCategoryService.getCPOptionCategories(
			themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public Set<CPSpecificationOption> getCPSpecificationOptions(
			List<CPDefinition> cpDefinitions)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPDefinition cpDefinition : cpDefinitions) {
			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpDefinition, false));
		}

		return cpSpecificationOptions;
	}

	public String getDeleteCompareProductURL(long cpDefinitionId) {
		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			_httpServletRequest);

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteCompareProduct");

		String redirect = PortalUtil.getCurrentURL(_httpServletRequest);

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	public String getDimensionCPMeasurementUnitName() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
				themeDisplay.getScopeGroupId(),
				CPMeasurementUnitConstants.TYPE_DIMENSION);

		if (cpMeasurementUnit == null) {
			return StringPool.BLANK;
		}

		return cpMeasurementUnit.getName(themeDisplay.getLanguageId());
	}

	public String getDisplayStyle() {
		return _cpCompareContentPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return
			_cpCompareContentPortletInstanceConfiguration.displayStyleGroupId();
	}

	public String getProductFriendlyURL(long cpDefinitionId)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public int getProductsLimit() {
		return _cpCompareContentPortletInstanceConfiguration.productsLimit();
	}

	public boolean hasCategorizedCPDefinitionSpecificationOptionValues(
			long cpOptionCategoryId)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions =
			getCategorizedCPSpecificationOptions(getCPDefinitions());

		for (CPSpecificationOption cpSpecificationOption :
				cpSpecificationOptions) {

			if (cpSpecificationOption.getCPOptionCategoryId() ==
					cpOptionCategoryId) {

				return true;
			}
		}

		return false;
	}

	protected List<String> getCPDefinitionOptionValueRels(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		String languageId) {

		List<String> cpDefinitionOptionValueRelValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRelValues.add(
				cpDefinitionOptionValueRel.getTitle(languageId));
		}

		return cpDefinitionOptionValueRelValues;
	}

	protected List<CPSpecificationOption> getCPSpecificationOptions(
			CPDefinition cpDefinition, boolean categorized)
		throws PortalException {

		List<CPSpecificationOption> cpSpecificationOptions = new ArrayList<>();

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinition.getCPDefinitionSpecificationOptionValues()) {

			CPSpecificationOption cpSpecificationOption =
				cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

			if (categorized &&
				(cpSpecificationOption.getCPOptionCategoryId() > 0)) {

				cpSpecificationOptions.add(cpSpecificationOption);
			}

			if (!categorized &&
				(cpSpecificationOption.getCPOptionCategoryId() == 0)) {

				cpSpecificationOptions.add(cpSpecificationOption);
			}
		}

		return cpSpecificationOptions;
	}

	protected List<CPDefinitionOptionRel> getMultiValueCPDefinitionOptionRels(
		CPDefinition cpDefinition) {

		List<CPDefinitionOptionRel> multiValueCPDefinitionOptionRels =
			new ArrayList<>();

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			Map<String, Object> properties =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
					cpDefinitionOptionRel.getDDMFormFieldTypeName());

			String fieldTypeDataDomain = MapUtil.getString(
				properties, "ddm.form.field.type.data.domain");

			if (Validator.isNotNull(fieldTypeDataDomain) &&
				fieldTypeDataDomain.equals("list")) {

				multiValueCPDefinitionOptionRels.add(cpDefinitionOptionRel);
			}
		}

		return multiValueCPDefinitionOptionRels;
	}

	private final CPCompareContentPortletInstanceConfiguration
		_cpCompareContentPortletInstanceConfiguration;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final List<Long> _cpDefinitionIds;
	private final CPDefinitionService _cpDefinitionService;
	private final CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;
	private final CPMeasurementUnitService _cpMeasurementUnitService;
	private final CPOptionCategoryService _cpOptionCategoryService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final HttpServletRequest _httpServletRequest;

}