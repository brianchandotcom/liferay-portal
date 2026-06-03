/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutSetSEORobotsContributor;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSetSEORobotsContributor;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + CustomFilterPortletKeys.CUSTOM_FILTER,
	service = LayoutSetSEORobotsContributor.class
)
public class CustomFilterPortletLayoutSetSEORobotsContributor
	extends BasePortletLayoutSetSEORobotsContributor {

	@Override
	protected String getPortletId() {
		return CustomFilterPortletKeys.CUSTOM_FILTER;
	}

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new CustomFilterPortletPreferencesImpl(portletPreferences);
	}

}