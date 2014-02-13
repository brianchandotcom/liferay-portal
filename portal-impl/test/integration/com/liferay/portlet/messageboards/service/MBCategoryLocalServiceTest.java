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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class MBCategoryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groupId = group.getGroupId();
	}

	@Test
	public void testGetCategoriesCountWithExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));

		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId,
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
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_groupId);
		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));

		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetCategoriesCountWithStatusApprovedAndExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));

		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId,
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
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));

		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_groupId, excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	public void testGetCategoriesWithExcludedCategories() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(_groupId);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory1);
		expectedCategories.add(excludedCategory2);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId,
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
			MBCategoryLocalServiceUtil.getCategories(_groupId);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));
		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId, excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithStatusApprovedAndExcludedCategories()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(
				_groupId,  WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory1);
		expectedCategories.add(excludedCategory2);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getCategories(
				_groupId, WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId,
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
				_groupId, WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getCategories(
				_groupId, WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_groupId, excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetParentCategory() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		MBCategory parentCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategory childCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		Assert.assertNotNull(childCategory.getParentCategory());
		Assert.assertNull(parentCategory.getParentCategory());
	}

	@Test
	public void testGetParentDiscussionCategory() throws Exception {
		MBCategory discussionCategory = MBCategoryLocalServiceUtil.getCategory(
			MBCategoryConstants.DISCUSSION_CATEGORY_ID);

		Assert.assertNotNull(discussionCategory);
		Assert.assertNull(discussionCategory.getParentCategory());
	}

	private long _groupId;

}