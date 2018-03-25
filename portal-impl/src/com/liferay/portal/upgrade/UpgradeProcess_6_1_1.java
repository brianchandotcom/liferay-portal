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
import com.liferay.portal.upgrade.v6_1_1.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayout;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayoutSet;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayoutSetBranch;
import com.liferay.portal.upgrade.v6_1_1.UpgradeSchema;

/**
 * @author Julio Camarero
 */
public class UpgradeProcess_6_1_1 extends Pre7UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_1_1_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSchema());

		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeLayout());
		upgrade(new UpgradeLayoutSet());
		upgrade(new UpgradeLayoutSetBranch());

		clearIndexesCache();
	}

}