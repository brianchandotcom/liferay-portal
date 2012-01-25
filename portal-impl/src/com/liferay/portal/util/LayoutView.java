/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutView implements Serializable {

	public LayoutView() {
		this(new ArrayList<String>(), 0);
	}

	public LayoutView(List<String> list, int depth) {
		_list = list;
		_depth = depth;
	}

	public List<String> getList() {
		return _list;
	}

	public int getDepth() {
		return _depth;
	}

	private int _depth;
	private List<String> _list;

}