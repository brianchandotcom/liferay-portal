/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeUser extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateContact();
	}

	protected void updateContact() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portal.model.User");

			runSQL("update Contact_ set classNameId = " + classNameId);

			StringBundler sb = new StringBundler(5);

			sb.append("update Contact_ set classPK = (select User_.userId ");
			sb.append("from User_ where User_.contactId = ");
			sb.append("Contact_.contactId), emailAddress = (select ");
			sb.append("User_.emailAddress from User_ where User_.contactId = ");
			sb.append("Contact_.contactId)");

			runSQL(sb.toString());
		}
	}

}