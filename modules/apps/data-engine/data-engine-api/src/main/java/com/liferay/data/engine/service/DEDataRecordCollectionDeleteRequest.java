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

/**
 * This class represents a request to delete a {@link DEDataRecordCollection} from the database.
 * @review
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionDeleteRequest {

	/**
	 * Returns the Data Record Collection ID of the Delete request.
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	/**
	 * Constructs the Delete Data Record Collections request using parameters. A
	 * Data Record Collection ID can be used as an argument.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the Delete Data Record Collections request.
		 * @review
		 * @return {@link DEDataRecordCollectionDeleteRequest }
		 */
		public DEDataRecordCollectionDeleteRequest build() {
			return _deDataRecordCollectionDeleteRequest;
		}

		/**
		 * Includes a Data Record Collection ID in the Delete request.
		 * @param deDataRecordCollectionId ID from the Data Record
		 * Collection to be deleted
		 * @return {@link Builder }
		 * @review
		 */
		public Builder byId(long deDataRecordCollectionId) {
			_deDataRecordCollectionDeleteRequest._deDataRecordCollectionId =
				deDataRecordCollectionId;

			return this;
		}

		private final DEDataRecordCollectionDeleteRequest
			_deDataRecordCollectionDeleteRequest =
				new DEDataRecordCollectionDeleteRequest();

	}

	private DEDataRecordCollectionDeleteRequest() {
	}

	private long _deDataRecordCollectionId;

}