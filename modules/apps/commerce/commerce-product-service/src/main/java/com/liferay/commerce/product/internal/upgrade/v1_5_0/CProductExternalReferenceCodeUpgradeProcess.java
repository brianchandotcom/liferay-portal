/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v1_5_0;

import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CProductImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CProductExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(CProductImpl.TABLE_NAME, "externalReferenceCode")) {
			Class<CProductExternalReferenceCodeUpgradeProcess> clazz =
				CProductExternalReferenceCodeUpgradeProcess.class;

			String template = StringUtil.read(
				clazz.getResourceAsStream(
					"dependencies" +
						"/CProductExternalReferenceCodeUpgradeProcess.sql"));

			runSQLTemplateString(template, false);

			alterTableDropColumn(
				CPDefinitionImpl.TABLE_NAME, "externalReferenceCode");
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CProduct", "externalReferenceCode VARCHAR(75)")
		};
	}

}