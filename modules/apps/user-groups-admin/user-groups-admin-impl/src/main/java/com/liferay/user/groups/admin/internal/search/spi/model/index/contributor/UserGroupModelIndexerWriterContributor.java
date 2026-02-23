/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Luan Maoski
 */
public class UserGroupModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<UserGroup> {

	public UserGroupModelIndexerWriterContributor(
		UserGroupLocalService userGroupLocalService) {

		_userGroupLocalService = userGroupLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _userGroupLocalService.getIndexableActionableDynamicQuery();
	}

	private final UserGroupLocalService _userGroupLocalService;

}