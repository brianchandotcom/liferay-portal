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

package com.liferay.account.service.result;

import java.util.List;

/**
 * @author Drew Brokke
 */
public class ServiceResults<T> {

	public ServiceResults(List<T> results, int total) {
		_results = results;
		_total = total;
	}

	public List<T> getResults() {
		return _results;
	}

	public long getTotal() {
		return _total;
	}

	private final List<T> _results;
	private final int _total;

}