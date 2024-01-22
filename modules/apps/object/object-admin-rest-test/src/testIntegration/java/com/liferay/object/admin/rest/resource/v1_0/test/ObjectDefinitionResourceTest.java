/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRuleSetting;
import com.liferay.object.admin.rest.client.dto.v1_0.Status;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.problem.Problem;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectDefinitionSerDes;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@FeatureFlags({"LPS-148856", "LPS-187142"})
@RunWith(Arquillian.class)
public class ObjectDefinitionResourceTest
	extends BaseObjectDefinitionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectFolder1 = _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());

		_objectFolder2 = _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			try {
				_objectDefinitionLocalService.deleteObjectDefinition(
					_objectDefinition.getId());
			}
			catch (NoSuchObjectDefinitionException
						noSuchObjectDefinitionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchObjectDefinitionException);
				}
			}
		}
	}

	@Override
	@Test
	public void testGetObjectDefinition() throws Exception {
		super.testGetObjectDefinition();

		ObjectDefinition objectDefinition =
			testGetObjectDefinitionsPage_addObjectDefinition(
				randomObjectDefinition());

		String objectDefinitionPluralName = StringUtil.lowerCaseFirstLetter(
			TextFormatter.formatPlural(objectDefinition.getName()));

		Assert.assertEquals(
			"/o/c/" + objectDefinitionPluralName,
			objectDefinition.getRestContextPath());
	}

	@Override
	@Test
	public void testGetObjectDefinitionsPage() throws Exception {
		super.testGetObjectDefinitionsPage();

		Page<ObjectDefinition> page =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, "status/any(k:k eq 2)", Pagination.of(1, 20), null);

		long totalCount = page.getTotalCount();

		ObjectDefinition objectDefinition =
			testGetObjectDefinitionsPage_addObjectDefinition(
				randomObjectDefinition());

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		Status status = new Status() {
			{
				code = WorkflowConstants.STATUS_APPROVED;
				label = WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED);
				label_i18n = _language.get(
					LanguageResources.getResourceBundle(
						LocaleUtil.getDefault()),
					WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_APPROVED));
			}
		};

		randomObjectDefinition.setStatus(status);

		testGetObjectDefinitionsPage_addObjectDefinition(
			randomObjectDefinition);

		page = objectDefinitionResource.getObjectDefinitionsPage(
			null, null, "status/any(k:k eq 2)", Pagination.of(1, 20), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		assertContains(
			objectDefinition, (List<ObjectDefinition>)page.getItems());
	}

	@Override
	@Test
	public void testGetObjectDefinitionsPageWithSortString() throws Exception {
		ObjectDefinition objectDefinition1 = randomObjectDefinition();

		objectDefinition1.setName("A" + objectDefinition1.getName());

		objectDefinition1 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition1);

		ObjectDefinition objectDefinition2 = randomObjectDefinition();

		objectDefinition2.setName("B" + objectDefinition2.getName());

		objectDefinition2 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition2);

		Page<ObjectDefinition> ascPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, null, null, "name:asc");

		List<ObjectDefinition> objectDefinitions =
			(List<ObjectDefinition>)ascPage.getItems();

		assertEquals(
			Arrays.asList(objectDefinition1, objectDefinition2),
			objectDefinitions.subList(2, 4));

		Page<ObjectDefinition> descPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, null, null, "name:desc");

		objectDefinitions = (List<ObjectDefinition>)descPage.getItems();

		assertEquals(
			Arrays.asList(objectDefinition2, objectDefinition1),
			objectDefinitions.subList(
				objectDefinitions.size() - 4, objectDefinitions.size() - 2));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition1.getId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition2.getId());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionByExternalReferenceCodeNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionNotFound() {
	}

	@Override
	@Test
	public void testGraphQLGetObjectDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"objectDefinitions",
			HashMapBuilder.<String, Object>put(
				"page", 1
			).put(
				"pageSize",
				() -> {
					int objectDefinitionsCount =
						_objectDefinitionLocalService.getObjectDefinitionsCount(
							TestPropsValues.getCompanyId());

					return objectDefinitionsCount + 10;
				}
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		long totalCount = objectDefinitionsJSONObject.getLong("totalCount");

		ObjectDefinition objectDefinition1 =
			testGraphQLGetObjectDefinitionsPage_addObjectDefinition();
		ObjectDefinition objectDefinition2 =
			testGraphQLGetObjectDefinitionsPage_addObjectDefinition();

		objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		Assert.assertEquals(
			totalCount + 2, objectDefinitionsJSONObject.getLong("totalCount"));

		assertContains(
			objectDefinition1,
			Arrays.asList(
				ObjectDefinitionSerDes.toDTOs(
					objectDefinitionsJSONObject.getString("items"))));
		assertContains(
			objectDefinition2,
			Arrays.asList(
				ObjectDefinitionSerDes.toDTOs(
					objectDefinitionsJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPostObjectDefinition() throws Exception {
		super.testPostObjectDefinition();

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		Status status = new Status() {
			{
				code = WorkflowConstants.STATUS_APPROVED;
				label = WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED);
				label_i18n = _language.get(
					LanguageResources.getResourceBundle(
						LocaleUtil.getDefault()),
					WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_APPROVED));
			}
		};

		randomObjectDefinition.setStatus(status);

		ObjectDefinition postObjectDefinition =
			testPostObjectDefinition_addObjectDefinition(
				randomObjectDefinition);

		assertEquals(postObjectDefinition, randomObjectDefinition);
		assertValid(postObjectDefinition);

		String randomListTypeDefinitionExternalReferenceCode =
			RandomTestUtil.randomString();

		ObjectDefinition randomModifiableSystemObjectDefinition =
			_randomModifiableSystemObjectDefinition();

		randomModifiableSystemObjectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						businessType = BusinessType.PICKLIST;
						DBType = ObjectField.DBType.create("String");
						externalReferenceCode = RandomTestUtil.randomString();
						indexed = false;
						indexedAsKeyword = false;
						label = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						listTypeDefinitionExternalReferenceCode =
							randomListTypeDefinitionExternalReferenceCode;
						localized = false;
						name = "a" + RandomTestUtil.randomString();
						readOnly = ReadOnly.FALSE;
						required = false;
						system = true;
					}
				}
			});

		testPostObjectDefinition_addObjectDefinition(
			randomModifiableSystemObjectDefinition);

		ListTypeDefinition serviceBuilderlistTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					randomListTypeDefinitionExternalReferenceCode,
					TestPropsValues.getCompanyId());

		Assert.assertNotNull(serviceBuilderlistTypeDefinition);
		Assert.assertTrue(serviceBuilderlistTypeDefinition.isSystem());
	}

	@Override
	@Test
	public void testPutObjectDefinition() throws Exception {
		super.testPutObjectDefinition();

		// Account entry restricted

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		randomObjectDefinition.setSystem(false);

		ObjectDefinition postObjectDefinition =
			objectDefinitionResource.postObjectDefinition(
				randomObjectDefinition);

		com.liferay.object.model.ObjectDefinition
			serviceBuilderAccountEntryObjectDefinition =
				_objectDefinitionLocalService.fetchSystemObjectDefinition(
					AccountEntry.class.getSimpleName());

		_objectDefinitionLocalService.enableAccountEntryRestricted(
			_objectRelationshipLocalService.addObjectRelationship(
				null, TestPropsValues.getUserId(),
				serviceBuilderAccountEntryObjectDefinition.
					getObjectDefinitionId(),
				postObjectDefinition.getId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"a" + RandomTestUtil.randomString(), false,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null));

		postObjectDefinition = objectDefinitionResource.getObjectDefinition(
			postObjectDefinition.getId());

		Assert.assertTrue(postObjectDefinition.getAccountEntryRestricted());

		String accountEntryRestrictedObjectFieldName =
			postObjectDefinition.getAccountEntryRestrictedObjectFieldName();

		ObjectDefinition accountEntryObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				serviceBuilderAccountEntryObjectDefinition.
					getObjectDefinitionId());

		accountEntryObjectDefinition.setExternalReferenceCode(
			RandomTestUtil.randomString());

		objectDefinitionResource.putObjectDefinition(
			accountEntryObjectDefinition.getId(), accountEntryObjectDefinition);

		postObjectDefinition = objectDefinitionResource.getObjectDefinition(
			postObjectDefinition.getId());

		Assert.assertTrue(postObjectDefinition.getAccountEntryRestricted());
		Assert.assertEquals(
			accountEntryRestrictedObjectFieldName,
			postObjectDefinition.getAccountEntryRestrictedObjectFieldName());

		_objectDefinitionLocalService.deleteObjectDefinition(
			postObjectDefinition.getId());

		// Draft custom object definition

		postObjectDefinition = objectDefinitionResource.postObjectDefinition(
			randomObjectDefinition());

		Assert.assertEquals(
			postObjectDefinition.getStatus(),
			new Status() {
				{
					code = WorkflowConstants.STATUS_DRAFT;
					label = WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_DRAFT);
					label_i18n = _language.get(
						LanguageResources.getResourceBundle(
							LocaleUtil.getDefault()),
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_DRAFT));
				}
			});

		postObjectDefinition.setStatus(
			new Status() {
				{
					code = WorkflowConstants.STATUS_APPROVED;
				}
			});

		ObjectDefinition randomPersistedPublishedObjectDefinition =
			objectDefinitionResource.putObjectDefinition(
				postObjectDefinition.getId(), postObjectDefinition);

		Assert.assertEquals(
			randomPersistedPublishedObjectDefinition.getStatus(),
			new Status() {
				{
					code = WorkflowConstants.STATUS_APPROVED;
					label = WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_APPROVED);
					label_i18n = _language.get(
						LanguageResources.getResourceBundle(
							LocaleUtil.getDefault()),
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_APPROVED));
				}
			});

		// Modifiable system object definition

		ObjectDefinition randomModifiableSystemObjectDefinition =
			_addObjectDefinition(_randomModifiableSystemObjectDefinition());

		ObjectValidationRule customObjectValidationRule =
			(ObjectValidationRule)ArrayUtil.getValue(
				randomModifiableSystemObjectDefinition.
					getObjectValidationRules(),
				0);
		ObjectValidationRule systemObjectValidationRule =
			(ObjectValidationRule)ArrayUtil.getValue(
				randomModifiableSystemObjectDefinition.
					getObjectValidationRules(),
				1);

		randomModifiableSystemObjectDefinition.setEnableObjectEntryDraft(
			(Boolean)null);
		randomModifiableSystemObjectDefinition.
			setObjectFolderExternalReferenceCode(StringPool.BLANK);

		ObjectValidationRule updatedCustomObjectValidationRule =
			new ObjectValidationRule() {
				{
					active = false;
					engine = ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY;
					errorLabel = Collections.singletonMap(
						"en_US", RandomTestUtil.randomString());
					externalReferenceCode =
						customObjectValidationRule.getExternalReferenceCode();
					name = Collections.singletonMap(
						"en_US", RandomTestUtil.randomString());
					objectDefinitionExternalReferenceCode =
						randomModifiableSystemObjectDefinition.
							getExternalReferenceCode();
					objectValidationRuleSettings =
						new ObjectValidationRuleSetting[] {
							new ObjectValidationRuleSetting() {
								{
									name =
										ObjectValidationRuleSettingConstants.
											NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE;
									value = "customObjectFieldERC";
								}
							}
						};
					outputType = OutputType.create("partialValidation");
					script = RandomTestUtil.randomString();
					system = false;
				}
			};

		randomModifiableSystemObjectDefinition.setObjectValidationRules(
			new ObjectValidationRule[] {
				updatedCustomObjectValidationRule,
				new ObjectValidationRule() {
					{
						active = false;
						engine =
							ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY;
						errorLabel = Collections.singletonMap(
							"en_US", RandomTestUtil.randomString());
						externalReferenceCode =
							systemObjectValidationRule.
								getExternalReferenceCode();
						name = Collections.singletonMap(
							"en_US", RandomTestUtil.randomString());
						objectDefinitionExternalReferenceCode =
							randomModifiableSystemObjectDefinition.
								getExternalReferenceCode();
						objectValidationRuleSettings =
							new ObjectValidationRuleSetting[] {
								new ObjectValidationRuleSetting() {
									{
										name =
											ObjectValidationRuleSettingConstants.NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE;
										value = "customObjectFieldERC";
									}
								}
							};
						outputType = OutputType.create("partialValidation");
						script = RandomTestUtil.randomString();
						system = true;
					}
				}
			});

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.clear("liferay.mode");

		try {
			objectDefinitionResource.putObjectDefinition(
				randomModifiableSystemObjectDefinition.getId(),
				randomModifiableSystemObjectDefinition);
		}
		finally {
			SystemProperties.set("liferay.mode", liferayMode);
		}

		ObjectDefinition getObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				randomModifiableSystemObjectDefinition.getId());

		_assertObjectValidationRule(
			"customObjectFieldERC", updatedCustomObjectValidationRule,
			(ObjectValidationRule)ArrayUtil.getValue(
				getObjectDefinition.getObjectValidationRules(), 0));
		_assertObjectValidationRule(
			"customObjectFieldERC", systemObjectValidationRule,
			(ObjectValidationRule)ArrayUtil.getValue(
				getObjectDefinition.getObjectValidationRules(), 1));

		_objectDefinitionLocalService.deleteObjectDefinition(
			randomModifiableSystemObjectDefinition.getId());

		// Storage type

		postObjectDefinition = testPutObjectDefinition_addObjectDefinition();

		randomObjectDefinition = randomObjectDefinition();

		randomObjectDefinition.setStorageType(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);

		try {
			objectDefinitionResource.putObjectDefinition(
				postObjectDefinition.getId(), randomObjectDefinition);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			postObjectDefinition.getId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "status"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"dateCreated", "dateModified", "label", "userId"};
	}

	@Override
	protected ObjectDefinition randomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = super.randomObjectDefinition();

		objectDefinition.setAccountEntryRestricted(false);
		objectDefinition.setAccountEntryRestrictedObjectFieldName("");
		objectDefinition.setActive(false);
		objectDefinition.setLabel(
			Collections.singletonMap(
				"en_US", "O" + objectDefinition.getName()));
		objectDefinition.setEnableLocalization(true);
		objectDefinition.setModifiable(true);
		objectDefinition.setName("O" + objectDefinition.getName());
		objectDefinition.setObjectFolderExternalReferenceCode(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_DEFAULT);
		objectDefinition.setPluralLabel(
			Collections.singletonMap(
				"en_US", "O" + objectDefinition.getName()));
		objectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						businessType = BusinessType.TEXT;
						DBType = ObjectField.DBType.create("String");
						indexed = false;
						indexedAsKeyword = false;
						label = Collections.singletonMap("en_US", "Column");
						localized = !objectDefinition.getSystem();
						name = StringUtil.randomId();
						readOnly = ReadOnly.FALSE;
						required = false;
						system = false;
					}
				}
			});
		objectDefinition.setScope(ObjectDefinitionConstants.SCOPE_COMPANY);
		objectDefinition.setStatus(
			new Status() {
				{
					code = WorkflowConstants.STATUS_DRAFT;
					label = WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_DRAFT);
					label_i18n = _language.get(
						LanguageResources.getResourceBundle(
							LocaleUtil.getDefault()),
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_DRAFT));
				}
			});
		objectDefinition.setSystem(false);

		if (!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {
			objectDefinition.setStorageType(StringPool.BLANK);
		}

		return objectDefinition;
	}

	@Override
	protected ObjectDefinition testDeleteObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition
			testGetObjectDefinitionByExternalReferenceCode_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinitionsPage_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected void testGetObjectDefinitionsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		ObjectDefinition objectDefinition1 = randomObjectDefinition();

		objectDefinition1.setObjectFolderExternalReferenceCode(
			_objectFolder1.getExternalReferenceCode());

		objectDefinition1 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition1);

		ObjectDefinition objectDefinition2 = randomObjectDefinition();

		objectDefinition2.setObjectFolderExternalReferenceCode(
			_objectFolder2.getExternalReferenceCode());

		testGetObjectDefinitionsPage_addObjectDefinition(objectDefinition2);

		for (EntityField entityField : entityFields) {
			_assertGetObjectDefinitionsPageWithFilter(
				Collections.singletonList(objectDefinition1),
				getFilterString(entityField, operator, objectDefinition1));

			_objectFolder1 = _objectFolderLocalService.updateObjectFolder(
				RandomTestUtil.randomString(),
				_objectFolder1.getObjectFolderId(),
				_objectFolder1.getLabelMap());

			objectDefinition1 = objectDefinitionResource.getObjectDefinition(
				objectDefinition1.getId());

			_assertGetObjectDefinitionsPageWithFilter(
				Collections.singletonList(objectDefinition1),
				getFilterString(entityField, operator, objectDefinition1));
		}
	}

	@Override
	protected ObjectDefinition testGraphQLObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPatchObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPostObjectDefinition_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition
			testPostObjectDefinitionPublish_addObjectDefinition(
				ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition testPutObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition
			testPutObjectDefinitionByExternalReferenceCode_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	private ObjectDefinition _addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		_objectDefinition = objectDefinitionResource.postObjectDefinition(
			objectDefinition);

		return _objectDefinition;
	}

	private void _assertGetObjectDefinitionsPageWithFilter(
			List<ObjectDefinition> expectedObjectDefinitions,
			String filterString)
		throws Exception {

		Page<ObjectDefinition> page =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, filterString, Pagination.of(1, 2), null);

		assertEquals(
			expectedObjectDefinitions, (List<ObjectDefinition>)page.getItems());
	}

	private void _assertObjectValidationRule(
		String expectedObjectFieldExternalReferenceCode,
		ObjectValidationRule expectedObjectValidationRule,
		ObjectValidationRule actualObjectValidationRule) {

		Assert.assertEquals(
			expectedObjectValidationRule.getActive(),
			actualObjectValidationRule.getActive());
		Assert.assertEquals(
			expectedObjectValidationRule.getEngine(),
			actualObjectValidationRule.getEngine());
		Assert.assertEquals(
			expectedObjectValidationRule.getErrorLabel(),
			actualObjectValidationRule.getErrorLabel());
		Assert.assertEquals(
			expectedObjectValidationRule.getExternalReferenceCode(),
			expectedObjectValidationRule.getExternalReferenceCode());
		Assert.assertEquals(
			expectedObjectValidationRule.getName(),
			actualObjectValidationRule.getName());
		Assert.assertEquals(
			expectedObjectValidationRule.getOutputType(),
			actualObjectValidationRule.getOutputType());
		Assert.assertEquals(
			expectedObjectValidationRule.getScript(),
			actualObjectValidationRule.getScript());

		if (StringUtil.equals(
				actualObjectValidationRule.getOutputTypeAsString(),
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION)) {

			Assert.assertTrue(
				ArrayUtil.isEmpty(
					actualObjectValidationRule.
						getObjectValidationRuleSettings()));
		}
		else if (StringUtil.equals(
					actualObjectValidationRule.getOutputTypeAsString(),
					ObjectValidationRuleConstants.
						OUTPUT_TYPE_PARTIAL_VALIDATION)) {

			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					actualObjectValidationRule.
						getObjectValidationRuleSettings()));

			for (ObjectValidationRuleSetting objectValidationRuleSetting :
					actualObjectValidationRule.
						getObjectValidationRuleSettings()) {

				if (StringUtil.equals(
						objectValidationRuleSetting.getName(),
						ObjectValidationRuleSettingConstants.
							NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE)) {

					Assert.assertEquals(
						expectedObjectFieldExternalReferenceCode,
						objectValidationRuleSetting.getValue());
				}
			}
		}
	}

	private ObjectDefinition _randomModifiableSystemObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition = randomObjectDefinition();

		objectDefinition.setActive(true);

		String randomObjectDefinitionExternalReferenceCode =
			"L_" + objectDefinition.getExternalReferenceCode();

		objectDefinition.setExternalReferenceCode(
			randomObjectDefinitionExternalReferenceCode);

		objectDefinition.setName("Test");
		objectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						businessType = BusinessType.TEXT;
						DBType = ObjectField.DBType.create("String");
						externalReferenceCode = "customObjectFieldERC";
						indexed = false;
						indexedAsKeyword = false;
						label = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						localized = false;
						name = "customObjectField";
						readOnly = ReadOnly.FALSE;
						required = false;
						system = false;
					}
				},
				new ObjectField() {
					{
						businessType = BusinessType.TEXT;
						DBType = ObjectField.DBType.create("String");
						externalReferenceCode = RandomTestUtil.randomString();
						indexed = false;
						indexedAsKeyword = false;
						label = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						localized = false;
						name = "systemObjectField";
						readOnly = ReadOnly.FALSE;
						required = false;
						system = true;
					}
				}
			});
		objectDefinition.setObjectValidationRules(
			new ObjectValidationRule[] {
				new ObjectValidationRule() {
					{
						active = true;
						engine = ObjectValidationRuleConstants.ENGINE_TYPE_DDM;
						errorLabel = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						externalReferenceCode = RandomTestUtil.randomString();
						name = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						objectDefinitionExternalReferenceCode =
							randomObjectDefinitionExternalReferenceCode;
						outputType = OutputType.create("fullValidation");
						script = "isEmailAddress(customObjectField)";
						system = false;
					}
				},
				new ObjectValidationRule() {
					{
						active = true;
						engine = ObjectValidationRuleConstants.ENGINE_TYPE_DDM;
						errorLabel = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						externalReferenceCode = RandomTestUtil.randomString();
						name = Collections.singletonMap(
							"en-US", RandomTestUtil.randomString());
						objectDefinitionExternalReferenceCode =
							randomObjectDefinitionExternalReferenceCode;
						outputType = OutputType.create("fullValidation");
						script = "isEmailAddress(systemObjectField)";
						system = true;
					}
				}
			});
		objectDefinition.setStatus(
			new Status() {
				{
					code = WorkflowConstants.STATUS_APPROVED;
					label = WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_APPROVED);
					label_i18n = _language.get(
						LanguageResources.getResourceBundle(
							LocaleUtil.getDefault()),
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_APPROVED));
				}
			});
		objectDefinition.setSystem(true);

		return objectDefinition;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionResourceTest.class);

	@Inject
	private Language _language;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectFolder _objectFolder1;

	@DeleteAfterTestRun
	private ObjectFolder _objectFolder2;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}