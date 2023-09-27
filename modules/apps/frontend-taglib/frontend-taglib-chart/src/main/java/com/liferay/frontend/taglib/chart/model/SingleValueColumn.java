/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class SingleValueColumn extends Column {

	public SingleValueColumn(String id) {
		super(id);
	}

	public SingleValueColumn(String id, Number value) {
		super(id);

		setValue(value);
	}

	public void setValue(Number value) {
		List<Number> data = getData();

		if (data.isEmpty()) {
			data.add(value);
		}
		else {
			data.set(0, value);
		}
	}

}