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

package com.liferay.dynamic.data.mapping.form.web.internal.layout.type.access.policy;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.layout.type.constants.DDMFormPortletLayoutTypeConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "layout.type=" + DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE,
	service = LayoutTypeAccessPolicy.class
)
public class DDMFormPortletLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public boolean isAddLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isDeleteLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isUpdateLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	protected boolean isAccessGrantedByPortletOnPage(
		Layout layout, Portlet portlet) {

		if (StringUtil.equalsIgnoreCase(
				portlet.getPortletId(),
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM)) {

			return true;
		}

		return false;
	}

}