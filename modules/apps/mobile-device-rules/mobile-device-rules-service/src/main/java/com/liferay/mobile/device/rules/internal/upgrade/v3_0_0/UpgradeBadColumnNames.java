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

package com.liferay.mobile.device.rules.internal.upgrade.v3_0_0;

import com.liferay.mobile.device.rules.internal.upgrade.v3_0_0.util.MDRActionTable;
import com.liferay.mobile.device.rules.internal.upgrade.v3_0_0.util.MDRRuleGroupTable;
import com.liferay.mobile.device.rules.internal.upgrade.v3_0_0.util.MDRRuleTable;
import com.liferay.portal.kernel.upgrade.BaseUpgradeBadColumnNames;

/**
 * @author Tina Tian
 */
public class UpgradeBadColumnNames extends BaseUpgradeBadColumnNames {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBadColumnNames(MDRActionTable.class, "description");
		upgradeBadColumnNames(MDRRuleGroupTable.class, "description");
		upgradeBadColumnNames(MDRRuleTable.class, "description");
	}

}