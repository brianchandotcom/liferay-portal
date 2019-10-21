/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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