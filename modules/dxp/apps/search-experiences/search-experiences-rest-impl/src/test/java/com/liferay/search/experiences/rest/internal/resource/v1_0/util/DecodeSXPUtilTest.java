/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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

		UiConfiguration uiConfiguration =
			elementDefinition.getUiConfiguration();

		FieldSet[] fieldSets = uiConfiguration.getFieldSets();

		Assert.assertEquals(Arrays.toString(fieldSets), 1, fieldSets.length);

		Field[] fields = fieldSets[0].getFields();

		Assert.assertEquals(Arrays.toString(fields), 1, fields.length);

		Field field = fields[0];

		Assert.assertEquals("entry_class_names", field.getName());
		Assert.assertEquals("multiselect", field.getType());

		Assert.assertNotNull(field.getDefaultValue());
	}

	private static JSONObject _entryClassNameOption(String entryClassName) {
		return JSONUtil.put(
			"label", entryClassName
		).put(
			"value", entryClassName
		);
	}

	private static final String _ELEMENT_JSON_WITH_MULTISELECT = JSONUtil.put(
		"elementDefinition",
		JSONUtil.put(
			"uiConfiguration",
			JSONUtil.put(
				"fieldSets",
				JSONUtil.putAll(
					JSONUtil.put(
						"fields",
						JSONUtil.putAll(
							JSONUtil.put(
								"defaultValue",
								JSONUtil.putAll(
									_entryClassNameOption(
										"com.liferay.blogs.model.BlogsEntry"),
									_entryClassNameOption(
										"com.liferay.journal.model." +
											"JournalArticle"))
							).put(
								"label", "Entry Class Names"
							).put(
								"name", "entry_class_names"
							).put(
								"type", "multiselect"
							))))))
	).toString();

}