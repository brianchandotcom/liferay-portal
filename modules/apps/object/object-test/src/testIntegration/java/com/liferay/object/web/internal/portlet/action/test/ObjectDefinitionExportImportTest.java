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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportImportObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition(
			"AccountEntry");

		testExportImport(
			"test-account-entry-system-object-definition.json",
			"test-account-entry-system-object-definition.json",
			objectDefinition.getExternalReferenceCode(), "AccountEntry");

		testExportImport(
			"test-object-definition.json", "test-object-definition.json", null,
			"TestObjectDefinition");

		testExportImport(
			"test-object-definition.portuguese-locale.json",
			"test-object-definition.site-default-locale.json",
			"TESTOBJECTDEFINITIONPORTUGUESE", "TestObjectDefinitionPortuguese");

		String externalReferenceCode = RandomTestUtil.randomString();
		String name = StringUtil.upperCaseFirstLetter(
			RandomTestUtil.randomString());

		String publishedObjectDefinitionJSONString =
			jsonFactory.createJSONObject(
				defaultDraftObjectDefinitionJSONString
			).put(
				"active", true
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"name", name
			).put(
				"status",
				jsonFactory.createJSONObject(
				).put(
					"code", 0
				).put(
					"label", "approved"
				).put(
					"label_i18n", "Approved"
				)
			).toString();

		testExportImportJSONString(
			publishedObjectDefinitionJSONString,
			publishedObjectDefinitionJSONString, externalReferenceCode, name);

		// Cannot modify the status of published object definition

		Class<?> clazz = getClass();

		testFailedImportJSONString(
			jsonFactory.createJSONObject(
				publishedObjectDefinitionJSONString
			).put(
				"status",
				jsonFactory.createJSONObject(
				).put(
					"code", 2
				).put(
					"label", "draft"
				).put(
					"label_i18n", "Draft"
				)
			).toString(),
			StringUtil.read(
				clazz.getResourceAsStream(
					"dependencies/test-object-definition.status-error-" +
						"message.json")),
			externalReferenceCode, name);

		// Import bound object definitions one by one

		String objectDefinition1JSONString = jsonFactory.createJSONObject(
			defaultDraftObjectDefinitionJSONString
		).put(
			"externalReferenceCode", "TESTOBJECTDEFINITION1"
		).put(
			"name", "TestObjectDefinition1"
		).put(
			"objectRelationships",
			JSONUtil.put(
				createOneToManyObjectRelationship(
					"TESTOBJECTDEFINITION1", "TESTOBJECTDEFINITION2",
					"TESTOBJECTDEFINITION2", "objectRelationship1"))
		).put(
			"rootObjectDefinitionExternalReferenceCode", "TESTOBJECTDEFINITION1"
		).toString();

		testExportImportJSONString(
			objectDefinition1JSONString, objectDefinition1JSONString,
			"TESTOBJECTDEFINITION1", "TestObjectDefinition1");

		Assert.assertNotNull(
			objectDefinitionResource.getObjectDefinitionByExternalReferenceCode(
				"TESTOBJECTDEFINITION2"));

		String objectDefinition2JSONString = jsonFactory.createJSONObject(
			defaultDraftObjectDefinitionJSONString
		).put(
			"externalReferenceCode", "TESTOBJECTDEFINITION2"
		).put(
			"name", "TestObjectDefinition2"
		).put(
			"rootObjectDefinitionExternalReferenceCode", "TESTOBJECTDEFINITION1"
		).toString();

		JSONObject expectedObjectDefinition2JSONObject =
			jsonFactory.createJSONObject(objectDefinition2JSONString);

		JSONArray objectFieldsJSONArray =
			expectedObjectDefinition2JSONObject.getJSONArray("objectFields");

		objectFieldsJSONArray.put(
			jsonFactory.createJSONObject(
			).put(
				"name", "r_objectRelationship1_c_testObjectDefinition1Id"
			));

		testExportImportJSONString(
			objectDefinition2JSONString,
			expectedObjectDefinition2JSONObject.toString(),
			"TESTOBJECTDEFINITION2", "TestObjectDefinition2");
	}

	@Override
	protected ClassLoader getClassLoader() {
		return ObjectDefinitionExportImportTest.class.getClassLoader();
	}

	@Override
	protected Class<?> getClazz() {
		return getClass();
	}

	@Override
	protected long getId(String name) throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition(name);

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

	private ObjectDefinition _getObjectDefinition(String name)
		throws Exception {

		Page<ObjectDefinition> page =
			objectDefinitionResource.getObjectDefinitionsPage(
				name, null, null, Pagination.of(1, 1), null);

		List<ObjectDefinition> items = (List<ObjectDefinition>)page.getItems();

		return items.get(0);
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_object_definition"
	)
	private MVCResourceCommand _mvcResourceCommand;

}