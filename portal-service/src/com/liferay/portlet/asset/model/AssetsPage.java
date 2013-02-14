/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.json.JSON;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetsPage<E> {

	public List<E> getAssets() {
		return _assets;
	}

	public int getEnd() {
		return _end;
	}

	public int getPage() {
		return _page;
	}

	public int getStart() {
		return _start;
	}

	public int getTotal() {
		return _total;
	}

	public void setAssets(List<E> assets) {
		_assets = assets;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setPage(int page) {
		_page = page;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setTotal(int total) {
		_total = total;
	}

	@JSON
	private List<E> _assets;
	private int _end;
	private int _page;
	private int _start;
	private int _total;

}