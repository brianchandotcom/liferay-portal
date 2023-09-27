/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.product.menu.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class PortletPreferencesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deletePortletPreferences("145");
		_deletePortletPreferences("160");
		_deletePortletPreferences("190");
	}

	private void _deletePortletPreferences(String portletId) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet preferences for portlet " + portletId);
		}

		runSQL(
			"delete from PortletPreferences where portletId = '" + portletId +
				"'");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesUpgradeProcess.class);

}