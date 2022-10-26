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

package com.liferay.user.groups.admin.internal.search;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = BaseSearcher.class
)
public class UserGroupSearcher extends BaseSearcher {

	public static final String CLASS_NAME = UserGroup.class.getName();

	public UserGroupSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.UID, Field.USER_GROUP_ID);
		setFilterSearch(true);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}