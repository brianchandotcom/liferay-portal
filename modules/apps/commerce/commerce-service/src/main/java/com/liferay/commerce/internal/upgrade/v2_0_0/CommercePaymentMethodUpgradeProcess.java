/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v2_0_0;

import com.liferay.commerce.model.impl.CommerceOrderImpl;
import com.liferay.commerce.model.impl.CommerceOrderPaymentImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentMethodUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(
				CommerceOrderImpl.TABLE_NAME, "commercePaymentMethodId")) {

			alterTableAddColumn(
				"CommerceOrder", "commercePaymentMethodKey", "VARCHAR(75)");

			String template = StringUtil.read(
				CommercePaymentMethodUpgradeProcess.class.getResourceAsStream(
					"dependencies/CommerceOrderUpgradeProcess.sql"));

			runSQLTemplateString(template, false);

			alterTableDropColumn("CommerceOrder", "commercePaymentMethodId");
		}

		if (hasColumn(
				CommerceOrderPaymentImpl.TABLE_NAME,
				"commercePaymentMethodId")) {

			alterTableAddColumn(
				"CommerceOrderPayment", "commercePaymentMethodKey",
				"VARCHAR(75)");

			String template = StringUtil.read(
				CommercePaymentMethodUpgradeProcess.class.getResourceAsStream(
					"dependencies/CommerceOrderPaymentUpgradeProcess.sql"));

			runSQLTemplateString(template, false);

			alterTableDropColumn(
				"CommerceOrderPayment", "commercePaymentMethodId");
		}

		if (hasTable("CommercePaymentMethod")) {
			String template = StringUtil.read(
				CommercePaymentMethodUpgradeProcess.class.getResourceAsStream(
					"dependencies/CommercePaymentMethodUpgradeProcess.sql"));

			runSQLTemplateString(template, false);
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceOrder", "transactionId VARCHAR(75)")
		};
	}

}