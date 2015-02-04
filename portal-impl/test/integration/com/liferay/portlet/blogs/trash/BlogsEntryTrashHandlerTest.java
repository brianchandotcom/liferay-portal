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

package com.liferay.portlet.blogs.trash;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;

import java.io.Serializable;

import java.util.HashMap;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 */
@Sync
public class BlogsEntryTrashHandlerTest extends BaseTrashHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore()
	@Override
	@Test
	public void testDeleteTrashVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteGroupTrashEntries()
		throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteParent() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashBaseModelAndParentAndRestoreModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashDuplicate() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModel()
		throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent1() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent2() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent3() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashIsRestorableBaseModelWithParent4() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMoveBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashParentAndDeleteGroupTrashEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashParentAndDeleteParent() throws Exception {
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

		String title = getSearchKeywords();
		String subtitle = StringPool.BLANK;
		String description = "Description";
		String content = "Content";
		int displayDateMonth = 1;
		int displayDateDay = 1;
		int displayDateYear = 2012;
		int displayDateHour = 12;
		int displayDateMinute = 0;
		boolean allowPingbacks = true;
		boolean allowTrackbacks = true;
		String[] trackbacks = new String[0];
		ImageSelector coverImageImageSelector = null;
		ImageSelector smallImageImageSelector = null;

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title, subtitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageImageSelector, smallImageImageSelector, serviceContext);

		if (approved) {
			entry = BlogsEntryLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}

		return entry;
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return BlogsEntryLocalServiceUtil.getEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		return BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), queryDefinition);
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
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

}