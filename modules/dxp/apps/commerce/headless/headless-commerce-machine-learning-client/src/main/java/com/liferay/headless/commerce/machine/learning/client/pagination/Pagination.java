/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.client.pagination;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
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
		StringBuilder sb = new StringBuilder("{\"page\": ");

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