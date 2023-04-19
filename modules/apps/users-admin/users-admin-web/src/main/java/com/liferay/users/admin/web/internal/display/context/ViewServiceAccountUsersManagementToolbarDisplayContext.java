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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

/**
 * @author Pei-Jung Lan
 */
public class ViewServiceAccountUsersManagementToolbarDisplayContext
	extends ViewFlatUsersManagementToolbarDisplayContext {

	public ViewServiceAccountUsersManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<User> searchContainer, boolean showDeleteButton,
		boolean showRestoreButton) {

		super(
			liferayPortletRequest, liferayPortletResponse, searchContainer,
			showDeleteButton, showRestoreButton);
	}

	@Override
	public Boolean isShowCreationMenu() {
		return false;
	}

}