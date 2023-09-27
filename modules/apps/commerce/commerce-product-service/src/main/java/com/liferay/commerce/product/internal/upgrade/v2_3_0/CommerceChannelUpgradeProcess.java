/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_3_0;

import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Riccardo Alberti
 */
public class CommerceChannelUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		runSQL(
			"update CommerceChannel set priceDisplayType = '" +
				CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE + "'");

		runSQL("update CommerceChannel set discountsTargetNetPrice = [$TRUE$]");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceChannel", "priceDisplayType VARCHAR(75)",
				"discountsTargetNetPrice BOOLEAN")
		};
	}

}