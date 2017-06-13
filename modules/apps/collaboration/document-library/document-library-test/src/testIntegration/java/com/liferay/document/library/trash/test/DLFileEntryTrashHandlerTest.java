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

package com.liferay.document.library.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileRank;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.document.library.kernel.service.DLFileRankLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.DefaultWhenIsAssetable;
import com.liferay.trash.test.util.DefaultWhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenCanBeDuplicatedInTrash;
import com.liferay.trash.test.util.WhenHasDraftStatus;
import com.liferay.trash.test.util.WhenHasGrandParent;
import com.liferay.trash.test.util.WhenHasMyBaseModel;
import com.liferay.trash.test.util.WhenHasRecentBaseModelCount;
import com.liferay.trash.test.util.WhenIsAssetable;
import com.liferay.trash.test.util.WhenIsAssetableBaseModel;
import com.liferay.trash.test.util.WhenIsAssetableParentModel;
import com.liferay.trash.test.util.WhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenIsMoveableFromTrashBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableBaseModel;
import com.liferay.trash.test.util.WhenIsUpdatableBaseModel;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenCanBeDuplicatedInTrash, WhenHasDraftStatus,
			   WhenHasGrandParent, WhenHasMyBaseModel,
			   WhenHasRecentBaseModelCount, WhenIsAssetableBaseModel,
			   WhenIsAssetableParentModel, WhenIsIndexableBaseModel,
			   WhenIsMoveableFromTrashBaseModel, WhenIsRestorableBaseModel,
			   WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(TrashHelper.class.getName());

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Override
	public BaseModel<?> addDraftBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		return addBaseModelWithWorkflow(
			dlFolder.getGroupId(), dlFolder.getFolderId(), false);
	}

	@Override
	public AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		return _whenIsAssetable.fetchAssetEntry(classedModel);
	}

	@Override
	public String getBaseModelName(ClassedModel classedModel) {
		DLFileEntry dlFileEntry = (DLFileEntry)classedModel;

		return dlFileEntry.getTitle();
	}

	@Override
	public int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return DLAppServiceUtil.getGroupFileEntriesCount(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getParentBaseModelClassName() {
		Class<DLFolder> dlFolderClass = DLFolder.class;

		return dlFolderClass.getName();
	}

	@Override
	public int getRecentBaseModelsCount(long groupId) throws Exception {
		return DLAppServiceUtil.getGroupFileEntriesCount(
			groupId, 0, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getSearchKeywords() {
		return _FILE_ENTRY_TITLE;
	}

	@Override
	public boolean isAssetEntryVisible(ClassedModel classedModel, long classPK)
		throws Exception {

		return _whenIsAssetable.isAssetEntryVisible(classedModel, classPK);
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		DLTrashServiceUtil.moveFileEntryFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		DLTrashServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return _whenIsIndexableBaseModel.searchBaseModelsCount(clazz, groupId);
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_trashHelper = _serviceTracker.getService();
	}

	@Test
	public void testFileNameUpdateWhenUpdatingTitle() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLFileEntry dlFileEntry = (DLFileEntry)addBaseModelWithWorkflow(
			serviceContext);

		moveBaseModelToTrash(dlFileEntry.getFileEntryId());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		String title = RandomTestUtil.randomString();

		trashHandler.updateTitle(dlFileEntry.getFileEntryId(), title);

		dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			dlFileEntry.getFileEntryId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		Assert.assertEquals(
			DLUtil.getSanitizedFileName(title, dlFileEntry.getExtension()),
			dlFileEntry.getFileName());

		Assert.assertEquals(
			DLUtil.getSanitizedFileName(title, dlFileVersion.getExtension()),
			dlFileVersion.getFileName());
	}

	@Test
	public void testTrashDLFileRank() throws Exception {
		trashDLFileRank();
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			primaryKey);

		String content = "Content: Enterprise. Open Source. For Life.";

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			primaryKey, RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, dlFileEntry.getTitle(), StringPool.BLANK,
			StringPool.BLANK, false, content.getBytes(), serviceContext);

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		return liferayFileEntry.getDLFileEntry();
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		return addBaseModelWithWorkflow(
			dlFolder.getGroupId(), dlFolder.getFolderId(), true);
	}

	protected BaseModel<?> addBaseModelWithWorkflow(
			long groupId, long folderId, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), groupId, folderId,
			RandomTestUtil.randomString() + ".txt", getSearchKeywords(),
			approved, serviceContext);

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModelWithWorkflow(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		DLFolderLocalServiceUtil.deleteFolder(dlFolder.getFolderId(), false);
	}

	protected int getActiveDLFileRanksCount(long groupId, long fileEntryId)
		throws Exception {

		List<DLFileRank> dlFileRanks = DLFileRankLocalServiceUtil.getFileRanks(
			groupId, TestPropsValues.getUserId());

		int count = 0;

		for (DLFileRank dlFileRank : dlFileRanks) {
			if (dlFileRank.getFileEntryId() == fileEntryId) {
				count++;
			}
		}

		return count;
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return DLFileEntryLocalServiceUtil.getDLFileEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return DLFileEntry.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		return DLFileEntryServiceUtil.getFileEntriesCount(
			dlFolder.getGroupId(), dlFolder.getFolderId(),
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppServiceUtil.addFolder(
			group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH),
			RandomTestUtil.randomString(), serviceContext);

		return (DLFolder)folder.getModel();
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		DLFileEntry dlFileEntry = (DLFileEntry)baseModel;

		String title = dlFileEntry.getTitle();

		return _trashHelper.getOriginalTitle(title);
	}

	@Override
	protected WorkflowedModel getWorkflowedModel(ClassedModel baseModel)
		throws Exception {

		DLFileEntry dlFileEntry = (DLFileEntry)baseModel;

		return dlFileEntry.getFileVersion();
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		DLTrashServiceUtil.moveFileEntryToTrash(primaryKey);
	}

	protected void trashDLFileRank() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> baseModel = addBaseModel(parentBaseModel, serviceContext);

		DLAppLocalServiceUtil.addFileRank(
			group.getGroupId(), TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), (Long)baseModel.getPrimaryKeyObj(),
			serviceContext);

		Assert.assertEquals(
			1,
			getActiveDLFileRanksCount(
				group.getGroupId(), (Long)baseModel.getPrimaryKeyObj()));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			0,
			getActiveDLFileRanksCount(
				group.getGroupId(), (Long)baseModel.getPrimaryKeyObj()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			1,
			getActiveDLFileRanksCount(
				group.getGroupId(), (Long)baseModel.getPrimaryKeyObj()));
	}

	private static final String _FILE_ENTRY_TITLE = RandomTestUtil.randomString(
		255);

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

	private static ServiceTracker<TrashHelper, TrashHelper> _serviceTracker;

	private TrashHelper _trashHelper;
	private final WhenIsAssetable _whenIsAssetable =
		new DefaultWhenIsAssetable();
	private final WhenIsIndexableBaseModel _whenIsIndexableBaseModel =
		new DefaultWhenIsIndexableBaseModel();

}