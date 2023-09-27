/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Stefan Tanasie
 */
public class SegmentsEntryItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public long[] getExcludedSegmentsEntryIds() {
		return _excludedSegmentsEntryIds;
	}

	public String[] getExcludedSources() {
		return _excludedSources;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setExcludedSegmentsEntryIds(long[] excludedSegmentsEntryIds) {
		_excludedSegmentsEntryIds = excludedSegmentsEntryIds;
	}

	public void setExcludedSources(String[] excludedSources) {
		_excludedSources = excludedSources;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private long[] _excludedSegmentsEntryIds;
	private String[] _excludedSources;
	private long _groupId;

}