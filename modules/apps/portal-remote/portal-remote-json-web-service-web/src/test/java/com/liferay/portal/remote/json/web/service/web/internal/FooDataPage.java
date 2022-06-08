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

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.portal.kernel.json.JSON;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class FooDataPage {

	public FooDataPage(FooData data, List<FooData> list, int page) {
		_data = data;
		_list = list;
		_page = page;
	}

	public FooData getData() {
		return _data;
	}

	public List<FooData> getList() {
		return _list;
	}

	public int getPage() {
		return _page;
	}

	public void setData(FooData data) {
		_data = data;
	}

	public void setList(List<FooData> list) {
		_list = list;
	}

	public void setPage(int page) {
		_page = page;
	}

	private FooData _data;

	@JSON
	private List<FooData> _list;

	private int _page;

}