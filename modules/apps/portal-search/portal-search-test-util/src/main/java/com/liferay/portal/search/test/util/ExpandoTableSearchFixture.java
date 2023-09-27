/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class ExpandoTableSearchFixture {

	public ExpandoTableSearchFixture(
		ClassNameLocalService classNameLocalService,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService) {

		this.classNameLocalService = classNameLocalService;
		this.expandoColumnLocalService = expandoColumnLocalService;
		this.expandoTableLocalService = expandoTableLocalService;
	}

	public void addExpandoColumn(
			Class<?> clazz, int indexType, int columnType, String... columns)
		throws Exception {

		ExpandoTable expandoTable = expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			classNameLocalService.getClassNameId(clazz), "CUSTOM_FIELDS");

		if (expandoTable == null) {
			expandoTable = expandoTableLocalService.addTable(
				TestPropsValues.getCompanyId(),
				classNameLocalService.getClassNameId(clazz), "CUSTOM_FIELDS");

			expandoTables.add(expandoTable);
		}

		for (String column : columns) {
			ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
				expandoTable, column, columnType);

			expandoColumns.add(expandoColumn);

			UnicodeProperties unicodeProperties =
				expandoColumn.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

			expandoColumn.setTypeSettingsProperties(unicodeProperties);

			expandoColumnLocalService.updateExpandoColumn(expandoColumn);
		}
	}

	public void addExpandoColumn(
			Class<?> clazz, int indexType, String... columns)
		throws Exception {

		addExpandoColumn(
			clazz, indexType, ExpandoColumnConstants.STRING, columns);
	}

	public List<ExpandoColumn> getExpandoColumns() {
		return expandoColumns;
	}

	public List<ExpandoTable> getExpandoTables() {
		return expandoTables;
	}

	protected ClassNameLocalService classNameLocalService;
	protected ExpandoColumnLocalService expandoColumnLocalService;
	protected final List<ExpandoColumn> expandoColumns = new ArrayList<>();
	protected ExpandoTableLocalService expandoTableLocalService;
	protected final List<ExpandoTable> expandoTables = new ArrayList<>();

}