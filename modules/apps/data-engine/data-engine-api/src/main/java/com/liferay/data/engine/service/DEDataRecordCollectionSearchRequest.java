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
 * This class represents a request for search and get a list of
 * {@link DEDataRecordCollection} from database
 * @review
 * @author Marcelo Mello
 */
public class DEDataRecordCollectionSearchRequest {

	/**
	 * Returns the company id responsible for the request
	 * @return The company id Long
	 * @review
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * Returns the end page pagination setted for the request
	 * @return The end page pagination Int
	 * @review
	 */
	public int getEnd() {
		return _end;
	}

	/**
	 * Returns the group id responsible for the request
	 * @return The group id Long
	 * @review
	 */
	public long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the keywords that setted for the request
	 * @return The keywords String
	 * @review
	 */
	public String getKeywords() {
		return _keywords;
	}

	/**
	 * Returns the start page pagination setted for the request
	 * @return The start page pagination Int
	 * @review
	 */
	public int getStart() {
		return _start;
	}

	/**
	 * Inner builder that assembles the request
	 * @review
	 */
	public static final class Builder {

		/**
		 * Builds the request and return the {@link DEDataRecordCollectionSearchRequest}
		 * object.
		 * @return the {@link DEDataRecordCollectionSearchRequest} object.
		 * @review
		 */
		public DEDataRecordCollectionSearchRequest build() {
			return _deDataRecordCollectionSearchRequest;
		}

		/**
		 * The number of last page for request pagination
		 * @param end
		 * @return the builder
		 * @review
		 */
		public Builder endingAt(int end) {
			_deDataRecordCollectionSearchRequest._end = end;

			return this;
		}

		/**
		 * The keywords that request will be sought
		 * @param keywords
		 * @return the builder
		 * @review
		 */
		public Builder havingKeywords(String keywords) {
			_deDataRecordCollectionSearchRequest._keywords = keywords;

			return this;
		}

		/**
		 * The company id that request will be sought
		 * @param companyId
		 * @return the builder
		 * @review
		 */
		public Builder inCompany(long companyId) {
			_deDataRecordCollectionSearchRequest._companyId = companyId;

			return this;
		}

		/**
		 * The group id that request will be sought
		 * @param groupId
		 * @return the builder
		 * @review
		 */
		public Builder inGroup(long groupId) {
			_deDataRecordCollectionSearchRequest._groupId = groupId;

			return this;
		}

		/**
		 * The number of first page for request pagination
		 * @param start
		 * @return
		 * @review
		 */
		public Builder startingAt(int start) {
			_deDataRecordCollectionSearchRequest._start = start;

			return this;
		}

		private final DEDataRecordCollectionSearchRequest
			_deDataRecordCollectionSearchRequest =
				new DEDataRecordCollectionSearchRequest();

	}

	private DEDataRecordCollectionSearchRequest() {
	}

	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long _groupId;
	private String _keywords;
	private int _start = QueryUtil.ALL_POS;

}