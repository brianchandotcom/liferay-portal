/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

/**
 * @author Rafael Praxedes
 */
public class UpgradeDDMFormAdminPortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{_OLD_PORTLET_NAME, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN}
		};
	}

	private static final String _OLD_PORTLET_NAME =
		"com_liferay_dynamic_data_lists_form_web_portlet_DDLFormAdminPortlet";

}