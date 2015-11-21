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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chema Balsas
 */
public class RowMover {

	public RowMover() {
	}

	public void addRowMoverDropTarget(RowMoverDropTarget rowMoverDropTarget) {
		_rowMoverDropTargets.add(rowMoverDropTarget);
	}

	public List<RowMoverDropTarget> getRowMoverDropTargets() {
		return _rowMoverDropTargets;
	}

	public String getRowSelector() {
		return _rowSelector;
	}

	public void setRowMoverDropTargets(
		List<RowMoverDropTarget> rowMoverDropTargets) {

		_rowMoverDropTargets = rowMoverDropTargets;
	}

	public void setRowSelector(String rowSelector) {
		_rowSelector = rowSelector;
	}

	public String toJSON() throws PortalException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("rowSelector", _rowSelector);

		JSONArray rowMoverDropTargetsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (RowMoverDropTarget rowMoverDropTarget : _rowMoverDropTargets) {
			String rowMoverDropTargetJSON = JSONFactoryUtil.looseSerialize(
				rowMoverDropTarget);

			JSONObject rowMoverDropTargetJSONObject =
				JSONFactoryUtil.createJSONObject(rowMoverDropTargetJSON);

			rowMoverDropTargetsJSONArray.put(
				rowMoverDropTargetJSONObject);
		}

		jsonObject.put("dropTargets", rowMoverDropTargetsJSONArray);

		return jsonObject.toString();
	}

	private List<RowMoverDropTarget> _rowMoverDropTargets = new ArrayList<>();
	private String _rowSelector = StringPool.BLANK;

}