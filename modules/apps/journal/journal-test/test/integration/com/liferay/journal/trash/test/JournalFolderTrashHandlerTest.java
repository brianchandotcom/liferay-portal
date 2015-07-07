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

package com.liferay.journal.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.test.DefaultWhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenCanBeDuplicatedInTrash;
import com.liferay.portlet.trash.test.WhenHasGrandParent;
import com.liferay.portlet.trash.test.WhenIsAssetableBaseModel;
import com.liferay.portlet.trash.test.WhenIsAssetableParentModel;
import com.liferay.portlet.trash.test.WhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenIsMoveableFromTrashBaseModel;
import com.liferay.portlet.trash.test.WhenIsRestorableBaseModel;
import com.liferay.portlet.trash.test.WhenIsUpdatableBaseModel;
import com.liferay.portlet.trash.util.TrashUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class JournalFolderTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenCanBeDuplicatedInTrash, WhenHasGrandParent,
			   WhenIsAssetableBaseModel, WhenIsAssetableParentModel,
			   WhenIsIndexableBaseModel, WhenIsMoveableFromTrashBaseModel,
			   WhenIsRestorableBaseModel, WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getBaseModelName(ClassedModel classedModel) {
		JournalFolder folder = (JournalFolder)classedModel;

		return folder.getName();
	}

	@Override
	public String getParentBaseModelClassName() {
		return getBaseModelClass().getName();
	}

	@Override
	public String getSearchKeywords() {
		return _FOLDER_NAME;
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		JournalFolderServiceUtil.moveFolderFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		JournalFolderServiceUtil.moveFolderToTrash(primaryKey);
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
		_whenIsIndexableBaseModel = new DefaultWhenIsIndexableBaseModel();

		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		PortalRunMode.setTestMode(_testMode);
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreUniqueTitle()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDeleteIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDeleteIsNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIndexable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
			primaryKey);

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			folder = JournalFolderLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), folder,
				WorkflowConstants.STATUS_DRAFT);
		}

		return folder;
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder parentFolder = (JournalFolder)parentBaseModel;

		String name = getSearchKeywords();

		name += RandomTestUtil.randomString(
			_FOLDER_NAME_MAX_LENGTH - name.length());

		return JournalTestUtil.addFolder(
			parentFolder.getGroupId(), parentFolder.getFolderId(), name);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			getSearchKeywords());
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		JournalFolderServiceUtil.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return JournalFolderLocalServiceUtil.getFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalFolder.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		JournalFolder parentDLFolder = (JournalFolder)parentBaseModel;

		return JournalFolderLocalServiceUtil.getFoldersCount(
			parentDLFolder.getGroupId(), parentDLFolder.getFolderId());
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH));
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			serviceContext);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		JournalFolder folder = (JournalFolder)baseModel;

		String name = folder.getName();

		return TrashUtil.getOriginalTitle(name);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		JournalFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	private static final String _FOLDER_NAME = RandomTestUtil.randomString(100);

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

	private boolean _testMode;
	private WhenIsIndexableBaseModel _whenIsIndexableBaseModel;

}