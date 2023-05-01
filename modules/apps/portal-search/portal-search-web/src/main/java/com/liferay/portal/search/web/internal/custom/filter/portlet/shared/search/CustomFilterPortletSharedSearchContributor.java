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

package com.liferay.portal.search.web.internal.custom.filter.portlet.shared.search;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferences;
import com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	property = "javax.portlet.name=" + CustomFilterPortletKeys.CUSTOM_FILTER,
	service = PortletSharedSearchContributor.class
)
public class CustomFilterPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFilterPortletPreferences customFilterPortletPreferences =
			new CustomFilterPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				customFilterPortletPreferences.getFederatedSearchKey());

		searchRequestBuilder.addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).boost(
				_getBoost(customFilterPortletPreferences)
			).disabled(
				customFilterPortletPreferences.isDisabled()
			).field(
				customFilterPortletPreferences.getFilterField()
			).name(
				customFilterPortletPreferences.getQueryName()
			).occur(
				customFilterPortletPreferences.getOccur()
			).parent(
				customFilterPortletPreferences.getParentQueryName()
			).type(
				customFilterPortletPreferences.getFilterQueryType()
			).value(
				_getFilterValue(
					portletSharedSearchSettings, customFilterPortletPreferences)
			).build());
	}

	private Float _getBoost(
		CustomFilterPortletPreferences customFilterPortletPreferences) {

		String boost = customFilterPortletPreferences.getBoost();

		if (Validator.isNull(boost)) {
			return null;
		}

		return GetterUtil.getFloat(boost);
	}

	private String _getFilterValue(
		PortletSharedSearchSettings portletSharedSearchSettings,
		CustomFilterPortletPreferences customFilterPortletPreferences) {

		String filterValue = customFilterPortletPreferences.getFilterValue();

		if (customFilterPortletPreferences.isImmutable()) {
			if (Validator.isNotNull(filterValue)) {
				return filterValue;
			}

			return null;
		}

		String parameterValue = portletSharedSearchSettings.getParameter(
			CustomFilterPortletUtil.getParameterName(
				customFilterPortletPreferences));

		if (parameterValue != null) {
			return parameterValue;
		}

		if (Validator.isNotNull(filterValue)) {
			return filterValue;
		}

		return null;
	}

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

}