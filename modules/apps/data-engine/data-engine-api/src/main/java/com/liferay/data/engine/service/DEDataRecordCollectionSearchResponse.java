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
 * @author Marcelo Mello
 */
public class DEDataRecordCollectionSearchResponse {

	public List<DEDataRecordCollection> getDeDataRecordCollections() {
		return _deDataRecordCollections;
	}

	public static final class Builder {

		public static Builder newBuilder(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return new Builder(deDataRecordCollections);
		}

		public static DEDataRecordCollectionSearchResponse of(
			List<DEDataRecordCollection> deDataRecordCollections) {

			return newBuilder(
				deDataRecordCollections
			).build();
		}

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