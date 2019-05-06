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
 * @author     Brian Wing Shun Chan
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
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
			FinderCacheUtil.clearCache(GroupImpl.class.getName());
		}
	}

}