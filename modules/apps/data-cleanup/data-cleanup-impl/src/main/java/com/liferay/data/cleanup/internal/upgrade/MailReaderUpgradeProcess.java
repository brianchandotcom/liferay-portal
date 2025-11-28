/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Preston Crary
 */
public class MailReaderUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.mail.reader.web"},
			new String[] {"1_WAR_mailportlet"},
			new String[] {"com_liferay_mail_reader_web_portlet_MailPortlet"});

		removeServiceData(
			"Mail", new String[] {"com.liferay.mail.reader.service"},
			new String[] {
				"com.liferay.mail.reader.model.Account",
				"com.liferay.mail.reader.model.Attachment",
				"com.liferay.mail.reader.model.Folder",
				"com.liferay.mail.reader.model.Message"
			},
			new String[] {
				"Mail_Account", "Mail_Attachment", "Mail_Folder", "Mail_Message"
			});
	}

}