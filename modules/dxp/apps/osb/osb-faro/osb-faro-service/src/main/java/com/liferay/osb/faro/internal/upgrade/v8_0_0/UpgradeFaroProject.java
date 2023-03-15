/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.internal.upgrade.v8_0_0;

import com.liferay.osb.faro.internal.upgrade.v8_0_0.util.FaroProjectTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marcellus Tavares
 */
public class UpgradeFaroProject extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			FaroProjectTable.class,
			new AlterTableAddColumn("recommendationsEnabled BOOLEAN"));
	}

}