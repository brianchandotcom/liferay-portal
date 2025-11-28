/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Eudaldo Alonso
 */
public class WysiwygUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.wysiwyg.web"},
			new String[] {"1_WAR_wysiwygportlet"},
			new String[] {"com_liferay_wysiwyg_web_portlet_WYSIWYGPortlet"});
	}

}