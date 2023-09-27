/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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