/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Shuyang Zhou
 */
public class UpgradePortalPreferenceValue extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update PortalPreferenceValue set namespace = ",
				"'com.liferay.portal.kernel.util.SessionTreeJSClicks' where ",
				"namespace = ",
				"'com.liferay.taglib.ui.util.SessionTreeJSClicks'"));
	}

}