/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Preston Crary
 */
public class ChatUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.chat.web"},
			new String[] {"1_WAR_chatportlet"},
			new String[] {"com_liferay_chat_web_portlet_ChatPortlet"});

		removeServiceData(
			"Chat", new String[] {"com.liferay.chat.service"},
			new String[] {
				"com.liferay.chat.model.Entry", "com.liferay.chat.model.Status"
			},
			new String[] {"Chat_Entry", "Chat_Status"});
	}

}