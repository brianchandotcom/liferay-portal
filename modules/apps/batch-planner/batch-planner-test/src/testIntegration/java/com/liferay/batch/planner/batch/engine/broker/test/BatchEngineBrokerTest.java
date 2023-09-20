/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.batch.engine.broker.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.DateTimeObjectFieldBuilder;
import com.liferay.object.field.builder.DecimalObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PrecisionDecimalObjectFieldBuilder;
import com.liferay.object.field.builder.RichTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFolderItemLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.persistence.ObjectViewColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewFilterColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.jackson.databind.ser.VulcanPropertyFilter;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import java.math.BigDecimal;
import java.math.MathContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@RunWith(Arquillian.class)
public class BatchEngineBrokerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() throws Exception {
		if (_objectDefinition1 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition1.getObjectDefinitionId());
		}

		if (_objectDefinition2 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition2.getObjectDefinitionId());
		}
	}

	@Test
	public void testExportCompanyScopeObjectEntry() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject",
			TestPropsValues.getUser());

		ObjectEntry objectEntry1 = _addObjectEntry(
			TestPropsValues.getCompanyId(),
			_objectDefinition1.getObjectDefinitionId(),
			TestPropsValues.getUserId());

		_company2 = CompanyTestUtil.addCompany();

		PortalInstances.initCompany(_company2);

		User user = UserTestUtil.getAdminUser(_company2.getCompanyId());

		_objectDefinition2 = _publishObjectDefinition(
			_company2.getCompanyId(), "TestObject", user);

		_addObjectEntry(
			_company2.getCompanyId(),
			_objectDefinition2.getObjectDefinitionId(), user.getUserId());

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), true,
				BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON, StringPool.SLASH,
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				RandomTestUtil.randomString(), 0, "C_TestObject", false);

		for (String fieldName : _objectEntryFieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		BatchEngineExportTask batchEngineExportTask =
			_getFinishedBatchEngineExportTask(
				batchPlannerPlan.getBatchPlannerPlanId());

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectEntryFieldNames), null));
				}
			});

		JsonNode expectedJsonNode = _getExpectedJsonNode(
			_objectDefinition1, objectEntry1.getObjectEntryId());

		JsonNode jsonNode = _objectMapper.readTree(
			_getZipInputStream(
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())));

		Assert.assertTrue(jsonNode.isArray());
		Assert.assertEquals(1, jsonNode.size());

		_assertEquals(
			expectedJsonNode, _objectEntryFieldNames, jsonNode.get(0));
	}

	@Test
	public void testExportObjectDefinition() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject1",
			TestPropsValues.getUser());

		_objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			new UnicodeProperties());

		ObjectFolder objectFolder = _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());

		_objectFolderItemLocalService.addObjectFolderItem(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			objectFolder.getObjectFolderId(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_objectLayoutLocalService.addObjectLayout(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.emptyList());

		_objectDefinition2 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject2",
			TestPropsValues.getUser());

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"a" + RandomTestUtil.randomString(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true,
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			"isEmailAddress(textObjectField)", false, Collections.emptyList());

		_objectViewLocalService.addObjectView(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Arrays.asList(_createObjectViewColumn("createDate")),
			Arrays.asList(_createObjectViewFilterColumn("createDate")),
			Arrays.asList(_createObjectViewSortColumn("createDate", "asc")));

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), true,
				BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON, StringPool.SLASH,
				"com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition",
				RandomTestUtil.randomString(), 0, null, false);

		for (String fieldName : _objectDefinitionFieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		BatchEngineExportTask batchEngineExportTask =
			_getFinishedBatchEngineExportTask(
				batchPlannerPlan.getBatchPlannerPlanId());

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectDefinitionFieldNames), null));
				}
			});

		JsonNode expectedJsonNode = _getExpectedJsonNode(_objectDefinition1);

		JsonNode jsonNode = _objectMapper.readTree(
			_getZipInputStream(
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())));

		Assert.assertTrue(jsonNode.isArray());
		Assert.assertTrue(jsonNode.size() >= 2);

		JsonNode actualJsonNode = _getActualJsonNode(jsonNode, "TestObject1");

		Assert.assertNotNull(
			"TestObject1 object definition is not exported", actualJsonNode);

		_assertEquals(
			expectedJsonNode, _objectDefinitionFieldNames, actualJsonNode);
	}

	private ObjectEntry _addObjectEntry(
			long companyId, long objectDefinitionId, long userId)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			userId, 0, objectDefinitionId,
			HashMapBuilder.<String, Serializable>put(
				"testAttachmentField", _getAttachmentFieldValue()
			).put(
				"testBooleanField", RandomTestUtil.randomBoolean()
			).put(
				"testDateField", "2022-01-01"
			).put(
				"testDateTimeField", "2023-07-27T12:00:00.000Z"
			).put(
				"testDecimalField", 7.5
			).put(
				"testIntegerField", RandomTestUtil.randomInt()
			).put(
				"testLongIntegerField", 123456789L
			).put(
				"testLongTextField", RandomTestUtil.randomString()
			).put(
				"testMultiselectPicklistField",
				"listTypeEntryKey1, listTypeEntryKey2"
			).put(
				"testPicklistField", "listTypeEntryKey1"
			).put(
				"testPrecisionDecimalField",
				new BigDecimal(0.1234567891234567, MathContext.DECIMAL64)
			).put(
				"testRichTextField",
				"<p>Test text</p><p><img alt=\\\"\\\" height=\\\"202\\\" src=" +
					"\\\"http://localhost:8080/image/company_logo?\\\"><br></p>"
			).put(
				"testTextField", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(companyId, 0, userId));
	}

	private void _assertActions(JsonNode fieldJsonNode, String fieldName) {
		JsonNode jsonNode = fieldJsonNode.get(fieldName);

		Assert.assertTrue(!jsonNode.isEmpty());
	}

	private void _assertEquals(
		JsonNode expectedJsonNode, List<String> fieldNames, JsonNode jsonNode) {

		for (String fieldName : fieldNames) {
			JsonNode expectedFieldJsonNode = expectedJsonNode.get(fieldName);

			JsonNode fieldJsonNode = jsonNode.get(fieldName);

			if (Objects.equals(fieldName, "actions")) {
				_assertActions(fieldJsonNode, "delete");
				_assertActions(fieldJsonNode, "get");
				_assertActions(fieldJsonNode, "permissions");
				_assertActions(fieldJsonNode, "update");
			}
			else {
				if ((expectedFieldJsonNode == null) &&
					(fieldJsonNode == null)) {

					continue;
				}

				Assert.assertEquals(
					expectedFieldJsonNode.toString(), fieldJsonNode.toString());
			}
		}
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private ObjectViewColumn _createObjectViewColumn(String objectFieldName)
		throws Exception {

		ObjectViewColumn objectViewColumn = _objectViewColumnPersistence.create(
			0);

		objectViewColumn.setLabelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectViewColumn.setObjectFieldName(objectFieldName);
		objectViewColumn.setPriority(0);

		return objectViewColumn;
	}

	private ObjectViewFilterColumn _createObjectViewFilterColumn(
		String objectFieldName) {

		ObjectViewFilterColumn objectViewFilterColumn =
			_objectViewFilterColumnPersistence.create(0);

		objectViewFilterColumn.setFilterType(null);
		objectViewFilterColumn.setJSON(null);
		objectViewFilterColumn.setObjectFieldName(objectFieldName);

		return objectViewFilterColumn;
	}

	private ObjectViewSortColumn _createObjectViewSortColumn(
		String objectFieldName, String sortOrder) {

		ObjectViewSortColumn objectViewSortColumn =
			_objectViewSortColumnPersistence.create(0);

		objectViewSortColumn.setObjectFieldName(objectFieldName);
		objectViewSortColumn.setPriority(0);
		objectViewSortColumn.setSortOrder(sortOrder);

		return objectViewSortColumn;
	}

	private JsonNode _getActualJsonNode(JsonNode arrayJsonNode, String name) {
		for (JsonNode jsonNode : arrayJsonNode) {
			JsonNode nameJsonNode = jsonNode.get("name");

			if (Objects.equals(name, nameJsonNode.textValue())) {
				return jsonNode;
			}
		}

		return null;
	}

	private Long _getAttachmentFieldValue() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			MimeTypesUtil.getExtensionContentType("txt"),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));

		return dlFileEntry.getFileEntryId();
	}

	private JsonNode _getExpectedJsonNode(ObjectDefinition objectDefinition)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			TestPropsValues.getUser()
		).build();

		return _objectMapper.convertValue(
			objectDefinitionResource.getObjectDefinition(
				objectDefinition.getObjectDefinitionId()),
			JsonNode.class);
	}

	private JsonNode _getExpectedJsonNode(
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		return _objectMapper.convertValue(
			defaultObjectEntryManager.getObjectEntry(
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), _dtoConverterRegistry,
					objectEntryId, LocaleUtil.getDefault(), null,
					TestPropsValues.getUser()),
				objectDefinition, objectEntryId),
			JsonNode.class);
	}

	private BatchEngineExportTask _getFinishedBatchEngineExportTask(
			long batchPlannerPlanId)
		throws Exception {

		while (true) {
			BatchEngineExportTask batchEngineExportTask =
				_batchEngineExportTaskLocalService.
					getBatchEngineExportTaskByExternalReferenceCode(
						String.valueOf(batchPlannerPlanId),
						TestPropsValues.getCompanyId());

			if (Objects.equals(
					BatchEngineTaskExecuteStatus.COMPLETED.toString(),
					batchEngineExportTask.getExecuteStatus()) ||
				Objects.equals(
					BatchEngineTaskExecuteStatus.FAILED.toString(),
					batchEngineExportTask.getExecuteStatus())) {

				return batchEngineExportTask;
			}

			Thread.sleep(1000);
		}
	}

	private ZipInputStream _getZipInputStream(InputStream inputStream)
		throws Exception {

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		zipInputStream.getNextEntry();

		return zipInputStream;
	}

	private ObjectDefinition _publishObjectDefinition(
			long companyId, String name, User user)
		throws Exception {

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			ListTypeEntry listTypeEntry1 =
				ListTypeEntryUtil.createListTypeEntry(
					"listTypeEntryKey1",
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()));

			ListTypeEntry listTypeEntry2 =
				ListTypeEntryUtil.createListTypeEntry(
					"listTypeEntryKey2",
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()));

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.addListTypeDefinition(
					null, user.getUserId(),
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()),
					false, Arrays.asList(listTypeEntry1, listTypeEntry2));

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					user.getUserId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false, false,
					false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					name, null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					false, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						new AttachmentObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testAttachmentField"
						).objectFieldSettings(
							Arrays.asList(
								_createObjectFieldSetting(
									"acceptedFileExtensions", "txt"),
								_createObjectFieldSetting(
									"fileSource", "documentsAndMedia"),
								_createObjectFieldSetting(
									"maximumFileSize", "100"))
						).build(),
						new BooleanObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testBooleanField"
						).build(),
						new DateObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testDateField"
						).build(),
						new DateTimeObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testDateTimeField"
						).objectFieldSettings(
							Collections.singletonList(
								_createObjectFieldSetting(
									ObjectFieldSettingConstants.
										NAME_TIME_STORAGE,
									ObjectFieldSettingConstants.
										VALUE_USE_INPUT_AS_ENTERED))
						).build(),
						new DecimalObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testDecimalField"
						).build(),
						new IntegerObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testIntegerField"
						).build(),
						new LongIntegerObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testLongIntegerField"
						).build(),
						new LongTextObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testLongTextField"
						).build(),
						new MultiselectPicklistObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).listTypeDefinitionId(
							listTypeDefinition.getListTypeDefinitionId()
						).name(
							"testMultiselectPicklistField"
						).build(),
						new PicklistObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).listTypeDefinitionId(
							listTypeDefinition.getListTypeDefinitionId()
						).name(
							"testPicklistField"
						).build(),
						new PrecisionDecimalObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testPrecisionDecimalField"
						).build(),
						new RichTextObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testRichTextField"
						).build(),
						new TextObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							"testTextField"
						).build()));

			return _objectDefinitionLocalService.publishCustomObjectDefinition(
				user.getUserId(), objectDefinition.getObjectDefinitionId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private static final List<String> _objectDefinitionFieldNames =
		Arrays.asList(
			"accountEntryRestricted", "accountEntryRestrictedObjectFieldName",
			"active", "dateCreated", "dateModified", "defaultLanguageId",
			"enableCategorization", "enableComments", "enableLocalization",
			"enableObjectEntryHistory", "externalReferenceCode", "id", "label",
			"modifiable", "name", "objectActions", "objectFields",
			"objectFolderExternalReferenceCode", "objectLayouts",
			"objectRelationships", "objectValidationRules", "objectViews",
			"panelAppOrder", "panelCategoryKey", "parameterRequired",
			"pluralLabel", "portlet", "restContextPath",
			"rootObjectDefinitionExternalReferenceCode", "scope", "status",
			"storageType", "system", "titleObjectFieldName");
	private static final List<String> _objectEntryFieldNames = Arrays.asList(
		"actions", "dateCreated", "dateModified", "externalReferenceCode", "id",
		"testAttachmentField", "testBooleanField", "testDateField",
		"testDateTimeField", "testDecimalField", "testIntegerField",
		"testLongIntegerField", "testLongTextField",
		"testMultiselectPicklistField", "testPicklistField",
		"testPrecisionDecimalField", "testRichTextField", "testTextField");
	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
			enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
			setDateFormat(new ISO8601DateFormat());
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

	@Inject
	private BatchEngineBroker _batchEngineBroker;

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Inject
	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

	@Inject
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	private Company _company2;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectFolderItemLocalService _objectFolderItemLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Inject
	private ObjectViewColumnPersistence _objectViewColumnPersistence;

	@Inject
	private ObjectViewFilterColumnPersistence
		_objectViewFilterColumnPersistence;

	@Inject
	private ObjectViewLocalService _objectViewLocalService;

	@Inject
	private ObjectViewSortColumnPersistence _objectViewSortColumnPersistence;

}