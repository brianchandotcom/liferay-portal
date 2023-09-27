/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceModifierUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update CommercePriceModifier set target = 'product-groups' " +
				"where target = 'pricing-classes'");
		runSQL(
			"update CommercePriceModifier set modifierType = 'fixed-amount' " +
				"where modifierType = 'absolute'");
		runSQL(
			"update CommercePriceModifier set modifierType = 'replace' where " +
				"modifierType = 'override'");
	}

}