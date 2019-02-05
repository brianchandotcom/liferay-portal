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

package com.liferay.data.engine.storage;

import java.util.Collections;
import java.util.Map;

/**
 * Response class used as a return value for the data storage retrieve operation
 *
 * @author Leonardo Barros
 */
public final class DEDataStorageGetResponse {

	/**
	 * Returns a map of the data storage field values
	 * @review
	 * @return A map of the data storage values
	 */
	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static class Builder {

		/**
		 * Builder constructor that receives the data storage field values as parameter.
		 * @param values The data storage values
		 * @review
		 */
		public Builder(Map<String, Object> values) {
			_deDataStorageGetResponse._values = values;
		}

		/**
		 * Builds the response and returns the {@link DEDataStorageGetResponse}
		 * object.
		 * @review
		 * @return The response object.
		 */
		public DEDataStorageGetResponse build() {
			return _deDataStorageGetResponse;
		}

		private final DEDataStorageGetResponse _deDataStorageGetResponse =
			new DEDataStorageGetResponse();

	}

	private DEDataStorageGetResponse() {
	}

	private Map<String, Object> _values;

}