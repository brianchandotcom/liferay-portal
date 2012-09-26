/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v6_0_3.UpgradeAsset;
import com.liferay.portal.upgrade.v6_0_3.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_0_3.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_0_3.UpgradeLayout;
import com.liferay.portal.upgrade.v6_0_3.UpgradeLookAndFeel;
import com.liferay.portal.upgrade.v6_0_3.UpgradePermission;
import com.liferay.portal.upgrade.v6_0_3.UpgradeSchema;
import com.liferay.portal.upgrade.v6_0_3.UpgradeScopes;
import com.liferay.portal.upgrade.v6_0_3.UpgradeSitemap;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_6_0_3 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_0_3_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeAsset.class);
		upgrade(UpgradeAssetPublisher.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeLayout.class);
		upgrade(UpgradeLookAndFeel.class);
		upgrade(UpgradePermission.class);
		upgrade(UpgradeScopes.class);
		upgrade(UpgradeSitemap.class);
	}

}