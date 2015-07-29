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

package com.liferay.product.menu.web.layout;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.DefaultLayoutTypeAccessPolicyImpl;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"layout.type=" + LayoutConstants.TYPE_USER_PERSONAL_PANEL},
	service = LayoutTypeAccessPolicy.class
)
public class UserPersonalPanelLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public void checkAccessAllowedToPortlet(
		HttpServletRequest request, Layout layout, Portlet portlet) {
	}

}