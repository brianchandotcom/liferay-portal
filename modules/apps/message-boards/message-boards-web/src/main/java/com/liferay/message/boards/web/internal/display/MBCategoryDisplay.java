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

package com.liferay.message.boards.web.internal.display;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListTree;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class MBCategoryDisplay {

	public MBCategoryDisplay(long scopeGroupId, long categoryId) {
		try {
			init(scopeGroupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public int getAllCategoriesCount() {
		return _allCategories.size();
	}

	public int getSubcategoriesCount(MBCategory category) {
		TreeNode<MBCategory> node = _categoryNodesMap.get(
			category.getCategoryId());

		List<TreeNode<MBCategory>> childNodes = _categoryTree.getChildNodes(
			node);

		return childNodes.size();
	}

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

	protected void init(long scopeGroupId, long categoryId) throws Exception {
		_allCategories = MBCategoryServiceUtil.getCategories(
			scopeGroupId, WorkflowConstants.STATUS_APPROVED);

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			_rootCategory = MBCategoryLocalServiceUtil.fetchMBCategory(
				categoryId);
		}

		_categoryTree = new ListTree<>(_rootCategory);

		_categoryNodesMap = new HashMap<>();

		Map<Long, List<MBCategory>> categoriesMap = new HashMap<>();

		for (MBCategory category : _allCategories) {
			Long parentCategoryId = category.getParentCategoryId();

			List<MBCategory> curCategories = categoriesMap.get(
				parentCategoryId);

			if (curCategories == null) {
				curCategories = new ArrayList<>();

				categoriesMap.put(parentCategoryId, curCategories);
			}

			curCategories.add(category);
		}

		populateCategoryNodesMap(_categoryTree.getRootNode(), categoriesMap);
	}

	protected void populateCategoryNodesMap(
		TreeNode<MBCategory> node, Map<Long, List<MBCategory>> categoriesMap) {

		MBCategory category = node.getValue();

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			_categoryNodesMap.put(categoryId, node);
		}

		List<MBCategory> categories = categoriesMap.get(categoryId);

		if (categories == null) {
			return;
		}

		for (MBCategory curCategory : categories) {
			TreeNode<MBCategory> curNode = node.addChildNode(curCategory);

			_categoryNodesMap.put(curCategory.getCategoryId(), curNode);

			populateCategoryNodesMap(curNode, categoriesMap);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBCategoryDisplay.class);

	private List<MBCategory> _allCategories;
	private Map<Long, TreeNode<MBCategory>> _categoryNodesMap;
	private ListTree<MBCategory> _categoryTree;
	private MBCategory _rootCategory;

}