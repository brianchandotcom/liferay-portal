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

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Arques
 */
public class PageMetadata {

	public long getNumber() {
		return _number;
	}

	public long getSize() {
		return _size;
	}

	public long getTotalElements() {
		return _totalElements;
	}

	public long getTotalPages() {
		return _totalPages;
	}

	protected PageMetadata() {
	}

	@JsonProperty("number")
	private long _number;

	@JsonProperty("size")
	private long _size;

	@JsonProperty("totalElements")
	private long _totalElements;

	@JsonProperty("totalPages")
	private long _totalPages;

}