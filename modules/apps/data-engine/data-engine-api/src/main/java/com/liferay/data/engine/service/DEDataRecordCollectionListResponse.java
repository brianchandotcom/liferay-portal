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
 * This class represents the response of the list {@link DEDataRecordCollection}
 * request.
 *
 * @author Marcela Cunha
 * @review
 */
public class DEDataRecordCollectionListResponse {

	/**
	 * Returns the Data Record Collection list result of the request
	 *
	 * @return deDataRecordCollections
	 * @review
	 */
	public List<DEDataRecordCollection> getDEDataRecordCollections() {
		return _deDataRecordCollections;
	}

	/**
	 * Inner builder that assembles the response.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Instantiate the builder providing a {@link DEDataRecordCollection}
		 * list
		 *
		 * @param deDataRecordCollections
		 * @return The builder
		 * @review
		 */
		public static Builder newBuilder(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return new Builder(deDataRecordCollections);
		}

		/**
		 * Builds a response directly from a {@link DEDataRecordCollection}
		 * list.
		 *
		 * @param deDataRecordCollections the list returned by the executor
		 * method
		 * @return The response object
		 * @review
		 */
		public static DEDataRecordCollectionListResponse of(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return newBuilder(
				deDataRecordCollections
			).build();
		}

		/**
		 * Builds the response and returns the
		 * {@link DEDataRecordCollectionListResponse} object.
		 *
		 * @return The response object.
		 * @review
		 */
		public DEDataRecordCollectionListResponse build() {
			return _deDataRecordCollectionListResponse;
		}

		private Builder(List<DEDataRecordCollection> deDataRecordCollections) {
			_deDataRecordCollectionListResponse._deDataRecordCollections =
				deDataRecordCollections;
		}

		private final DEDataRecordCollectionListResponse
			_deDataRecordCollectionListResponse =
				new DEDataRecordCollectionListResponse();

	}

	private DEDataRecordCollectionListResponse() {
	}

	private List<DEDataRecordCollection> _deDataRecordCollections;

}