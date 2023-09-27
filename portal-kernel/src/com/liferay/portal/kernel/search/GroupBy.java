/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class GroupBy implements Serializable {

	public GroupBy(String field) {
		_field = field;
	}

	public String getField() {
		return _field;
	}

	public int getSize() {
		return _size;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setSize(int size) {
		_size = size;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	private String _field;
	private int _size;
	private Sort[] _sorts;
	private int _start;

}