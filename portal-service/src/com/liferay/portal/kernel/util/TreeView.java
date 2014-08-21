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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class TreeView implements Serializable {

	public TreeView() {
		this(new ArrayList<TreeNodeView>(), 0);
	}

	public TreeView(List<TreeNodeView> list, int depth) {
		_list = list;
		_depth = depth;
	}

	public int getDepth() {
		return _depth;
	}

	public List<TreeNodeView> getList() {
		return _list;
	}

	private final int _depth;
	private final List<TreeNodeView> _list;

}