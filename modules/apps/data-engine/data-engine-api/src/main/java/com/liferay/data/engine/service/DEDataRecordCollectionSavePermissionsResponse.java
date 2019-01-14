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

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionSavePermissionsResponse {

	public List<String> getRoleNames() {
		return Collections.unmodifiableList(_roleNames);
	}

	public static final class Builder {

		public static Builder newBuilder(String... roleNames) {
			return new Builder(roleNames);
		}

		public static DEDataRecordCollectionSavePermissionsResponse of(
			String... roleNames) {

			return newBuilder(
				roleNames
			).build();
		}

		public DEDataRecordCollectionSavePermissionsResponse build() {
			return _deDataRecordCollectionSavePermissionsResponse;
		}

		private Builder(String... roleNames) {
			_deDataRecordCollectionSavePermissionsResponse._roleNames =
				ListUtil.fromArray(roleNames);
		}

		private final DEDataRecordCollectionSavePermissionsResponse
			_deDataRecordCollectionSavePermissionsResponse =
				new DEDataRecordCollectionSavePermissionsResponse();

	}

	private DEDataRecordCollectionSavePermissionsResponse() {
	}

	private List<String> _roleNames;

}