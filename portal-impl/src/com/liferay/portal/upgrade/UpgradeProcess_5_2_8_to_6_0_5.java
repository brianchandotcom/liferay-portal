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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.UpgradeSchema;
import com.liferay.portal.upgrade.v6_0_0.UpgradeAsset;
import com.liferay.portal.upgrade.v6_0_0.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_0_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v6_0_0.UpgradeExpando;
import com.liferay.portal.upgrade.v6_0_0.UpgradePolls;
import com.liferay.portal.upgrade.v6_0_0.UpgradePortletId;
import com.liferay.portal.upgrade.v6_0_0.UpgradeShopping;
import com.liferay.portal.upgrade.v6_0_2.UpgradeNestedPortlets;
import com.liferay.portal.upgrade.v6_0_3.UpgradeLookAndFeel;
import com.liferay.portal.upgrade.v6_0_3.UpgradePermission;
import com.liferay.portal.upgrade.v6_0_3.UpgradeScopes;
import com.liferay.portal.upgrade.v6_0_3.UpgradeSitemap;
import com.liferay.portal.upgrade.v6_0_5.UpgradeJournal;
import com.liferay.portal.upgrade.v6_0_5.UpgradeLayout;

/**
 * @author Douglas Wong
 */
public class UpgradeProcess_5_2_8_to_6_0_5 extends Pre7UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_0_5_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);

		upgrade(UpgradeAsset.class);
		upgrade(UpgradeAssetPublisher.class);
		upgrade(UpgradeBlogs.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradePolls.class);
		upgrade(UpgradePortletId.class);
		upgrade(UpgradeShopping.class);

		upgrade(com.liferay.portal.upgrade.v6_0_2.UpgradeExpando.class);
		upgrade(UpgradeNestedPortlets.class);

		upgrade(com.liferay.portal.upgrade.v6_0_3.UpgradeAsset.class);
		upgrade(com.liferay.portal.upgrade.v6_0_3.UpgradeAssetPublisher.class);
		upgrade(com.liferay.portal.upgrade.v6_0_3.UpgradeDocumentLibrary.class);
		upgrade(UpgradeLookAndFeel.class);
		upgrade(UpgradePermission.class);
		upgrade(UpgradeScopes.class);
		upgrade(UpgradeSitemap.class);

		upgrade(UpgradeJournal.class);
		upgrade(UpgradeLayout.class);

		clearIndexesCache();
	}

}