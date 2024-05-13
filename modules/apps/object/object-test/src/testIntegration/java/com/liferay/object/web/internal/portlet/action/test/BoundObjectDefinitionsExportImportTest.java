/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Guilherme Sá
 */
@FeatureFlags("LPS-187142")
@RunWith(Arquillian.class)
public class BoundObjectDefinitionsExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportImportBoundObjectDefinitions() throws Exception {
		String draftBoundObjectDefinitionsJSON = String.valueOf(
			JSONUtil.putAll(
				jsonFactory.createJSONObject(
					defaultDraftObjectDefinitionJSON
				).put(
					"externalReferenceCode", "TESTOBJECTDEFINITION1"
				).put(
					"name", "TestObjectDefinition1"
				).put(
					"objectRelationships",
					JSONUtil.put(
						createOneToManyObjectRelationship(
							"TESTOBJECTDEFINITION1", "TESTOBJECTDEFINITION2",
							"TestObjectDefinition2", "objectRelationship1"))
				).put(
					"rootObjectDefinitionExternalReferenceCode",
					"TESTOBJECTDEFINITION1"
				),
				jsonFactory.createJSONObject(
					defaultDraftObjectDefinitionJSON
				).put(
					"externalReferenceCode", "TESTOBJECTDEFINITION2"
				).put(
					"name", "TestObjectDefinition2"
				).put(
					"objectRelationships",
					JSONUtil.put(
						createOneToManyObjectRelationship(
							"TESTOBJECTDEFINITION2", "TESTOBJECTDEFINITION3",
							"TestObjectDefinition3", "objectRelationship2"))
				).put(
					"rootObjectDefinitionExternalReferenceCode",
					"TESTOBJECTDEFINITION1"
				),
				jsonFactory.createJSONObject(
					defaultDraftObjectDefinitionJSON
				).put(
					"externalReferenceCode", "TESTOBJECTDEFINITION3"
				).put(
					"name", "TestObjectDefinition3"
				).put(
					"rootObjectDefinitionExternalReferenceCode",
					"TESTOBJECTDEFINITION1"
				)));

		JSONArray expectedDraftBoundObjectDefinitionsJSONArray =
			jsonFactory.createJSONArray(draftBoundObjectDefinitionsJSON);

		JSONObject testObjectDefinition2JSONObject =
			expectedDraftBoundObjectDefinitionsJSONArray.getJSONObject(1);

		JSONArray objectFieldsJSONArray =
			testObjectDefinition2JSONObject.getJSONArray("objectFields");

		objectFieldsJSONArray.put(
			jsonFactory.createJSONObject(
			).put(
				"name", "r_objectRelationship1_c_testObjectDefinition1Id"
			));

		JSONObject testObjectDefinition3JSONObject =
			expectedDraftBoundObjectDefinitionsJSONArray.getJSONObject(2);

		objectFieldsJSONArray = testObjectDefinition3JSONObject.getJSONArray(
			"objectFields");

		objectFieldsJSONArray.put(
			jsonFactory.createJSONObject(
			).put(
				"name", "r_objectRelationship2_c_testObjectDefinition2Id"
			));

		testExportImportJSON(
			draftBoundObjectDefinitionsJSON,
			expectedDraftBoundObjectDefinitionsJSONArray.toString(), null,
			"TestObjectDefinition1");

		JSONArray publishedBoundObjectDefinitionsJSONArray =
			jsonFactory.createJSONArray(draftBoundObjectDefinitionsJSON);

		for (Object publishedBoundObjectDefinition :
				publishedBoundObjectDefinitionsJSONArray) {

			JSONObject publishedBoundObjectDefinitionJSONObject =
				(JSONObject)publishedBoundObjectDefinition;

			publishedBoundObjectDefinitionJSONObject.put(
				"status",
				jsonFactory.createJSONObject(
				).put(
					"code", 0
				).put(
					"label", "approved"
				).put(
					"label_i18n", "Approved"
				));
		}

		testExportImportJSON(
			publishedBoundObjectDefinitionsJSONArray.toString(),
			expectedDraftBoundObjectDefinitionsJSONArray.toString(), null,
			"TestObjectDefinition1");

		JSONArray invalidBoundObjectDefinitionsJSONArray =
			jsonFactory.createJSONArray(draftBoundObjectDefinitionsJSON);

		for (Object invalidBoundObjectDefinition :
				invalidBoundObjectDefinitionsJSONArray) {

			JSONObject invalidBoundObjectDefinitionJSONObject =
				(JSONObject)invalidBoundObjectDefinition;

			invalidBoundObjectDefinitionJSONObject.put(
				"name",
				"!@" + invalidBoundObjectDefinitionJSONObject.get("name"));
		}

		Class<?> clazz = getClass();

		testFailedImportJSON(
			invalidBoundObjectDefinitionsJSONArray.toString(),
			StringUtil.read(
				clazz.getResourceAsStream(
					"dependencies/test-bound-object-definitions." +
						"name-error-message.json")),
			null, null);
	}

	@Override
	protected ClassLoader getClassLoader() {
		return BoundObjectDefinitionsExportImportTest.class.getClassLoader();
	}

	@Override
	protected Class<?> getClazz() {
		return getClass();
	}

	@Override
	protected long getId(String name) throws Exception {
		Page<ObjectDefinition> objectDefinitionsPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				name, null, null, Pagination.of(1, 1), null);

		List<ObjectDefinition> objectDefinitions =
			(List<ObjectDefinition>)objectDefinitionsPage.getItems();

		ObjectDefinition objectDefinition = objectDefinitions.get(0);

		return objectDefinition.getId();
	}

	@Override
	protected String getIdentifierName() {
		return "objectDefinitionId";
	}

	@Override
	protected String getJSONName() {
		return "objectDefinitionJSON";
	}

	@Override
	protected MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected MVCResourceCommand getMVCResourceCommand() {
		return _mvcResourceCommand;
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_bound_object_definitions"
	)
	private MVCResourceCommand _mvcResourceCommand;

}