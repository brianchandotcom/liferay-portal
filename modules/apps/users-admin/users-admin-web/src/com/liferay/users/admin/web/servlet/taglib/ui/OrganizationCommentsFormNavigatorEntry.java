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

package com.liferay.users.admin.web.servlet.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {"service.ranking:Integer=30"},
	service = FormNavigatorEntry.class
)
public class OrganizationCommentsFormNavigatorEntry
	extends BaseOrganizationFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_ORGANIZATION_MISCELLANEOUS;
	}

	@Override
	public String getKey() {
		return "comments";
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		if (organization != null) {
			return true;
		}

		return false;
	}

	@Override
	protected String getJspPath() {
		return "/html/portlet/users_admin/organization/comments.jsp";
	}

}