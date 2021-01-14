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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPMeasurementUnitConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.util.CPCompareContentHelper;
import com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentPortletInstanceConfiguration;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = CPCompareContentHelper.class
)
public class CPCompareContentHelperImpl implements CPCompareContentHelper {

	@Override
	public Set<CPSpecificationOption> getCategorizedCPSpecificationOptions(
			CPDataSourceResult cpDataSourceResult)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpCatalogEntry, true));
		}

		return cpSpecificationOptions;
	}

	@Override
	public String getCompareContentPortletNamespace() {
		return _portal.getPortletNamespace(
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);
	}

	public String getCompareProductsURL(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout curLayout = themeDisplay.getLayout();

		long plid = _portal.getPlidFromPortletId(
			themeDisplay.getScopeGroupId(), curLayout.isPrivateLayout(),
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return StringPool.BLANK;
		}

		return _portal.getLayoutURL(layout, themeDisplay);
	}

	@Override
	public List<CPCatalogEntry> getCPCatalogEntries(
			long groupId, long commerceAccountId,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _cpCompareHelper.getCPCatalogEntries(
			groupId, commerceAccountId,
			CookieKeys.getCookie(
				httpServletRequest,
				_cpCompareHelper.getCPDefinitionIdsCookieKey(groupId)));
	}

	@Override
	public Set<String> getCPDefinitionOptionRelNames(
			CPDataSourceResult cpDataSourceResult, Locale locale)
		throws PortalException {

		Set<String> cpDefinitionOptionRelNames = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				getMultiValueCPDefinitionOptionRels(cpCatalogEntry);

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				String cpDefinitionOptionRelName =
					cpDefinitionOptionRel.getName(locale);

				cpDefinitionOptionRelNames.add(cpDefinitionOptionRelName);
			}
		}

		return cpDefinitionOptionRelNames;
	}

	@Override
	public String getCPDefinitionOptionValueRels(
			CPCatalogEntry cpCatalogEntry, String cpDefinitionOptionRelName,
			Locale locale)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpCatalogEntry.getCPDefinitionId());

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			if (cpDefinitionOptionRelName.equals(
					cpDefinitionOptionRel.getName(locale))) {

				return StringUtil.merge(
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCPDefinitionOptionValueRels(),
						locale),
					StringPool.COMMA_AND_SPACE);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public String getCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId, Locale locale) {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_cpDefinitionSpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValuesByC_CSO(
						cpDefinitionId, cpSpecificationOptionId);

		StringBundler sb = new StringBundler(
			cpDefinitionSpecificationOptionValues.size() * 2);

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			sb.append(cpDefinitionSpecificationOptionValue.getValue(locale));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		if (!cpDefinitionSpecificationOptionValues.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(long groupId) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public Set<CPSpecificationOption> getCPSpecificationOptions(
			CPDataSourceResult cpDataSourceResult)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions = new HashSet<>();

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			cpSpecificationOptions.addAll(
				getCPSpecificationOptions(cpCatalogEntry, false));
		}

		return cpSpecificationOptions;
	}

	@Override
	public String getDeleteCompareProductURL(
		long cpDefinitionId, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/cp_compare_content_mini_web/delete_compare_product");

		String redirect = _portal.getCurrentURL(renderRequest);

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	@Override
	public String getDimensionCPMeasurementUnitName(
		long companyId, Locale locale) {

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
				companyId, CPMeasurementUnitConstants.TYPE_DIMENSION);

		if (cpMeasurementUnit == null) {
			return StringPool.BLANK;
		}

		return cpMeasurementUnit.getName(locale);
	}

	@Override
	public int getProductsLimit(PortletDisplay portletDisplay)
		throws PortalException {

		if (CPPortletKeys.CP_COMPARE_CONTENT_MINI_WEB.equals(
				portletDisplay.getPortletName())) {

			CPCompareContentMiniPortletInstanceConfiguration
				cpCompareContentMiniPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						CPCompareContentMiniPortletInstanceConfiguration.class);

			return cpCompareContentMiniPortletInstanceConfiguration.
				productsLimit();
		}
		else if (CPPortletKeys.CP_COMPARE_CONTENT_WEB.equals(
					portletDisplay.getPortletName())) {

			CPCompareContentPortletInstanceConfiguration
				cpCompareContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						CPCompareContentPortletInstanceConfiguration.class);

			return cpCompareContentPortletInstanceConfiguration.productsLimit();
		}

		return 0;
	}

	@Override
	public boolean hasCategorizedCPDefinitionSpecificationOptionValues(
			CPDataSourceResult cpDataSourceResult, long cpOptionCategoryId)
		throws PortalException {

		Set<CPSpecificationOption> cpSpecificationOptions =
			getCategorizedCPSpecificationOptions(cpDataSourceResult);

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

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpCatalogEntry.getCPDefinitionId());

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

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpCatalogEntry.getCPDefinitionId());

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

	@Reference
	private CPCompareHelper _cpCompareHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}