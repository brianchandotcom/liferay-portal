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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.batch.planner.service.BatchPlannerPolicyLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
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
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.dto.v1_0.util.LinkUtil;
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
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.jackson.databind.ser.VulcanPropertyFilter;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.math.BigDecimal;
import java.math.MathContext;

import java.net.URI;

import java.nio.file.Files;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@FeatureFlags({"LPS-135430", "LPS-148856", "LPS-181663", "LPS-187142"})
@RunWith(Arquillian.class)
public class BatchEngineBrokerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

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

		if (_company2 != null) {
			_companyLocalService.deleteCompany(_company2);
			_company2 = null;
		}
	}

	@Test
	public void testExportCompanyScopeObjectEntryCSV() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObjectCSV",
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		ObjectEntry objectEntry = _addObjectEntry(
			TestPropsValues.getCompanyId(), _OBJECT_ENTRY_ERC_1,
			TestPropsValues.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_addObjectEntryInDifferentCompany("TestObjectCSV");

		_assertEqualsExportCSV(
			_getExportFileString(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, null,
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				"C_TestObjectCSV"),
			_getExpectedCSVString(
				objectEntry.getCreateDate(), _OBJECT_ENTRY_ERC_1,
				"object_entry.csv", null, objectEntry.getObjectEntryId(),
				objectEntry.getModifiedDate()),
			objectEntry.getExternalReferenceCode(),
			_objectEntryExportCSVFieldNames);
	}

	@Test
	public void testExportCompanyScopeObjectEntryJSON() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject",
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		ObjectEntry objectEntry1 = _addObjectEntry(
			TestPropsValues.getCompanyId(), RandomTestUtil.randomString(),
			TestPropsValues.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_addObjectEntryInDifferentCompany("TestObject");

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectEntryExportFieldNames), null));
				}
			});

		_assertEqualsExport(
			_getExpectedJsonNode(
				_objectDefinition1, objectEntry1.getObjectEntryId()),
			_objectEntryExportFieldNames,
			_getActualJsonNode(
				_objectMapper.readTree(
					_getExportFileString(
						BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON,
						_objectEntryExportFieldNames, null,
						"com.liferay.object.rest.dto.v1_0.ObjectEntry",
						"C_TestObject"))));
	}

	@Test
	public void testExportObjectDefinitionCSV() throws Exception {
		_setUpObjectDefinition("TestObjectCSV");

		_assertEqualsExportCSV(
			_getExportFileString(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectDefinitionExportCSVFieldNames, null,
				"com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition",
				null),
			_getExpectedCSVString(
				_objectDefinition1.getCreateDate(), null,
				"object_definition.csv", null,
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getModifiedDate()),
			_objectDefinition1.getExternalReferenceCode(),
			_objectDefinitionExportCSVFieldNames);
	}

	@Test
	public void testExportObjectDefinitionJSON() throws Exception {
		_setUpObjectDefinition("TestObjectJSON1");

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectDefinitionExportFieldNames),
							null));
				}
			});

		_assertEqualsExport(
			_getExpectedJsonNode(_objectDefinition1),
			_objectDefinitionExportFieldNames,
			_getActualJsonNode(
				_objectMapper.readTree(
					_getExportFileString(
						BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON,
						_objectDefinitionExportFieldNames, null,
						"com.liferay.object.admin.rest.dto.v1_0." +
							"ObjectDefinition",
						null)),
				_objectDefinition1.getShortName()));
	}

	@Test
	public void testExportSiteScopeObjectEntryCSV() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObjectCSV",
			ObjectDefinitionConstants.SCOPE_SITE, TestPropsValues.getUser());

		ObjectEntry objectEntry = _addObjectEntry(
			TestPropsValues.getCompanyId(), _OBJECT_ENTRY_ERC_1,
			TestPropsValues.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_addObjectEntry(
			TestPropsValues.getCompanyId(), RandomTestUtil.randomString(),
			_group.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_assertEqualsExportCSV(
			_getExportFileString(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, TestPropsValues.getGroupId(),
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				"C_TestObjectCSV"),
			_getExpectedCSVString(
				objectEntry.getCreateDate(), _OBJECT_ENTRY_ERC_1,
				"object_entry.csv", TestPropsValues.getGroupId(),
				objectEntry.getObjectEntryId(), objectEntry.getModifiedDate()),
			objectEntry.getExternalReferenceCode(),
			_objectEntryExportCSVFieldNames);
	}

	@Test
	public void testExportSiteScopeObjectEntryJSON() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject",
			ObjectDefinitionConstants.SCOPE_SITE, TestPropsValues.getUser());

		ObjectEntry objectEntry = _addObjectEntry(
			TestPropsValues.getCompanyId(), RandomTestUtil.randomString(),
			TestPropsValues.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_addObjectEntry(
			TestPropsValues.getCompanyId(), RandomTestUtil.randomString(),
			_group.getGroupId(), _objectDefinition1,
			TestPropsValues.getUserId());

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectEntryExportFieldNames), null));
				}
			});

		_assertEqualsExport(
			_getExpectedJsonNode(
				_objectDefinition1, objectEntry.getObjectEntryId()),
			_objectEntryExportFieldNames,
			_getActualJsonNode(
				_objectMapper.readTree(
					_getExportFileString(
						BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON,
						_objectEntryExportFieldNames,
						TestPropsValues.getGroupId(),
						"com.liferay.object.rest.dto.v1_0.ObjectEntry",
						"C_TestObject"))));
	}

	@Test
	public void testImportCompanyScopeObjectEntryCSV() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObjectCSV",
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		try (FileInputStream fileInputStream = new FileInputStream(
				_prepareCSVFile(
					RandomTestUtil.nextDate(), _OBJECT_ENTRY_ERC_1,
					"object_entry.csv", TestPropsValues.getGroupId(),
					RandomTestUtil.randomLong(), RandomTestUtil.nextDate(),
					false))) {

			_executeImportTask(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, TestPropsValues.getGroupId(),
				"com.liferay.object.rest.dto.v1_0.ObjectEntry", false,
				"C_TestObjectCSV", _getURIString(fileInputStream));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			_OBJECT_ENTRY_ERC_1, _objectDefinition1.getObjectDefinitionId());

		Assert.assertNotNull(objectEntry);

		_assertEqualsExportCSV(
			_getExportFileString(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, null,
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				"C_TestObjectCSV"),
			_getExpectedCSVString(
				objectEntry.getCreateDate(), _OBJECT_ENTRY_ERC_1,
				"object_entry.csv", null, objectEntry.getObjectEntryId(),
				objectEntry.getModifiedDate()),
			objectEntry.getExternalReferenceCode(),
			_objectEntryExportCSVFieldNames);
	}

	@Test
	public void testImportCompanyScopeObjectEntryJSON() throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject",
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		File file = _createImportFile(
			_addDLFileEntry(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()),
			_objectDefinition1.getExternalReferenceCode(), _OBJECT_ENTRY_ERC_1,
			"object_entry_import_template.txt");

		URI uri = file.toURI();

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), false,
				BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON, uri.toString(),
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				RandomTestUtil.randomString(), 0, "C_TestObject", false);

		for (String fieldName : _objectEntryImportFieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "onErrorFail", "true");

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectEntryImportFieldNames), null));
				}
			});

		JsonNode jsonNode = _objectMapper.readTree(file);

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		_getFinishedBatchEngineImportTask(
			batchPlannerPlan.getBatchPlannerPlanId());

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			_OBJECT_ENTRY_ERC_1, _objectDefinition1.getObjectDefinitionId());

		_assertEqualsImport(
			_getExpectedJsonNode(
				_objectDefinition1, objectEntry.getObjectEntryId()),
			_objectEntryImportFieldNames, jsonNode.get(0));
	}

	@Test
	public void testImportObjectDefinitionJSON() throws Exception {
		File file = _createImportFile("json", "object_definition_import.json");

		URI uri = file.toURI();

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), false,
				BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON, uri.toString(),
				"com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition",
				RandomTestUtil.randomString(), 0, "DEFAULT", false);

		for (String fieldName : _objectDefinitionImportFieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "onErrorFail", "true");

		_objectDefinition2 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject2",
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		_objectDefinition2 =
			_objectDefinitionLocalService.updateExternalReferenceCode(
				_objectDefinition2.getObjectDefinitionId(),
				_OBJECT_DEFINITION_2_ERC);

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectDefinitionImportFieldNames),
							null));
				}
			});

		JsonNode jsonNode = _objectMapper.readTree(file);

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		_getFinishedBatchEngineImportTask(
			batchPlannerPlan.getBatchPlannerPlanId());

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_OBJECT_DEFINITION_1_ERC, TestPropsValues.getCompanyId());

		_assertEqualsImport(
			_getExpectedJsonNode(_objectDefinition1),
			_objectDefinitionImportFieldNames, jsonNode.get(0));
	}

	@Test
	public void testImportSiteScopeObjectEntryCSV() throws Exception {

		// Default group

		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObjectCSV",
			ObjectDefinitionConstants.SCOPE_SITE, TestPropsValues.getUser());

		_testImportSiteScopeObjectEntryCSV(
			TestPropsValues.getGroupId(), _OBJECT_ENTRY_ERC_1);

		// Global group

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group globalGroup = company.getGroup();

		_testImportSiteScopeObjectEntryCSV(
			globalGroup.getGroupId(), _OBJECT_ENTRY_ERC_3);

		// New group

		_testImportSiteScopeObjectEntryCSV(
			_group.getGroupId(), _OBJECT_ENTRY_ERC_2);
	}

	@Test
	public void testImportSiteScopeObjectEntryJSON() throws Exception {

		// Default group

		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), "TestObject",
			ObjectDefinitionConstants.SCOPE_SITE, TestPropsValues.getUser());

		_testImportSiteScopeObjectEntry(
			TestPropsValues.getGroupId(), _OBJECT_ENTRY_ERC_1);

		// Global group

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group globalGroup = company.getGroup();

		_testImportSiteScopeObjectEntry(
			globalGroup.getGroupId(), _OBJECT_ENTRY_ERC_3);

		// New group

		_testImportSiteScopeObjectEntry(
			_group.getGroupId(), _OBJECT_ENTRY_ERC_2);
	}

	private Company _addCompany(String webId) throws Exception {
		long companyId = _counterLocalService.increment();

		WebAppPool.put(
			companyId, WebKeys.PORTLET_CATEGORY, new PortletCategory());

		return _companyLocalService.addCompany(
			companyId, webId, webId, webId, 0, true, null, null, null, null,
			null, null);
	}

	private DLFileEntry _addDLFileEntry(long groupId, long userId)
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		return _dlFileEntryLocalService.addFileEntry(
			null, userId, groupId, groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			MimeTypesUtil.getExtensionContentType("txt"),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private ObjectEntry _addObjectEntry(
			long companyId, String externalReferenceCode, long groupId,
			ObjectDefinition objectDefinition, long userId)
		throws Exception {

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(
					_userLocalService.getUser(userId)));

			PrincipalThreadLocal.setName(userId);

			DLFileEntry dlFileEntry = _addDLFileEntry(groupId, userId);

			return _objectEntryLocalService.addOrUpdateObjectEntry(
				externalReferenceCode, userId,
				_getGroupId(groupId, objectDefinition),
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"testAttachmentField", dlFileEntry.getFileEntryId()
				).put(
					"testBooleanField", RandomTestUtil.randomBoolean()
				).put(
					"testDateField", "2022-01-01"
				).put(
					"testDateTimeField", "2023-07-27T12:00:00.000Z"
				).put(
					"testDecimalField", 7.5
				).put(
					"testIntegerField", 5
				).put(
					"testLongIntegerField", 123456789L
				).put(
					"testLongTextField",
					StringBundler.concat(
						"Lorem ipsum dolor sit amet, consectetur adipiscing ",
						"elit, sed do eiusmod tempor incididunt ut labore et ",
						"dolore magna aliqua. Ut enim ad minim veniam, quis ",
						"nostrud exercitation ullamco laboris nisi ut aliquip ",
						"ex ea commodo consequat. Duis aute irure dolor in ",
						"reprehenderit in voluptate velit esse cillum dolore ",
						"eu fugiat nulla pariatur. Excepteur sint occaecat ",
						"cupidatat non proident, sunt in culpa qui officia ",
						"deserunt mollit anim id est laborum.")
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
					StringBundler.concat(
						"<p>Test text</p>\n<p>\n",
						"  <img alt=\"\" height=\"202\" src=",
						"http://localhost:8080/image/company_logo\">\n</p>")
				).put(
					"testTextField", "Lorem Ipsum"
				).build(),
				ServiceContextTestUtil.getServiceContext(
					companyId, groupId, userId));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private void _addObjectEntryInDifferentCompany(String name)
		throws Exception {

		_company2 = _addCompany("test.com");

		User user = UserTestUtil.getAdminUser(_company2.getCompanyId());

		_objectDefinition2 = _publishObjectDefinition(
			_company2.getCompanyId(), name,
			ObjectDefinitionConstants.SCOPE_COMPANY, user);

		_addObjectEntry(
			_company2.getCompanyId(), RandomTestUtil.randomString(),
			_company2.getGroupId(), _objectDefinition2, user.getUserId());
	}

	private void _assertActions(JsonNode fieldJsonNode, String fieldName) {
		JsonNode jsonNode = fieldJsonNode.get(fieldName);

		Assert.assertTrue(!jsonNode.isEmpty());
	}

	private void _assertColumnNames(
		List<String> actualColumnNames, List<String> expectedColumnNames,
		List<String> fieldNames) {

		Assert.assertEquals(fieldNames, actualColumnNames);

		Assert.assertEquals(expectedColumnNames, actualColumnNames);
	}

	private void _assertEqualsExport(
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
					fieldName + " value mismatch",
					expectedFieldJsonNode.toString(), fieldJsonNode.toString());
			}
		}
	}

	private void _assertEqualsExportCSV(
			String actualCSVString, String expectedCSVString,
			String externalReferenceCode, List<String> fieldNames)
		throws Exception {

		UnsyncBufferedReader actualUnsyncBufferedReader =
			new UnsyncBufferedReader(
				new InputStreamReader(
					new ByteArrayInputStream(actualCSVString.getBytes())));

		UnsyncBufferedReader expectedUnsyncBufferedReader =
			new UnsyncBufferedReader(
				new InputStreamReader(
					new ByteArrayInputStream(expectedCSVString.getBytes())));

		_assertColumnNames(
			_getContentRow(actualUnsyncBufferedReader.readLine()),
			_getContentRow(expectedUnsyncBufferedReader.readLine()),
			fieldNames);

		String actualLineString = actualUnsyncBufferedReader.readLine();

		String expectedLineString = expectedUnsyncBufferedReader.readLine();

		Assert.assertNotNull(actualLineString);

		boolean found = false;

		while (actualLineString != null) {
			if (actualLineString.contains(externalReferenceCode)) {
				Assert.assertEquals(expectedLineString, actualLineString);

				found = true;
			}

			actualLineString = actualUnsyncBufferedReader.readLine();
		}

		Assert.assertTrue(
			"There is no CSV line for externalReferenceCode: " +
				externalReferenceCode,
			found);
	}

	private void _assertEqualsImport(
		JsonNode expectedJsonNode, List<String> fieldNames, JsonNode jsonNode) {

		for (String fieldName : fieldNames) {
			JsonNode expectedFieldJsonNode = _removeBackendGeneratedFields(
				fieldName, expectedJsonNode.get(fieldName));

			JsonNode fieldJsonNode = _removeBackendGeneratedFields(
				fieldName, jsonNode.get(fieldName));

			if ((expectedFieldJsonNode == null) && (fieldJsonNode == null)) {
				continue;
			}

			Assert.assertEquals(
				fieldName + " value mismatch", expectedFieldJsonNode.toString(),
				fieldJsonNode.toString());
		}
	}

	private File _createImportFile(
			DLFileEntry dlFileEntry,
			String objectDefinitionExternalReferenceCode, String objectEntryERC,
			String templateName)
		throws Exception {

		File file = _file.createTempFile("json");

		String template = StreamUtil.toString(_getInputStream(templateName));

		Link link = LinkUtil.toLink(
			_dlAppService, dlFileEntry, _dlURLHelper,
			objectDefinitionExternalReferenceCode, objectEntryERC, _portal);

		template = StringUtil.replace(
			template,
			new String[] {
				"$[ATTACHMENT_HREF]", "$[ATTACHMENT_ID]", "$[ATTACHMENT_LABEL]",
				"$[ATTACHMENT_NAME]", "$[OBJECT_ENTRY_ERC]"
			},
			new String[] {
				link.getHref(), String.valueOf(dlFileEntry.getFileEntryId()),
				link.getLabel(), dlFileEntry.getFileName(), objectEntryERC
			});

		_file.write(file, template);

		return file;
	}

	private File _createImportFile(String extension, String fileName)
		throws Exception {

		File file = _file.createTempFile(extension);

		Files.copy(_getInputStream(fileName), file.toPath());

		return file;
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

	private void _executeImportTask(
			String externalType, List<String> fieldNames, long groupId,
			String internalClassName, boolean siteScope,
			String taskItemDelegateName, String uriString)
		throws Exception {

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), false, externalType, uriString,
				internalClassName, RandomTestUtil.randomString(), 0,
				taskItemDelegateName, false);

		for (String fieldName : fieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		if (siteScope) {
			_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), "siteId",
				String.valueOf(groupId));
		}

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "onErrorFail", "true");

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "delimiter",
			StringPool.COMMA);

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "enclosingCharacter",
			StringPool.QUOTE);

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		_getFinishedBatchEngineImportTask(
			batchPlannerPlan.getBatchPlannerPlanId());
	}

	private JsonNode _getActualJsonNode(JsonNode arrayJsonNode) {
		Assert.assertTrue(arrayJsonNode.isArray());
		Assert.assertEquals(1, arrayJsonNode.size());

		return arrayJsonNode.get(0);
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

	private List<String> _getContentRow(String line) {
		return Arrays.asList(line.split(StringPool.COMMA, -1));
	}

	private String _getExpectedCSVString(
			Date createDate, String externalReferenceCode, String fileName,
			Long groupId, long id, Date modifiedDate)
		throws Exception {

		String groupNameString = null;

		if (Validator.isNotNull(groupId)) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			groupNameString = group.getGroupKey();
		}

		return StringUtil.replace(
			StreamUtil.toString(_getInputStream(fileName)),
			new String[] {
				"$[DATE_CREATED]", "$[DATE_MODIFIED]",
				"$[EXTERNAL_REFERENCE_CODE]", "$[ID]", "$[SCOPE_KEY]"
			},
			new String[] {
				_toDateString(createDate), _toDateString(modifiedDate),
				externalReferenceCode, String.valueOf(id), groupNameString
			});
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

	private String _getExportFileString(
			String externalType, List<String> fieldNames, Long groupId,
			String internalClassName, String taskItemDelegateName)
		throws Exception {

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), true, externalType,
				StringPool.SLASH, internalClassName,
				RandomTestUtil.randomString(), 0, taskItemDelegateName, false);

		for (String fieldName : fieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		if (Validator.isNotNull(groupId)) {
			_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), "siteId",
				String.valueOf(groupId));
		}

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		BatchEngineExportTask batchEngineExportTask =
			_getFinishedBatchEngineExportTask(
				batchPlannerPlan.getBatchPlannerPlanId(),
				TestPropsValues.getCompanyId());

		return StreamUtil.toString(
			_getZipInputStream(
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())));
	}

	private BatchEngineExportTask _getFinishedBatchEngineExportTask(
			long batchPlannerPlanId, long companyId)
		throws Exception {

		while (true) {
			BatchEngineExportTask batchEngineExportTask =
				_batchEngineExportTaskLocalService.
					getBatchEngineExportTaskByExternalReferenceCode(
						String.valueOf(batchPlannerPlanId), companyId);

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

	private BatchEngineImportTask _getFinishedBatchEngineImportTask(
			long batchPlannerPlanId)
		throws Exception {

		while (true) {
			BatchEngineImportTask batchEngineImportTask =
				_batchEngineImportTaskLocalService.
					getBatchEngineImportTaskByExternalReferenceCode(
						String.valueOf(batchPlannerPlanId),
						TestPropsValues.getCompanyId());

			if (Objects.equals(
					BatchEngineTaskExecuteStatus.COMPLETED.toString(),
					batchEngineImportTask.getExecuteStatus()) ||
				Objects.equals(
					BatchEngineTaskExecuteStatus.FAILED.toString(),
					batchEngineImportTask.getExecuteStatus())) {

				return batchEngineImportTask;
			}

			Thread.sleep(1000);
		}
	}

	private long _getGroupId(long groupId, ObjectDefinition objectDefinition) {
		if (!Objects.equals(
				objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_SITE)) {

			return 0;
		}

		return groupId;
	}

	private InputStream _getInputStream(String fileName) {
		return getClass().getResourceAsStream(
			StringBundler.concat(
				"/com/liferay/batch/planner/batch/engine/broker/test",
				"/dependencies/", fileName));
	}

	private String _getURIString(InputStream inputStream) throws Exception {
		File file = _file.createTempFile("csv");

		Files.copy(inputStream, file.toPath());

		URI uri = file.toURI();

		return uri.toString();
	}

	private ZipInputStream _getZipInputStream(InputStream inputStream)
		throws Exception {

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		zipInputStream.getNextEntry();

		return zipInputStream;
	}

	private File _prepareCSVFile(
			Date createDate, String externalReferenceCode, String fileName,
			long groupId, long id, Date modifiedDate, boolean siteScope)
		throws Exception {

		File file = _file.createTempFile("csv");

		String template = StreamUtil.toString(_getInputStream(fileName));

		String groupNameString = null;

		if (siteScope) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			groupNameString = group.getGroupKey();
		}

		template = StringUtil.replace(
			template,
			new String[] {
				"$[DATE_CREATED]", "$[DATE_MODIFIED]",
				"$[EXTERNAL_REFERENCE_CODE]", "$[ID]", "$[SCOPE_KEY]"
			},
			new String[] {
				_toDateString(createDate), _toDateString(modifiedDate),
				externalReferenceCode, String.valueOf(id), groupNameString
			});

		_file.write(file, template);

		return file;
	}

	private ObjectDefinition _publishObjectDefinition(
			long companyId, String name, String scope, User user)
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
						LocaleUtil.US, "listTypeEntryName1"));

			ListTypeEntry listTypeEntry2 =
				ListTypeEntryUtil.createListTypeEntry(
					"listTypeEntryKey2",
					Collections.singletonMap(
						LocaleUtil.US, "listTypeEntryName2"));

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.addListTypeDefinition(
					null, user.getUserId(),
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()),
					false, Arrays.asList(listTypeEntry1, listTypeEntry2));

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					user.getUserId(), 0, false, false, false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					name, null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					false, scope,
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

	private JsonNode _removeBackendGeneratedFields(
		String fieldName, JsonNode jsonNode) {

		if (jsonNode == null) {
			return null;
		}

		if (!jsonNode.isArray()) {
			return jsonNode;
		}

		for (JsonNode itemJsonNode : jsonNode) {
			if (itemJsonNode.isObject() &&
				_ignoredImportFields.containsKey(fieldName)) {

				ObjectNode objectNode = (ObjectNode)itemJsonNode;

				objectNode.remove(_ignoredImportFields.get(fieldName));
			}
		}

		return jsonNode;
	}

	private void _setUpObjectDefinition(String name) throws Exception {
		_objectDefinition1 = _publishObjectDefinition(
			TestPropsValues.getCompanyId(), name,
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		_objectDefinition1 =
			_objectDefinitionLocalService.updateExternalReferenceCode(
				_objectDefinition1.getObjectDefinitionId(),
				_OBJECT_DEFINITION_1_ERC);

		_objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			new UnicodeProperties(), false);

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
			ObjectDefinitionConstants.SCOPE_COMPANY, TestPropsValues.getUser());

		_objectRelationshipLocalService.addObjectRelationship(
			null, TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"a" + RandomTestUtil.randomString(), false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);

		_objectValidationRuleLocalService.addObjectValidationRule(
			StringPool.BLANK, TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true,
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			"isEmailAddress(textObjectField)", false, Collections.emptyList());

		_objectViewLocalService.addObjectView(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Arrays.asList(_createObjectViewColumn("createDate")),
			Arrays.asList(_createObjectViewFilterColumn("createDate")),
			Arrays.asList(_createObjectViewSortColumn("createDate", "asc")));
	}

	private void _testImportSiteScopeObjectEntry(
			long groupId, String objectEntryERC)
		throws Exception {

		File file = _createImportFile(
			_addDLFileEntry(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()),
			_objectDefinition1.getExternalReferenceCode(), objectEntryERC,
			"object_entry_import_template.txt");

		URI uri = file.toURI();

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.addBatchPlannerPlan(
				TestPropsValues.getUserId(), false,
				BatchPlannerPlanConstants.EXTERNAL_TYPE_JSON, uri.toString(),
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				RandomTestUtil.randomString(), 0, "C_TestObject", false);

		for (String fieldName : _objectEntryImportFieldNames) {
			_batchPlannerMappingLocalService.addBatchPlannerMapping(
				TestPropsValues.getUserId(),
				batchPlannerPlan.getBatchPlannerPlanId(), fieldName, "String",
				fieldName, "String", StringPool.BLANK);
		}

		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "onErrorFail", "true");
		_batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			TestPropsValues.getUserId(),
			batchPlannerPlan.getBatchPlannerPlanId(), "siteId",
			String.valueOf(groupId));

		_objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					addFilter(
						"Liferay.Vulcan",
						VulcanPropertyFilter.of(
							new HashSet<>(_objectEntryImportFieldNames), null));
				}
			});

		JsonNode jsonNode = _objectMapper.readTree(file);

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		_getFinishedBatchEngineImportTask(
			batchPlannerPlan.getBatchPlannerPlanId());

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntryERC, _objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(objectEntry.getGroupId(), groupId);

		_assertEqualsImport(
			_getExpectedJsonNode(
				_objectDefinition1, objectEntry.getObjectEntryId()),
			_objectEntryImportFieldNames, jsonNode.get(0));
	}

	private void _testImportSiteScopeObjectEntryCSV(
			long groupId, String objectEntryERC)
		throws Exception {

		try (FileInputStream fileInputStream = new FileInputStream(
				_prepareCSVFile(
					RandomTestUtil.nextDate(), objectEntryERC,
					"object_entry.csv", groupId, RandomTestUtil.randomLong(),
					RandomTestUtil.nextDate(), true))) {

			_executeImportTask(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, groupId,
				"com.liferay.object.rest.dto.v1_0.ObjectEntry", true,
				"C_TestObjectCSV", _getURIString(fileInputStream));
		}

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntryERC, _objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(objectEntry.getGroupId(), groupId);

		_assertEqualsExportCSV(
			_getExportFileString(
				BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				_objectEntryExportCSVFieldNames, groupId,
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				"C_TestObjectCSV"),
			_getExpectedCSVString(
				objectEntry.getCreateDate(), objectEntryERC, "object_entry.csv",
				groupId, objectEntry.getObjectEntryId(),
				objectEntry.getModifiedDate()),
			objectEntry.getExternalReferenceCode(),
			_objectEntryExportCSVFieldNames);
	}

	private String _toDateString(Date date) {
		Instant instant = date.toInstant();

		return String.valueOf(instant.truncatedTo(ChronoUnit.SECONDS));
	}

	private static final String _OBJECT_DEFINITION_1_ERC =
		"TEST-OBJECT-DEFINITION-1";

	private static final String _OBJECT_DEFINITION_2_ERC =
		"TEST-OBJECT-DEFINITION-2";

	private static final String _OBJECT_ENTRY_ERC_1 = "TEST-OBJECT-ENTRY-1";

	private static final String _OBJECT_ENTRY_ERC_2 = "TEST-OBJECT-ENTRY-2";

	private static final String _OBJECT_ENTRY_ERC_3 = "TEST-OBJECT-ENTRY-3";

	private static final Map<String, List<String>> _ignoredImportFields =
		HashMapBuilder.<String, List<String>>put(
			"objectActions", Arrays.asList("dateCreated", "dateModified", "id")
		).put(
			"objectFields",
			Arrays.asList(
				"dateCreated", "dateModified", "externalReferenceCode", "id",
				"localized")
		).put(
			"objectLayouts",
			Arrays.asList(
				"dateCreated", "dateModified", "id", "objectDefinitionId")
		).put(
			"objectRelationships",
			Arrays.asList("id", "objectDefinitionId1", "objectDefinitionId2")
		).put(
			"objectValidationRules",
			Arrays.asList(
				"dateCreated", "dateModified", "id", "objectDefinitionId")
		).put(
			"objectViews",
			Arrays.asList(
				"dateCreated", "dateModified", "id", "objectDefinitionId")
		).build();
	private static final List<String> _objectDefinitionExportCSVFieldNames =
		Arrays.asList(
			"accountEntryRestrictedObjectFieldName", "dateCreated",
			"dateModified", "defaultLanguageId", "externalReferenceCode", "id",
			"name", "objectFolderExternalReferenceCode", "panelAppOrder",
			"panelCategoryKey", "restContextPath",
			"rootObjectDefinitionExternalReferenceCode", "scope", "storageType",
			"titleObjectFieldName");
	private static final List<String> _objectDefinitionExportFieldNames =
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
	private static final List<String> _objectDefinitionImportFieldNames =
		Arrays.asList(
			"accountEntryRestricted", "accountEntryRestrictedObjectFieldName",
			"active", "defaultLanguageId", "enableCategorization",
			"enableComments", "enableLocalization", "enableObjectEntryHistory",
			"externalReferenceCode", "label", "modifiable", "name",
			"objectActions", "objectFields",
			"objectFolderExternalReferenceCode", "objectLayouts",
			"objectRelationships", "objectValidationRules", "objectViews",
			"panelAppOrder", "panelCategoryKey", "parameterRequired",
			"pluralLabel", "portlet",
			"rootObjectDefinitionExternalReferenceCode", "scope", "status",
			"storageType", "system", "titleObjectFieldName");
	private static final List<String> _objectEntryExportCSVFieldNames =
		Arrays.asList(
			"dateCreated", "dateModified", "externalReferenceCode", "id",
			"keywords", "scopeKey", "testDateField", "testDateTimeField",
			"testDecimalField", "testIntegerField", "testLongIntegerField",
			"testLongTextField", "testPrecisionDecimalField",
			"testRichTextField", "testTextField");
	private static final List<String> _objectEntryExportFieldNames =
		Arrays.asList(
			"actions", "dateCreated", "dateModified", "externalReferenceCode",
			"id", "testAttachmentField", "testBooleanField", "testDateField",
			"testDateTimeField", "testDecimalField", "testIntegerField",
			"testLongIntegerField", "testLongTextField",
			"testMultiselectPicklistField", "testPicklistField",
			"testPrecisionDecimalField", "testRichTextField", "testTextField");
	private static final List<String> _objectEntryImportFieldNames =
		Arrays.asList(
			"externalReferenceCode", "keywords", "testAttachmentField",
			"testBooleanField", "testDateField", "testDateTimeField",
			"testDecimalField", "testIntegerField", "testLongIntegerField",
			"testLongTextField", "testMultiselectPicklistField",
			"testPicklistField", "testPrecisionDecimalField",
			"testRichTextField", "testTextField");
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
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

	@Inject
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	@Inject
	private BatchPlannerPolicyLocalService _batchPlannerPolicyLocalService;

	private Company _company2;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private com.liferay.portal.kernel.util.File _file;

	@DeleteAfterTestRun
	private Group _group;

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

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}