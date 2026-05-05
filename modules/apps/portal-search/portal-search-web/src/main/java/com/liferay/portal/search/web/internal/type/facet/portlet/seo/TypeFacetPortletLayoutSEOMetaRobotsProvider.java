/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.type.facet.portlet.seo;

import com.liferay.layout.seo.provider.LayoutSEOMetaRobotsProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSEOMetaRobotsProvider;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferencesImpl;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + TypeFacetPortletKeys.TYPE_FACET,
	service = LayoutSEOMetaRobotsProvider.class
)
public class TypeFacetPortletLayoutSEOMetaRobotsProvider
	extends BasePortletLayoutSEOMetaRobotsProvider {

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new TypeFacetPortletPreferencesImpl(
			_objectDefinitionLocalService, portletPreferences,
			_searchableAssetClassNamesProvider);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}