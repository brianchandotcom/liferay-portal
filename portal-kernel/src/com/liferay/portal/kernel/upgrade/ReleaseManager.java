/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

/**
 * @author Samuel Ziemer
 * @author Alberto Chaparro
 */
public interface ReleaseManager {

	public String getShortStatusMessage(boolean onlyRequiredUpgrades);

	public String getStatusMessage(boolean showUpgradeSteps);

	public boolean isUpgraded() throws Exception;

}