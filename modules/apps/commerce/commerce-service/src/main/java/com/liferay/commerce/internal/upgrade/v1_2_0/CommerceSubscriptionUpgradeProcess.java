/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v1_2_0;

import com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(CommerceSubscriptionEntryImpl.TABLE_NAME)) {
			String template = StringUtil.read(
				CommerceSubscriptionUpgradeProcess.class.getResourceAsStream(
					"dependencies/CommerceSubscriptionEntry.sql"));

			runSQLTemplateString(template, false);
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceOrderItem", "subscription BOOLEAN")
		};
	}

}