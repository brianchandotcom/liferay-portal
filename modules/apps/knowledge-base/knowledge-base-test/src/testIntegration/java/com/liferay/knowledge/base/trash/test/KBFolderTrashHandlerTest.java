/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.test.util.KBTestUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.WhenHasGrandParent;
import com.liferay.trash.test.util.WhenHasMyBaseModel;
import com.liferay.trash.test.util.WhenIsMoveableFromTrashBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableBaseModel;
import com.liferay.trash.test.util.WhenIsUpdatableBaseModel;
import com.liferay.trash.test.util.WhenParentModelIsSameType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class KBFolderTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenHasGrandParent, WhenHasMyBaseModel,
			   WhenIsMoveableFromTrashBaseModel, WhenIsRestorableBaseModel,
			   WhenIsUpdatableBaseModel, WhenParentModelIsSameType {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return KBFolderLocalServiceUtil.getKBFoldersCount(
			groupId, KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getParentBaseModelClassName() {
		return KBFolder.class.getName();
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = (KBFolder)classedModel;

		KBFolderLocalServiceUtil.moveKBFolderFromTrash(
			kbFolder.getUserId(), kbFolder.getKbFolderId(),
			kbFolder.getParentKBFolderId());

		return KBFolderLocalServiceUtil.getKBFolder(
			kbFolder.getParentKBFolderId());
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(primaryKey);

		KBFolderLocalServiceUtil.moveKBFolderToTrash(
			kbFolder.getUserId(), primaryKey);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(primaryKey);

		return KBFolderLocalServiceUtil.updateKBFolder(
			kbFolder.getClassNameId(), kbFolder.getParentKBFolderId(),
			kbFolder.getKbFolderId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		return KBTestUtil.addKBFolder(
			kbFolder.getGroupId(), kbFolder.getKbFolderId(), serviceContext);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		KBFolderLocalServiceUtil.deleteKBFolder(
			(KBFolder)parentBaseModel, includeTrashedEntries);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return KBFolderLocalServiceUtil.getKBFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return KBFolder.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		return KBFolderLocalServiceUtil.getKBFoldersCount(
			kbFolder.getGroupId(), kbFolder.getKbFolderId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return KBTestUtil.addKBFolder(
			group.getGroupId(), parentBaseModelId, serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected boolean isInTrashContainer(TrashedModel trashedModel) {
		return _trashHelper.isInTrashContainer(trashedModel);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(primaryKey);

		KBFolderLocalServiceUtil.moveKBFolderToTrash(
			kbFolder.getUserId(), kbFolder.getKbFolderId());
	}

	@Inject
	private TrashHelper _trashHelper;

}