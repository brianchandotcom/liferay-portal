/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.Field;
import com.liferay.search.experiences.rest.dto.v1_0.FieldSet;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.UiConfiguration;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Selena Aungst
 */
public class DecodeSXPUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDecodeSXPElementPreservesMultiselectDefaultValue()
		throws Exception {

		SXPElement sxpElement = SXPElement.unsafeToDTO(
			_ELEMENT_JSON_WITH_MULTISELECT);

		DecodeSXPUtil.decodeSXPElement(sxpElement);

		ElementDefinition elementDefinition = sxpElement.getElementDefinition();

		Assert.assertNotNull(elementDefinition);

		UiConfiguration uiConfiguration =
			elementDefinition.getUiConfiguration();

		Assert.assertNotNull(uiConfiguration);

		FieldSet[] fieldSets = uiConfiguration.getFieldSets();

		Assert.assertNotNull(fieldSets);
		Assert.assertEquals(Arrays.toString(fieldSets), 1, fieldSets.length);

		Field[] fields = fieldSets[0].getFields();

		Assert.assertNotNull(fields);
		Assert.assertEquals(Arrays.toString(fields), 1, fields.length);

		Field field = fields[0];

		Assert.assertEquals("multiselect", field.getType());
		Assert.assertEquals("entry_class_names", field.getName());

		Assert.assertNotNull(field.getDefaultValue());
	}

	private static final String _ELEMENT_JSON_WITH_MULTISELECT =
		StringBundler.concat(
			"{\"elementDefinition\": {\"uiConfiguration\": {\"fieldSets\": ",
			"[{\"fields\": [{\"defaultValue\": [{\"label\": ",
			"\"com.liferay.blogs.model.BlogsEntry\", \"value\": ",
			"\"com.liferay.blogs.model.BlogsEntry\"}, {\"label\": ",
			"\"com.liferay.journal.model.JournalArticle\", \"value\": ",
			"\"com.liferay.journal.model.JournalArticle\"}], \"label\": ",
			"\"Entry Class Names\", \"name\": \"entry_class_names\", ",
			"\"type\": \"multiselect\"}]}]}}}");

}