/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.GroupImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUser extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyInactive();
	}

	protected void verifyInactive() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = null;

			DB db = DBManagerUtil.getDB();

			if ((db.getDBType() == DBType.MARIADB) ||
				(db.getDBType() == DBType.MYSQL)) {

				sb = new StringBundler(7);

				sb.append("update Group_ inner join User_ on ");
				sb.append("Group_.companyId = User_.companyId and ");
				sb.append("Group_.classPK = User_.userId set active_ = ");
				sb.append("[$FALSE$] where Group_.classNameId = ");
				sb.append(PortalUtil.getClassNameId(User.class));
				sb.append(" and User_.status = ");
				sb.append(WorkflowConstants.STATUS_INACTIVE);
			}
			else {
				sb = new StringBundler(9);

				sb.append("update Group_ set active_ = [$FALSE$] where ");
				sb.append("groupId in (select Group_.groupId from Group_ ");
				sb.append("inner join User_ on Group_.companyId = ");
				sb.append("User_.companyId and Group_.classPK = User_.userId ");
				sb.append("where Group_.classNameId = ");
				sb.append(PortalUtil.getClassNameId(User.class));
				sb.append(" and User_.status = ");
				sb.append(WorkflowConstants.STATUS_INACTIVE);
				sb.append(")");
			}

			runSQL(sb.toString());

			EntityCacheUtil.clearCache(GroupImpl.class);
			FinderCacheUtil.clearCache(GroupImpl.class);
		}
	}

}