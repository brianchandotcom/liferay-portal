/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.upgrade.util.LayoutTypeSettingsUtil;

/**
 * @author Preston Crary
 */
public class MailReaderUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_mail_reader_web_portlet_MailPortlet");

		deleteFromClassName(
			"com.liferay.mail.reader.model.Account",
			"com.liferay.mail.reader.model.Attachment",
			"com.liferay.mail.reader.model.Folder",
			"com.liferay.mail.reader.model.Message");

		deleteFromPortlet("com_liferay_mail_reader_web_portlet_MailPortlet");

		deleteFromPortletPreferences(
			"com_liferay_mail_reader_web_portlet_MailPortlet");

		deleteFromRelease(
			"com.liferay.mail.reader.service", "com.liferay.mail.reader.web");

		deleteFromResourcePermission(
			"com.liferay.mail.reader.model.Account",
			"com.liferay.mail.reader.model.Attachment",
			"com.liferay.mail.reader.model.Folder",
			"com.liferay.mail.reader.model.Message");

		deleteFromServiceComponent("Mail");

		dropTables(
			"Mail_Account", "Mail_Attachment", "Mail_Folder", "Mail_Message");
	}

}