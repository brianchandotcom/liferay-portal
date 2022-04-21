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

package com.liferay.asset.vocabulary.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetVocabularyItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public AssetVocabularyItemSelectorCriterion() {
		_includeInternalVocabularies = true;
	}

	public long getGroupId() {
		return _groupId;
	}

	public boolean isIncludeAncestorSiteAndDepotGroupIds() {
		return _includeAncestorSiteAndDepotGroupIds;
	}

	public boolean isIncludeInternalVocabularies() {
		return _includeInternalVocabularies;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setIncludeAncestorSiteAndDepotGroupIds(
		boolean includeAncestorSiteAndDepotGroupIds) {

		_includeAncestorSiteAndDepotGroupIds =
			includeAncestorSiteAndDepotGroupIds;
	}

	public void setIncludeInternalVocabularies(
		boolean includeInternalVocabularies) {

		_includeInternalVocabularies = includeInternalVocabularies;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	private long _groupId;
	private boolean _includeAncestorSiteAndDepotGroupIds;
	private boolean _includeInternalVocabularies;
	private boolean _multiSelection;

}