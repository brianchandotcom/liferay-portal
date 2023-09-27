/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.model.impl.UserGroupRoleModelImpl;

/**
 * @author Preston Crary
 */
public class UserGroupRoleModelListener
	extends BaseModelListener<UserGroupRole> {

	@Override
	public void onAfterCreate(UserGroupRole userGroupRole) {
		_clearCache(userGroupRole);
		_reindexUser(userGroupRole.getUserId());
	}

	@Override
	public void onAfterRemove(UserGroupRole userGroupRole) {
		_clearCache(userGroupRole);
		_reindexUser(userGroupRole.getUserId());
	}

	@Override
	public void onAfterUpdate(
		UserGroupRole originalUserGroupRole, UserGroupRole userGroupRole) {

		_clearCache(userGroupRole);
		_reindexUser(userGroupRole.getUserId());
	}

	@Override
	public void onBeforeUpdate(
		UserGroupRole originalUserGroupRole, UserGroupRole userGroupRole) {

		UserGroupRoleModelImpl userGroupRoleModelImpl =
			(UserGroupRoleModelImpl)userGroupRole;

		long originalUserId = userGroupRoleModelImpl.getColumnOriginalValue(
			"userId");

		if (originalUserId != userGroupRoleModelImpl.getUserId()) {
			PermissionCacheUtil.clearCache(originalUserId);
		}
	}

	private void _clearCache(UserGroupRole userGroupRole) {
		if (userGroupRole != null) {
			PermissionCacheUtil.clearCache(userGroupRole.getUserId());
		}
	}

	private void _reindexUser(long userId) {
		try {
			Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class);

			indexer.reindex(User.class.getName(), userId);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

}