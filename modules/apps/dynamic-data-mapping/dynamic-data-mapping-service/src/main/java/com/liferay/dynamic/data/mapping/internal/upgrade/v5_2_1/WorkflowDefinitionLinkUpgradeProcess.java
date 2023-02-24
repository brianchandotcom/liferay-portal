/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
				_classNameLocalService.getClassNameId(_CLASS_NAME_RECORD_SET));
			preparedStatement.setLong(
				2,
				_classNameLocalService.getClassNameId(
					_CLASS_NAME_FORM_INSTANCE));

			preparedStatement.execute();
		}
	}

	private static final String _CLASS_NAME_FORM_INSTANCE =
		"com.liferay.dynamic.data.mapping.model.DDMFormInstance";

	private static final String _CLASS_NAME_RECORD_SET =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

	private final ClassNameLocalService _classNameLocalService;

}