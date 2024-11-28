/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.upgrade.UpgradeException;

/**
 * @author Mariano Álvaro Sáiz
 */
public interface StartupHelper {

	public void initResourceActions();

	public boolean isDBNew();

	public boolean isDBWarmed();

	public boolean isNewRelease();

	public boolean isUpgrading();

	public void printPatchLevel();

	public void setDBNew(boolean dbNew);

	public void setNewRelease(boolean newRelease);

	public void setUpgrading(boolean upgrading);

	public void upgradeProcess(int buildNumber) throws UpgradeException;

	public void verifyRequiredSchemaVersion() throws Exception;

}