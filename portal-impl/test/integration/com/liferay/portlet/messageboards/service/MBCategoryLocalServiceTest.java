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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 * @author Roberto Díaz
 */
public class MBCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetCategoriesCountWithExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		addCategory();

		MBCategory excludedCategory1 = addCategory();
		MBCategory excludedCategory2 = addCategory();

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()
				},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetCategoriesCountWithExcludedCategory() throws Exception {
		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		addCategory();
		addCategory();

		MBCategory excludedCategory = addCategory();

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(), excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetCategoriesCountWithStatusApprovedAndExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		addCategory();

		MBCategory excludedCategory1 = addCategory();
		MBCategory excludedCategory2 = addCategory();

		MBCategory draftCategory = addCategory();

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()
				},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	public void testGetCategoriesCountWithStatusApprovedAndExcludedCategory()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		addCategory();

		MBCategory excludedCategory = addCategory();
		MBCategory draftCategory = addCategory();

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(), excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	public void testGetCategoriesWithExcludedCategories() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(_group.getGroupId());

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(addCategory());

		MBCategory excludedCategory1 = addCategory();

		expectedCategories.add(excludedCategory1);

		MBCategory excludedCategory2 = addCategory();

		expectedCategories.add(excludedCategory2);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId());

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(),
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()
			},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithExcludedCategory() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(_group.getGroupId());

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(addCategory());
		expectedCategories.add(addCategory());

		MBCategory excludedCategory = addCategory();

		expectedCategories.add(excludedCategory);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId());

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithStatusApprovedAndExcludedCategories()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(
				_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(addCategory());

		MBCategory excludedCategory1 = addCategory();

		expectedCategories.add(excludedCategory1);

		MBCategory excludedCategory2 = addCategory();

		expectedCategories.add(excludedCategory2);

		MBCategory draftCategory = addCategory();

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(),
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()
			},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithStatusApprovedAndExcludedCategory()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(
				_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(addCategory());

		MBCategory excludedCategory = addCategory();

		expectedCategories.add(excludedCategory);

		MBCategory draftCategory = addCategory();

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetParentCategory() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBCategory parentCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategory category = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		Assert.assertNotNull(category.getParentCategory());
		Assert.assertNull(parentCategory.getParentCategory());
	}

	@Test
	public void testGetParentDiscussionCategory() throws Exception {
		MBCategory discussionCategory = MBCategoryLocalServiceUtil.getCategory(
			MBCategoryConstants.DISCUSSION_CATEGORY_ID);

		Assert.assertNotNull(discussionCategory);
		Assert.assertNull(discussionCategory.getParentCategory());
	}

	protected MBCategory addCategory() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}