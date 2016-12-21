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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.Document;

/**
 * @author André de Oliveira
 */
public class DocumentCreationHelpers {

	public static DocumentCreationHelper singleGeoLocation(
		final String fieldName, final double latitude, final double longitude) {

		return new DocumentCreationHelper() {

			@Override
			public void populate(Document document) {
				document.addGeoLocation(fieldName, latitude, longitude);
			}

		};
	}

	public static DocumentCreationHelper singleKeyword(
		final String field, final String value) {

		return new DocumentCreationHelper() {

			@Override
			public void populate(Document document) {
				document.addKeyword(field, value);
			}

		};
	}

	public static DocumentCreationHelper singleNumberSortable(
		final String field, final int value) {

		return new DocumentCreationHelper() {

			@Override
			public void populate(Document document) {
				document.addNumberSortable(field, value);
			}

		};
	}

	public static DocumentCreationHelper singleText(
		final String field, final String... values) {

		return new DocumentCreationHelper() {

			@Override
			public void populate(Document document) {
				document.addText(field, values);
			}

		};
	}

}