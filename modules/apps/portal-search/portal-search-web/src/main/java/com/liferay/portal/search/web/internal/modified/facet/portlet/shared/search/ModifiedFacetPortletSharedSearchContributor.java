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

package com.liferay.portal.search.web.internal.modified.facet.portlet.shared.search;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.search.web.internal.modified.facet.builder.ModifiedFacetBuilder;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferences;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ModifiedFacetPortletKeys.MODIFIED_FACET,
	service = PortletSharedSearchContributor.class
)
public class ModifiedFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ModifiedFacetPortletPreferences modifiedFacetPortletPreferences =
			new ModifiedFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		portletSharedSearchSettings.addFacet(
			buildFacet(
				modifiedFacetPortletPreferences, portletSharedSearchSettings));
	}

	protected Facet buildFacet(
		ModifiedFacetPortletPreferences modifiedFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ModifiedFacetBuilder modifiedFacetBuilder = new ModifiedFacetBuilder(
			modifiedFacetFactory, getCalendarFactory(), getDateFormatFactory(),
			getJSONFactory());

		modifiedFacetBuilder.setRangesJSONArray(
			replaceAliases(
				modifiedFacetPortletPreferences.getRangesJSONArray()));

		modifiedFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		String parameterName =
			modifiedFacetPortletPreferences.getParameterName();

		modifiedFacetBuilder.setSelectedRanges(
			portletSharedSearchSettings.getParameterValues(parameterName));

		SearchOptionalUtil.copy(
			() -> portletSharedSearchSettings.getParameterOptional(
				parameterName + "From"),
			modifiedFacetBuilder::setCustomRangeFrom);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchSettings.getParameterOptional(
				parameterName + "To"),
			modifiedFacetBuilder::setCustomRangeTo);

		return modifiedFacetBuilder.build();
	}

	protected CalendarFactory getCalendarFactory() {

		// See LPS-72507 and LPS-76500

		if (calendarFactory != null) {
			return calendarFactory;
		}

		return CalendarFactoryUtil.getCalendarFactory();
	}

	protected DateFormatFactory getDateFormatFactory() {

		// See LPS-72507 and LPS-76500

		if (dateFormatFactory != null) {
			return dateFormatFactory;
		}

		return DateFormatFactoryUtil.getDateFormatFactory();
	}

	protected DateRangeFactory getDateRangeFactory() {
		if (dateRangeFactory == null) {
			dateRangeFactory = new DateRangeFactory(getDateFormatFactory());
		}

		return dateRangeFactory;
	}

	protected JSONFactory getJSONFactory() {

		// See LPS-72507 and LPS-76500

		if (jsonFactory != null) {
			return jsonFactory;
		}

		return JSONFactoryUtil.getJSONFactory();
	}

	protected JSONArray replaceAliases(JSONArray rangesJSONArray) {
		DateRangeFactory dateRangeFactory = getDateRangeFactory();

		CalendarFactory calendarFactory = getCalendarFactory();

		return dateRangeFactory.replaceAliases(
			rangesJSONArray, calendarFactory.getCalendar(), getJSONFactory());
	}

	protected CalendarFactory calendarFactory;
	protected DateFormatFactory dateFormatFactory;
	protected DateRangeFactory dateRangeFactory;
	protected JSONFactory jsonFactory;

	@Reference
	protected ModifiedFacetFactory modifiedFacetFactory;

}