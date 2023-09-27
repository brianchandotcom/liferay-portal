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
public abstract class BaseTable<T> implements Iterable<T> {

	public abstract Iterable<T> getTable();

	@Override
	public abstract Iterator<T> iterator();

	protected BaseTable(List<List<String>> rawData) {
		this.rawData = rawData;
	}

	protected List<List<String>> rawData;

}