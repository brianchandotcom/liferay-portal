/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class Tree implements ResponseElement {

	public static final String CLOSE_UL = "</ul>";

	public static final String OPEN_UL = "<ul>";

	public void addChild(ResponseElement node) {
		_children.add(node);
	}

	@Override
	public String parse() {
		StringBundler sb = new StringBundler(_children.size() * 4 + 4);

		sb.append(OPEN_UL);
		sb.append(StringPool.NEW_LINE);

		for (ResponseElement child : _children) {
			sb.append(child.parse());
		}

		sb.append(CLOSE_UL);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private List<ResponseElement> _children = new ArrayList<ResponseElement>();

}