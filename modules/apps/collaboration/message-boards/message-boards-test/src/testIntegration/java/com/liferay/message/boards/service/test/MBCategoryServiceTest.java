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

package com.liferay.message.boards.service.test;

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBThreadConstants;
import com.liferay.message.boards.kernel.service.MBCategoryServiceUtil;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class MBCategoryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);
	}

	@Test
	public void testGetCategoriesAndThreadsCountInRootCategory()
		throws Exception {

		addMessage(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBCategory category1 = addCategory();

		addMessage(category1.getCategoryId());

		MBCategory category2 = addCategory();

		addMessage(category2.getCategoryId());

		addMessage(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			int categoriesAndThreadsCount =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(4, categoriesAndThreadsCount);
		}
	}

	@Test
	public void testGetCategoriesAndThreadsCountInRootCategoryWithOnlyCategories()
		throws Exception {

		MBCategory category1 = addCategory();

		addCategory(category1.getCategoryId());

		addCategory();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			int categoriesAndThreadsCount =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(2, categoriesAndThreadsCount);
		}
	}

	@Test
	public void testGetCategoriesAndThreadsCountInRootCategoryWithOnlyThreads()
		throws Exception {

		addMessage(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		addMessage(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			int categoriesAndThreadsCount =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(2, categoriesAndThreadsCount);
		}
	}

	@Test
	public void testGetCategoriesAndThreadsCountWithOnlyCategories()
		throws Exception {

		MBCategory category1 = addCategory();

		addCategory(category1.getCategoryId());

		addCategory();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			int categoriesAndThreadsCount =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					_group.getGroupId(), category1.getCategoryId(),
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(1, categoriesAndThreadsCount);
		}
	}

	@Test
	public void testGetCategoriesAndThreadsInRootCategory() throws Exception {
		MBMessage message1 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBCategory category1 = addCategory();

		addMessage(category1.getCategoryId());

		MBCategory category2 = addCategory();

		addMessage(category2.getCategoryId());

		MBMessage message2 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			List<Object> categoriesAndThreads =
				MBCategoryServiceUtil.getCategoriesAndThreads(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(
				categoriesAndThreads.toString(), 4,
				categoriesAndThreads.size());
			Assert.assertEquals(category1, categoriesAndThreads.get(0));
			Assert.assertEquals(category2, categoriesAndThreads.get(1));
			Assert.assertEquals(
				message1.getThread(), categoriesAndThreads.get(2));
			Assert.assertEquals(
				message2.getThread(), categoriesAndThreads.get(3));
		}
	}

	@Test
	public void testGetCategoriesAndThreadsInRootCategoryWithOnlyCategories()
		throws Exception {

		MBCategory category1 = addCategory();

		addCategory(category1.getCategoryId());

		MBCategory category2 = addCategory();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			List<Object> categoriesAndThreads =
				MBCategoryServiceUtil.getCategoriesAndThreads(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(
				categoriesAndThreads.toString(), 2,
				categoriesAndThreads.size());
			Assert.assertEquals(category1, categoriesAndThreads.get(0));
			Assert.assertEquals(category2, categoriesAndThreads.get(1));
		}
	}

	@Test
	public void testGetCategoriesAndThreadsInRootCategoryWithOnlyThreads()
		throws Exception {

		MBMessage message1 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBMessage message2 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			List<Object> categoriesAndThreads =
				MBCategoryServiceUtil.getCategoriesAndThreads(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(
				categoriesAndThreads.toString(), 2,
				categoriesAndThreads.size());
			Assert.assertEquals(
				message1.getThread(), categoriesAndThreads.get(0));
			Assert.assertEquals(
				message2.getThread(), categoriesAndThreads.get(1));
		}
	}

	@Test
	public void testGetCategoriesAndThreadsWithOnlyCategories()
		throws Exception {

		MBCategory category1 = addCategory();

		MBCategory subcategory1 = addCategory(category1.getCategoryId());

		addCategory();

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			List<Object> categoriesAndThreads =
				MBCategoryServiceUtil.getCategoriesAndThreads(
					_group.getGroupId(), category1.getCategoryId(),
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(
				categoriesAndThreads.toString(), 1,
				categoriesAndThreads.size());
			Assert.assertEquals(subcategory1, categoriesAndThreads.get(0));
		}
	}

	@Test
	public void testGetCategoriesAndThreadsWithPriorityThread()
		throws Exception {

		MBMessage message1 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBCategory category1 = addCategory();

		addMessage(category1.getCategoryId());

		MBMessage priorityMessage = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, 2.0);

		MBMessage message2 = addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		try (ContextUserReplace contextUserReplacer = new ContextUserReplace(
				_user)) {

			List<Object> categoriesAndThreads =
				MBCategoryServiceUtil.getCategoriesAndThreads(
					_group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					WorkflowConstants.STATUS_APPROVED);

			Assert.assertEquals(
				categoriesAndThreads.toString(), 4,
				categoriesAndThreads.size());
			Assert.assertEquals(category1, categoriesAndThreads.get(0));
			Assert.assertEquals(
				priorityMessage.getThread(), categoriesAndThreads.get(1));
			Assert.assertEquals(
				message1.getThread(), categoriesAndThreads.get(2));
			Assert.assertEquals(
				message2.getThread(), categoriesAndThreads.get(3));
		}
	}

	protected MBCategory addCategory() throws Exception {
		return addCategory(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	protected MBCategory addCategory(long parentCategoryId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategoryId,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	protected MBMessage addMessage(long categoryId) throws Exception {
		return addMessage(categoryId, MBThreadConstants.PRIORITY_NOT_GIVEN);
	}

	protected MBMessage addMessage(long categoryId, double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), categoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			inputStreamOVPs, false, priority, false, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}