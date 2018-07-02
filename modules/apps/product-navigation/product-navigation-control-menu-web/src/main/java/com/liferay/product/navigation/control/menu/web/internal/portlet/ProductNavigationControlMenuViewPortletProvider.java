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

package com.liferay.product.navigation.control.menu.web.internal.portlet;

import com.liferay.admin.kernel.util.PortalProductNavigationControlMenuApplicationType;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.product.navigation.control.menu.web.internal.constants.ProductNavigationControlMenuPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of Judson (7.1.x), with a replacement. Theme developers must
 *             eventually switch from using Velocity  templates that leverage
 *             this taglib wrapper mechanism, to using FreeMarker templates that
 *             leverage the <code>liferay-product-navigation:control-menu</code>
 *             tag.
 */
@Component(
	immediate = true,
	property = "model.class.name=" + PortalProductNavigationControlMenuApplicationType.ProductNavigationControlMenu.CLASS_NAME,
	service = ViewPortletProvider.class
)
@Deprecated
public class ProductNavigationControlMenuViewPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return ProductNavigationControlMenuPortletKeys.
			PRODUCT_NAVIGATION_CONTROL_MENU;
	}

}