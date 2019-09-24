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

package com.liferay.document.library.web.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class DLPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	public static final String NAMESPACE = "document_library";

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@After
	public void tearDown() {
		if (_serviceRegistrations != null) {
			_serviceRegistrations.forEach(ServiceRegistration::unregister);
		}
	}

	@Test
	public void testCustomRepositoryEntriesExport() throws Exception {
		initContext();

		addRepositoryEntries();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		LongWrapper fileEntryModelAdditionCounter = modelAdditionCounters.get(
			DLFileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());
	}

	@Test
	public void testCustomRepositoryEntriesPrepareManifestSummary()
		throws Exception {

		initContext();

		addRepositoryEntries();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		LongWrapper fileEntryModelAdditionCounter = modelAdditionCounters.get(
			DLFileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());
	}

	@Test
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder parentFolder = DLAppServiceUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parent", RandomTestUtil.randomString(), serviceContext);

		Folder childFolder = DLAppServiceUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "child",
			RandomTestUtil.randomString(), serviceContext);

		DLTrashServiceUtil.moveFolderToTrash(childFolder.getFolderId());

		DLTrashServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLAppServiceUtil.deleteFolder(parentFolder.getFolderId());

		GroupLocalServiceUtil.deleteGroup(group);

		int foldersCount = DLFolderLocalServiceUtil.getFoldersCount(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(0, foldersCount);
	}

	@Test
	public void testDLExportableRepositoryPublisherIsInvokedWhenExporting()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		_registerService(
			new CountingDLExportableRepositoryPublisher(atomicInteger));

		initContext();

		portletDataHandler.exportData(
			portletDataContext, DLPortletKeys.DOCUMENT_LIBRARY, null);

		Assert.assertTrue(atomicInteger.get() >= 1);
	}

	@Test
	public void testDLExportableRepositoryPublisherIsInvokedWhenPreparingSummary()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		_registerService(
			new CountingDLExportableRepositoryPublisher(atomicInteger));

		initContext();

		portletDataHandler.prepareManifestSummary(portletDataContext);

		Assert.assertTrue(atomicInteger.get() >= 1);
	}

	@Test
	public void testGetManifestSummaryWithFolderAndFile() throws Exception {
		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), this.stagingGroup, false, false,
			new ServiceContext());

		Group stagingGroup = this.stagingGroup.getStagingGroup();

		Folder folder = DLAppServiceUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new ServiceContext());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			folder.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), new ServiceContext());

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		initContext();

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE});

		portletDataContext.setParameterMap(parameterMap);

		portletDataContext.setEndDate(getEndDate());
		portletDataContext.setScopeGroupId(stagingGroup.getGroupId());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(DLFolder.class.getName()));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(DLFileEntry.class.getName()));
	}

	@Test
	public void testGetManifestSummaryWithFolderAndFileInTrash()
		throws Exception {

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), this.stagingGroup, false, false,
			new ServiceContext());

		Group stagingGroup = this.stagingGroup.getStagingGroup();

		Folder folder = DLAppServiceUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new ServiceContext());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			folder.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), new ServiceContext());

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(), new HashMap<>());

		_dlAppHelperLocalService.moveFolderToTrash(
			TestPropsValues.getUserId(), folder);

		initContext();

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE});

		portletDataContext.setParameterMap(parameterMap);

		portletDataContext.setEndDate(getEndDate());
		portletDataContext.setScopeGroupId(stagingGroup.getGroupId());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Assert.assertEquals(
			0, manifestSummary.getModelAdditionCount(DLFolder.class.getName()));
		Assert.assertEquals(
			0,
			manifestSummary.getModelAdditionCount(DLFileEntry.class.getName()));
	}

	@Test
	public void testPublishedCustomRepositoryEntriesExport() throws Exception {
		long repositoryId = addRepositoryEntries();

		_registerService(
			new ConstantDLExportableRepositoryPublisher(repositoryId));

		initContext();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		LongWrapper fileEntryModelAdditionCounter = modelAdditionCounters.get(
			DLFileEntry.class.getName());

		Assert.assertTrue(fileEntryModelAdditionCounter.getValue() >= 1);

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertTrue(folderModelAdditionCounter.getValue() >= 1);
	}

	@Test
	public void testPublishedCustomRepositoryEntriesPrepareManifestSummary()
		throws Exception {

		long repositoryId = addRepositoryEntries();

		_registerService(
			new ConstantDLExportableRepositoryPublisher(repositoryId));

		initContext();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		LongWrapper fileEntryModelAdditionCounter = modelAdditionCounters.get(
			DLFileEntry.class.getName());

		Assert.assertTrue(fileEntryModelAdditionCounter.getValue() >= 1);

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertTrue(folderModelAdditionCounter.getValue() >= 1);
	}

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, "document_library", "repositories", true);
	}

	protected long addRepositoryEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		Repository repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortletKeys.BACKGROUND_TASK, StringPool.BLANK,
			PortletKeys.BACKGROUND_TASK, new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
			serviceContext);

		return repository.getRepositoryId();
	}

	@Override
	protected void addStagedModels() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DLFileEntryMetadata.class.getName());

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		DLAppTestUtil.populateServiceContext(
			serviceContext, dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
			serviceContext);

		DLAppLocalServiceUtil.addFileShortcut(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			folder.getFolderId(), fileEntry.getFileEntryId(), serviceContext);
	}

	@Override
	protected DataLevel getDataLevel() {
		return DataLevel.PORTLET_INSTANCE;
	}

	@Override
	protected String[] getDataPortletPreferences() {
		return new String[] {"rootFolderId"};
	}

	@Override
	protected PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				NAMESPACE, "repositories", true, false, null,
				Repository.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL),
			new PortletDataHandlerBoolean(
				NAMESPACE, "folders", true, false, null,
				DLFolderConstants.getClassName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "documents", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "previews-and-thumbnails")
				},
				DLFileEntryConstants.getClassName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "document-types", true, false, null,
				DLFileEntryType.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "shortcuts", true, false, null,
				DLFileShortcutConstants.getClassName())
		};
	}

	@Override
	protected PortletDataHandlerControl[] getImportControls() {
		return getExportControls();
	}

	@Override
	protected String getPortletId() {
		return DLPortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	protected List<StagedModel> getStagedModels() {
		List<StagedModel> stagedModels = new ArrayList<>();

		List<DLFolder> folders = DLFolderLocalServiceUtil.getFolders(
			portletDataContext.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		stagedModels.addAll(folders);

		for (DLFolder folder : folders) {
			stagedModels.addAll(
				DLFileEntryLocalServiceUtil.getFileEntries(
					portletDataContext.getGroupId(), folder.getFolderId()));

			stagedModels.addAll(
				DLFileShortcutLocalServiceUtil.getFileShortcuts(
					portletDataContext.getGroupId(), folder.getFolderId(), true,
					WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS));
		}

		stagedModels.addAll(
			DLFileEntryLocalServiceUtil.getFileEntries(
				portletDataContext.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		stagedModels.addAll(
			DLFileShortcutLocalServiceUtil.getFileShortcuts(
				portletDataContext.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));

		stagedModels.addAll(
			RepositoryLocalServiceUtil.getGroupRepositories(
				portletDataContext.getGroupId()));

		stagedModels.addAll(
			DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
				new long[] {portletDataContext.getGroupId()}));

		return stagedModels;
	}

	@Override
	protected PortletDataHandlerControl[] getStagingControls() {
		return getExportControls();
	}

	@Override
	protected boolean isDataPortalLevel() {
		return false;
	}

	@Override
	protected boolean isDataPortletInstanceLevel() {
		return true;
	}

	@Override
	protected boolean isDataSiteLevel() {
		return false;
	}

	@Override
	protected boolean isDeleteDataTested() {
		return true;
	}

	@Override
	protected boolean isDisplayPortlet() {
		return super.isDisplayPortlet();
	}

	@Override
	protected boolean isExportImportDataTested() {
		return true;
	}

	@Override
	protected boolean isGetExportConfigurationControlsTested() {
		return true;
	}

	@Override
	protected boolean isGetExportModelCountTested() {
		return true;
	}

	private void _registerService(
		DLExportableRepositoryPublisher dlExportableRepositoryPublisher) {

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistrations.add(
			registry.registerService(
				DLExportableRepositoryPublisher.class,
				dlExportableRepositoryPublisher, new HashMap<>()));
	}

	@Inject
	private DLAppHelperLocalService _dlAppHelperLocalService;

	private final Collection
		<ServiceRegistration<DLExportableRepositoryPublisher>>
			_serviceRegistrations = new ArrayList<>();

	private static class ConstantDLExportableRepositoryPublisher
		implements DLExportableRepositoryPublisher {

		public ConstantDLExportableRepositoryPublisher(long repositoryId) {
			_repositoryId = repositoryId;
		}

		@Override
		public void publish(long groupId, Consumer<Long> repositoryIdConsumer) {
			repositoryIdConsumer.accept(_repositoryId);
		}

		private final long _repositoryId;

	}

	private static class CountingDLExportableRepositoryPublisher
		implements DLExportableRepositoryPublisher {

		public CountingDLExportableRepositoryPublisher(
			AtomicInteger atomicInteger) {

			_atomicInteger = atomicInteger;
		}

		@Override
		public void publish(long groupId, Consumer<Long> repositoryIdConsumer) {
			_atomicInteger.incrementAndGet();
		}

		private final AtomicInteger _atomicInteger;

	}

}