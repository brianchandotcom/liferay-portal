/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class UserStagedModelDataHandler
	extends BaseStagedModelDataHandler<User> {

	public static final String[] CLASS_NAMES = {User.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, User user)
		throws Exception {

		return;
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, User user)
		throws Exception {

		return;
	}

	@Override
	protected boolean validateMissingReference(String uuid, long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			UserLocalServiceUtil.getUserByUuidAndCompanyId(
				uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

}