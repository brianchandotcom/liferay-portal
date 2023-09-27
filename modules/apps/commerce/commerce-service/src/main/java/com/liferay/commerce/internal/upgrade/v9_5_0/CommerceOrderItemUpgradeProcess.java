/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v9_5_0;

import com.liferay.commerce.model.impl.CommerceOrderItemModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Stefano Motta
 */
public class CommerceOrderItemUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		runSQL("update CommerceOrderItem set priceOnApplication = [$FALSE$]");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				CommerceOrderItemModelImpl.TABLE_NAME,
				"priceOnApplication BOOLEAN")
		};
	}

}