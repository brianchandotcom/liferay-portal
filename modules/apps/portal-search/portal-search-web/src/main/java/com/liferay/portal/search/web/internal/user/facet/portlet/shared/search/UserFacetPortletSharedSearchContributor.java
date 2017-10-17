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

package com.liferay.portal.search.web.internal.user.facet.portlet.shared.search;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetBuilder;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortletPreferences;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + UserFacetPortletKeys.USER_FACET
)
public class UserFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		UserFacetPortletPreferences userFacetPortletPreferences =
			new UserFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Facet facet = buildFacet(
			userFacetPortletPreferences, portletSharedSearchSettings);

		portletSharedSearchSettings.addFacet(facet);
	}

	protected Facet buildFacet(
		UserFacetPortletPreferences userFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		UserFacetBuilder userFacetBuilder = new UserFacetBuilder(
			userFacetFactory);

		userFacetBuilder.setFrequencyThreshold(
			userFacetPortletPreferences.getFrequencyThreshold());
		userFacetBuilder.setMaxTerms(userFacetPortletPreferences.getMaxTerms());
		userFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		SearchOptionalUtil.copy(
			() -> portletSharedSearchSettings.getParameterValues(
				userFacetPortletPreferences.getParameterName()),
			userFacetBuilder::setSelectedUserNames);

		return userFacetBuilder.build();
	}

	@Reference
	protected UserFacetFactory userFacetFactory;

}