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

/**
 * This class represents a response of the get {@link DEDataRecordCollection} executor.
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionGetResponse {

	/**
	 * Returns the Data Record Collection ID of the Get response.
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public DEDataRecordCollection getDEDataRecordCollection() {
		return _deDataRecordCollection;
	}

	/**
	 * Constructs the Get Data Record Collections response using parameters.
	 * A Data Record Collection ID can be used as an argument.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the Data Record Collection builder
		 * @param deDataRecordCollection Data Record Collection returned by
		 * the executor method
		 * @return {@link Builder }
		 * @review
		 */
		public static Builder newBuilder(
			DEDataRecordCollection deDataRecordCollection) {

			return new Builder(deDataRecordCollection);
		}

		/**
		 * Includes a Data Record Collection in the Get response.
		 *
		 * @param deDataRecordCollection Data Record Collection returned by
		 * the executor method
		 * @return {@link DEDataRecordCollectionGetResponse }
		 * @review
		 */
		public static DEDataRecordCollectionGetResponse of(
			DEDataRecordCollection deDataRecordCollection) {

			return newBuilder(
				deDataRecordCollection
			).build();
		}

		/**
		 * Constructs the Get Data Record Collections response.
		 * @return {@link DEDataRecordCollectionGetResponse }
		 * @review
		 */
		public DEDataRecordCollectionGetResponse build() {
			return _deDataRecordCollectionGetResponse;
		}

		private Builder(DEDataRecordCollection deDataRecordCollection) {
			_deDataRecordCollectionGetResponse._deDataRecordCollection =
				deDataRecordCollection;
		}

		private final DEDataRecordCollectionGetResponse
			_deDataRecordCollectionGetResponse =
				new DEDataRecordCollectionGetResponse();

	}

	private DEDataRecordCollectionGetResponse() {
	}

	private DEDataRecordCollection _deDataRecordCollection;

}