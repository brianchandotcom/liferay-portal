/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chema Balsas
 */
public class RowMover {

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
		JSONArray rowMoverDropTargetsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (RowMoverDropTarget rowMoverDropTarget : _rowMoverDropTargets) {
			String rowMoverDropTargetJSON = JSONFactoryUtil.looseSerialize(
				rowMoverDropTarget);

			JSONObject rowMoverDropTargetJSONObject =
				JSONFactoryUtil.createJSONObject(rowMoverDropTargetJSON);

			rowMoverDropTargetsJSONArray.put(rowMoverDropTargetJSONObject);
		}

		return JSONUtil.put(
			"dropTargets", rowMoverDropTargetsJSONArray
		).put(
			"rowSelector", _rowSelector
		).toString();
	}

	private List<RowMoverDropTarget> _rowMoverDropTargets = new ArrayList<>();
	private String _rowSelector = StringPool.BLANK;

}