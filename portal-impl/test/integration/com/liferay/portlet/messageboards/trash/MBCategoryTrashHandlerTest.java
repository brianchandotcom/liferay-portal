/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class MBCategoryTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Ignore()
	@Override
	@Test
	public void testDeleteTrashVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashAndDeleteDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashAndRestoreDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashDuplicate() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashRecentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		MBCategory parentCategory = (MBCategory)parentBaseModel;

		return MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			getSearchKeywords(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, getSearchKeywords(),
			StringPool.BLANK, serviceContext);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		MBCategory parentCategory = (MBCategory)parentBaseModel;

		MBCategoryLocalServiceUtil.deleteCategory(parentCategory, false);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return MBCategoryLocalServiceUtil.getCategory(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBCategory.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		MBCategory category = (MBCategory)classedModel;

		return category.getName();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		MBCategory parentCategory = (MBCategory)parentBaseModel;

		return MBCategoryLocalServiceUtil.getCategoriesCount(
			parentCategory.getGroupId(), parentCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, getSearchKeywords(),
			StringPool.BLANK, serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected boolean isAssetableModel() {
		return false;
	}

	@Override
	protected boolean isIndexableBaseModel() {
		return false;
	}

	@Override
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		MBCategoryLocalServiceUtil.moveCategoryFromTrash(
			TestPropsValues.getUserId(), (Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj());

		return parentBaseModel;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

}