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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListTree;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBCategoryDisplay;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class MBCategoryDisplayImpl implements MBCategoryDisplay {

	public MBCategoryDisplayImpl(long scopeGroupId, long categoryId) {
		try {
			init(scopeGroupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public List<MBCategory> getAllCategories() {
		return _allCategories;
	}

	@Override
	public int getAllCategoriesCount() {
		return _allCategories.size();
	}

	@Override
	public List<MBCategory> getCategories() {
		return _categoryTree.getRootNode().getChildValues();
	}

	@Override
	public List<MBCategory> getCategories(MBCategory category) {
		TreeNode<MBCategory> node = _categoryNodesMap.get(
			category.getCategoryId());

		return node.getChildValues();
	}

	@Override
	public MBCategory getRootCategory() {
		return _categoryTree.getRootNode().getValue();
	}

	@Override
	public int getSubcategoriesCount(MBCategory category) {
		TreeNode<MBCategory> node = _categoryNodesMap.get(
			category.getCategoryId());

		return _categoryTree.getChildNodes(node).size();
	}

	@Override
	public int getSubcategoriesMessagesCount(MBCategory category) {
		int count = category.getMessageCount();

		TreeNode<MBCategory> node = _categoryNodesMap.get(
			category.getCategoryId());

		List<TreeNode<MBCategory>> childNodes = _categoryTree.getChildNodes(
			node);

		for (TreeNode<MBCategory> curNode : childNodes) {
			MBCategory curCategory = curNode.getValue();

			count += curCategory.getMessageCount();
		}

		return count;
	}

	@Override
	public int getSubcategoriesThreadsCount(MBCategory category) {
		int count = category.getThreadCount();

		TreeNode<MBCategory> node = _categoryNodesMap.get(
			category.getCategoryId());

		List<TreeNode<MBCategory>> childNodes = _categoryTree.getChildNodes(
			node);

		for (TreeNode<MBCategory> curNode : childNodes) {
			MBCategory curCategory = curNode.getValue();

			count += curCategory.getThreadCount();
		}

		return count;
	}

	@Override
	public void getSubcategoryIds(MBCategory category, List<Long> categoryIds) {
		List<MBCategory> categories = getCategories(category);

		for (MBCategory curCategory : categories) {
			categoryIds.add(curCategory.getCategoryId());

			getSubcategoryIds(curCategory, categoryIds);
		}
	}

	protected void init(long scopeGroupId, long categoryId) throws Exception {
		long[] categoryIds = MBCategoryServiceUtil.getCategoryIds(
			scopeGroupId, categoryId);

		_allCategories = MBCategoryServiceUtil.getCategories(
			scopeGroupId, categoryIds, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		_rootCategory = new MBCategoryImpl();

		_rootCategory.setCategoryId(categoryId);

		_categoryTree = new ListTree<MBCategory>(_rootCategory);

		_categoryNodesMap = new HashMap<Long, TreeNode<MBCategory>>();

		Map<Long, List<MBCategory>> categoriesMap =
			new HashMap<Long, List<MBCategory>>();

		for (MBCategory category : _allCategories) {
			Long parentCategoryId = category.getParentCategoryId();

			List<MBCategory> curCategories = categoriesMap.get(
				parentCategoryId);

			if (curCategories == null) {
				curCategories = new ArrayList<MBCategory>();

				categoriesMap.put(parentCategoryId, curCategories);
			}

			curCategories.add(category);
		}

		populateCategoryNodesMap(_categoryTree.getRootNode(), categoriesMap);
	}

	protected void populateCategoryNodesMap(
		TreeNode<MBCategory> node, Map<Long, List<MBCategory>> categoriesMap) {

		MBCategory category = node.getValue();

		if (category.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			_categoryNodesMap.put(category.getCategoryId(), node);
		}

		List<MBCategory> categories = categoriesMap.get(
			category.getCategoryId());

		if (categories == null) {
			return;
		}

		for (MBCategory curCategory : categories) {
			TreeNode<MBCategory> curNode = node.addChildNode(curCategory);

			_categoryNodesMap.put(curCategory.getCategoryId(), curNode);

			populateCategoryNodesMap(curNode, categoriesMap);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBCategoryDisplayImpl.class);

	private List<MBCategory> _allCategories;
	private Map<Long, TreeNode<MBCategory>> _categoryNodesMap;
	private ListTree<MBCategory> _categoryTree;
	private MBCategory _rootCategory;

}