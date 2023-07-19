/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v10_0_0;

import com.liferay.commerce.model.impl.CommerceOrderItemModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Stefano Motta
 */
public class CommerceOrderItemUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		if (hasColumn(
				CommerceOrderItemModelImpl.TABLE_NAME, "decimalQuantity")) {

			alterColumnType(
				CommerceOrderItemModelImpl.TABLE_NAME, "quantity",
				"BIGDECIMAL null");
			runSQL(
				"update CommerceOrderItem set quantity = decimalQuantity " +
					"where decimalQuantity is not null and decimalQuantity > " +
						"0");
			alterTableDropColumn(
				CommerceOrderItemModelImpl.TABLE_NAME, "decimalQuantity");
		}
	}

}