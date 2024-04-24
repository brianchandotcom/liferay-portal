/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		user = TestPropsValues.getUser();

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		_objectDefinitionResource = builder.user(
			user
		).build();

		Class<?> clazz = getClazz();

		_baseObjectDefinitionJSONString = StringUtil.read(
			clazz.getResourceAsStream(
				"dependencies/test-base-object-definition.json"));
	}

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

		testExportImportJSONString(
			_baseObjectDefinitionJSONString, _baseObjectDefinitionJSONString,
			null, "TestObjectDefinitionJSON");

		// Import an object definition with default locale different from
		// the site locale

		JSONObject actualObjectDefinitionJSONObject =
			_jsonFactory.createJSONObject(
				_baseObjectDefinitionJSONString
			).put(
				"defaultLanguageId", "de_DE"
			).put(
				"externalReferenceCode", "TESTOBJECTDEFINITIONINGERMAN"
			).put(
				"label",
				JSONUtil.put(
					"de_DE", "Test Objekt Definition"
				).put(
					"en_US", "Test Object Definition"
				)
			).put(
				"name", "TestObjectDefinitionInGerman"
			).put(
				"pluralLabel",
				JSONUtil.put(
					"de_DE", "Test Objekt Definitions"
				).put(
					"en_US", "Test Object Definitions"
				)
			);

		_setObjectFieldLabels(
			false,
			JSONUtil.put(
				"createDate", JSONUtil.put("de_DE", "Erstellt am")
			).put(
				"creator", JSONUtil.put("de_DE", "Autor")
			).put(
				"externalReferenceCode",
				JSONUtil.put("de_DE", "Externer Referenzcode")
			).put(
				"id", JSONUtil.put("de_DE", "ID")
			).put(
				"modifiedDate", JSONUtil.put("de_DE", "Änderungsdatum")
			).put(
				"status", JSONUtil.put("de_DE", "Status")
			).put(
				"testField",
				JSONUtil.put(
					"de_DE", "Test Feld"
				).put(
					"en_US", "Test Field"
				)
			),
			actualObjectDefinitionJSONObject.getJSONArray("objectFields"));

		// The object definition must be created in the site default locale

		JSONObject expectedObjectDefinitionJSONObject =
			_jsonFactory.createJSONObject(
				actualObjectDefinitionJSONObject.toString()
			).put(
				"defaultLanguageId", "en_US"
			);

		_setObjectFieldLabels(
			true,
			JSONUtil.put(
				"createDate", JSONUtil.put("en_US", "Create Date")
			).put(
				"creator", JSONUtil.put("en_US", "Author")
			).put(
				"externalReferenceCode",
				JSONUtil.put("en_US", "External Reference Code")
			).put(
				"id", JSONUtil.put("en_US", "ID")
			).put(
				"modifiedDate", JSONUtil.put("en_US", "Modified Date")
			).put(
				"status", JSONUtil.put("en_US", "Status")
			),
			expectedObjectDefinitionJSONObject.getJSONArray("objectFields"));

		testExportImportJSONString(
			actualObjectDefinitionJSONObject.toString(),
			expectedObjectDefinitionJSONObject.toString(),
			"TESTOBJECTDEFINITIONINGERMAN", "TestObjectDefinitionInGerman");
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
			_objectDefinitionResource.getObjectDefinitionsPage(
				name, null, null, Pagination.of(1, 1), null);

		List<ObjectDefinition> items = (List<ObjectDefinition>)page.getItems();

		return items.get(0);
	}

	private void _setObjectFieldLabels(
			boolean mergeObjectFieldLabels,
			JSONObject objectFieldLabelsJSONObject,
			JSONArray objectFieldsJSONArray)
		throws Exception {

		for (int i = 0; i < objectFieldsJSONArray.length(); i++) {
			JSONObject objectFieldJSONObject =
				objectFieldsJSONArray.getJSONObject(i);

			JSONObject objectFieldLabelJSONObject =
				objectFieldLabelsJSONObject.getJSONObject(
					objectFieldJSONObject.getString("name"));

			if (mergeObjectFieldLabels) {
				objectFieldJSONObject.put(
					"label",
					JSONUtil.merge(
						objectFieldJSONObject.getJSONObject("label"),
						objectFieldLabelJSONObject));
			}
			else {
				objectFieldJSONObject.put("label", objectFieldLabelJSONObject);
			}
		}
	}

	private String _baseObjectDefinitionJSONString;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_object_definition"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ObjectDefinitionResource _objectDefinitionResource;

	@Inject
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

}