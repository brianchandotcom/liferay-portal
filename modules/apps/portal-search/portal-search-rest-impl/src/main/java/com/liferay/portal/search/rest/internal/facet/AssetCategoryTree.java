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

	public void addAssetCategory(long assetCategoryId, int frequency) {
		AssetCategory assetCategory = _getAssetCategory(assetCategoryId);

		if (assetCategory == null) {
			return;
		}

		String[] treePathParts = _getTreePathParts(assetCategory);

		AssetCategoryNode assetCategoryNode = _rootAssetCategoryNode;

		for (String treePathPart : treePathParts) {
			AssetCategory parentAssetCategory = _getAssetCategory(
				GetterUtil.getLong(treePathPart));

			if (parentAssetCategory == null) {
				return;
			}

			assetCategoryNode = assetCategoryNode.addChildAssetCategoryNode(
				parentAssetCategory.getCategoryId(),
				parentAssetCategory.getTitle(_locale), frequency);
		}
	}

	public List<AssetCategoryNode> getChildren() {
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
			long assetCategoryId, String displayName, int frequency) {

			_assetCategoryId = assetCategoryId;
			_displayName = displayName;
			_frequency = frequency;
		}

		public AssetCategoryNode addChildAssetCategoryNode(
			long categoryId, String displayName, int frequency) {

			if (_childrenAssetCategoryNodes == null) {
				_childrenAssetCategoryNodes = new ArrayList<>();
			}

			if (_isRoot()) {
				addToFrequency(frequency);
			}

			AssetCategoryNode childAssetCategoryNode =
				_getChildAssetCategoryNode(categoryId);

			if (childAssetCategoryNode != null) {
				childAssetCategoryNode.addToFrequency(frequency);

				return childAssetCategoryNode;
			}

			childAssetCategoryNode = new AssetCategoryNode(
				categoryId, displayName, frequency);

			_childrenAssetCategoryNodes.add(childAssetCategoryNode);

			return childAssetCategoryNode;
		}

		public void addToFrequency(int frequency) {
			_frequency += frequency;
		}

		public List<AssetCategoryNode> getChildren() {
			return _childrenAssetCategoryNodes;
		}

		public String getDisplayName() {
			return _displayName;
		}

		public int getFrequency() {
			return _frequency;
		}

		public String getTerm() {
			return String.valueOf(_assetCategoryId);
		}

		private AssetCategoryNode _getChildAssetCategoryNode(long categoryId) {
			if (ListUtil.isEmpty(_childrenAssetCategoryNodes)) {
				return null;
			}

			for (AssetCategoryNode assetCategoryNode :
					_childrenAssetCategoryNodes) {

				if (assetCategoryNode._assetCategoryId == categoryId) {
					return assetCategoryNode;
				}
			}

			return null;
		}

		private boolean _isRoot() {
			if (_assetCategoryId == 0) {
				return true;
			}

			return false;
		}

		private final long _assetCategoryId;
		private List<AssetCategoryNode> _childrenAssetCategoryNodes;
		private final String _displayName;
		private int _frequency;

	}

	private AssetCategory _getAssetCategory(long assetCategoryId) {
		for (AssetCategory assetCategory : _vocabularyCategories) {
			if ((assetCategory.getCategoryId() == assetCategoryId) &&
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