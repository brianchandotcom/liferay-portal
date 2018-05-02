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

package com.liferay.wiki.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Roberto Díaz
 */
public class WikiPageItemSelectorCriterion extends BaseItemSelectorCriterion {

	public WikiPageItemSelectorCriterion() {
	}

	public WikiPageItemSelectorCriterion(long nodeId, int status) {
		_nodeId = nodeId;
		_status = status;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public int getStatus() {
		return _status;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _nodeId;
	private int _status;

}