/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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