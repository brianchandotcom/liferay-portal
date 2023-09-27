/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormFieldTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDataSourceTypeInvalid() {
		DDMFormField ddmFormField = new DDMFormField("select1", "select");

		ddmFormField.setProperty("dataSourceType", 1);

		Assert.assertEquals(StringPool.BLANK, ddmFormField.getDataSourceType());
	}

	@Test
	public void testGetDataSourceTypeValid() {
		DDMFormField ddmFormField1 = new DDMFormField("select1", "select");

		ddmFormField1.setProperty(
			"dataSourceType",
			JSONFactoryUtil.createJSONArray(
				new String[] {"manual", "data-provider", "from-autofill"}));

		DDMFormField ddmFormField2 = new DDMFormField("select2", "select");

		ddmFormField2.setProperty("dataSourceType", "manual");

		Assert.assertEquals(
			ddmFormField1.getDataSourceType(),
			ddmFormField2.getDataSourceType());
	}

}