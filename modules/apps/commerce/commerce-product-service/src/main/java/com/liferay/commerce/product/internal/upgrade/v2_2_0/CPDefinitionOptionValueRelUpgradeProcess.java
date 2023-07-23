/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_2_0;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionValueRelTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			String.format(
				"update %s set priceType = '%s'",
				CPDefinitionOptionRelTable.TABLE_NAME,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC));

		runSQL(
			String.format(
				"update %s set price = 0",
				CPDefinitionOptionValueRelTable.TABLE_NAME));
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CPDefinitionOptionValueRel", "CPInstanceUuid VARCHAR(75)",
				"CProductId LONG", "quantity INTEGER", "price BIGDECIMAL"),
			UpgradeProcessFactory.addColumns(
				"CPDefinitionOptionRel", "priceType VARCHAR(75)")
		};
	}

}