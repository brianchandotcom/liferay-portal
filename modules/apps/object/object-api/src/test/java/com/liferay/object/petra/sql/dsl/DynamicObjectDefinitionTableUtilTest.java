/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gabriel Albuquerque
 */
public class DynamicObjectDefinitionTableUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore
	@Test
	public void testGetDataType() {
		Assert.assertEquals(
			"BOOLEAN",
			DynamicObjectDefinitionTableUtil.getDataType(
				ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
				ObjectFieldConstants.DB_TYPE_BOOLEAN));
		Assert.assertEquals(
			"VARCHAR(5000)",
			DynamicObjectDefinitionTableUtil.getDataType(
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
				ObjectFieldConstants.DB_TYPE_STRING));
		Assert.assertEquals(
			"VARCHAR(75)",
			DynamicObjectDefinitionTableUtil.getDataType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
				ObjectFieldConstants.DB_TYPE_STRING));
		Assert.assertEquals(
			"VARCHAR(280)",
			DynamicObjectDefinitionTableUtil.getDataType(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING));

		_setDBType(DBType.SQLSERVER);

		Assert.assertEquals(
			"VARCHAR(4000)",
			DynamicObjectDefinitionTableUtil.getDataType(
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
				ObjectFieldConstants.DB_TYPE_STRING));
	}

	private void _setDBType(DBType dbType) {
		DBManager dbManager = Mockito.mock(DBManagerImpl.class);

		Mockito.when(
			dbManager.getDBType()
		).thenReturn(
			dbType
		);

		ReflectionTestUtil.setFieldValue(
			DBManagerUtil.class, "_dbManager", dbManager);
	}

}