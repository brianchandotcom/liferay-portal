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

package com.liferay.osb.faro.internal.upgrade.v17_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Leilany Ulisses
 */
public class UpgradeFaroProjectEmailDomainUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableName(
			"OSBFaro_FaroProjectEmailAddressDomain",
			"OSBFaro_FaroProjectEmailDomain");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.alterColumnName(
				"OSBFaro_FaroProjectEmailAddressDomain",
				"faroProjectEmailAddressDomainId",
				"faroProjectEmailDomainId LONG not null"),
			UpgradeProcessFactory.alterColumnName(
				"OSBFaro_FaroProjectEmailAddressDomain", "emailAddressDomain",
				"emailDomain VARCHAR(75) null")
		};
	}

}