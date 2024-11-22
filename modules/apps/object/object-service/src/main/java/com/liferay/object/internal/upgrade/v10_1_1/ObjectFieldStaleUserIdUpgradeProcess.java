/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_1_1;

import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.BaseStaleUserIdUpgradeProcess;

/**
 * @author Igor Costa
 */
public class ObjectFieldStaleUserIdUpgradeProcess
	extends BaseStaleUserIdUpgradeProcess {

	public ObjectFieldStaleUserIdUpgradeProcess(
		UserLocalService userLocalService) {

		super(userLocalService);
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeUserId("objectFieldId", "ObjectField");
	}

}