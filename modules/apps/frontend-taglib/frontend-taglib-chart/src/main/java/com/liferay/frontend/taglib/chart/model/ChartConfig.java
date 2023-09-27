/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public abstract class ChartConfig<ColumnType extends Column>
	extends ChartObject {

	public void addColumn(ColumnType columnType) {
		ArrayList<ColumnType> columns = get("data", ArrayList.class);

		columns.add(columnType);
	}

	public void addColumns(Collection<? extends ColumnType> columnTypes) {
		for (ColumnType columnType : columnTypes) {
			addColumn(columnType);
		}
	}

	public void addColumns(ColumnType... columnTypes) {
		for (ColumnType columnType : columnTypes) {
			addColumn(columnType);
		}
	}

	@JSON(include = false)
	public AxisX getAxisX() {
		return get("axisX", AxisX.class);
	}

	@JSON(include = false)
	public AxisY getAxisY() {
		return get("axisY", AxisY.class);
	}

	@JSON(include = false)
	public AxisY2 getAxisY2() {
		return get("axisY2", AxisY2.class);
	}

	public void setAxisRotated(boolean rotated) {
		set("axisRotated", rotated);
	}

	public void setPollingInterval(int pollingInterval) {
		set("pollingInterval", pollingInterval);
	}

}