/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = {})
public class DateFacetPortletFeatureFlagHandler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (FeatureFlagManagerUtil.isEnabled("LPS-153839")) {
			componentContext.enableComponent(DateFacetPortlet.class.getName());
		}
		else {
			componentContext.disableComponent(DateFacetPortlet.class.getName());
		}
	}

}