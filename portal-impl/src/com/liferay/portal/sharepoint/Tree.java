/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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
		StringBundler sb = new StringBundler((_children.size() * 4) + 4);

		sb.append(OPEN_UL);
		sb.append(StringPool.NEW_LINE);

		for (ResponseElement child : _children) {
			sb.append(child.parse());
		}

		sb.append(CLOSE_UL);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private final List<ResponseElement> _children = new ArrayList<>();

}