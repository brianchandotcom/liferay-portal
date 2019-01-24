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

import com.liferay.data.engine.model.DEDataLayout;

/**
 * Request class used to save a data layout
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutSaveRequest {

	/**
	 * Returns the data layout that will be saved.
	 * @review
	 * @return the data layout
	 */
	public DEDataLayout getDEDataLayout() {
		return _deDataLayout;
	}

	/**
	 * Returns the group id responsible for the request
	 * @review
	 * @return The group id Long
	 */
	public Long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the user id responsible for the request
	 * @review
	 * @return The user id Long
	 */
	public Long getUserId() {
		return _userId;
	}

	/**
	 * Inner builder that assembles the request
	 * @review
	 */
	public static final class Builder {

		/**
		 * Builder constructor that receives the dataLayout as parameter.
		 * @review
		 * @param deDataLayout The {@link DEDataLayout} object
		 */
		public Builder(DEDataLayout deDataLayout) {
			_deDataLayoutSaveRequest._deDataLayout = deDataLayout;
		}

		/**
		 * Builds the request and return the {@link DEDataLayoutSaveRequest}
		 * object.
		 * @return the {@link DEDataLayoutSaveRequest} object.
		 */
		public DEDataLayoutSaveRequest build() {
			return _deDataLayoutSaveRequest;
		}

		/**
		 * The group id responsible for the save operation.
		 * @review
		 * @param groupId
		 * @return the builder
		 */
		public Builder inGroup(Long groupId) {
			_deDataLayoutSaveRequest._groupId = groupId;

			return this;
		}

		/**
		 * The user responsible for the save operation
		 * @param userId
		 * @return the builder
		 */
		public Builder onBehalfOf(Long userId) {
			_deDataLayoutSaveRequest._userId = userId;

			return this;
		}

		private final DEDataLayoutSaveRequest _deDataLayoutSaveRequest =
			new DEDataLayoutSaveRequest();

	}

	private DEDataLayout _deDataLayout;
	private Long _groupId;
	private Long _userId;

}