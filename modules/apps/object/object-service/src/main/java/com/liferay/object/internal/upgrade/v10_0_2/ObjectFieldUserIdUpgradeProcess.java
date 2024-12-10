/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_0_2;

import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.BaseUserIdUpgradeProcess;

/**
 * @author Igor Costa
 */
public class ObjectFieldUserIdUpgradeProcess extends BaseUserIdUpgradeProcess {

	public ObjectFieldUserIdUpgradeProcess(UserLocalService userLocalService) {
		super(userLocalService);
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeUserId("objectFieldId", "ObjectField");
	}

}