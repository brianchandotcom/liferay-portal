/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.var.type;

import java.util.Iterator;
import java.util.List;

/**
 * @author Yi-Chen Tsai
 */
public class RawTable extends BaseTable<List<String>> {

	@Override
	public Iterable<List<String>> getTable() {
		return rawData;
	}

	@Override
	public Iterator<List<String>> iterator() {
		return rawData.iterator();
	}

	protected RawTable(List<List<String>> rawData) {
		super(rawData);
	}

}