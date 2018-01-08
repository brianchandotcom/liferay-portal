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