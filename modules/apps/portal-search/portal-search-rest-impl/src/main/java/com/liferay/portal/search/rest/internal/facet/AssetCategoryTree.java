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

package com.liferay.portal.search.rest.internal.facet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Petteri Karttunen
 */
public class AssetCategoryTree {

	public AssetCategoryTree(AssetVocabulary assetVocabulary, Locale locale) {
		_locale = locale;

		_displayName = assetVocabulary.getTitle(locale);
		_rootAssetCategoryNode = new AssetCategoryNode(0, StringPool.BLANK, 0);
		_vocabularyCategories = assetVocabulary.getCategories();
		_vocabularyId = assetVocabulary.getVocabularyId();
	}

	public void addCategory(long categoryId, int frequency) {
		AssetCategory assetCategory = _getAssetCategory(categoryId);

		if (assetCategory == null) {
			return;
		}

		String[] treePathParts = _getTreePathParts(assetCategory);

		AssetCategoryNode assetCategoryNode = _rootAssetCategoryNode;

		for (String part : treePathParts) {
			AssetCategory parentAssetCategory = _getAssetCategory(
				GetterUtil.getLong(part));

			if (parentAssetCategory == null) {
				return;
			}

			assetCategoryNode = assetCategoryNode.addChild(
				parentAssetCategory.getCategoryId(),
				parentAssetCategory.getTitle(_locale), frequency);
		}
	}

	public List<AssetCategoryNode> getCategories() {
		return _rootAssetCategoryNode.getChildren();
	}

	public String getDisplayName() {
		return _displayName;
	}

	public int getFrequency() {
		return _rootAssetCategoryNode.getFrequency();
	}

	public String getTerm() {
		return String.valueOf(_vocabularyId);
	}

	public class AssetCategoryNode {

		public AssetCategoryNode(
			long categoryId, String displayName, int frequency) {

			_categoryId = categoryId;
			_displayName = displayName;
			_frequency = frequency;
		}

		public AssetCategoryNode addChild(
			long categoryId, String displayName, int frequency) {

			if (_children == null) {
				_children = new ArrayList<>();
			}

			if (_isRoot()) {
				addToFrequency(frequency);
			}

			AssetCategoryNode childAssetCategoryNode = _getChild(categoryId);

			if (childAssetCategoryNode != null) {
				childAssetCategoryNode.addToFrequency(frequency);

				return childAssetCategoryNode;
			}

			childAssetCategoryNode = new AssetCategoryNode(
				categoryId, displayName, frequency);

			_children.add(childAssetCategoryNode);

			return childAssetCategoryNode;
		}

		public void addToFrequency(int frequency) {
			_frequency += frequency;
		}

		public List<AssetCategoryNode> getChildren() {
			return _children;
		}

		public String getDisplayName() {
			return _displayName;
		}

		public int getFrequency() {
			return _frequency;
		}

		public String getTerm() {
			return String.valueOf(_categoryId);
		}

		private AssetCategoryNode _getChild(long categoryId) {
			if (ListUtil.isEmpty(_children)) {
				return null;
			}

			for (AssetCategoryNode assetCategoryNode : _children) {
				if (assetCategoryNode._categoryId == categoryId) {
					return assetCategoryNode;
				}
			}

			return null;
		}

		private boolean _isRoot() {
			if (_categoryId == 0) {
				return true;
			}

			return false;
		}

		private final long _categoryId;
		private List<AssetCategoryNode> _children;
		private final String _displayName;
		private int _frequency;

	}

	private AssetCategory _getAssetCategory(long categoryId) {
		for (AssetCategory assetCategory : _vocabularyCategories) {
			if ((assetCategory.getCategoryId() == categoryId) &&
				_hasViewPermission(assetCategory)) {

				return assetCategory;
			}
		}

		return null;
	}

	private String[] _getTreePathParts(AssetCategory assetCategory) {
		String treePath = assetCategory.getTreePath();

		String[] treePathParts = treePath.split("/");

		if (treePathParts.length > 1) {
			treePathParts = ArrayUtil.remove(treePathParts, StringPool.BLANK);
		}

		return treePathParts;
	}

	private boolean _hasViewPermission(AssetCategory assetCategory) {
		try {
			return AssetCategoryPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), assetCategory,
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final String _displayName;
	private final Locale _locale;
	private final AssetCategoryNode _rootAssetCategoryNode;
	private final List<AssetCategory> _vocabularyCategories;
	private final long _vocabularyId;

}