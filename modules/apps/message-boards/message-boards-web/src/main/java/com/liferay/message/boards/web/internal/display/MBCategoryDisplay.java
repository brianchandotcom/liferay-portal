/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_init(scopeGroupId, categoryId);
		}
		catch (Exception exception) {
			_log.error(exception);
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

	private void _init(long scopeGroupId, long categoryId) throws Exception {
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

		_populateCategoryNodesMap(_categoryTree.getRootNode(), categoriesMap);
	}

	private void _populateCategoryNodesMap(
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

			_populateCategoryNodesMap(curNode, categoriesMap);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBCategoryDisplay.class);

	private List<MBCategory> _allCategories;
	private Map<Long, TreeNode<MBCategory>> _categoryNodesMap;
	private ListTree<MBCategory> _categoryTree;
	private MBCategory _rootCategory;

}