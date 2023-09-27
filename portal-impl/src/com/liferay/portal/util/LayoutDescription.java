/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class LayoutDescription implements Serializable {

	public LayoutDescription(long plid, String name, int depth) {
		_plid = plid;
		_name = name;
		_depth = depth;
	}

	public int getDepth() {
		return _depth;
	}

	public String getDisplayName() {
		StringBundler sb = new StringBundler(_depth + 1);

		for (int i = 0; i < _depth; i++) {
			sb.append("-&nbsp;");
		}

		sb.append(HtmlUtil.escape(_name));

		return sb.toString();
	}

	public String getName() {
		return _name;
	}

	public long getPlid() {
		return _plid;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{depth=", _depth, ", name=", _name, ", plid=", _plid, "}");
	}

	private final int _depth;
	private final String _name;
	private final long _plid;

}