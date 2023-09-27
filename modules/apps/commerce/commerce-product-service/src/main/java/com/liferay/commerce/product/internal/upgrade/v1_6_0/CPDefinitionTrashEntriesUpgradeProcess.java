/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v1_6_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luca Pellizzon
 */
public class CPDefinitionTrashEntriesUpgradeProcess extends UpgradeProcess {

	public CPDefinitionTrashEntriesUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {

		// CPDefinition

		String template = "DELETE FROM TrashEntry WHERE classNameId = '%s';";

		runSQLTemplateString(
			String.format(
				template,
				_classNameLocalService.getClassNameId(
					CPDefinition.class.getName())),
			false);

		// CPInstance

		runSQLTemplateString(
			String.format(
				template,
				_classNameLocalService.getClassNameId(
					CPInstance.class.getName())),
			false);
	}

	private final ClassNameLocalService _classNameLocalService;

}