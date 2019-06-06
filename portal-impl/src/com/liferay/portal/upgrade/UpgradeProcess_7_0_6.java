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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_6.UpgradeRepository;
import com.liferay.portal.upgrade.v7_0_6.UpgradeThemeId;

/**
 * @author Alberto Chaparro
 */
public class UpgradeProcess_7_0_6 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_6_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeRepository());
		upgrade(new UpgradeThemeId());

		clearIndexesCache();
	}

	@Override
	protected boolean isSkipUpgradeProcess() throws Exception {
		if (hasColumnType("Repository", "name", "VARCHAR(200) null")) {
			return true;
		}

		return false;
	}

}