/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_2_1;

import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author István András Dézsi
 */
public class WorkflowDefinitionLinkUpgradeProcess extends UpgradeProcess {

	public WorkflowDefinitionLinkUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update WorkflowDefinitionLink set classNameId = ? where " +
					"classNameId = ? and classPK in (select recordSetId from " +
						"DDLRecordSet)")) {

			preparedStatement.setLong(
				1,
				_classNameLocalService.getClassNameId(
					"com.liferay.dynamic.data.lists.model.DDLRecordSet"));
			preparedStatement.setLong(
				2,
				_classNameLocalService.getClassNameId(
					"com.liferay.dynamic.data.mapping.model.DDMFormInstance"));

			preparedStatement.execute();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}