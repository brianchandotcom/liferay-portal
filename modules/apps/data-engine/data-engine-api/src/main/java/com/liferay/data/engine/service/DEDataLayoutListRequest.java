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
 * This class represents a request to list {@link DEDataLayout}s from the database.
 *
 * @author Gabriel Albuquerque
 * @review
 */
public class DEDataLayoutListRequest {

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
	 * Returns the group id used as a filter in the list request
	 *
	 * @return groupId
	 * @review
	 */
	public long getGroupId() {
		return _groupId;
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
	 * Constructs the List Data Layout request.
	 * The group ID, pagination start index and final index can be used as
	 * alternative parameters
	 *
	 * @review
	 */
	public static final class Builder {

		/**
		 * Constructs the List Data Layout request.
		 *
		 * @return {@link DEDataLayoutListRequest}
		 * @review
		 */
		public DEDataLayoutListRequest build() {
			return _deDataLayoutListRequest;
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
			_deDataLayoutListRequest._end = end;

			return this;
		}

		/**
		 * This method sets the group id used as a filter for the list method
		 *
		 * @param groupId
		 * @return {@link Builder}
		 * @review
		 */
		public Builder inGroup(long groupId) {
			_deDataLayoutListRequest._groupId = groupId;

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
			_deDataLayoutListRequest._start = start;

			return this;
		}

		private final DEDataLayoutListRequest _deDataLayoutListRequest =
			new DEDataLayoutListRequest();

	}

	private DEDataLayoutListRequest() {
	}

	private int _end = QueryUtil.ALL_POS;
	private long _groupId;
	private int _start = QueryUtil.ALL_POS;

}