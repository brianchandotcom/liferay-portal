/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class AddObjectFieldCompositeKeyCandidatesMVCResourceCommandTest
	extends BaseMVCResourceCommandTestCase {

	@Test
	public void testAddObjectFieldCompositeKeyCandidates() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						RandomTestUtil.randomLocaleStringMap()
					).name(
						"a" + RandomTestUtil.randomString()
					).required(
						true
					).build(),
					new TextObjectFieldBuilder(
					).labelMap(
						RandomTestUtil.randomLocaleStringMap()
					).localized(
						true
					).name(
						"a" + RandomTestUtil.randomString()
					).required(
						true
					).build()),
				ObjectDefinitionConstants.SCOPE_SITE);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"errorLabel", StringPool.BLANK
			).put(
				"status", "success"
			).toString(),
			String.valueOf(
				getJSONObject(
					HashMapBuilder.put(
						"objectDefinitionId",
						String.valueOf(objectDefinition.getObjectDefinitionId())
					).put(
						"objectFieldsIds",
						() -> {
							StringBundler sb = new StringBundler();

							for (ObjectField objectField :
									_objectFieldLocalService.getObjectFields(
										objectDefinition.
											getObjectDefinitionId())) {

								sb.append(objectField.getObjectFieldId());
								sb.append(StringPool.COMMA);
							}

							sb.setIndex(sb.index() - 1);

							return sb.toString();
						}
					).build())),
			JSONCompareMode.LENIENT);
	}

	@Override
	protected MVCResourceCommand getMVCResourceCommand() {
		return _mvcResourceCommand;
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/add_object_field_composite_key_candidates"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}