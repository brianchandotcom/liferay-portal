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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPCatalogEntryFactory;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPMeasurementUnitConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPCompareUtil;
import com.liferay.commerce.product.util.CPInstanceHelper;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareContentDisplayContext {

	public CPCompareContentDisplayContext(
			CPCatalogEntryFactory cpCatalogEntryFactory,
			CPContentListEntryRendererRegistry
				cpContentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPDefinitionService cpDefinitionService,
			CPDefinitionSpecificationOptionValueLocalService
				cpDefinitionSpecificationOptionValueLocalService,
			CPInstanceHelper cpInstanceHelper,
			CPMeasurementUnitService cpMeasurementUnitService,
			CPOptionCategoryLocalService cpOptionCategoryLocalService,
			CPTypeServicesTracker cpTypeServicesTracker,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_cpCatalogEntryFactory = cpCatalogEntryFactory;
		_cpContentListEntryRendererRegistry =
			cpContentListEntryRendererRegistry;
		_cpContentListRendererRegistry = cpContentListRendererRegistry;
		_cpDefinitionService = cpDefinitionService;
		_cpDefinitionSpecificationOptionValueLocalService =
			cpDefinitionSpecificationOptionValueLocalService;
		_cpInstanceHelper = cpInstanceHelper;
		_cpMeasurementUnitService = cpMeasurementUnitService;
		_cpOptionCategoryLocalService = cpOptionCategoryLocalService;
		_cpTypeServicesTracker = cpTypeServicesTracker;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCompareContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCompareContentPortletInstanceConfiguration.class);

		_cpDefinitionIds = CPCompareUtil.getCPDefinitionIds(httpServletRequest);
	}

	public Set<CPSpecificationOption> getCategorizedCPSpecificationOptions(
			List<CPCatalogEntry> cpCatalogEntries)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpCatalogEntry, true));
		}

		return cpSpecificationOptions;
	}

	public List<CPCatalogEntry> getCPCatalogEntries() throws PortalException {
		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		for (Long cpDefinitionId : _cpDefinitionIds) {
			cpCatalogEntries.add(
				_cpCatalogEntryFactory.create(
					cpDefinitionId, _cpRequestHelper.getLocale()));
		}

		if (cpCatalogEntries.size() > getProductsLimit()) {
			return cpCatalogEntries.subList(0, getProductsLimit());
		}

		return cpCatalogEntries;
	}

	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpType) {

		return
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderers(
				getCPContentListRendererKey(), cpType);
	}

	public String getCPContentListRendererKey() {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			"cpContentListRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListRenderer> cpContentListRenderers =
			getCPContentListRenderers();

		if (cpContentListRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListRenderer cpContentListRenderer =
			cpContentListRenderers.get(0);

		if (cpContentListRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListRenderer.getKey();
	}

	public List<CPContentListRenderer> getCPContentListRenderers() {
		return _cpContentListRendererRegistry.getCPContentListRenderers(
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);
	}

	public Set<String> getCPDefinitionOptionRelNames(
			List<CPCatalogEntry> cpCatalogEntries)
		throws PortalException {

		Set<String> cpDefinitionOptionRelNames = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				getMultiValueCPDefinitionOptionRels(cpCatalogEntry);

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				String cpDefinitionOptionRelName =
					cpDefinitionOptionRel.getName(_cpRequestHelper.getLocale());

				cpDefinitionOptionRelNames.add(cpDefinitionOptionRelName);
			}
		}

		return cpDefinitionOptionRelNames;
	}

	public String getCPDefinitionOptionValueRels(
			CPCatalogEntry cpCatalogEntry, String cpDefinitionOptionRelName)
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition(cpCatalogEntry);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			if (cpDefinitionOptionRelName.equals(
					cpDefinitionOptionRel.getName(
						_cpRequestHelper.getLocale()))) {

				return StringUtil.merge(
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCPDefinitionOptionValueRels(),
						_cpRequestHelper.getLocale()));
			}
		}

		return StringPool.BLANK;
	}

	public String getCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId) {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueLocalService.
					fetchCPDefinitionSpecificationOptionValue(
						cpDefinitionId, cpSpecificationOptionId);

		if (cpDefinitionSpecificationOptionValue == null) {
			return StringPool.BLANK;
		}

		return cpDefinitionSpecificationOptionValue.getValue(
			_cpRequestHelper.getLocale());
	}

	public List<CPOptionCategory> getCPOptionCategories() {
		return _cpOptionCategoryLocalService.getCPOptionCategories(
			_cpRequestHelper.getScopeGroupId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public Set<CPSpecificationOption> getCPSpecificationOptions(
			List<CPCatalogEntry> cpCatalogEntries)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpCatalogEntry, false));
		}

		return cpSpecificationOptions;
	}

	public String getCPTypeListEntryRendererKey(String cpType) {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			cpType + "--cpTypeListEntryRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListEntryRenderer> cpContentListEntryRenderers =
			getCPContentListEntryRenderers(cpType);

		if (cpContentListEntryRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListEntryRenderer cpContentListEntryRenderer =
			cpContentListEntryRenderers.get(0);

		if (cpContentListEntryRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListEntryRenderer.getKey();
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	public CPInstance getDefaultCPInstance(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		return _cpInstanceHelper.getCPInstance(
			cpCatalogEntry.getCPDefinitionId(), null);
	}

	public String getDeleteCompareProductURL(long cpDefinitionId) {
		RenderResponse renderResponse = _cpRequestHelper.getRenderResponse();

		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteCompareProduct");

		String redirect = _cpRequestHelper.getCurrentURL();

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	public String getDimensionCPMeasurementUnitName() {
		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
				_cpRequestHelper.getScopeGroupId(),
				CPMeasurementUnitConstants.TYPE_DIMENSION);

		if (cpMeasurementUnit == null) {
			return StringPool.BLANK;
		}

		return cpMeasurementUnit.getName(_cpRequestHelper.getLocale());
	}

	public String getDisplayStyle() {
		return _cpCompareContentPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return
			_cpCompareContentPortletInstanceConfiguration.displayStyleGroupId();
	}

	public int getProductsLimit() {
		return _cpCompareContentPortletInstanceConfiguration.productsLimit();
	}

	public String getSelectionStyle() {
		return _cpCompareContentPortletInstanceConfiguration.selectionStyle();
	}

	public boolean hasCategorizedCPDefinitionSpecificationOptionValues(
			long cpOptionCategoryId)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions =
			getCategorizedCPSpecificationOptions(getCPCatalogEntries());

		for (CPSpecificationOption cpSpecificationOption :
				cpSpecificationOptions) {

			if (cpSpecificationOption.getCPOptionCategoryId() ==
					cpOptionCategoryId) {

				return true;
			}
		}

		return false;
	}

	public boolean isSelectionStyleADT() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("adt")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleCustomRenderer() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("custom")) {
			return true;
		}

		return false;
	}

	public void renderCPContentList() throws Exception {
		CPContentListRenderer cpContentListRenderer =
			_cpContentListRendererRegistry.getCPContentListRenderer(
				getCPContentListRendererKey());

		if (cpContentListRenderer != null) {
			cpContentListRenderer.render(
				getCPCatalogEntries(), _cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	public void renderCPContentListEntry(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		String cpType = cpCatalogEntry.getProductTypeName();

		CPContentListEntryRenderer cpContentListEntryRenderer =
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderer(
				getCPTypeListEntryRendererKey(cpType),
				getCPContentListRendererKey(), cpType);

		if (cpContentListEntryRenderer != null) {
			cpContentListEntryRenderer.render(
				cpCatalogEntry, _cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	protected CPDefinition getCPDefinition(CPCatalogEntry cpCatalogEntry)
		throws PortalException {

		return _cpDefinitionService.getCPDefinition(
			cpCatalogEntry.getCPDefinitionId());
	}

	protected List<String> getCPDefinitionOptionValueRels(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		Locale locale) {

		List<String> cpDefinitionOptionValueRelValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRelValues.add(
				cpDefinitionOptionValueRel.getName(locale));
		}

		return cpDefinitionOptionValueRelValues;
	}

	protected List<CPSpecificationOption> getCPSpecificationOptions(
			CPCatalogEntry cpCatalogEntry, boolean categorized)
		throws PortalException {

		List<CPSpecificationOption> cpSpecificationOptions = new ArrayList<>();

		CPDefinition cpDefinition = getCPDefinition(cpCatalogEntry);

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
			CPCatalogEntry cpCatalogEntry)
		throws PortalException {

		List<CPDefinitionOptionRel> multiValueCPDefinitionOptionRels =
			new ArrayList<>();

		CPDefinition cpDefinition = getCPDefinition(cpCatalogEntry);

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

	private final CPCatalogEntryFactory _cpCatalogEntryFactory;
	private final CPCompareContentPortletInstanceConfiguration
		_cpCompareContentPortletInstanceConfiguration;
	private final CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;
	private final CPContentListRendererRegistry _cpContentListRendererRegistry;
	private final List<Long> _cpDefinitionIds;
	private final CPDefinitionService _cpDefinitionService;
	private final CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPMeasurementUnitService _cpMeasurementUnitService;
	private final CPOptionCategoryLocalService _cpOptionCategoryLocalService;
	private final CPRequestHelper _cpRequestHelper;
	private final CPTypeServicesTracker _cpTypeServicesTracker;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}