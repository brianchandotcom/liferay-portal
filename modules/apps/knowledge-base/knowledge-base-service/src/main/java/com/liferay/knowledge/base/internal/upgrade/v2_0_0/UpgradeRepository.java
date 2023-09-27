/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.BaseRepositoryUpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class UpgradeRepository extends BaseRepositoryUpgradeProcess {

	@Override
	protected String[][] getRenamePortletNamesArray() {
		return new String[][] {
			{
				"com.liferay.knowledgebase.admin.portlet.AdminPortlet",
				"com.liferay.knowledge.base"
			},
			{"3_WAR_knowledgebaseportlet", "com.liferay.knowledge.base"}
		};
	}

}