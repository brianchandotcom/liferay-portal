/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.xml.SAXReaderImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jürgen Kappler
 */
public class JournalConverterImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpdateContentDynamicElement() {
		_testUpdateContentDynamicElement(StringPool.BLANK, null);

		String value = RandomTestUtil.randomString();

		_testUpdateContentDynamicElement(value, value);
	}

	private DDMFormField _createDDMFormField(
		String dataType, boolean localizable, String name, String type) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setLocalizable(localizable);

		return ddmFormField;
	}

	private void _testUpdateContentDynamicElement(
		String expectedValue, String value) {

		DDMFormField ddmFormField = _createDDMFormField(
			"string", true, "field1", "text");

		SAXReaderImpl saxReaderImpl = new SAXReaderImpl();

		Document document = saxReaderImpl.createDocument();

		Element rootElement = document.addElement("root");

		Field field = new Field(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(), value);

		ReflectionTestUtil.invoke(
			new JournalConverterImpl(), "_updateContentDynamicElement",
			new Class<?>[] {
				int.class, DDMFormField.class, Element.class, Field.class
			},
			0, ddmFormField, rootElement, field);

		Assert.assertEquals(expectedValue, rootElement.getStringValue());
	}

}