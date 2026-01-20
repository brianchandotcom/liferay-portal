/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.test.util.TreeTestUtil;
import com.liferay.object.tree.Tree;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.staging.StagingGroupHelper;

import jakarta.portlet.GenericPortlet;
import jakarta.portlet.Portlet;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
@FeatureFlag("LPD-35914")
@RunWith(Arquillian.class)
public class BatchEnginePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");

		Bundle bundle = FrameworkUtil.getBundle(
			BatchEnginePortletDataHandlerTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-35914");
	}

	@Test
	@TestInfo("LPD-51604")
	public void testEnableLocalStaging() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.exportimport.internal.lifecycle." +
					"LoggerExportImportLifecycleListener",
				LoggerTestUtil.ERROR)) {

			_stagingLocalService.enableLocalStaging(
				TestPropsValues.getUserId(), group, false, false,
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			for (LogEntry logEntry : logEntries) {
				String message = logEntry.getMessage();

				Assert.assertFalse(
					message,
					message.contains(
						"Portlet export failed for portlet com_liferay_object" +
							"_web_internal_object_definitions_portlet" +
								"_ObjectDefinitionsPortlet"));
			}

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@Test
	@TestInfo("LPD-61995")
	public void testEnableLocalStagingWithSiteScopedObjectDefinition()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		_addObjectDefinition(ObjectDefinitionConstants.SCOPE_SITE);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.exportimport.internal.lifecycle." +
					"ExportImportProcessCallbackLifecycleListener",
				LoggerTestUtil.ERROR)) {

			_stagingLocalService.enableLocalStaging(
				TestPropsValues.getUserId(), group, false, false,
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportImportCompanyGroupObjectEntries() throws Exception {
		_testExportImportObjectEntriesToSameGroup(
			_stagingGroupHelper.fetchCompanyGroup(
				TestPropsValues.getCompanyId()),
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void testExportImportCompanyGroupObjectEntriesWithError()
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, 0L, objectDefinition);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		_deleteObjectEntries(objectEntries);

		ObjectEntry objectEntry = objectEntries[1];

		Map<String, Serializable> values = objectEntry.getValues();

		ObjectEntry duplicateObjectEntry = _addObjectEntry(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition,
			values.get(_OBJECT_FIELD_NAME_TEXT));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.OFF)) {

			new ExportImportExecutor(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition
			).executeImport();
		}

		List<ObjectEntry> objectEntriesList =
			_objectEntryLocalService.getObjectEntries(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			objectEntriesList.toString(), 3, objectEntriesList.size());

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getExternalReferenceCode(),
				objectEntry.getGroupId(),
				objectDefinition.getObjectDefinitionId()));
		Assert.assertNotEquals(
			objectEntry.getExternalReferenceCode(),
			duplicateObjectEntry.getExternalReferenceCode());
		Assert.assertTrue(
			ListUtil.exists(
				_batchEngineImportTaskLocalService.getBatchEngineImportTasks(
					BatchEngineTaskExecuteStatus.COMPLETED.toString()),
				batchEngineImportTask -> Objects.equals(
					batchEngineImportTask.getTaskItemDelegateName(),
					objectDefinition.getName())));
	}

	@Test
	@TestInfo("LPD-61997")
	public void testExportImportCompanyGroupObjectEntriesWithRelatedObjectEntries()
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		_testExportImportObjectEntriesWithRelatedObjectEntries(
			group, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_testExportImportObjectEntriesWithRelatedObjectEntries(
			group, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	@Test
	@TestInfo("LPD-72635")
	public void testExportImportCompanyObjectEntriesWithRichTextAndURLs()
		throws Exception {

		// File entry from a depot entry

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		DepotEntry depotEntry = _addDepotEntry();

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			company.getCompanyId());

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(depotEntry.getGroupId()), true, objectDefinition,
			group);

		// File entry from a depot entry, deleted before exporting

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, group);

		// File entry from a depot entry, deleted before importing

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(depotEntry.getGroupId()), true,
			objectDefinition, group);

		// File entry from a group

		Group testGroup = GroupTestUtil.addGroup();

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(testGroup.getGroupId()), false, objectDefinition,
			group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true, _addImageFileEntry(testGroup.getGroupId()),
			false, objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true, _addImageFileEntry(testGroup.getGroupId()),
			true, objectDefinition, group);

		// File entry from a group, deleted before exporting

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(testGroup.getGroupId()), false,
			objectDefinition, group);

		// File entry from a group, deleted before importing

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(testGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(testGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(testGroup.getGroupId()), true,
			objectDefinition, group);

		// File entry from the company group

		Group companyGroup = company.getGroup();

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, group);

		// File entry from the company group, deleted before exporting

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, group);

		// File entry from the company group, deleted before importing

		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, group);
		_testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, group);
	}

	@Test
	@TestInfo("LPD-72635")
	public void testExportImportDepotObjectEntriesWithRichTextAndURLs()
		throws Exception {

		// File entry from a different depot entry

		DepotEntry differentDepotEntry = _addDepotEntry();

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_DEPOT);

		DepotEntry sourceDepotEntry = _addDepotEntry();

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(differentDepotEntry.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(differentDepotEntry.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(differentDepotEntry.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from a different depot entry, deleted before exporting

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(differentDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());

		// File entry from a different depot entry, deleted before importing

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(differentDepotEntry.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(differentDepotEntry.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(differentDepotEntry.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from a group

		Group group = GroupTestUtil.addGroup();

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, false, _addImageFileEntry(group.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true, _addImageFileEntry(group.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true, _addImageFileEntry(group.getGroupId()),
			true, objectDefinition, sourceDepotEntry.getGroup());

		// File entry from a group, deleted before exporting

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(group.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from a group, deleted before importing

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(group.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(group.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(group.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the company group

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the company group, deleted before exporting

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the company group, deleted before importing

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the same depot entry

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, true, _addImageFileEntry(sourceDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			null, null, true, true,
			_addImageFileEntry(sourceDepotEntry.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the same depot entry, deleted before exporting

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());

		// File entry from the same depot entry, deleted before importing

		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, true, _addImageFileEntry(sourceDepotEntry.getGroupId()),
			false, objectDefinition, sourceDepotEntry.getGroup());
		_testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, null, true, true,
			_addImageFileEntry(sourceDepotEntry.getGroupId()), true,
			objectDefinition, sourceDepotEntry.getGroup());
	}

	@FeatureFlag("LPD-35443")
	@Test
	@TestInfo("LPD-64365")
	public void testExportImportLayoutsToOtherSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(group1);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(group1);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group1.getGroupId()
		).withIncludeLayoutSetLayouts(
		).withLayoutId(
			layout1.getLayoutId()
		).executeExport();

		Group group2 = GroupTestUtil.addGroup();

		new ExportImportExecutor(
		).withGroupId(
			group2.getGroupId()
		).withIncludeLayoutSetLayouts(
		).withLARFile(
			larFile
		).executeImport();

		layout1 = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			layout1.getExternalReferenceCode(), group2.getGroupId());

		Assert.assertNotNull(layout1);

		layout2 = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			layout2.getExternalReferenceCode(), group2.getGroupId());

		Assert.assertNull(layout2);
	}

	@Test
	public void testExportImportListTypeDefinitions() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ListTypeDefinition listTypeDefinition = _addListTypeDefinition();

		ListTypeEntry[] listTypeEntries = _addListTypeEntries(
			3, listTypeDefinition);

		File larFile1 = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).executeExport();

		_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntries[0]);
		_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntries[1]);

		File larFile2 = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).executeExport();

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).withLARFile(
			larFile1
		).executeImport();

		_assertListTypeDefinition(
			listTypeDefinition, listTypeEntries.length, listTypeEntries);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).withLARFile(
			larFile2
		).executeImport();

		_assertListTypeDefinition(listTypeDefinition, 1, listTypeEntries[2]);
	}

	@Test
	public void testExportImportObjectDefinitions() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		ObjectField[] objectFields = _addObjectFields(3, objectDefinition);

		File larFile1 = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).executeExport();

		_objectFieldLocalService.deleteObjectField(objectFields[0]);
		_objectFieldLocalService.deleteObjectField(objectFields[1]);

		File larFile2 = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).executeExport();

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile1
		).executeImport();

		_assertObjectDefinition(
			objectDefinition, objectFields.length, objectFields);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile2
		).executeImport();

		_assertObjectDefinition(objectDefinition, 1, objectFields[2]);
	}

	@Test
	@TestInfo("LPD-54863")
	public void testExportImportObjectEntriesWithErrorReport()
		throws Exception {

		_testExportImportObjectEntriesWithErrorReport(
			_stagingGroupHelper.fetchCompanyGroup(
				TestPropsValues.getCompanyId()),
			ObjectDefinitionConstants.SCOPE_COMPANY);
		_testExportImportObjectEntriesWithErrorReport(
			GroupTestUtil.addGroup(), ObjectDefinitionConstants.SCOPE_SITE);
	}

	@Ignore("LPD-40798")
	@Test
	@TestInfo("LPD-57756")
	public void testExportImportPriorityWithSiteScopeObjectEntries()
		throws Exception {

		Group group1 = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group1);

		LayoutTestUtil.addPortletToLayout(
			layout, JournalContentPortletKeys.JOURNAL_CONTENT);

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, group1.getGroupId(), objectDefinition);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group1.getGroupId()
		).withLayoutId(
			layout.getLayoutId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		Group group2 = GroupTestUtil.addGroup();

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				StagedModelDataHandler.class,
				new BaseStagedModelDataHandler<Layout>() {

					@Override
					public String[] getClassNames() {
						return new String[] {Layout.class.getName()};
					}

					@Override
					protected void doExportStagedModel(
							PortletDataContext portletDataContext,
							Layout layout)
						throws Exception {
					}

					@Override
					protected void doImportStagedModel(
							PortletDataContext portletDataContext,
							Layout layout)
						throws Exception {

						throw new PortletDataException();
					}

				},
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", Layout.class.getName()
				).put(
					"service.ranking", Integer.MAX_VALUE
				).build());

		try {
			new ExportImportExecutor(
			).withExpectError(
			).withGroupId(
				group2.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition
			).executeImport();

			Assert.fail();
		}
		catch (PortletDataException portletDataException) {
			List<ObjectEntry> objectEntriesList =
				_objectEntryLocalService.getObjectEntries(
					group2.getGroupId(),
					objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			Assert.assertEquals(
				Arrays.toString(objectEntries), objectEntries.length,
				objectEntriesList.size());
		}
		finally {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	@Test
	@TestInfo("LPD-64361")
	public void testExportImportPrivateLayoutsToOtherSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(group1, true);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(group1, true);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group1.getGroupId()
		).withLayoutId(
			layout1.getLayoutId()
		).withPrivateLayouts(
		).executeExport();

		Group group2 = GroupTestUtil.addGroup();

		new ExportImportExecutor(
		).withGroupId(
			group2.getGroupId()
		).withLARFile(
			larFile
		).withPrivateLayouts(
		).executeImport();

		layout1 = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			layout1.getExternalReferenceCode(), group2.getGroupId());

		Assert.assertNotNull(layout1);

		layout2 = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			layout2.getExternalReferenceCode(), group2.getGroupId());

		Assert.assertNull(layout2);
	}

	@Test
	public void testExportImportSiteObjectEntriesToOtherSite()
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		Group group1 = GroupTestUtil.addGroup();

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, group1.getGroupId(), objectDefinition);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group1.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		Group group2 = GroupTestUtil.addGroup();

		new ExportImportExecutor(
		).withGroupId(
			group2.getGroupId()
		).withLARFile(
			larFile
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		List<ObjectEntry> objectEntriesList =
			_objectEntryLocalService.getObjectEntries(
				group2.getGroupId(), objectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			Arrays.toString(objectEntries), objectEntriesList.size(),
			objectEntries.length);
	}

	@Test
	public void testExportImportSiteObjectEntriesToSameSite() throws Exception {
		_testExportImportObjectEntriesToSameGroup(
			GroupTestUtil.addGroup(), ObjectDefinitionConstants.SCOPE_SITE);
	}

	@Test
	@TestInfo("LPD-61997")
	public void testExportImportSiteObjectEntriesWithRelatedObjectEntries()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		_testExportImportObjectEntriesWithRelatedObjectEntries(
			group, ObjectDefinitionConstants.SCOPE_SITE,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_testExportImportObjectEntriesWithRelatedObjectEntries(
			group, ObjectDefinitionConstants.SCOPE_SITE,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	@Test
	@TestInfo("LPD-72635")
	public void testExportImportSiteObjectEntriesWithRichTextAndURLs()
		throws Exception {

		// File entry from a depot entry

		DepotEntry depotEntry = _addDepotEntry();

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		Group sourceGroup = GroupTestUtil.addGroup();

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(depotEntry.getGroupId()), true, objectDefinition,
			sourceGroup);

		// File entry from a depot entry, deleted before exporting

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, sourceGroup);

		// File entry from a depot entry, deleted before importing

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(depotEntry.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(depotEntry.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from a different group

		Group differentGroup = GroupTestUtil.addGroup();

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(differentGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(differentGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(differentGroup.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from a different group, deleted before exporting

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(differentGroup.getGroupId()),
			false, objectDefinition, sourceGroup);

		// File entry from a different group, deleted before importing

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(differentGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(differentGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(differentGroup.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from the company group

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, false,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, false, true,
			_addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from the company group, deleted before exporting

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", fileEntry.getGroupId()),
			false, false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceGroup);

		// File entry from the company group, deleted before importing

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			false, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, _defaultReportEntryBiFunction, false,
			true, _addImageFileEntry(companyGroup.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from the same group

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, true, _addImageFileEntry(sourceGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			null, null, true, true,
			_addImageFileEntry(sourceGroup.getGroupId()), true,
			objectDefinition, sourceGroup);

		// File entry from the same group, deleted before exporting

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_EXPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				"", targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceGroup.getGroupId()), false,
			objectDefinition, sourceGroup);

		// File entry from the same group, deleted before importing

		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, false, _addImageFileEntry(sourceGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT,
			(fileEntry, targetGroup) -> new ObjectValuePair<>(
				fileEntry.getExternalReferenceCode(), targetGroup.getGroupId()),
			true, true, _addImageFileEntry(sourceGroup.getGroupId()), false,
			objectDefinition, sourceGroup);
		_testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry.BEFORE_IMPORT, null, true, true,
			_addImageFileEntry(sourceGroup.getGroupId()), true,
			objectDefinition, sourceGroup);
	}

	@Test
	@TestInfo("LPD-58645")
	public void testExportImportWithDifferentScopedObjectEntries()
		throws Exception {

		Group group1 = GroupTestUtil.addGroupWithType(
			GroupConstants.TYPE_DEPOT);

		Layout layout = LayoutTestUtil.addTypePortletLayout(group1);

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		_addObjectEntries(3, group1.getGroupId(), objectDefinition);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group1.getGroupId()
		).withLayoutId(
			layout.getLayoutId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		Group group2 = GroupTestUtil.addGroupWithType(
			GroupConstants.TYPE_DEPOT);

		new ExportImportExecutor(
		).withExpectError(
		).withGroupId(
			group2.getGroupId()
		).withLARFile(
			larFile
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				group2.getGroupId(), objectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportIndividualDeletionsCompanyGroup() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition1 = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition1);

		_deleteObjectEntries(objectEntries);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
						false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry objectEntry = _addObjectEntry(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition2,
			RandomTestUtil.randomString());

		_deleteObjectEntries(objectEntry);

		File larFile = new ExportImportExecutor(
		).withDeletions(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition1
		).executeExport();

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_getExternalReferenceCodes(objectEntries)
			).toString(),
			_getExternalReferenceCodesJSONArray(
				objectDefinition1.getName(), larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);

		larFile = new ExportImportExecutor(
		).withDeletions(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition2
		).withPrivateLayouts(
		).executeExport();

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				objectEntry.getExternalReferenceCode()
			).toString(),
			_getExternalReferenceCodesJSONArray(
				objectDefinition2.getName(), larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);

		larFile = new ExportImportExecutor(
		).withDeletions(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition1
		).withObjectDefinition(
			objectDefinition2
		).executeExport();

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_getExternalReferenceCodes(objectEntries)
			).toString(),
			_getExternalReferenceCodesJSONArray(
				objectDefinition1.getName(), larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				objectEntry.getExternalReferenceCode()
			).toString(),
			_getExternalReferenceCodesJSONArray(
				objectDefinition2.getName(), larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				larFile, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);
	}

	@FeatureFlag("LPD-34594")
	@Test
	public void testGetDescriptionAndTagWithRootObjectHierarchy()
		throws Exception {

		Tree tree = TreeTestUtil.createObjectDefinitionTree(
			_objectDefinitionLocalService, _objectRelationshipLocalService,
			true,
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA"}
			).put(
				"AA", new String[] {"AAA"}
			).put(
				"AAA", new String[0]
			).build());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				TestPropsValues.getCompanyId(),
				objectDefinition.getPortletId());

		String description = portletDataHandler.getDescription(
			LocaleUtil.getDefault());

		TreeTestUtil.forEachNodeObjectDefinition(
			tree.iterator(), _objectDefinitionLocalService,
			nodeObjectDefinition -> {
				if (nodeObjectDefinition.isRootNode()) {
					return;
				}

				String modelResourceNamePrefix =
					ResourceActionsUtil.getModelResourceNamePrefix();

				Assert.assertTrue(
					description.contains(
						LanguageUtil.get(
							LocaleUtil.getDefault(),
							modelResourceNamePrefix +
								nodeObjectDefinition.getResourceName())));
			});

		Assert.assertEquals(
			LanguageUtil.get(LocaleUtil.getDefault(), "root-object"),
			portletDataHandler.getTag(LocaleUtil.getDefault()));

		TreeTestUtil.deleteObjectDefinitionHierarchy(
			_objectDefinitionLocalService,
			new String[] {"C_A", "C_AA", "C_AAA"}, _objectEntryLocalService,
			_objectRelationshipLocalService);
	}

	@Test
	@TestInfo("LPD-65748")
	public void testGetExportModelCount() throws Exception {
		String portletId = RandomTestUtil.randomString();

		Assert.assertNull(
			_portletDataHandlerProvider.provide(
				TestPropsValues.getCompanyId(), portletId));

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withFunction(
					filter -> {
						if (filter != null) {
							return Page.of(Arrays.asList(new TestItem(1)));
						}

						return Page.of(
							Arrays.asList(
								new TestItem(1), new TestItem(2),
								new TestItem(3)));
					}
				).withPortletId(
					portletId
				).build())) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertEquals(
				1,
				portletDataHandler.getExportModelCount(
					_getManifestSummary(
						PortletDataContextFactoryUtil.
							createExportPortletDataContext(
								TestPropsValues.getCompanyId(), 0L,
								new HashMap<>(),
								new Date(System.currentTimeMillis() - 10000),
								new Date(System.currentTimeMillis() - 5000),
								null),
						portletDataHandler)));

			// Filter is null

			Assert.assertEquals(
				3,
				portletDataHandler.getExportModelCount(
					_getManifestSummary(
						PortletDataContextFactoryUtil.
							createExportPortletDataContext(
								TestPropsValues.getCompanyId(), 0L,
								new HashMap<>(), null, null, null),
						portletDataHandler)));
		}
	}

	@Test
	public void testGetExportModelCountWithObjectEntries() throws Exception {
		_testGetExportModelCount(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			_addObjectDefinition(ObjectDefinitionConstants.SCOPE_COMPANY));
		_testGetExportModelCount(
			TestPropsValues.getGroupId(),
			_addObjectDefinition(ObjectDefinitionConstants.SCOPE_SITE));
	}

	@Test
	@TestInfo("LPD-75687")
	public void testGetRank() throws Exception {
		String portletId = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).build())) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertEquals(100, portletDataHandler.getRank());
		}

		int rank1 = 101;

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).withRank(
					rank1
				).build())) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertEquals(rank1, portletDataHandler.getRank());
		}

		int rank2 = 99;

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).withRank(
					rank2
				).build())) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertEquals(rank2, portletDataHandler.getRank());
		}

		try (SafeCloseable safeCloseable1 = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).build())) {

			try (SafeCloseable safeCloseable2 = _register(
					new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
					).withPortletId(
						portletId
					).withRank(
						rank1
					).build())) {

				PortletDataHandler portletDataHandler =
					_portletDataHandlerProvider.provide(
						TestPropsValues.getCompanyId(), portletId);

				Assert.assertEquals(100, portletDataHandler.getRank());
			}

			try (SafeCloseable safeCloseable2 = _register(
					new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
					).withPortletId(
						portletId
					).withRank(
						rank2
					).build())) {

				PortletDataHandler portletDataHandler =
					_portletDataHandlerProvider.provide(
						TestPropsValues.getCompanyId(), portletId);

				Assert.assertEquals(rank2, portletDataHandler.getRank());
			}

			try (SafeCloseable safeCloseable3 = _register(
					new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
					).withPortletId(
						portletId
					).withRank(
						rank1
					).build());
				SafeCloseable safeCloseable4 = _register(
					new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
					).withPortletId(
						portletId
					).withRank(
						rank2
					).build())) {

				PortletDataHandler portletDataHandler =
					_portletDataHandlerProvider.provide(
						TestPropsValues.getCompanyId(), portletId);

				Assert.assertEquals(rank2, portletDataHandler.getRank());
			}
		}
	}

	@Test
	@TestInfo("LPD-49421")
	public void testImportIndividualDeletionsCompanyGroup() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition);

		File larFile1 = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		_deleteObjectEntries(objectEntries[0], objectEntries[1]);

		File larFile2 = new ExportImportExecutor(
		).withDeletions(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		_deleteObjectEntries(objectEntries[2]);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile1
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		_assertObjectEntries(
			false, objectDefinition.getObjectDefinitionId(), objectEntries);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile2
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		_assertObjectEntries(
			false, objectDefinition.getObjectDefinitionId(), objectEntries);

		new ExportImportExecutor(
		).withDeletions(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile2
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		_assertObjectEntries(
			false, objectDefinition.getObjectDefinitionId(), objectEntries[2]);
		_assertNull(
			objectDefinition.getObjectDefinitionId(), objectEntries[0],
			objectEntries[1]);
	}

	@Test
	@TestInfo("LPD-75687")
	public void testImportOrderByRank() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		// First: list type definition

		ListTypeDefinition listTypeDefinition = _addListTypeDefinition();

		ListTypeEntry[] listTypeEntries = _addListTypeEntries(
			3, listTypeDefinition);

		// Second: object definition

		String picklistName = StringUtil.randomId();
		String textFieldName = StringUtil.randomId();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).externalReferenceCode(
						picklistName
					).labelMap(
						RandomTestUtil.randomLocaleStringMap()
					).name(
						picklistName
					).listTypeDefinitionId(
						listTypeDefinition.getListTypeDefinitionId()
					).build(),
					new TextObjectFieldBuilder(
					).labelMap(
						RandomTestUtil.randomLocaleStringMap()
					).name(
						StringUtil.randomId()
					).required(
						false
					).build()));

		// Third: object entries

		ObjectEntry objectEntry = _addObjectEntry(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition,
			(Map)HashMapBuilder.<String, Serializable>put(
				picklistName, listTypeEntries[0].getKey()
			).put(
				textFieldName, RandomTestUtil.randomString()
			).build());

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		_objectFieldLocalService.deleteObjectField(
			_objectFieldLocalService.fetchObjectField(
				picklistName, objectDefinition.getObjectDefinitionId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withIncludeListTypeDefinitions(
		).withLARFile(
			larFile
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		_assertListTypeDefinition(
			listTypeDefinition, listTypeEntries.length, listTypeEntries);

		Assert.assertNotNull(
			_objectFieldLocalService.fetchObjectField(
				picklistName, objectDefinition.getObjectDefinitionId()));

		List<ObjectEntry> importedObjectEntries =
			_objectEntryLocalService.getObjectEntries(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			importedObjectEntries.toString(), 1, importedObjectEntries.size());

		ObjectEntry importedObjectEntry = importedObjectEntries.get(0);

		Assert.assertEquals(
			listTypeEntries[0].getKey(),
			MapUtil.getString(importedObjectEntry.getValues(), picklistName));
	}

	@FeatureFlag("LPD-41367")
	@Test
	@TestInfo("LPD-70661")
	public void testIsConfigurationEnabled() throws Exception {
		_testIsConfigurationEnabled(false);
		_testIsConfigurationEnabled(true);
	}

	@Test
	@TestInfo("LPD-73132")
	public void testIsStagedWithObjectDefinitions() throws Exception {
		String portletId = StringBundler.concat(
			ObjectPortletKeys.OBJECT_DEFINITIONS, StringPool.POUND,
			RandomTestUtil.randomString());

		Assert.assertNull(
			_portletDataHandlerProvider.provide(
				TestPropsValues.getCompanyId(), portletId));

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).build())) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertFalse(portletDataHandler.isStaged());
		}
	}

	public static class TestItem implements Serializable {

		public TestItem(long id) {
			this.id = id;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		protected long id;

	}

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
	}

	private DLFileEntry _addDLFileEntry(String content, long groupId)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), groupId, 0,
			TempFileEntryUtil.getTempFileName(
				RandomTestUtil.randomString() + ".txt"),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(content.getBytes()), 0, null, null, null,
			ServiceContextTestUtil.getServiceContext());

		return _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());
	}

	private FileEntry _addImageFileEntry(long groupId) throws Exception {
		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "dependencies/image.jpg"), null, null,
			null, ServiceContextTestUtil.getServiceContext());
	}

	private ListTypeDefinition _addListTypeDefinition() throws Exception {
		return _listTypeDefinitionLocalService.addListTypeDefinition(
			null, TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			false, Collections.emptyList(), new ServiceContext());
	}

	private ListTypeEntry[] _addListTypeEntries(
			int count, ListTypeDefinition listTypeDefinition)
		throws Exception {

		ListTypeEntry[] listTypeEntries = new ListTypeEntry[count];

		for (int i = 0; i < count; i++) {
			listTypeEntries[i] = _listTypeEntryLocalService.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				listTypeDefinition.isSystem());
		}

		return listTypeEntries;
	}

	private ObjectDefinition _addObjectDefinition(String scope)
		throws Exception {

		String objectDefinitionName = ObjectDefinitionTestUtil.getRandomName();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				objectDefinitionName,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						RandomTestUtil.randomString(),
						_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								"100"
							).build()),
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						RandomTestUtil.randomString(),
						_OBJECT_FIELD_NAME_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_USER_COMPUTER
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_SHOW_FILES_IN_DOCS_AND_MEDIA
							).value(
								Boolean.TRUE.toString()
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_STORAGE_DL_FOLDER_PATH
							).value(
								StringPool.SLASH + objectDefinitionName
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								"100"
							).build()),
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						RandomTestUtil.randomString(),
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_USER_COMPUTER
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								"100"
							).build()),
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, false, false, null,
						RandomTestUtil.randomString(),
						_OBJECT_FIELD_NAME_RICH_TEXT, false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
							).value(
								Boolean.TRUE.toString()
							).build()),
						false)),
				scope);

		if (Objects.equals(ObjectDefinitionConstants.SCOPE_DEPOT, scope)) {
			_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
				objectDefinition.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS,
				StringPool.TRUE);
		}

		return objectDefinition;
	}

	private ObjectEntry[] _addObjectEntries(
			int count, long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectEntry[] objectEntries = new ObjectEntry[count];

		for (int i = 0; i < count; i++) {
			objectEntries[i] = _addObjectEntry(
				groupId, objectDefinition, RandomTestUtil.randomString());
		}

		return objectEntries;
	}

	private ObjectEntry _addObjectEntry(
			long groupId, ObjectDefinition objectDefinition,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			groupId, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null, values, ServiceContextTestUtil.getServiceContext());
	}

	private ObjectEntry _addObjectEntry(
			long groupId, ObjectDefinition objectDefinition,
			Serializable objectFieldValue)
		throws Exception {

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		DLFileEntry dlFileEntry = _addDLFileEntry(
			_OBJECT_FIELD_VALUE_ATTACHMENT_DOCS_AND_MEDIA,
			company.getGroupId());

		FileEntry tempFileEntry1 = _addTempFileEntry(
			objectDefinition,
			_OBJECT_FIELD_VALUE_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA);
		FileEntry tempFileEntry2 = _addTempFileEntry(
			objectDefinition, _OBJECT_FIELD_VALUE_ATTACHMENT_USER_COMPUTER);

		return _addObjectEntry(
			groupId, objectDefinition,
			(Map)HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA,
				dlFileEntry.getFileEntryId()
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA,
				tempFileEntry1.getFileEntryId()
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER,
				tempFileEntry2.getFileEntryId()
			).put(
				_OBJECT_FIELD_NAME_TEXT, objectFieldValue
			).build());
	}

	private ObjectField[] _addObjectFields(
			int count, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectField[] objectFields = new ObjectField[count];

		for (int i = 0; i < count; i++) {
			objectFields[i] = ObjectFieldUtil.addCustomObjectField(
				new TextObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).name(
					StringUtil.randomId()
				).objectDefinitionId(
					objectDefinition.getObjectDefinitionId()
				).required(
					false
				).userId(
					TestPropsValues.getUserId()
				).build());
		}

		return objectFields;
	}

	private FileEntry _addTempFileEntry(
			ObjectDefinition objectDefinition, String tempFileName)
		throws Exception {

		return TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getPortletId(),
			TempFileEntryUtil.getTempFileName(tempFileName + ".txt"),
			FileUtil.createTempFile(tempFileName.getBytes()),
			ContentTypes.TEXT_PLAIN);
	}

	private void _assertExportImportReportEntry(
		long expectedClassNameId, long expectedClassPK,
		String expectedExternalReferenceCode, long expectedGroupId,
		String expectedModelNameLanguageKey, int expectedType,
		ExportImportReportEntry exportImportReportEntry) {

		Assert.assertEquals(
			expectedExternalReferenceCode,
			exportImportReportEntry.getClassExternalReferenceCode());
		Assert.assertEquals(
			expectedClassNameId, exportImportReportEntry.getClassNameId());
		Assert.assertEquals(
			expectedClassPK, exportImportReportEntry.getClassPK());
		Assert.assertEquals(
			expectedGroupId, exportImportReportEntry.getGroupId());
		Assert.assertEquals(
			expectedModelNameLanguageKey,
			exportImportReportEntry.getModelNameLanguageKey());
		Assert.assertEquals(expectedType, exportImportReportEntry.getType());
	}

	private void _assertListTypeDefinition(
			ListTypeDefinition listTypeDefinition, int listTypeEntriesCount,
			ListTypeEntry... listTypeEntries)
		throws Exception {

		ListTypeDefinition importedListTypeDefinition =
			_listTypeDefinitionLocalService.
				getListTypeDefinitionByExternalReferenceCode(
					listTypeDefinition.getExternalReferenceCode(),
					listTypeDefinition.getCompanyId());

		Assert.assertEquals(
			listTypeDefinition.getName(LocaleUtil.getDefault()),
			importedListTypeDefinition.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			listTypeEntriesCount,
			_listTypeEntryLocalService.getListTypeEntriesCount(
				importedListTypeDefinition.getListTypeDefinitionId()));

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			ListTypeEntry importedListTypeEntry =
				_listTypeEntryLocalService.
					getListTypeEntryByExternalReferenceCode(
						listTypeEntry.getExternalReferenceCode(),
						listTypeEntry.getCompanyId(),
						importedListTypeDefinition.getListTypeDefinitionId());

			Assert.assertEquals(
				listTypeEntry.getKey(), importedListTypeEntry.getKey());
		}
	}

	private void _assertNull(
		long objectDefinitionId, ObjectEntry... objectEntries) {

		for (ObjectEntry objectEntry : objectEntries) {
			Assert.assertNull(
				_objectEntryLocalService.fetchObjectEntry(
					objectEntry.getExternalReferenceCode(),
					objectEntry.getGroupId(), objectDefinitionId));
		}
	}

	private void _assertObjectDefinition(
			ObjectDefinition objectDefinition, int objectFieldsCount,
			ObjectField... objectFields)
		throws Exception {

		ObjectDefinition importedObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					objectDefinition.getExternalReferenceCode(),
					objectDefinition.getCompanyId());

		Assert.assertEquals(
			objectDefinition.getLabel(LocaleUtil.getDefault()),
			importedObjectDefinition.getLabel(LocaleUtil.getDefault()));
		Assert.assertEquals(
			objectDefinition.getName(), importedObjectDefinition.getName());
		Assert.assertEquals(
			objectFieldsCount,
			_objectFieldLocalService.getObjectFieldsCount(
				importedObjectDefinition.getObjectDefinitionId(), false));

		for (ObjectField objectField : objectFields) {
			ObjectField importedObjectField =
				_objectFieldLocalService.getObjectField(
					objectField.getExternalReferenceCode(),
					importedObjectDefinition.getObjectDefinitionId());

			Assert.assertEquals(
				objectField.getLabel(LocaleUtil.getDefault()),
				importedObjectField.getLabel(LocaleUtil.getDefault()));
			Assert.assertEquals(
				objectField.getName(), importedObjectField.getName());
		}
	}

	private void _assertObjectEntries(
			boolean empty, long objectDefinitionId,
			ObjectEntry... objectEntries)
		throws Exception {

		for (ObjectEntry objectEntry : objectEntries) {
			ObjectEntry importedObjectEntry =
				_objectEntryLocalService.getObjectEntry(
					objectEntry.getExternalReferenceCode(),
					objectEntry.getGroupId(), objectDefinitionId);

			if (empty) {
				Assert.assertEquals(
					WorkflowConstants.STATUS_EMPTY,
					importedObjectEntry.getStatus());

				return;
			}

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
				MapUtil.getLong(
					importedObjectEntry.getValues(),
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA));

			Assert.assertEquals(
				StringPool.BLANK,
				StringUtil.read(dlFileEntry.getContentStream()));

			dlFileEntry = _dlFileEntryLocalService.getFileEntry(
				MapUtil.getLong(
					importedObjectEntry.getValues(),
					_OBJECT_FIELD_NAME_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA));

			Assert.assertEquals(
				StringPool.BLANK,
				StringUtil.read(dlFileEntry.getContentStream()));

			dlFileEntry = _dlFileEntryLocalService.getFileEntry(
				MapUtil.getLong(
					importedObjectEntry.getValues(),
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER));

			Assert.assertEquals(
				_OBJECT_FIELD_VALUE_ATTACHMENT_USER_COMPUTER,
				StringUtil.read(dlFileEntry.getContentStream()));
		}
	}

	private void _deleteObjectEntries(ObjectEntry... objectEntries)
		throws Exception {

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);

			long fileEntryId = MapUtil.getLong(
				objectEntry.getValues(),
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA);

			if (fileEntryId != 0) {
				_dlFileEntryLocalService.deleteFileEntry(fileEntryId);
			}

			fileEntryId = MapUtil.getLong(
				objectEntry.getValues(),
				_OBJECT_FIELD_NAME_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA);

			if (fileEntryId != 0) {
				_dlFileEntryLocalService.deleteFileEntry(fileEntryId);
			}
		}
	}

	private String _getBatchFileNameWithPath(String fileName, long groupId) {
		return StringBundler.concat(
			"group/", groupId, StringPool.FORWARD_SLASH, fileName);
	}

	private JSONArray _getClassExternalReferenceCodesJSONArray(
			File file, long groupId)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				_getBatchFileNameWithPath(
					"deletion-system-events.xml", groupId));

			if (zipEntry == null) {
				throw new FileNotFoundException();
			}

			Document document = _saxReader.read(
				zipFile.getInputStream(zipEntry));

			Element rootElement = document.getRootElement();

			return JSONUtil.toJSONArray(
				rootElement.elements("deletion-system-event"),
				deletionSystemEventElement ->
					deletionSystemEventElement.attributeValue(
						"class-external-reference-code"),
				exception -> {
					throw new RuntimeException(exception);
				});
		}
	}

	private JSONArray _getExportedObjectEntriesJSONArray(
			String className, File file, long groupId)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				_getBatchFileNameWithPath(className + ".json", groupId));

			return JSONFactoryUtil.createJSONArray(
				StringUtil.read(zipFile.getInputStream(zipEntry)));
		}
	}

	private Map<String, String[]> _getExportImportParameterMap(
		boolean deletions, boolean includeDocumentLibrary,
		boolean includeLayoutSetLayoutsPortlet,
		boolean includeListTypeDefinitions,
		List<ObjectDefinition> objectDefinitions) {

		Map<String, String[]> parameterMap = HashMapBuilder.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.toString(deletions)}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" +
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			() -> {
				if (includeDocumentLibrary) {
					return new String[] {Boolean.TRUE.toString()};
				}

				return null;
			}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" +
				ObjectPortletKeys.LIST_TYPE_DEFINITIONS,
			() -> {
				if (includeListTypeDefinitions) {
					return new String[] {Boolean.TRUE.toString()};
				}

				return null;
			}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" +
				ObjectPortletKeys.OBJECT_DEFINITIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			"PORTLET_DATA_com_liferay_layout_admin_web_portlet_" +
				"LayoutSetLayoutsPortlet",
			() -> {
				if (includeLayoutSetLayoutsPortlet) {
					return new String[] {Boolean.TRUE.toString()};
				}

				return null;
			}
		).build();

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					objectDefinition.getPortletId(),
				new String[] {Boolean.TRUE.toString()});
		}

		return parameterMap;
	}

	private String[] _getExternalReferenceCodes(ObjectEntry... objectEntries) {
		String[] externalReferenceCodes = new String[objectEntries.length];

		for (int i = 0; i < objectEntries.length; i++) {
			externalReferenceCodes[i] =
				objectEntries[i].getExternalReferenceCode();
		}

		return externalReferenceCodes;
	}

	private JSONArray _getExternalReferenceCodesJSONArray(
			String className, File file, long groupId)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				_getBatchFileNameWithPath(
					className + "_deletions.json", groupId));

			if (zipEntry == null) {
				throw new FileNotFoundException();
			}

			JSONArray jsonArray1 = JSONFactoryUtil.createJSONArray();

			JSONArray jsonArray2 = JSONFactoryUtil.createJSONArray(
				StringUtil.read(zipFile.getInputStream(zipEntry)));

			for (int i = 0; i < jsonArray2.length(); i++) {
				JSONObject jsonObject = jsonArray2.getJSONObject(i);

				jsonArray1.put(jsonObject.getString("externalReferenceCode"));
			}

			return jsonArray1;
		}
	}

	private String _getFriendlyURL(FileEntry fileEntry) throws Exception {
		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		return friendlyURLEntry.getUrlTitle();
	}

	private String _getImgTag(String previewURL) {
		return String.format("<p><img alt=\"\" src=\"%s\" /></p>", previewURL);
	}

	private LogCapture _getLogCapture(boolean expectError) {
		LogCapture logCapture = null;

		if (expectError) {
			logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR);
		}

		return logCapture;
	}

	private ManifestSummary _getManifestSummary(
			PortletDataContext portletDataContext,
			PortletDataHandler portletDataHandler)
		throws Exception {

		portletDataContext.setManifestSummary(new ManifestSummary());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		return portletDataContext.getManifestSummary();
	}

	private long _getObjectEntryGroupId(long groupId, String scope) {
		if (Objects.equals(ObjectDefinitionConstants.SCOPE_COMPANY, scope)) {
			return GroupConstants.DEFAULT_PARENT_GROUP_ID;
		}

		return groupId;
	}

	private String _getObjectEntryScopeKey(Group group, String scope) {
		if (Objects.equals(ObjectDefinitionConstants.SCOPE_COMPANY, scope)) {
			return null;
		}

		return group.getGroupKey();
	}

	private String _getPreviewURL(FileEntry fileEntry) throws Exception {
		return _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, null, false, false);
	}

	private String _getPreviewURL(String fileEntryFriendlyURL, Group group)
		throws Exception {

		return _dlURLHelper.getPreviewURL(
			fileEntryFriendlyURL, group.getFriendlyURL());
	}

	private SafeCloseable _register(
			TestExportImportVulcanBatchEngineTaskItemDelegate
				testExportImportVulcanBatchEngineTaskItemDelegate)
		throws Exception {

		String portletId =
			testExportImportVulcanBatchEngineTaskItemDelegate._portletId;

		SafeCloseable safeCloseable1 = () -> {
		};

		if (ArrayUtil.isEmpty(
				_bundleContext.getServiceReferences(
					Portlet.class.getName(),
					StringBundler.concat(
						"(jakarta.portlet.name=", portletId, ")")))) {

			safeCloseable1 = _registerServiceWithSafeCloseable(
				Portlet.class,
				new GenericPortlet() {
				},
				MapUtil.singletonDictionary("jakarta.portlet.name", portletId));
		}

		SafeCloseable safeCloseable2 = _registerServiceWithSafeCloseable(
			VulcanBatchEngineTaskItemDelegate.class,
			testExportImportVulcanBatchEngineTaskItemDelegate,
			HashMapDictionaryBuilder.put(
				"batch.engine.task.item.delegate", "true"
			).put(
				"batch.engine.task.item.delegate.class.name",
				TestItem.class.getName()
			).put(
				"batch.engine.task.item.delegate.name",
				RandomTestUtil.randomString()
			).put(
				"companyId", String.valueOf(TestPropsValues.getCompanyId())
			).put(
				"export.import.vulcan.batch.engine.task.item.delegate", "true"
			).build());

		SafeCloseable finalSafeCloseable = safeCloseable1;

		return () -> {
			try {
				safeCloseable2.close();
			}
			finally {
				finalSafeCloseable.close();
			}
		};
	}

	private <S> SafeCloseable _registerServiceWithSafeCloseable(
		Class<S> clazz, S service, Dictionary<String, ?> properties) {

		ServiceRegistration<S> serviceRegistration =
			_bundleContext.registerService(clazz, service, properties);

		return serviceRegistration::unregister;
	}

	private String _sanitize(
			String content, Group group, ObjectDefinition objectDefinition)
		throws Exception {

		return SanitizerUtil.sanitize(
			objectDefinition.getCompanyId(), group.getGroupId(),
			TestPropsValues.getUserId(), objectDefinition.getClassName(), 0L,
			ContentTypes.TEXT_HTML, content);
	}

	private void _testExportImportCompanyObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry deleteFileEntry,
			BiFunction<FileEntry, Group, ObjectValuePair<String, Long>>
				expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			boolean expectedRecalculateURL, boolean exportFileEntries,
			FileEntry fileEntry, boolean importFileEntries,
			ObjectDefinition objectDefinition, Group sourceGroup)
		throws Exception {

		_testExportImportObjectEntriesWithRichTextAndURLs(
			deleteFileEntry,
			expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			expectedRecalculateURL, exportFileEntries, fileEntry,
			importFileEntries, objectDefinition, sourceGroup, sourceGroup);
	}

	private void _testExportImportDepotObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry deleteFileEntry,
			BiFunction<FileEntry, Group, ObjectValuePair<String, Long>>
				expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			boolean expectedRecalculateURL, boolean exportFileEntries,
			FileEntry fileEntry, boolean importFileEntries,
			ObjectDefinition objectDefinition, Group sourceGroup)
		throws Exception {

		DepotEntry targetDepotEntry = _addDepotEntry();

		_testExportImportObjectEntriesWithRichTextAndURLs(
			deleteFileEntry,
			expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			expectedRecalculateURL, exportFileEntries, fileEntry,
			importFileEntries, objectDefinition, sourceGroup,
			targetDepotEntry.getGroup());
	}

	private void _testExportImportObjectEntriesToSameGroup(
			Group group, String scope)
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition(scope);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, _getObjectEntryGroupId(group.getGroupId(), scope),
			objectDefinition);

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		_deleteObjectEntries(objectEntries);

		new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withLARFile(
			larFile
		).withObjectDefinition(
			objectDefinition
		).executeImport();

		_assertObjectEntries(
			false, objectDefinition.getObjectDefinitionId(), objectEntries);
	}

	private void _testExportImportObjectEntriesWithErrorReport(
			Group group, String scope)
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition(scope);

		ObjectEntry objectEntry = _addObjectEntry(
			_getObjectEntryGroupId(group.getGroupId(), scope), objectDefinition,
			StringUtil.randomString());

		String originalExternalReferenceCode =
			objectEntry.getExternalReferenceCode();

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		objectEntry.setExternalReferenceCode(StringUtil.randomString());

		objectEntry = _objectEntryLocalService.updateObjectEntry(objectEntry);

		ExportImportConfiguration exportImportConfiguration =
			new ExportImportExecutor(
			).withExpectError(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition
			).executeImport();

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(),
				exportImportConfiguration.getExportImportConfigurationId());

		Assert.assertEquals(
			exportImportReportEntries.toString(), 1,
			exportImportReportEntries.size());

		ExportImportReportEntry exportImportReportEntry =
			exportImportReportEntries.get(0);

		_assertExportImportReportEntry(
			_portal.getClassNameId(objectDefinition.getClassName()),
			objectEntry.getPrimaryKey(), originalExternalReferenceCode,
			objectEntry.getGroupId(),
			"model.resource." + objectDefinition.getResourceName(),
			ExportImportReportEntryConstants.TYPE_ERROR,
			exportImportReportEntry);
	}

	private void _testExportImportObjectEntriesWithRelatedObjectEntries(
			boolean childFirst, Group group, String scope, String type)
		throws Exception {

		ObjectDefinition objectDefinition1 = _addObjectDefinition(scope);

		ObjectEntry[] objectEntries1 = _addObjectEntries(
			3, _getObjectEntryGroupId(group.getGroupId(), scope),
			objectDefinition1);

		ObjectDefinition objectDefinition2 = _addObjectDefinition(scope);

		ObjectEntry[] objectEntries2 = _addObjectEntries(
			3, _getObjectEntryGroupId(group.getGroupId(), scope),
			objectDefinition2);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, objectDefinition1,
				objectDefinition2,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				StringUtil.randomId(), type);

		for (int i = 0; i < objectEntries1.length; i++) {
			ObjectRelationshipTestUtil.relateObjectEntries(
				objectEntries1[i].getPrimaryKey(),
				objectEntries2[i].getPrimaryKey(), objectRelationship,
				TestPropsValues.getUserId());
		}

		File larFile = new ExportImportExecutor(
		).withGroupId(
			group.getGroupId()
		).withObjectDefinition(
			objectDefinition1
		).withObjectDefinition(
			objectDefinition2
		).executeExport();

		JSONArray exportedObjectEntriesJSONArray =
			_getExportedObjectEntriesJSONArray(
				objectDefinition2.getName(), larFile, group.getGroupId());

		if (Objects.equals(
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY, type)) {

			JSONAssert.assertEquals(
				JSONUtil.toJSONArray(
					objectEntries1,
					objectEntry -> JSONUtil.put(
						_toSimplifiedObjectEntryJSONObject(
							group, objectEntry, scope))
				).toString(),
				JSONUtil.toJSONArray(
					JSONUtil.toList(
						exportedObjectEntriesJSONArray,
						objectEntry -> objectEntry),
					exportedObjectEntry -> exportedObjectEntry.getJSONArray(
						objectRelationship.getName())
				).toString(),
				JSONCompareMode.NON_EXTENSIBLE);

			exportedObjectEntriesJSONArray = _getExportedObjectEntriesJSONArray(
				objectDefinition1.getName(), larFile, group.getGroupId());

			JSONAssert.assertEquals(
				JSONUtil.toJSONArray(
					objectEntries2,
					objectEntry -> JSONUtil.put(
						_toSimplifiedObjectEntryJSONObject(
							group, objectEntry, scope))
				).toString(),
				JSONUtil.toJSONArray(
					JSONUtil.toList(
						exportedObjectEntriesJSONArray,
						objectEntry -> objectEntry),
					exportedObjectEntry -> exportedObjectEntry.getJSONArray(
						objectRelationship.getName())
				).toString(),
				JSONCompareMode.NON_EXTENSIBLE);
		}
		else {
			JSONAssert.assertEquals(
				JSONUtil.toJSONArray(
					objectEntries1,
					objectEntry -> _toSimplifiedObjectEntryJSONObject(
						group, objectEntry, scope)
				).toString(),
				JSONUtil.toJSONArray(
					JSONUtil.toList(
						exportedObjectEntriesJSONArray,
						objectEntry -> objectEntry),
					exportedObjectEntry -> exportedObjectEntry.getJSONObject(
						objectRelationship.getName())
				).toString(),
				JSONCompareMode.NON_EXTENSIBLE);
		}

		_deleteObjectEntries(objectEntries1);
		_deleteObjectEntries(objectEntries2);

		if (childFirst) {
			new ExportImportExecutor(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition2
			).executeImport();

			_assertObjectEntries(
				true, objectDefinition1.getObjectDefinitionId(),
				objectEntries1);
			_assertObjectEntries(
				false, objectDefinition2.getObjectDefinitionId(),
				objectEntries2);

			new ExportImportExecutor(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition1
			).executeImport();

			_assertObjectEntries(
				false, objectDefinition1.getObjectDefinitionId(),
				objectEntries1);
		}
		else {
			new ExportImportExecutor(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition1
			).executeImport();

			_assertObjectEntries(
				false, objectDefinition1.getObjectDefinitionId(),
				objectEntries1);

			if (Objects.equals(
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY, type)) {

				_assertObjectEntries(
					true, objectDefinition2.getObjectDefinitionId(),
					objectEntries2);
			}
			else if (Objects.equals(
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY, type)) {

				for (ObjectEntry objectEntry : objectEntries2) {
					AssertUtils.assertFailure(
						NoSuchObjectEntryException.class,
						String.format(
							"No ObjectEntry exists with the key {" +
								"externalReferenceCode=%s, groupId=%s, " +
									"companyId=%s, objectDefinitionId=%s}",
							objectEntry.getExternalReferenceCode(),
							objectEntry.getGroupId(),
							objectEntry.getCompanyId(),
							objectDefinition2.getObjectDefinitionId()),
						() -> _objectEntryLocalService.getObjectEntry(
							objectEntry.getExternalReferenceCode(),
							objectEntry.getGroupId(),
							objectDefinition2.getObjectDefinitionId()));
				}
			}

			new ExportImportExecutor(
			).withGroupId(
				group.getGroupId()
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition2
			).executeImport();

			_assertObjectEntries(
				false, objectDefinition2.getObjectDefinitionId(),
				objectEntries2);
		}
	}

	private void _testExportImportObjectEntriesWithRelatedObjectEntries(
			Group group, String scope, String type)
		throws Exception {

		_testExportImportObjectEntriesWithRelatedObjectEntries(
			false, group, scope, type);
		_testExportImportObjectEntriesWithRelatedObjectEntries(
			true, group, scope, type);
	}

	private void _testExportImportObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry deleteFileEntry,
			BiFunction<FileEntry, Group, ObjectValuePair<String, Long>>
				expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			boolean expectedRecalculateURL, boolean exportFileEntries,
			FileEntry fileEntry, boolean importFileEntries,
			ObjectDefinition objectDefinition, Group sourceGroup,
			Group targetGroup)
		throws Exception {

		String imgTag = _getImgTag(_getPreviewURL(fileEntry));

		ObjectEntry objectEntry = _addObjectEntry(
			_getObjectEntryGroupId(
				sourceGroup.getGroupId(), objectDefinition.getScope()),
			objectDefinition,
			(Map)HashMapBuilder.put(
				_OBJECT_FIELD_NAME_RICH_TEXT,
				_sanitize(imgTag, sourceGroup, objectDefinition)
			).build());

		// Keep it before removing the file entry

		String fileEntryFriendlyURL = _getFriendlyURL(fileEntry);

		if (deleteFileEntry == DeleteFileEntry.BEFORE_EXPORT) {
			_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
		}

		File larFile = new ExportImportExecutor(
		).withGroupId(
			sourceGroup.getGroupId()
		).withIncludeDocumentLibrary(
			exportFileEntries
		).withObjectDefinition(
			objectDefinition
		).executeExport();

		if (deleteFileEntry == DeleteFileEntry.BEFORE_IMPORT) {
			_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
		}

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());

		ExportImportConfiguration exportImportConfiguration =
			new ExportImportExecutor(
			).withGroupId(
				targetGroup.getGroupId()
			).withIncludeDocumentLibrary(
				importFileEntries
			).withLARFile(
				larFile
			).withObjectDefinition(
				objectDefinition
			).executeImport();

		List<ObjectEntry> objectEntriesList =
			_objectEntryLocalService.getObjectEntries(
				_getObjectEntryGroupId(
					targetGroup.getGroupId(), objectDefinition.getScope()),
				objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			objectEntriesList.toString(), 1, objectEntriesList.size());

		ObjectEntry importedObjectEntry = objectEntriesList.get(0);

		String importedRichTextValue = MapUtil.getString(
			importedObjectEntry.getValues(), _OBJECT_FIELD_NAME_RICH_TEXT);

		String expectedImgTag = null;

		if (expectedRecalculateURL) {
			expectedImgTag = _getImgTag(
				_getPreviewURL(fileEntryFriendlyURL, targetGroup));
		}
		else {
			expectedImgTag = imgTag;
		}

		Assert.assertTrue(
			importedRichTextValue.contains(
				_sanitize(expectedImgTag, targetGroup, objectDefinition)));

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(),
				exportImportConfiguration.getExportImportConfigurationId());

		if (expectedReportEntryGroupIdExternalReferenceCodeBiFunction == null) {
			Assert.assertTrue(exportImportReportEntries.isEmpty());
		}
		else {
			Assert.assertEquals(
				exportImportReportEntries.toString(), 1,
				exportImportReportEntries.size());

			long classNameId = _classNameLocalService.getClassNameId(
				FileEntry.class);

			ObjectValuePair<String, Long> objectValuePair =
				expectedReportEntryGroupIdExternalReferenceCodeBiFunction.apply(
					fileEntry, targetGroup);

			_assertExportImportReportEntry(
				classNameId, 0L, objectValuePair.getKey(),
				objectValuePair.getValue(), FileEntry.class.getName(),
				ExportImportReportEntryConstants.TYPE_EMPTY,
				exportImportReportEntries.get(0));
		}

		_deleteObjectEntries(importedObjectEntry);
	}

	private void _testExportImportSiteObjectEntriesWithRichTextAndURLs(
			DeleteFileEntry deleteFileEntry,
			BiFunction<FileEntry, Group, ObjectValuePair<String, Long>>
				expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			boolean expectedRecalculateURL, boolean exportFileEntries,
			FileEntry fileEntry, boolean importFileEntries,
			ObjectDefinition objectDefinition, Group sourceGroup)
		throws Exception {

		_testExportImportObjectEntriesWithRichTextAndURLs(
			deleteFileEntry,
			expectedReportEntryGroupIdExternalReferenceCodeBiFunction,
			expectedRecalculateURL, exportFileEntries, fileEntry,
			importFileEntries, objectDefinition, sourceGroup,
			GroupTestUtil.addGroup());
	}

	private void _testGetExportModelCount(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				objectDefinition.getPortletId());

		Assert.assertEquals(
			0,
			portletDataHandler.getExportModelCount(
				_getManifestSummary(
					PortletDataContextFactoryUtil.
						createExportPortletDataContext(
							objectDefinition.getCompanyId(), groupId,
							new HashMap<>(), null, null, null),
					portletDataHandler)));

		ObjectEntry[] siteScopedObjectEntries = _addObjectEntries(
			3, groupId, objectDefinition);

		Assert.assertEquals(
			siteScopedObjectEntries.length,
			portletDataHandler.getExportModelCount(
				_getManifestSummary(
					PortletDataContextFactoryUtil.
						createExportPortletDataContext(
							objectDefinition.getCompanyId(), groupId,
							new HashMap<>(), null, null, null),
					portletDataHandler)));

		Assert.assertEquals(
			0,
			portletDataHandler.getExportModelCount(
				_getManifestSummary(
					PortletDataContextFactoryUtil.
						createExportPortletDataContext(
							objectDefinition.getCompanyId(), groupId,
							new HashMap<>(),
							new Date(System.currentTimeMillis() - 10000),
							new Date(System.currentTimeMillis() - 5000), null),
					portletDataHandler)));
	}

	private void _testIsConfigurationEnabled(boolean stagingSupported)
		throws Exception {

		String portletId = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable = _register(
				new TestExportImportVulcanBatchEngineTaskItemDelegateBuilder(
				).withPortletId(
					portletId
				).withStagingSupported(
					stagingSupported
				).build())) {

			Thread.sleep(1000);

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					TestPropsValues.getCompanyId(), portletId);

			Assert.assertEquals(
				stagingSupported, portletDataHandler.isConfigurationEnabled());
		}
	}

	/**
	 * @see com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter#_toSimplifiedObjectEntry(
	 *      com.liferay.object.rest.dto.v1_0.ObjectEntry, ObjectDefinition,
	 *      ObjectEntryVersion, ObjectEntry)
	 */
	private JSONObject _toSimplifiedObjectEntryJSONObject(
		Group group, ObjectEntry objectEntry, String scope) {

		return JSONUtil.put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).put(
			"scopeId", (Long)_getObjectEntryGroupId(group.getGroupId(), scope)
		).put(
			"scopeKey", _getObjectEntryScopeKey(group, scope)
		);
	}

	private static final String _OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA =
		"xAttachment1" + RandomTestUtil.randomString();

	private static final String
		_OBJECT_FIELD_NAME_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA =
			"xAttachment2" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER =
		"xAttachment3" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_RICH_TEXT =
		"xRichText" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"xText" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_ATTACHMENT_DOCS_AND_MEDIA =
		RandomTestUtil.randomString();

	private static final String
		_OBJECT_FIELD_VALUE_ATTACHMENT_SHOW_FILES_IN_DOCS_AND_MEDIA =
			RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_ATTACHMENT_USER_COMPUTER =
		RandomTestUtil.randomString();

	private static BundleContext _bundleContext;
	private static final BiFunction
		<FileEntry, Group, ObjectValuePair<String, Long>>
			_defaultReportEntryBiFunction =
				(fileEntry, targetGroup) -> new ObjectValuePair<>(
					fileEntry.getExternalReferenceCode(),
					fileEntry.getGroupId());

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Inject
	private SAXReader _saxReader;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

	@Inject
	private StagingLocalService _stagingLocalService;

	private static class TestExportImportVulcanBatchEngineTaskItemDelegate
		implements EntityModelResource,
				   ExportImportVulcanBatchEngineTaskItemDelegate<TestItem>,
				   VulcanBatchEngineTaskItemDelegate<TestItem> {

		public TestExportImportVulcanBatchEngineTaskItemDelegate(
			Function<Filter, Page<TestItem>> function, String portletId,
			Integer rank, boolean stagingSupported) {

			_function = function;
			_portletId = portletId;
			_rank = rank;
			_stagingSupported = stagingSupported;
		}

		@Override
		public void create(
			Collection<TestItem> items, Map<String, Serializable> parameters) {
		}

		@Override
		public void delete(
			Collection<TestItem> items, Map<String, Serializable> parameters) {
		}

		@Override
		public EntityModel getEntityModel(
			Map<String, List<String>> multivaluedMap) {

			return _getEntityModel();
		}

		@Override
		public EntityModel getEntityModel(MultivaluedMap<?, ?> multivaluedMap)
			throws Exception {

			return _getEntityModel();
		}

		@Override
		public ExportImportDescriptor getExportImportDescriptor() {
			return new ExportImportDescriptor() {

				@Override
				public String getLabelLanguageKey() {
					return _modelClassName;
				}

				@Override
				public String getModelClassName() {
					return _modelClassName;
				}

				@Override
				public String getPortletId() {
					return _portletId;
				}

				@Override
				public int getRank() {
					if (_rank != null) {
						return _rank;
					}

					return ExportImportDescriptor.super.getRank();
				}

				@Override
				public String getResourceClassName() {
					return _resourceClassName;
				}

				@Override
				public Scope getScope() {
					return Scope.COMPANY;
				}

				@Override
				public boolean isStagingSupported() {
					return _stagingSupported;
				}

			};
		}

		@Override
		public Page<TestItem> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search) {

			return _function.apply(filter);
		}

		@Override
		public void setContextBatchUnsafeBiConsumer(
			UnsafeBiConsumer
				<Collection<TestItem>,
				 UnsafeFunction<TestItem, TestItem, Exception>, Exception>
					contextBatchUnsafeBiConsumer) {
		}

		@Override
		public void setContextCompany(Company contextCompany) {
		}

		@Override
		public void setContextUriInfo(UriInfo uriInfo) {
		}

		@Override
		public void setContextUser(User contextUser) {
		}

		@Override
		public void setGroupLocalService(GroupLocalService groupLocalService) {
		}

		@Override
		public void setLanguageId(String languageId) {
		}

		@Override
		public void setResourceActionLocalService(
			ResourceActionLocalService resourceActionLocalService) {
		}

		@Override
		public void setResourcePermissionLocalService(
			ResourcePermissionLocalService resourcePermissionLocalService) {
		}

		@Override
		public void setRoleLocalService(RoleLocalService roleLocalService) {
		}

		@Override
		public void update(
			Collection<TestItem> testItems,
			Map<String, Serializable> parameters) {
		}

		private EntityModel _getEntityModel() {
			return new EntityModel() {

				@Override
				public Map<String, EntityField> getEntityFieldsMap() {
					return HashMapBuilder.<String, EntityField>put(
						"dateModified",
						new DateTimeEntityField(
							"dateModified", locale -> "dateModified",
							locale -> "dateModified")
					).build();
				}

				@Override
				public String getName() {
					return "TestEntityModel";
				}

			};
		}

		private final Function<Filter, Page<TestItem>> _function;
		private final String _modelClassName = RandomTestUtil.randomString();
		private final String _portletId;
		private final Integer _rank;
		private final String _resourceClassName = RandomTestUtil.randomString();
		private final boolean _stagingSupported;

	}

	private static class
		TestExportImportVulcanBatchEngineTaskItemDelegateBuilder {

		public TestExportImportVulcanBatchEngineTaskItemDelegate build() {
			if (_portletId == null) {
				throw new IllegalArgumentException(
					"portletId must not be null");
			}

			return new TestExportImportVulcanBatchEngineTaskItemDelegate(
				_function, _portletId, _rank, _stagingSupported);
		}

		public TestExportImportVulcanBatchEngineTaskItemDelegateBuilder
			withFunction(Function<Filter, Page<TestItem>> function) {

			_function = function;

			return this;
		}

		public TestExportImportVulcanBatchEngineTaskItemDelegateBuilder
			withPortletId(String portletId) {

			_portletId = portletId;

			return this;
		}

		public TestExportImportVulcanBatchEngineTaskItemDelegateBuilder
			withRank(int rank) {

			_rank = rank;

			return this;
		}

		public TestExportImportVulcanBatchEngineTaskItemDelegateBuilder
			withStagingSupported(boolean stagingSupported) {

			_stagingSupported = stagingSupported;

			return this;
		}

		private Function<Filter, Page<TestItem>> _function;
		private String _portletId;
		private Integer _rank;
		private boolean _stagingSupported;

	}

	private enum DeleteFileEntry {

		BEFORE_EXPORT, BEFORE_IMPORT

	}

	private class ExportImportExecutor {

		public File executeExport() throws Exception {
			return _exportImportLocalService.exportLayoutsAsFile(
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						TestPropsValues.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						ExportImportConfigurationSettingsMapFactoryUtil.
							buildExportLayoutSettingsMap(
								TestPropsValues.getUser(), _groupId,
								_privateLayouts,
								ArrayUtil.toLongArray(_layoutIds),
								_getExportImportParameterMap(
									_deletions, _includeDocumentLibrary,
									_includeLayoutSetLayouts,
									_includeListTypeDefinitions,
									_objectDefinitions))));
		}

		public ExportImportConfiguration executeImport() throws Exception {
			try (LogCapture logCapture = _getLogCapture(_expectError)) {
				ExportImportConfiguration exportImportConfiguration =
					_exportImportConfigurationLocalService.
						addDraftExportImportConfiguration(
							TestPropsValues.getUserId(),
							ExportImportConfigurationConstants.
								TYPE_IMPORT_LAYOUT,
							ExportImportConfigurationSettingsMapFactoryUtil.
								buildImportLayoutSettingsMap(
									TestPropsValues.getUser(), _groupId,
									_privateLayouts, null,
									_getExportImportParameterMap(
										_deletions, _includeDocumentLibrary,
										_includeLayoutSetLayouts,
										_includeListTypeDefinitions,
										_objectDefinitions)));

				if (_deletions) {
					_exportImportLocalService.importLayoutsDataDeletions(
						exportImportConfiguration, _larFile);
				}

				_exportImportLocalService.importLayouts(
					exportImportConfiguration, _larFile);

				return exportImportConfiguration;
			}
		}

		public ExportImportExecutor withDeletions() {
			_deletions = true;

			return this;
		}

		public ExportImportExecutor withExpectError() {
			_expectError = true;

			return this;
		}

		public ExportImportExecutor withGroupId(long groupId) {
			_groupId = groupId;

			return this;
		}

		public ExportImportExecutor withIncludeDocumentLibrary(
			boolean includeDocumentLibrary) {

			_includeDocumentLibrary = includeDocumentLibrary;

			return this;
		}

		public ExportImportExecutor withIncludeLayoutSetLayouts() {
			_includeLayoutSetLayouts = true;

			return this;
		}

		public ExportImportExecutor withIncludeListTypeDefinitions() {
			_includeListTypeDefinitions = true;

			return this;
		}

		public ExportImportExecutor withLARFile(File larFile) {
			_larFile = larFile;

			return this;
		}

		public ExportImportExecutor withLayoutId(long layoutId) {
			_layoutIds.add(layoutId);

			return this;
		}

		public ExportImportExecutor withObjectDefinition(
			ObjectDefinition objectDefinition) {

			_objectDefinitions.add(objectDefinition);

			return this;
		}

		public ExportImportExecutor withPrivateLayouts() {
			_privateLayouts = true;

			return this;
		}

		private boolean _deletions;
		private boolean _expectError;
		private long _groupId;
		private boolean _includeDocumentLibrary;
		private boolean _includeLayoutSetLayouts;
		private boolean _includeListTypeDefinitions;
		private File _larFile;
		private List<Long> _layoutIds = new ArrayList<>();
		private List<ObjectDefinition> _objectDefinitions = new ArrayList<>();
		private boolean _privateLayouts;

	}

}