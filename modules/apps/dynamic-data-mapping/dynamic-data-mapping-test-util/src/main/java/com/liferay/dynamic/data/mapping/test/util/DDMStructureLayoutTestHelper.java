/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMStructureLayoutTestHelper {

	public DDMStructureLayoutTestHelper(Group group) throws Exception {
		_group = group;
	}

	public DDMStructureLayout addStructureLayout(
			long structureId, DDMFormLayout ddmFormLayout)
		throws Exception {

		return DDMStructureLayoutLocalServiceUtil.addStructureLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), structureId,
			ddmFormLayout,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	public List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... ddmFormFieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int size = 12 / ddmFormFieldNames.length;

		for (String ddmFormFieldName : ddmFormFieldNames) {
			ddmFormLayoutColumns.add(
				new DDMFormLayoutColumn(size, ddmFormFieldName));
		}

		return ddmFormLayoutColumns;
	}

	public DDMFormLayoutRow createDDMFormLayoutRow(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);

		return ddmFormLayoutRow;
	}

	private final Group _group;

}