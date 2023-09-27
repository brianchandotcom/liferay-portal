/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;

/**
 * @author Preston Crary
 */
public class TeamModelListener extends BaseModelListener<Team> {

	@Override
	public void onAfterAddAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK) {

		if (!associationClassName.equals(User.class.getName())) {
			PermissionCacheUtil.clearCache();
		}
	}

	@Override
	public void onAfterRemove(Team group) {
		PermissionCacheUtil.clearCache();
	}

	@Override
	public void onAfterRemoveAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK) {

		if (!associationClassName.equals(User.class.getName())) {
			PermissionCacheUtil.clearCache();
		}
	}

}