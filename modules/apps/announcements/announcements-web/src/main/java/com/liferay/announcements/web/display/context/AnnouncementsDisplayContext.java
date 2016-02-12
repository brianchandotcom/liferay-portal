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

package com.liferay.announcements.web.display.context;

import com.liferay.portal.kernel.display.context.DisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;

import java.text.Format;

import java.util.LinkedHashMap;

/**
 * @author Adolfo Pérez
 */
public interface AnnouncementsDisplayContext extends DisplayContext {

	public LinkedHashMap<Long, long[]> getAnnouncementScopes()
		throws PortalException;

	public Format getDateFormatDate();

	public int getPageDelta();

	public String getTabs1Names();

	public String getTabs1PortletURL();

	public boolean isCustomizeAnnouncementsDisplayed();

	public boolean isScopeGroupSelected(Group scopeGroup);

	public boolean isScopeOrganizationSelected(Organization organization);

	public boolean isScopeRoleSelected(Role role);

	public boolean isScopeUserGroupSelected(UserGroup userGroup);

	public boolean isShowEntries();

	public boolean isShowManageEntries();

	public boolean isShowPreview();

	public boolean isShowScopeName();

	public boolean isTabs1Visible();

}