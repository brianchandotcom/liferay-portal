/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.product.menu.web.internal.portlet;

import com.liferay.admin.kernel.util.PortalProductMenuApplicationType;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=" + PortalProductMenuApplicationType.ProductMenu.CLASS_NAME,
	service = PortletProvider.class
)
public class ProductNavigationProductMenuViewPortletProvider
	extends BasePortletProvider {

	@Override
	public String getPortletName() {
		return ProductNavigationProductMenuPortletKeys.
			PRODUCT_NAVIGATION_PRODUCT_MENU;
	}

	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {Action.VIEW};

}