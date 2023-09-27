/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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