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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DEDataRecordCollection;

import java.util.List;

/**
 * Response class used as return value for the data collection search operation
 *
 * @author Marcelo Mello
 */
public class DEDataRecordCollectionSearchResponse {

	/**
	 * Returns a list of data collection
	 * @return list of {@link DEDataRecordCollection}
	 */
	public List<DEDataRecordCollection> getDeDataRecordCollections() {
		return _deDataRecordCollections;
	}

	/**
	 * Inner builder that assembles the response
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a deDataRecordCollections
		 * @param deDataRecordCollections
		 * @return the builder
		 */
		public static Builder newBuilder(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return new Builder(deDataRecordCollections);
		}

		/**
		 * Build a response directly from {@link DEDataRecordCollection}
		 * @param deDataRecordCollections
		 * @return the response object
		 */
		public static DEDataRecordCollectionSearchResponse of(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return newBuilder(
				deDataRecordCollections
			).build();
		}

		/**
		 * Builds the response and returns the {@link DEDataDefinitionSearchResponse}
		 * @return the response object
		 */
		public DEDataRecordCollectionSearchResponse build() {
			return _deDataRecordCollectionSearchResponse;
		}

		private Builder(List<DEDataRecordCollection> deDataRecordCollections) {
			_deDataRecordCollectionSearchResponse._deDataRecordCollections =
				deDataRecordCollections;
		}

		private final DEDataRecordCollectionSearchResponse
			_deDataRecordCollectionSearchResponse =
				new DEDataRecordCollectionSearchResponse();

	}

	private DEDataRecordCollectionSearchResponse() {
	}

	private List<DEDataRecordCollection> _deDataRecordCollections;

}