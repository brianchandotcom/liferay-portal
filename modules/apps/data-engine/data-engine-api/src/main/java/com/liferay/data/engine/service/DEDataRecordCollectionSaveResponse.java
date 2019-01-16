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
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionSaveResponse {

	public DEDataRecordCollection getDEDataRecordCollection() {
		return _deDataRecordCollection;
	}

	public static final class Builder {

		public static Builder newBuilder(
			DEDataRecordCollection deDataRecordCollection) {

			return new Builder(deDataRecordCollection);
		}

		public static DEDataRecordCollectionSaveResponse of(
			DEDataRecordCollection deDataRecordCollection) {

			return newBuilder(
				deDataRecordCollection
			).build();
		}

		public DEDataRecordCollectionSaveResponse build() {
			return _deDataRecordCollectionSaveResponse;
		}

		private Builder(DEDataRecordCollection deDataRecordCollection) {
			_deDataRecordCollectionSaveResponse._deDataRecordCollection =
				deDataRecordCollection;
		}

		private final DEDataRecordCollectionSaveResponse
			_deDataRecordCollectionSaveResponse =
				new DEDataRecordCollectionSaveResponse();

	}

	private DEDataRecordCollectionSaveResponse() {
	}

	private DEDataRecordCollection _deDataRecordCollection;

}