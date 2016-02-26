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

package com.liferay.portal.kernel.dao.search;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ResultRowSplitterEntry {

	public ResultRowSplitterEntry(String title, List<ResultRow> resultRows) {
		_title = title;
		_resultRows = resultRows;
	}

	public List<ResultRow> getResultRows() {
		return _resultRows;
	}

	public String getTitle() {
		return _title;
	}

	public void setResultRows(List<ResultRow> resultRows) {
		_resultRows = resultRows;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<ResultRow> _resultRows;
	private String _title;

}