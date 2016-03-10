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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Hugo Huijser
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateResourcePermissions();
	}

	protected void updateResourcePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(11);

			sb.append("update ResourcePermission set scope = ");
			sb.append(ResourceConstants.SCOPE_GROUP_TEMPLATE);
			sb.append(", primKey = '");
			sb.append(GroupConstants.DEFAULT_PARENT_GROUP_ID);
			sb.append("' where scope = ");
			sb.append(ResourceConstants.SCOPE_COMPANY);
			sb.append(
				" and primKey = CAST_TEXT(companyId) and exists (select ");
			sb.append("roleId from Role_ where Role_.roleId = ");
			sb.append("ResourcePermission.roleId and Role_.type_ = ");
			sb.append(RoleConstants.TYPE_PROVIDER);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			runSQL(sb.toString());
		}
	}

}