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

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.search.web.internal.modified.facet.builder.ModifiedFacetBuilder;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferences;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@Component(
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
			_buildFacet(
				modifiedFacetPortletPreferences, portletSharedSearchSettings));
	}

	@Activate
	protected void activate() {
		_dateRangeFactory = new DateRangeFactory(_dateFormatFactory);
	}

	private Facet _buildFacet(
		ModifiedFacetPortletPreferences modifiedFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ModifiedFacetBuilder modifiedFacetBuilder = new ModifiedFacetBuilder(
			_modifiedFacetFactory, _dateFormatFactory, _jsonFactory);

		modifiedFacetBuilder.setOrder(
			modifiedFacetPortletPreferences.getOrder());
		modifiedFacetBuilder.setRangesJSONArray(
			_dateRangeFactory.replaceAliases(
				modifiedFacetPortletPreferences.getRangesJSONArray(),
				CalendarFactoryUtil.getCalendar(), _jsonFactory));
		modifiedFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		String parameterName =
			modifiedFacetPortletPreferences.getParameterName();

		modifiedFacetBuilder.setSelectedRanges(
			portletSharedSearchSettings.getParameterValues(parameterName));
		modifiedFacetBuilder.setCustomRangeFrom(
			portletSharedSearchSettings.getParameter(parameterName + "From"));
		modifiedFacetBuilder.setCustomRangeTo(
			portletSharedSearchSettings.getParameter(parameterName + "To"));

		return modifiedFacetBuilder.build();
	}

	@Reference
	private DateFormatFactory _dateFormatFactory;

	private volatile DateRangeFactory _dateRangeFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ModifiedFacetFactory _modifiedFacetFactory;

}