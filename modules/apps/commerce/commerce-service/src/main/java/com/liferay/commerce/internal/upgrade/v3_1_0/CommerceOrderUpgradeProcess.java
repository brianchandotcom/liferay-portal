/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v3_1_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.impl.CommerceOrderModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Marco Leo
 */
public class CommerceOrderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(
				CommerceOrderModelImpl.TABLE_NAME, "lastPriceUpdateDate")) {

			String sql =
				"UPDATE %s SET lastPriceUpdateDate = createDate WHERE " +
					"orderStatus = %s";

			runSQL(
				String.format(
					sql, CommerceOrderModelImpl.TABLE_NAME,
					CommerceOrderConstants.ORDER_STATUS_OPEN));
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "couponCode VARCHAR(75)",
				"lastPriceUpdateDate DATE")
		};
	}

}