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

package com.liferay.blogs.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class BlogsEntryStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		BlogsEntry approvedEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		stagedModels.add(approvedEntry);

		BlogsEntry pendingEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), false,
			serviceContext);

		stagedModels.add(pendingEntry);

		return stagedModels;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return BlogsEntryLocalServiceUtil.getBlogsEntryByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		BlogsEntry entry = (BlogsEntry)stagedModel;
		BlogsEntry importedEntry = (BlogsEntry)importedStagedModel;

		Assert.assertEquals(entry.getTitle(), importedEntry.getTitle());
		Assert.assertEquals(entry.getSubtitle(), importedEntry.getSubtitle());
		Assert.assertEquals(entry.getUrlTitle(), importedEntry.getUrlTitle());
		Assert.assertEquals(
			entry.getDescription(), importedEntry.getDescription());

		Calendar displayDateCalendar = Calendar.getInstance();
		Calendar importedDisplayDateCalendar = Calendar.getInstance();

		displayDateCalendar.setTime(entry.getDisplayDate());
		importedDisplayDateCalendar.setTime(importedEntry.getDisplayDate());

		displayDateCalendar.set(Calendar.SECOND, 0);
		displayDateCalendar.set(Calendar.MILLISECOND, 0);

		importedDisplayDateCalendar.set(Calendar.SECOND, 0);
		importedDisplayDateCalendar.set(Calendar.MILLISECOND, 0);

		Assert.assertEquals(displayDateCalendar, importedDisplayDateCalendar);

		Assert.assertEquals(
			entry.isAllowPingbacks(), importedEntry.isAllowPingbacks());
		Assert.assertEquals(
			entry.isAllowTrackbacks(), importedEntry.isAllowTrackbacks());
		Assert.assertEquals(
			entry.getTrackbacks().trim(), importedEntry.getTrackbacks().trim());
		Assert.assertEquals(
			entry.getCoverImageCaption(), importedEntry.getCoverImageCaption());
		Assert.assertEquals(entry.isSmallImage(), importedEntry.isSmallImage());
	}

}