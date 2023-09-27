/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet.shared.search;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.custom.CustomFacetSearchContributor;
import com.liferay.portal.search.facet.nested.NestedFacetSearchContributor;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	property = "javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
	service = PortletSharedSearchContributor.class
)
public class CustomFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		String aggregationField =
			customFacetPortletPreferences.getAggregationField();

		if (Validator.isNull(aggregationField)) {
			return;
		}

		if (!ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
			aggregationField.startsWith(DDMIndexer.DDM_FIELD_ARRAY)) {

			_contributeWithDDMFieldArray(
				customFacetPortletPreferences, aggregationField,
				portletSharedSearchSettings);
		}
		else if (!ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
				 aggregationField.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {

			_contributeWithDDMField(
				customFacetPortletPreferences, aggregationField,
				portletSharedSearchSettings);
		}
		else if (aggregationField.startsWith("nestedFieldArray")) {
			_contributeWithNestedFieldArray(
				customFacetPortletPreferences, aggregationField,
				portletSharedSearchSettings);
		}
		else {
			_contributeWithCustomFacet(
				customFacetPortletPreferences, aggregationField,
				portletSharedSearchSettings);
		}
	}

	@Reference
	protected CustomFacetSearchContributor customFacetSearchContributor;

	@Reference
	protected DDMIndexer ddmIndexer;

	@Reference
	protected NestedFacetSearchContributor nestedFacetSearchContributor;

	private void _contributeWithCustomFacet(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		customFacetSearchContributor.contribute(
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				customFacetPortletPreferences.getFederatedSearchKey()),
			customFacetBuilder -> customFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).fieldToAggregate(
				fieldToAggregate
			).frequencyThreshold(
				customFacetPortletPreferences.getFrequencyThreshold()
			).maxTerms(
				customFacetPortletPreferences.getMaxTerms()
			).selectedValues(
				portletSharedSearchSettings.getParameterValues(
					_getParameterName(customFacetPortletPreferences))
			));
	}

	private void _contributeWithDDMField(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String[] ddmFieldParts = StringUtil.split(
			fieldToAggregate, DDMIndexer.DDM_FIELD_SEPARATOR);

		if (ddmFieldParts.length != 4) {
			return;
		}

		_contributeWithNestedFieldFacet(
			customFacetPortletPreferences,
			ddmIndexer.getValueFieldName(
				ddmFieldParts[1], _getSuffixLocale(ddmFieldParts[3])),
			DDMIndexer.DDM_FIELD_NAME, fieldToAggregate,
			DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings);
	}

	private void _contributeWithDDMFieldArray(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String[] ddmFieldArrayParts = StringUtil.split(
			fieldToAggregate, StringPool.PERIOD);

		if (ddmFieldArrayParts.length != 3) {
			return;
		}

		_contributeWithNestedFieldFacet(
			customFacetPortletPreferences, ddmFieldArrayParts[2],
			DDMIndexer.DDM_FIELD_NAME, ddmFieldArrayParts[1],
			DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings);
	}

	private void _contributeWithNestedFieldArray(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String[] fieldToAggregrateParts = StringUtil.split(
			fieldToAggregate, StringPool.PERIOD);

		if (fieldToAggregrateParts.length != 3) {
			return;
		}

		_contributeWithNestedFieldFacet(
			customFacetPortletPreferences, fieldToAggregrateParts[2],
			"fieldName", fieldToAggregrateParts[1], "nestedFieldArray",
			portletSharedSearchSettings);
	}

	private void _contributeWithNestedFieldFacet(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate, String filterField, String filterValue,
		String path, PortletSharedSearchSettings portletSharedSearchSettings) {

		nestedFacetSearchContributor.contribute(
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				customFacetPortletPreferences.getFederatedSearchKey()),
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).fieldToAggregate(
				StringBundler.concat(path, StringPool.PERIOD, fieldToAggregate)
			).filterField(
				StringBundler.concat(path, StringPool.PERIOD, filterField)
			).filterValue(
				filterValue
			).frequencyThreshold(
				customFacetPortletPreferences.getFrequencyThreshold()
			).maxTerms(
				customFacetPortletPreferences.getMaxTerms()
			).path(
				path
			).selectedValues(
				portletSharedSearchSettings.getParameterValues(
					_getParameterName(customFacetPortletPreferences))
			));
	}

	private String _getParameterName(
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		String parameterName = customFacetPortletPreferences.getParameterName();

		if (Validator.isNotNull(parameterName)) {
			return parameterName;
		}

		String aggregationField =
			customFacetPortletPreferences.getAggregationField();

		if (Validator.isNotNull(aggregationField)) {
			return aggregationField;
		}

		return "customfield";
	}

	private Locale _getSuffixLocale(String string) {
		for (Locale availableLocale : _language.getAvailableLocales()) {
			String availableLanguageId = _language.getLanguageId(
				availableLocale);

			if (string.endsWith(availableLanguageId)) {
				return availableLocale;
			}
		}

		return null;
	}

	@Reference
	private Language _language;

}