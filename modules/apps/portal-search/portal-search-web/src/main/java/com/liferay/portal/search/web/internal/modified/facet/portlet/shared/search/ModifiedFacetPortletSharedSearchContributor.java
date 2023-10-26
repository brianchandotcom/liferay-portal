/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				portletSharedSearchSettings.getPortletPreferences());

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

		modifiedFacetBuilder.setCustomRangeFrom(
			portletSharedSearchSettings.getParameter(parameterName + "From"));
		modifiedFacetBuilder.setCustomRangeTo(
			portletSharedSearchSettings.getParameter(parameterName + "To"));
		modifiedFacetBuilder.setSelectedRanges(
			portletSharedSearchSettings.getParameterValues(parameterName));

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