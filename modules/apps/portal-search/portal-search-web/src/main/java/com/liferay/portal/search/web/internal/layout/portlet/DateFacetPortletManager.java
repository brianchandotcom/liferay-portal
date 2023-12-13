/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.layout.portlet;

import com.liferay.layout.portlet.PortletManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.search.web.internal.date.facet.constants.DateFacetPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "javax.portlet.name=" + DateFacetPortletKeys.DATE_FACET,
	service = PortletManager.class
)
public class DateFacetPortletManager implements PortletManager {

	@Override
	public boolean isVisble(Layout layout) {
		if (FeatureFlagManagerUtil.isEnabled("LPS-153839")) {
			return true;
		}

		return false;
	}

}