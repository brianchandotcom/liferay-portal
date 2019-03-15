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
 * @author Marcelo Mello
 */
public class DEDataLayoutCountRequest {

	public long getGroupId() {
		return _groupId;
	}

	public static final class Builder {

		public DEDataLayoutCountRequest build() {
			return _deDataLayoutCountRequest;
		}

		public Builder inGroup(long groupId) {
			_deDataLayoutCountRequest._groupId = groupId;

			return this;
		}

		private final DEDataLayoutCountRequest _deDataLayoutCountRequest =
			new DEDataLayoutCountRequest();

	}

	private DEDataLayoutCountRequest() {
	}

	private long _groupId;

}