/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_2_1;

import com.liferay.account.role.AccountRole;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Drew Brokke
 */
public class RoleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		int newType = RoleConstants.TYPE_ACCOUNT;
		int oldType = RoleConstants.TYPE_PROVIDER;

		String className = StringUtil.quote(
			AccountRole.class.getName(), StringPool.APOSTROPHE);

		runSQL(
			StringBundler.concat(
				"update Role_ set type_ = ", newType,
				" where classNameId = (select ClassName_.classNameId from ",
				"ClassName_ where ClassName_.value = ", className,
				") and type_ = ", oldType));
	}

}