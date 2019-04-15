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

package com.liferay.headless.delivery.client.dto.v1_0.page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Rubén Pulido
 */
public class Page<T> {

	public Collection<T> getItems() {
		return new ArrayList<>(items);
	}

	public long getLastPage() {
		return lastPage;
	}

	public long getPage() {
		return page;
	}

	public long getPageSize() {
		return pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setItems(Collection<T> items) {
		this.items = items;
	}

	public void setLastPage(long lastPage) {
		this.lastPage = lastPage;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	protected Collection<T> items;
	protected long lastPage;
	protected long page;
	protected long pageSize;
	protected long totalCount;

}