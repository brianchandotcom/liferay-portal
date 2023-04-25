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

package com.liferay.feature.flag.web.internal.display;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlags;
import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlagsProvider;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagDisplay;
import com.liferay.feature.flag.web.internal.model.FeatureFlagType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = FeatureFlagsDisplayContextFactory.class)
public class FeatureFlagsDisplayContextFactory {

	public FeatureFlagsDisplayContext create(
		FeatureFlagType featureFlagType,
		HttpServletRequest httpServletRequest) {

		FeatureFlagsDisplayContext featureFlagsDisplayContext =
			new FeatureFlagsDisplayContext();

		Locale locale = _portal.getLocale(httpServletRequest);

		featureFlagsDisplayContext.setDescription(
			featureFlagType.getDescription(locale));

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle", "descriptive");

		featureFlagsDisplayContext.setDisplayStyle(displayStyle);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(portletRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		SearchContainer<FeatureFlagDisplay> searchContainer =
			new SearchContainer<>(
				portletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-feature-flags-were-found");

		searchContainer.setId("accountEntryAccountGroupsSearchContainer");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				portletRequest, ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				"order-by-col", "name"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				portletRequest, ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				"order-by-type", "asc"));

		Predicate<FeatureFlag> predicate = featureFlagType.getPredicate();

		String keywords = ParamUtil.getString(portletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			predicate = predicate.and(
				featureFlag ->
					_contains(locale, featureFlag.getKey(), keywords) ||
					_contains(locale, featureFlag.getTitle(locale), keywords) ||
					_contains(
						locale, featureFlag.getDescription(locale), keywords));
		}

		for (FeatureFlagsManagementToolbarDisplayContext.Filter filter :
				FeatureFlagsManagementToolbarDisplayContext.FILTERS) {

			predicate = predicate.and(filter.getPredicate(httpServletRequest));
		}

		Predicate<FeatureFlag> finalPredicate = predicate;

		CompanyFeatureFlags companyFeatureFlags =
			_companyFeatureFlagsProvider.getOrCreateCompanyFeatureFlags(
				_portal.getCompanyId(httpServletRequest));

		List<FeatureFlagDisplay> featureFlagDisplays = TransformUtil.transform(
			companyFeatureFlags.getFeatureFlags(finalPredicate),
			featureFlag -> new FeatureFlagDisplay(
				companyFeatureFlags.getFeatureFlags(
					featureFlag1 -> ArrayUtil.contains(
						featureFlag.getDependencyKeys(),
						featureFlag1.getKey())),
				featureFlag, locale));

		Comparator<FeatureFlagDisplay> comparator = Comparator.comparing(
			FeatureFlagDisplay::getTitle);

		if (Objects.equals(searchContainer.getOrderByType(), "desc")) {
			comparator = comparator.reversed();
		}

		featureFlagDisplays.sort(comparator);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			searchContainer.getStart(), searchContainer.getEnd(),
			featureFlagDisplays.size());

		searchContainer.setResultsAndTotal(
			() -> featureFlagDisplays.subList(startAndEnd[0], startAndEnd[1]),
			featureFlagDisplays.size());

		featureFlagsDisplayContext.setManagementToolbarDisplayContext(
			new FeatureFlagsManagementToolbarDisplayContext(
				httpServletRequest, liferayPortletRequest,
				liferayPortletResponse, searchContainer));
		featureFlagsDisplayContext.setSearchContainer(searchContainer);

		if (Objects.equals(displayStyle, "descriptive")) {
			featureFlagsDisplayContext.setSearchResultCssClass("list-group");
		}

		featureFlagsDisplayContext.setTitle(featureFlagType.getTitle(locale));

		return featureFlagsDisplayContext;
	}

	private boolean _contains(Locale locale, String s1, String s2) {
		String normalized = _normalize(locale, s1);

		return normalized.contains(_normalize(locale, s2));
	}

	private String _normalize(Locale locale, String string) {
		return StringUtil.toLowerCase(string, locale);
	}

	@Reference
	private CompanyFeatureFlagsProvider _companyFeatureFlagsProvider;

	@Reference
	private Portal _portal;

}