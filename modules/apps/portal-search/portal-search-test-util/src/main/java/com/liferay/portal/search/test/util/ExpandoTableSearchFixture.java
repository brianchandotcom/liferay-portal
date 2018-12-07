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

package com.liferay.portal.search.test.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

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
			Class<?> clazz, int indexType, String... columns)
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
				expandoTable, column, ExpandoColumnConstants.STRING);

			expandoColumns.add(expandoColumn);

			UnicodeProperties unicodeProperties =
				expandoColumn.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

			expandoColumn.setTypeSettingsProperties(unicodeProperties);

			expandoColumnLocalService.updateExpandoColumn(expandoColumn);
		}
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