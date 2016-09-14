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

package com.liferay.portlet.expando.util.test;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Manuel de la Peña
 */
public class ExpandoTestUtil {

	public static ExpandoColumn addColumn(
			ExpandoTable table, String columnName, int type)
		throws Exception {

		return ExpandoColumnLocalServiceUtil.addColumn(
			table.getTableId(), columnName, type);
	}

	public static ExpandoColumn addColumn(
			ExpandoTable table, String columnName, int type,
			Map<Locale, String> defaultData)
		throws Exception {

		return ExpandoColumnLocalServiceUtil.addColumn(
			table.getTableId(), columnName, type, defaultData);
	}

	public static ExpandoTable addTable(long classNameId, String tableName)
		throws Exception {

		return ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), classNameId, tableName);
	}

	public static ExpandoValue addValue(
			ExpandoTable table, ExpandoColumn column, long classPK, Object data)
		throws Exception {

		return ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(table.getClassNameId()), table.getName(),
			column.getName(), classPK, data);
	}

	public static ExpandoValue addValue(
			ExpandoTable table, ExpandoColumn column, Map<Locale, String> data,
			Locale defaultLocale)
		throws Exception {

		return ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(table.getClassNameId()), table.getName(),
			column.getName(), CounterLocalServiceUtil.increment(), data,
			defaultLocale);
	}

	public static ExpandoValue addValue(
			ExpandoTable table, ExpandoColumn column, Object data)
		throws Exception {

		return addValue(
			table, column, CounterLocalServiceUtil.increment(), data);
	}

	public static ExpandoValue addValue(
			long classNameId, long classPK, Object data)
		throws Exception {

		ExpandoTable table = addTable(
			classNameId, RandomTestUtil.randomString());

		ExpandoColumn column = addColumn(
			table, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING);

		return addValue(table, column, classPK, data);
	}

}