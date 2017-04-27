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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetTermDisplayContext {

	public int getCount() {
		return _count;
	}

	public String getDescriptiveName() throws PortalException {
		return _descriptiveName;
	}

	public long getGroupId() {
		return _groupId;
	}

	public boolean isSelected() {
		return _selected;
	}

	public boolean isShowCount() {
		return _showCount;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setDescriptiveName(String descriptiveName) {
		_descriptiveName = descriptiveName;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setShowCount(boolean showCount) {
		_showCount = showCount;
	}

	private int _count;
	private String _descriptiveName;
	private long _groupId;
	private boolean _selected;
	private boolean _showCount;

}