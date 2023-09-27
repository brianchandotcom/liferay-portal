/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.full.page.application.internal.model;

import com.liferay.layout.type.controller.full.page.application.internal.constants.FullPageApplicationLayoutTypeControllerConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "layout.type=" + FullPageApplicationLayoutTypeControllerConstants.LAYOUT_TYPE_FULL_PAGE_APPLICATION,
	service = LayoutTypeAccessPolicy.class
)
public class FullPageApplicationLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	protected boolean isAccessGrantedByPortletOnPage(
		Layout layout, Portlet portlet) {

		String portletId = layout.getTypeSettingsProperty(
			"fullPageApplicationPortlet");

		if (StringUtil.equalsIgnoreCase(portlet.getPortletId(), portletId)) {
			return true;
		}

		return super.isAccessGrantedByPortletOnPage(layout, portlet);
	}

}