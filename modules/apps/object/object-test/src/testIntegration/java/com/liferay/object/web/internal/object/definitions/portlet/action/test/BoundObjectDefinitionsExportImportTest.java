/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.BaseExportImportTestCase;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
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

		// Draft

		testFailedImportJSON(
			_getBoundObjectDefinitionsJSONArray(
				true,
				JSONUtil.put(
					"code", 2
				).put(
					"label", "draft"
				).put(
					"label_i18n", "Draft"
				)
			).toString(),
			read("test-bound-object-definitions.name-error-message.json"), null,
			null);

		JSONArray boundObjectDefinitionsJSONArray =
			_getBoundObjectDefinitionsJSONArray(
				false,
				JSONUtil.put(
					"code", 2
				).put(
					"label", "draft"
				).put(
					"label_i18n", "Draft"
				));

		JSONObject objectDefinition2JSONObject =
			boundObjectDefinitionsJSONArray.getJSONObject(1);
		JSONObject objectDefinition3JSONObject =
			boundObjectDefinitionsJSONArray.getJSONObject(2);

		testExportImportJSON(
			boundObjectDefinitionsJSONArray.toString(),
			JSONUtil.putAll(
				boundObjectDefinitionsJSONArray.getJSONObject(0),
				objectDefinition2JSONObject.put(
					"objectFields",
					JSONUtil.concat(
						objectDefinition2JSONObject.getJSONArray(
							"objectFields"),
						JSONUtil.putAll(
							JSONUtil.put(
								"name",
								"r_objectRelationship1_c" +
									"_testObjectDefinition1Id")))),
				objectDefinition3JSONObject.put(
					"objectFields",
					JSONUtil.concat(
						objectDefinition3JSONObject.getJSONArray(
							"objectFields"),
						JSONUtil.putAll(
							JSONUtil.put(
								"name",
								"r_objectRelationship2_c" +
									"_testObjectDefinition2Id"))))
			).toString(),
			null, "TestObjectDefinition1");

		// Update draft to approved

		JSONObject statusJSONObject = JSONUtil.put(
			"code", 0
		).put(
			"label", "approved"
		).put(
			"label_i18n", "Approved"
		);

		testExportImportJSON(
			_getBoundObjectDefinitionsJSONArray(
				false, statusJSONObject
			).toString(),
			JSONUtil.putAll(
				jsonFactory.createJSONObject(
					String.valueOf(
						boundObjectDefinitionsJSONArray.getJSONObject(0))
				).put(
					"active", true
				).put(
					"status", statusJSONObject
				),
				jsonFactory.createJSONObject(
					objectDefinition2JSONObject.toString()
				).put(
					"active", true
				).put(
					"status", statusJSONObject
				),
				jsonFactory.createJSONObject(
					objectDefinition3JSONObject.toString()
				).put(
					"active", true
				).put(
					"status", statusJSONObject
				)
			).toString(),
			null, "TestObjectDefinition1");

		_deleteObjectDefinition("TESTOBJECTDEFINITION1", "objectRelationship1");
		_deleteObjectDefinition("TESTOBJECTDEFINITION2", "objectRelationship2");
		_deleteObjectDefinition("TESTOBJECTDEFINITION3", null);
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

	private void _deleteObjectDefinition(
			String objectDefinitionExternalReferenceCode,
			String objectRelationshipName)
		throws Exception {

		com.liferay.object.model.ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode,
					TestPropsValues.getCompanyId());

		if (Validator.isNull(objectRelationshipName)) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());

			return;
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId(),
					objectRelationshipName);

		_objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getExternalReferenceCode(),
			objectRelationship.getObjectRelationshipId(),
			objectRelationship.getParameterObjectFieldId(),
			objectRelationship.getDeletionType(), false,
			objectRelationship.getLabelMap(), null);

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	private JSONArray _getBoundObjectDefinitionsJSONArray(
			boolean invalidName, JSONObject statusJSONObject)
		throws Exception {

		return JSONUtil.putAll(
			jsonFactory.createJSONObject(
				defaultObjectDefinitionJSON
			).put(
				"externalReferenceCode", "TESTOBJECTDEFINITION1"
			).put(
				"name",
				() -> {
					if (invalidName) {
						return "!@TestObjectDefinition1";
					}

					return "TestObjectDefinition1";
				}
			).put(
				"objectRelationships",
				JSONUtil.put(
					createOneToManyObjectRelationship(
						"TESTOBJECTDEFINITION1", "TESTOBJECTDEFINITION2",
						"TestObjectDefinition2", "objectRelationship1"))
			).put(
				"status", statusJSONObject
			),
			jsonFactory.createJSONObject(
				defaultObjectDefinitionJSON
			).put(
				"externalReferenceCode", "TESTOBJECTDEFINITION2"
			).put(
				"name",
				() -> {
					if (invalidName) {
						return "!@TestObjectDefinition2";
					}

					return "TestObjectDefinition2";
				}
			).put(
				"objectRelationships",
				JSONUtil.put(
					createOneToManyObjectRelationship(
						"TESTOBJECTDEFINITION2", "TESTOBJECTDEFINITION3",
						"TestObjectDefinition3", "objectRelationship2"))
			).put(
				"status", statusJSONObject
			),
			jsonFactory.createJSONObject(
				defaultObjectDefinitionJSON
			).put(
				"externalReferenceCode", "TESTOBJECTDEFINITION3"
			).put(
				"name",
				() -> {
					if (invalidName) {
						return "!@TestObjectDefinition3";
					}

					return "TestObjectDefinition3";
				}
			).put(
				"status", statusJSONObject
			));
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_bound_object_definitions"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}