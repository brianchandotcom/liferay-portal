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

import com.liferay.portal.kernel.dao.orm.QueryUtil;

/**
 * This class represents a request to list a {@link DEDataRecord} from the database.
 *
 * @author Marcela Cunha
 * @review
 */
public class DEDataRecordCollectionListRecordRequest {

	/**
	 * Returns the {@link DEDataRecordCollection} ID that is the filter in the
	 * list request
	 *
	 * @return deDataRecordCollectionId
	 * @review
	 */
	public long getDERecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	/**
	 * Returns the final index of the pagination
	 *
	 * @return end
	 * @review
	 */
	public int getEnd() {
		return _end;
	}

	/**
	 * Returns the start index of the pagination
	 *
	 * @return start
	 * @review
	 */
	public int getStart() {
		return _start;
	}

	/**
	 * Constructs the List Data Record request.
	 * The Data Record Collection ID is a required parameter.
	 * Pagination start index and final index can be  used as an alternative
	 * parameter.
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the List Data Record request.
		 *
		 * @param deDataRecordCollectionId primary key of {@link DEDataRecordCollection}
		 * @return {@link Builder}
		 * @review
		 */
		public Builder(long deDataRecordCollectionId) {
			_deDataRecordCollectionListRecordRequest._deDataRecordCollectionId =
				deDataRecordCollectionId;
		}

		/**
		 * Constructs the List Data Record request.
		 *
		 * @return {@link DEDataRecordCollectionListRecordRequest}
		 * @review
		 */
		public DEDataRecordCollectionListRecordRequest build() {
			return _deDataRecordCollectionListRecordRequest;
		}

		/**
		 * This method sets the final index of the pagination
		 *
		 *
		 * @param end
		 * @return {@link Builder}
		 * @review
		 */
		public Builder endingAt(int end) {
			_deDataRecordCollectionListRecordRequest._end = end;

			return this;
		}

		/**
		 * This method sets the start index of the pagination
		 *
		 *
		 * @param start
		 * @return {@link Builder}
		 * @review
		 */
		public Builder startingAt(int start) {
			_deDataRecordCollectionListRecordRequest._start = start;

			return this;
		}

		private final DEDataRecordCollectionListRecordRequest
			_deDataRecordCollectionListRecordRequest =
				new DEDataRecordCollectionListRecordRequest();

	}

	private DEDataRecordCollectionListRecordRequest() {
	}

	private long _deDataRecordCollectionId;
	private int _end = QueryUtil.ALL_POS;
	private int _start = QueryUtil.ALL_POS;

}