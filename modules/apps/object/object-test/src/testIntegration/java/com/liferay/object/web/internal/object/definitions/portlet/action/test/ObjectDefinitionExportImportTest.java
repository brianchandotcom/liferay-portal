/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.web.internal.BaseExportImportTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@FeatureFlags("LPS-187142")
@RunWith(Arquillian.class)
public class ObjectDefinitionExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportImportLocalizedObjectDefinition() throws Exception {

		// Localized modifiable system object definition

		testExportImport(
			"test-modifiable-system-object-definition.portuguese-default-" +
				"locale.json",
			"test-modifiable-system-object-definition.site-default-locale.json",
			"TESTMODIFIABLESYSTEMOBJECTDEFINITIONPORTUGUESE",
			"TestModifiableSystemObjectDefinitionptBR");

		objectDefinitionResource.deleteObjectDefinition(
			getId("TestModifiableSystemObjectDefinitionptBR"));

		// Localized object definition

		testExportImport(
			"test-object-definition.portuguese-default-locale.json",
			"test-object-definition.site-default-locale.json",
			"TESTOBJECTDEFINITIONPORTUGUESE", "TestObjectDefinitionptBR");

		// Localized object definition update

		testExportImport(
			"test-object-definition.portuguese-default-locale.json",
			"test-object-definition.site-default-locale.json",
			"TESTOBJECTDEFINITIONPORTUGUESE", "TestObjectDefinitionptBR");

		objectDefinitionResource.deleteObjectDefinition(
			getId("TestObjectDefinitionptBR"));

		// Localized object definition with portuguese locale removed from the
		// company available locales

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		try {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(),
				SetUtil.fromArray(LocaleUtil.SPAIN, LocaleUtil.US),
				LocaleUtil.getDefault());

			String expectedJSON = read(
				"test-object-definition.site-default-locale.json");

			testExportImportJSON(
				read("test-object-definition.portuguese-default-locale.json"),
				expectedJSON.replaceAll(
					",[\n\t]+\"pt_BR\": \"(.*?)\"", StringPool.BLANK),
				"TESTOBJECTDEFINITIONPORTUGUESEREMOVED",
				"TestObjectDefinitionptBRRemoved");

			objectDefinitionResource.deleteObjectDefinition(
				getId("TestObjectDefinitionptBRRemoved"));
		}
		finally {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(), availableLocales,
				LocaleUtil.getDefault());
		}
	}

	@Test
	public void testExportImportObjectDefinition() throws Exception {

		// Custom object definition

		testExportImport(
			"test-object-definition.json", "test-object-definition.json",
			"TestObjectDefinition", "TestObjectDefinition");

		ObjectDefinition testObjectDefinition =
			objectDefinitionResource.getObjectDefinitionByExternalReferenceCode(
				"TestObjectDefinition");

		Status status = testObjectDefinition.getStatus();

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, (int)status.getCode());

		objectDefinitionResource.postObjectDefinitionPublish(
			testObjectDefinition.getId());

		testObjectDefinition = objectDefinitionResource.getObjectDefinition(
			testObjectDefinition.getId());

		status = testObjectDefinition.getStatus();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, (int)status.getCode());

		// Published object definition

		String externalReferenceCode = RandomTestUtil.randomString();
		String name = ObjectDefinitionTestUtil.getRandomName();

		String publishedObjectDefinitionJSON = jsonFactory.createJSONObject(
			defaultObjectDefinitionJSON
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

		testExportImportJSON(
			publishedObjectDefinitionJSON, publishedObjectDefinitionJSON,
			externalReferenceCode, name);

		// Published object definition cannot be a draft

		testFailedImportJSON(
			jsonFactory.createJSONObject(
				publishedObjectDefinitionJSON
			).put(
				"status",
				JSONUtil.put(
					"code", 2
				).put(
					"label", "draft"
				).put(
					"label_i18n", "Draft"
				)
			).toString(),
			read("test-object-definition.status-error-message.json"),
			externalReferenceCode, name);

		// Root node object definition

		String objectDefinition1JSON = jsonFactory.createJSONObject(
			defaultObjectDefinitionJSON
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

		testExportImportJSON(
			objectDefinition1JSON, objectDefinition1JSON,
			"TESTOBJECTDEFINITION1", "TestObjectDefinition1");

		Assert.assertNotNull(
			objectDefinitionResource.getObjectDefinitionByExternalReferenceCode(
				"TESTOBJECTDEFINITION2"));

		// Root node object definition, override descendant

		JSONObject objectDefinition2JSONObject = jsonFactory.createJSONObject(
			defaultObjectDefinitionJSON
		).put(
			"externalReferenceCode", "TESTOBJECTDEFINITION2"
		).put(
			"name", "TestObjectDefinition2"
		).put(
			"rootObjectDefinitionExternalReferenceCode", "TESTOBJECTDEFINITION1"
		);

		testExportImportJSON(
			objectDefinition2JSONObject.toString(),
			objectDefinition2JSONObject.put(
				"objectFields",
				JSONUtil.concat(
					objectDefinition2JSONObject.getJSONArray("objectFields"),
					JSONUtil.putAll(
						JSONUtil.put(
							"name",
							"r_objectRelationship1_c_testObjectDefinition1Id")))
			).toString(),
			"TESTOBJECTDEFINITION2", "TestObjectDefinition2");

		// System object definition

		ObjectDefinition objectDefinition = _getObjectDefinition(
			"AccountEntry");

		testExportImport(
			"test-account-entry-system-object-definition.json",
			"test-account-entry-system-object-definition.json",
			objectDefinition.getExternalReferenceCode(), "AccountEntry");
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