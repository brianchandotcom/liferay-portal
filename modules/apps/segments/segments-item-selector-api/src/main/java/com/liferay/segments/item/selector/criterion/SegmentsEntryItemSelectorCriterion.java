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