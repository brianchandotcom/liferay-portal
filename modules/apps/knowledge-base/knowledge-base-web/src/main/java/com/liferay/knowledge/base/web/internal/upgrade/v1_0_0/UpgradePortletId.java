/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.upgrade.v1_0_0;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{"1_WAR_knowledgebaseportlet", KBPortletKeys.KNOWLEDGE_BASE_ADMIN},
			{
				"2_WAR_knowledgebaseportlet",
				KBPortletKeys.KNOWLEDGE_BASE_DISPLAY
			},
			{
				"3_WAR_knowledgebaseportlet",
				KBPortletKeys.KNOWLEDGE_BASE_ARTICLE
			},
			{
				"4_WAR_knowledgebaseportlet",
				KBPortletKeys.KNOWLEDGE_BASE_SECTION
			},
			{"5_WAR_knowledgebaseportlet", KBPortletKeys.KNOWLEDGE_BASE_SEARCH}
		};
	}

}