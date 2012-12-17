/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBCategoryTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashAndDeleteDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashAndRestoreDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashDuplicate() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndRestore() throws Exception {
		Assert.assertTrue("This test does not apply", true);
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
	protected int getBaseModelsNotInTrashCount(BaseModel<?> parentBaseModel)
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
	protected boolean isInTrashFolder(ClassedModel classedModel)
		throws Exception {

		MBCategory category = (MBCategory)classedModel;

		return category.isInTrashCategory();
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