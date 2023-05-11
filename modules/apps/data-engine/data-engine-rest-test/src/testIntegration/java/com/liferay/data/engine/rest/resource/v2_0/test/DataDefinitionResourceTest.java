/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectRegistry;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.permission.Permission;
import com.liferay.data.engine.rest.client.problem.Problem;
import com.liferay.data.engine.rest.resource.exception.DataLayoutValidationException;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataLayoutTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.content.type.ModelResourceActionTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.content.type.TestDataDefinitionContentType;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDataDefinitionResourceTestCase.setUpClass();

		ModelResourceActionTestUtil.populateModelResourceAction(
			_resourceActions);
	}

	@AfterClass
	public static void tearDownClass() {
		ModelResourceActionTestUtil.deleteModelResourceAction(
			_resourceActionLocalService, _resourceActions);
	}

	@Override
	@Test
	public void testDeleteDataDefinition() throws Exception {
		super.testDeleteDataDefinition();

		DataDefinition parentDataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-parent.json")));

		DataDefinition childDataDefinition = DataDefinition.toDTO(
			DataDefinitionTestUtil.read("data-definition-child.json"));

		DataDefinitionField[] dataDefinitionFields =
			childDataDefinition.getDataDefinitionFields();

		for (DataDefinitionField dataDefinitionField : dataDefinitionFields) {
			dataDefinitionField.setCustomProperties(
				HashMapBuilder.<String, Object>put(
					"collapsible", true
				).put(
					"ddmStructureId", parentDataDefinition.getId()
				).put(
					"ddmStructureLayoutId", ""
				).build());
		}

		dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, childDataDefinition);

		assertHttpResponseStatusCode(
			204,
			dataDefinitionResource.deleteDataDefinitionHttpResponse(
				parentDataDefinition.getId()));
		assertHttpResponseStatusCode(
			404,
			dataDefinitionResource.getDataDefinitionHttpResponse(
				parentDataDefinition.getId()));
	}

	@Override
	@Test
	public void testGetDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes();

		Assert.assertTrue(Validator.isNotNull(fieldTypes));
	}

	@Override
	@Test
	public void testGetDataDefinitionPermissionsPage() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		Page<Permission> page =
			dataDefinitionResource.getDataDefinitionPermissionsPage(
				postDataDefinition.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	@Override
	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		super.testGetSiteDataDefinitionByContentTypeContentTypePage();

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId(),
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					"definition", Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetSiteDataDefinitionsPage(
			"DeFiNiTiON dEsCrIpTiOn", "DEFINITION", "name");
		_testGetSiteDataDefinitionsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789", "definition");
		_testGetSiteDataDefinitionsPage(
			"description name", "description name", "definition");
		_testGetSiteDataDefinitionsPage(
			"description", "DEFINITION", "DeFiNiTiON NaMe");
		_testGetSiteDataDefinitionsPage(
			"description", "definition name", "definition name");
		_testGetSiteDataDefinitionsPage(
			"description", "nam", "definition name");

		dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(),
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
			randomDataDefinition());

		page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId(),
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					null, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());
	}

	@Override
	@Test
	public void testGraphQLGetDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testGraphQLDataDefinition_addDataDefinition();

		Assert.assertEquals(
			MapUtil.getString(dataDefinition.getName(), "en_US"),
			JSONUtil.getValue(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataDefinition",
						HashMapBuilder.<String, Object>put(
							"dataDefinitionId", dataDefinition.getId()
						).build(),
						getGraphQLFields())),
				"JSONObject/data", "JSONObject/dataDefinition",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		Assert.assertEquals(
			MapUtil.getString(dataDefinition.getName(), "en_US"),
			JSONUtil.getValue(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataDefinitionByContentTypeByDataDefinitionKey",
						HashMapBuilder.<String, Object>put(
							"contentType",
							StringBundler.concat(
								StringPool.QUOTE, _CONTENT_TYPE,
								StringPool.QUOTE)
						).put(
							"dataDefinitionKey",
							StringBundler.concat(
								StringPool.QUOTE,
								dataDefinition.getDataDefinitionKey(),
								StringPool.QUOTE)
						).put(
							"siteKey",
							StringBundler.concat(
								StringPool.QUOTE, dataDefinition.getSiteId(),
								StringPool.QUOTE)
						).build(),
						getGraphQLFields())),
				"JSONObject/data",
				"JSONObject/dataDefinitionByContentTypeByDataDefinitionKey",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKeyNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataDefinitionByContentTypeByDataDefinitionKey",
						HashMapBuilder.<String, Object>put(
							"contentType", "\"" + _CONTENT_TYPE + "\""
						).put(
							"dataDefinitionKey",
							"\"" + RandomTestUtil.randomString() + "\""
						).put(
							"siteKey",
							"\"" + irrelevantGroup.getGroupId() + "\""
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Override
	@Test
	public void testPostDataDefinitionByContentType() throws Exception {
		super.testPostDataDefinitionByContentType();

		// Allow invalid field languages

		assertValid(
			DataDefinitionTestUtil.addDataDefinitionWithFieldset(
				testGroup.getGroupId()));

		// MustNotDuplicateFieldName

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-not-duplicate-field-name.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("text2", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustNotDuplicateFieldName",
				problem.getType());
		}

		// MustSetAvailableLocales

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-available-locales.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetAvailableLocales",
				problem.getType());
		}

		// MustSetDefaultLocaleAsAvailableLocale

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-default-locale-as-" +
							"available-locale.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("es_ES", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException." +
					"MustSetDefaultLocaleAsAvailableLocale",
				problem.getType());
		}

		// MustSetFields

		_testDataDefinitionContentType.setAllowEmptyDataDefinition(false);

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				"test",
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-fields.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetFields",
				problem.getType());
		}

		_testDataDefinitionContentType.setAllowEmptyDataDefinition(true);

		dataDefinitionResource.postDataDefinitionByContentType(
			"test",
			DataDefinition.toDTO(
				DataDefinitionTestUtil.read(
					"data-definition-must-set-fields.json")));

		// MustSetFieldType

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-field-type.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("text1", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetFieldType",
				problem.getType());
		}

		// MustSetOptionsForField

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-field-options-for-" +
							"field.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("select1", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetOptionsForField",
				problem.getType());
		}

		// MustSetValidCharactersForFieldName

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-valid-characters-for-field-" +
							"name.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("#name*", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException." +
					"MustSetValidCharactersForFieldName",
				problem.getType());
		}

		// MustSetValidCharactersForFieldType

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-valid-characters-for-field-" +
							"type.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("text$#", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException." +
					"MustSetValidCharactersForFieldType",
				problem.getType());
		}

		// MustSetValidContentType

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				"INVALID",
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read("data-definition.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("INVALID", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetValidContentType",
				problem.getType());
		}

		// MustSetValidName

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-valid-name.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetValidName",
				problem.getType());
		}

		// MustSetValidType

		try {
			dataDefinitionResource.postDataDefinitionByContentType(
				_CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-must-set-valid-field-type.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("string", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"DataDefinitionValidationException.MustSetValidType",
				problem.getType());
		}

		// Provide default layout name when none is informed

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE,
				DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-empty-data-layout-name.json")));

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		Assert.assertEquals(dataDefinition.getName(), dataLayout.getName());
	}

	@Override
	@Test
	public void testPostDataDefinitionCopy() throws Exception {
		DataDefinition randomDataDefinition =
			testGetDataDefinition_addDataDefinition();

		DataDefinition copiedDataDefinition =
			testPostDataDefinitionCopy_addDataDefinition(randomDataDefinition);

		_testCopiedDataDefinitionDataLayout(
			randomDataDefinition.getDefaultDataLayout(),
			copiedDataDefinition.getDefaultDataLayout());
		_testCopiedDataDefinitionFields(
			randomDataDefinition.getDataDefinitionFields(),
			copiedDataDefinition.getDataDefinitionFields());

		assertValid(copiedDataDefinition);
	}

	@Override
	@Test
	public void testPostSiteDataDefinitionByContentType() throws Exception {
		super.testPostSiteDataDefinitionByContentType();

		Group group = GroupTestUtil.addGroup();

		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourceBuilder.user(
				TestPropsValues.getUser()
			).build();

		try {
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				group.getGroupId(), _CONTENT_TYPE,
				com.liferay.data.engine.rest.dto.v2_0.DataDefinition.toDTO(
					DataDefinitionTestUtil.read(
						"data-definition-invalid-row-size.json")));

			Assert.fail("An exception must be thrown");
		}
		catch (DataLayoutValidationException.InvalidRowSize
					dataLayoutValidationException) {

			Assert.assertEquals(
				0,
				_ddmStructureLocalService.getStructuresCount(
					group.getGroupId(),
					_portal.getClassNameId(
						TestDataDefinitionContentType.class)));
		}
	}

	@Override
	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataLayout newDataLayout = DataLayoutTestUtil.createDataLayout(
			postDataDefinition.getId(), "Data Layout Updated",
			postDataDefinition.getSiteId());

		randomDataDefinition.setDefaultDataLayout(newDataLayout);

		DataDefinition putDataDefinition =
			dataDefinitionResource.putDataDefinition(
				postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected void assertValid(DataDefinition dataDefinition) throws Exception {
		super.assertValid(dataDefinition);

		boolean valid = true;

		if (dataDefinition.getDataDefinitionFields() != null) {
			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (Validator.isNull(dataDefinitionField.getFieldType()) ||
					Validator.isNull(dataDefinitionField.getLabel()) ||
					Validator.isNull(dataDefinitionField.getName()) ||
					Validator.isNull(dataDefinitionField.getTip())) {

					valid = false;
				}
			}
		}
		else {
			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return _createDataDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Override
	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	@Override
	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	@Override
	protected DataDefinition
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				String contentType, DataDefinition dataDefinition)
		throws Exception {

		return dataDefinitionResource.postDataDefinitionByContentType(
			contentType, dataDefinition);
	}

	@Override
	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		return _CONTENT_TYPE;
	}

	@Override
	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		dataDefinition.setContentType(_CONTENT_TYPE);

		return dataDefinition;
	}

	@Override
	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			siteId, contentType, dataDefinition);
	}

	@Override
	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		return _CONTENT_TYPE;
	}

	@Override
	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected DataDefinition testGraphQLDataDefinition_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	@Override
	protected DataDefinition testPatchDataDefinition_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	@Override
	protected DataDefinition
			testPostDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, dataDefinition);
	}

	@Override
	protected DataDefinition testPostDataDefinitionCopy_addDataDefinition(
			DataDefinition dataDefinition)
		throws Exception {

		return dataDefinitionResource.postDataDefinitionCopy(
			dataDefinition.getId());
	}

	@Override
	protected DataDefinition
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, dataDefinition);
	}

	@Override
	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	@Override
	protected DataDefinition
			testPutDataDefinitionPermissionsPage_addDataDefinition()
		throws Exception {

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());
	}

	private DataDefinition _createDataDefinition(
			String description, String name)
		throws Exception {

		DataDefinition dataDefinition = DataDefinition.toDTO(
			DataDefinitionTestUtil.read(
				"data-definition-with-fields-group.json"));

		dataDefinition.setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", description
			).put(
				"pt_BR", description
			).build());
		dataDefinition.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", name
			).put(
				"pt_BR", name
			).build());
		dataDefinition.setSiteId(testGroup.getGroupId());

		return dataDefinition;
	}

	private List<String> _getDataLayoutColumnFieldNames(DataLayout dataLayout) {
		List<String> dataLayoutColumnFieldNames = new ArrayList<>();

		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			for (DataLayoutRow dataLayoutRow :
					dataLayoutPage.getDataLayoutRows()) {

				for (DataLayoutColumn dataLayoutColumn :
						dataLayoutRow.getDataLayoutColumns()) {

					Collections.addAll(
						dataLayoutColumnFieldNames,
						dataLayoutColumn.getFieldNames());
				}
			}
		}

		return dataLayoutColumnFieldNames;
	}

	private void _testCopiedDataDefinitionDataLayout(
		DataLayout dataLayout1, DataLayout dataLayout2) {

		List<String> dataLayoutColumnFieldNames1 =
			_getDataLayoutColumnFieldNames(dataLayout1);

		List<String> dataLayoutColumnFieldNames2 =
			_getDataLayoutColumnFieldNames(dataLayout2);

		Assert.assertEquals(
			dataLayoutColumnFieldNames2.toString(),
			dataLayoutColumnFieldNames1.size(),
			dataLayoutColumnFieldNames2.size());

		for (int i = 0; i < dataLayoutColumnFieldNames1.size(); i++) {
			Assert.assertEquals(
				"CopyOf" + dataLayoutColumnFieldNames1.get(i),
				dataLayoutColumnFieldNames2.get(i));
		}
	}

	private void _testCopiedDataDefinitionFields(
		DataDefinitionField[] dataDefinitionFields1,
		DataDefinitionField[] dataDefinitionFields2) {

		Assert.assertEquals(
			Arrays.toString(dataDefinitionFields2),
			dataDefinitionFields1.length, dataDefinitionFields2.length);

		for (int i = 0; i < dataDefinitionFields1.length; i++) {
			Assert.assertEquals(
				"CopyOf" + dataDefinitionFields1[i].getName(),
				dataDefinitionFields2[i].getName());

			Map<String, Object> customProperties1 =
				dataDefinitionFields1[i].getCustomProperties();
			Map<String, Object> customProperties2 =
				dataDefinitionFields2[i].getCustomProperties();

			Assert.assertEquals(
				customProperties1.containsKey("fieldReference"),
				customProperties2.containsKey("fieldReference"));

			if (customProperties1.containsKey("fieldReference")) {
				Assert.assertEquals(
					"CopyOf" + customProperties1.get("fieldReference"),
					customProperties2.get("fieldReference"));
			}

			if (!customProperties1.containsKey("structureId") &&
				!Objects.equals(
					dataDefinitionFields1[i].getFieldType(), "fieldset")) {

				_testCopiedDataDefinitionFields(
					dataDefinitionFields1[i].getNestedDataDefinitionFields(),
					dataDefinitionFields2[i].getNestedDataDefinitionFields());
			}
		}
	}

	private void _testGetSiteDataDefinitionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();

		DataDefinition dataDefinition =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId,
				testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
				_createDataDefinition(description, name));

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId,
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
	}

	private static final String _CONTENT_TYPE = "test";

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private static ResourceActions _resourceActions;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject(type = DataEngineNativeObjectRegistry.class)
	private DataEngineNativeObjectRegistry _dataEngineNativeObjectRegistry;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject(type = Portal.class)
	private Portal _portal;

	@Inject
	private TestDataDefinitionContentType _testDataDefinitionContentType;

}