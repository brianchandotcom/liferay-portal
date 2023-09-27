/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.upgrade.v2_1_0;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		runSQL(
			"UPDATE CommercePriceList SET type_ = '" +
				CommercePriceListConstants.TYPE_PRICE_LIST + "'");

		runSQL("UPDATE CommercePriceList SET catalogBasePriceList = [$FALSE$]");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommercePriceList", "type_ VARCHAR(75)",
				"catalogBasePriceList BOOLEAN")
		};
	}

}