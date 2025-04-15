/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class DDMFieldAttributeImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetAttributeValue() {
		DDMFieldAttribute ddmFieldAttribute = new DDMFieldAttributeImpl();

		ddmFieldAttribute.setAttributeValue(_repeat("a", 150));

		Assert.assertEquals(
			StringPool.BLANK, ddmFieldAttribute.getLargeAttributeValue());
		Assert.assertEquals(
			ddmFieldAttribute.getAttributeValue(),
			ddmFieldAttribute.getSmallAttributeValue());

		ddmFieldAttribute.setAttributeValue(_repeat("á", 150));

		Assert.assertEquals(
			ddmFieldAttribute.getAttributeValue(),
			ddmFieldAttribute.getLargeAttributeValue());
		Assert.assertEquals(
			StringPool.BLANK, ddmFieldAttribute.getSmallAttributeValue());
	}

	private String _repeat(String string, int times) {
		StringBundler sb = new StringBundler(times);

		for (int i = 0; i < times; i++) {
			sb.append(string);
		}

		return sb.toString();
	}

}