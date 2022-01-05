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

package com.liferay.portal.search.web.internal.sort.portlet.shared.search;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortBuilder;
import com.liferay.portal.search.sort.SortBuilderFactory;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.web.internal.sort.constants.SortPortletKeys;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferences;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + SortPortletKeys.SORT,
	service = PortletSharedSearchContributor.class
)
public class SortPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SortPortletPreferences sortPortletPreferences =
			new SortPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getSearchRequestBuilder();

		Stream<Sort> stream = _buildSorts(
			portletSharedSearchSettings, sortPortletPreferences);

		searchRequestBuilder.sorts(stream.toArray(Sort[]::new));
	}

	@Reference
	protected DDMIndexer ddmIndexer;

	private Sort _buildSort(String fieldValue, Locale locale) {
		SortOrder sortOrder = SortOrder.ASC;

		if (fieldValue.endsWith("+")) {
			fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
		}
		else if (fieldValue.endsWith("-")) {
			fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
			sortOrder = SortOrder.DESC;
		}

		if (fieldValue.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {
			try {
				return ddmIndexer.createDDMStructureFieldSort(
					fieldValue, locale, sortOrder);
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		SortBuilder sortBuilder = _sortBuilderFactory.getSortBuilder();

		return sortBuilder.field(
			fieldValue
		).locale(
			locale
		).sortOrder(
			sortOrder
		).build();
	}

	private Stream<Sort> _buildSorts(
		PortletSharedSearchSettings portletSharedSearchSettings,
		SortPortletPreferences sortPortletPreferences) {

		List<String> fieldValues = _getFieldValues(
			sortPortletPreferences.getParameterName(),
			portletSharedSearchSettings);

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		Stream<String> stream = fieldValues.stream();

		return stream.filter(
			fieldValue -> !fieldValue.isEmpty()
		).map(
			fieldValue -> _buildSort(fieldValue, themeDisplay.getLocale())
		);
	}

	private List<String> _getFieldValues(
		String parameterName,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String[] fieldValues = portletSharedSearchSettings.getParameterValues(
			parameterName);

		if (ArrayUtil.isNotEmpty(fieldValues)) {
			return Arrays.asList(fieldValues);
		}

		String portletId = portletSharedSearchSettings.getPortletId();
		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		try {
			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getExistingPortletSetup(
					themeDisplay.getLayout(), portletId);

			SortPortletPreferences sortPortletPreferences =
				new SortPortletPreferencesImpl(Optional.of(portletPreferences));

			JSONArray fieldsJSONArray =
				sortPortletPreferences.getFieldsJSONArray();

			JSONObject jsonObject = fieldsJSONArray.getJSONObject(0);

			String fieldValue = jsonObject.getString("field");

			return ListUtil.fromArray(fieldValue);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private SortBuilderFactory _sortBuilderFactory;

}