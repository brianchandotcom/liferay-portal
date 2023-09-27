/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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