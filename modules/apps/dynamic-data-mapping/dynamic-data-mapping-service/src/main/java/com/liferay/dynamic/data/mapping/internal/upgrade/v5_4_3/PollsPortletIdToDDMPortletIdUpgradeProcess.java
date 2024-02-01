/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_3;

/**
 * @author Carolina Barbosa
 */
public class PollsPortletIdToDDMPortletIdUpgradeProcess
	extends com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_4.
				PollsPortletIdToDDMPortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removeDuplicatePortletPreferences();

		super.doUpgrade();
	}

}