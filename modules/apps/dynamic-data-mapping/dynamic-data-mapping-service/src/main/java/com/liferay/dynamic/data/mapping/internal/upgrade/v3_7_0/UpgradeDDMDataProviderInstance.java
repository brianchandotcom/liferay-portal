/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_0;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.portal.kernel.upgrade.BaseLastPublishDateUpgradeProcess;

/**
 * @author Carolina Barbosa
 */
public class UpgradeDDMDataProviderInstance
	extends BaseLastPublishDateUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DDMDataProviderInstance", "lastPublishDate")) {
			addLastPublishDateColumn("DDMDataProviderInstance");

			updateLastPublishDates(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
				"DDMDataProviderInstance");
		}
	}

}