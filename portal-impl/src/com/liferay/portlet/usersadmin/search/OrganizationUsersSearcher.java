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

package com.liferay.portlet.usersadmin.search;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;

/**
 * @author Pei-Jung Lan
 */
public class OrganizationUsersSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		Organization.class.getName(), User.class.getName()
	};

	public static Indexer<?> getInstance() {
		return new OrganizationUsersSearcher();
	}

	public OrganizationUsersSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.ORGANIZATION_ID, Field.UID, Field.USER_ID);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

}