/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.model.dao.ReleaseDAO;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pei-Jung Lan
 */
public class UpgradePersonalMenu extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		ReleaseDAO releaseDAO = new ReleaseDAO();

		releaseDAO.addRelease(
			connection, "com.liferay.product.navigation.personal.menu.web");
	}

}