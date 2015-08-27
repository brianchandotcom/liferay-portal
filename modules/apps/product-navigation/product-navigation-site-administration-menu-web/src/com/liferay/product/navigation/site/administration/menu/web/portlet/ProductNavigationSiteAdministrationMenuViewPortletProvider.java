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

package com.liferay.product.navigation.site.administration.menu.web.portlet;

import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portlet.admin.util.PortalSiteAdministrationMenuApplicationType;
import com.liferay.product.navigation.site.administration.menu.web.constants.ProductNavigationSiteAdministrationMenuPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=" + PortalSiteAdministrationMenuApplicationType.SiteAdministrationMenu.CLASS_NAME
	},
	service = ViewPortletProvider.class
)
public class ProductNavigationSiteAdministrationMenuViewPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletId() {
		return ProductNavigationSiteAdministrationMenuPortletKeys.
			SITE_ADMINISTRATION;
	}

}