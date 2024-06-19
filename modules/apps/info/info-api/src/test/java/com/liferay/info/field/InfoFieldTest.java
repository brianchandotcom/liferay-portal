/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field;

import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class InfoFieldTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		InfoField<?> infoField1 = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			"uniquedId"
		).name(
			"text"
		).build();

		InfoField<?> infoField2 = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			"uniquedId"
		).name(
			"text"
		).build();

		Assert.assertEquals(infoField1, infoField2);
	}

	@Test
	public void testNotEquals() {
		InfoField<?> infoField1 = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			RandomTestUtil.randomString()
		).name(
			"text"
		).build();

		InfoField<?> infoField2 = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).uniqueId(
			RandomTestUtil.randomString()
		).name(
			"text"
		).build();

		Assert.assertNotEquals(infoField1, infoField2);
	}

}