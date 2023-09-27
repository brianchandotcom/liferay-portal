/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.pagination;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alejandro Hernández
 * @author Zoltán Takács
 */
public class Pagination {

	public static Pagination of(int page, int pageSize) {
		return new Pagination(page, pageSize);
	}

	public int getEndPosition() {
		if ((_page < 0) || (_pageSize < 0)) {
			return -1;
		}

		return _page * _pageSize;
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public int getStartPosition() {
		if ((_page < 0) || (_pageSize < 0)) {
			return -1;
		}

		return (_page - 1) * _pageSize;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler("{\"page\": ");

		sb.append(_page);
		sb.append(", \"pageSize\": ");
		sb.append(_pageSize);
		sb.append("}");

		return sb.toString();
	}

	private Pagination(int page, int pageSize) {
		_page = page;
		_pageSize = pageSize;
	}

	private final int _page;
	private final int _pageSize;

}