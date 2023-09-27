/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public abstract class Column extends ChartObject {

	public Column(String id) {
		setId(id);
	}

	public void setId(String id) {
		set("id", id);
	}

	public void setName(String name) {
		set("name", name);
	}

	@JSON(include = false)
	protected <T> List<T> getData() {
		return get("data", ArrayList.class);
	}

}