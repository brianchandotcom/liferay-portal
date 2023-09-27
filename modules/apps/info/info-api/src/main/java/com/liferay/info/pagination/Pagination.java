/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.pagination;

/**
 * @author Jorge Ferrer
 */
public class Pagination {

	public static Pagination of(int end, int start) {
		return new Pagination(end, start);
	}

	public int getDelta() {
		return _end - _start;
	}

	public int getEnd() {
		return _end;
	}

	public int getStart() {
		return _start;
	}

	private Pagination(int end, int start) {
		_end = end;
		_start = start;
	}

	private final int _end;
	private final int _start;

}