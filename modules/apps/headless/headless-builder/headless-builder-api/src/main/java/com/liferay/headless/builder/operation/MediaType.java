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

package com.liferay.headless.builder.operation;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Carlos Correa
 */
public enum MediaType {

	ALL("*/*"), APPLICATION_JSON("application/json"),
	APPLICATION_XML("application/xml");

	public static MediaType parse(String value) {
		for (MediaType mediaType : MediaType.values()) {
			if (Objects.equals(mediaType.getValue(), value)) {
				return mediaType;
			}
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	public static class MediaTypeComparator implements Comparator<MediaType> {

		@Override
		public int compare(MediaType mediaType1, MediaType mediaType2) {
			if (Objects.equals(MediaType.ALL, mediaType1)) {
				return -2;
			}
			else if (Objects.equals(MediaType.ALL, mediaType2)) {
				return 2;
			}
			else if (Objects.equals(MediaType.APPLICATION_JSON, mediaType1)) {
				return -1;
			}
			else if (Objects.equals(MediaType.APPLICATION_JSON, mediaType2)) {
				return 1;
			}

			return 0;
		}

	}

	private MediaType(String value) {
		_value = value;
	}

	private final String _value;

}