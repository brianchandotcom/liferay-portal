/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.navigation.web.internal.layout.portlet;

import com.liferay.layout.portlet.PortletManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.wiki.navigation.web.internal.constants.WikiNavigationPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mikel Lorza
 */
@Component(
	property = {
		"javax.portlet.name=" + WikiNavigationPortletKeys.PAGE_MENU,
		"javax.portlet.name=" + WikiNavigationPortletKeys.TREE_MENU
	},
	service = PortletManager.class
)
public class WikiNavigationPortletManager implements PortletManager {

	@Override
	public boolean isVisible(Layout layout) {
		if (!FeatureFlagManagerUtil.isEnabled(
				layout.getCompanyId(), "LPD-35013")) {

			return false;
		}

		return true;
	}

}