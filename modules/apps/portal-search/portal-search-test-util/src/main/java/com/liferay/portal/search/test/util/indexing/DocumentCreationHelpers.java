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

import java.util.Date;

/**
 * @author André de Oliveira
 */
public class DocumentCreationHelpers {

	public static DocumentCreationHelper singleDate(
		String fieldName, Date value) {

		return document -> document.addDate(fieldName, value);
	}

	public static DocumentCreationHelper singleGeoLocation(
		String fieldName, double latitude, double longitude) {

		return document -> document.addGeoLocation(
			fieldName, latitude, longitude);
	}

	public static DocumentCreationHelper singleKeyword(
		String fieldName, String value) {

		return document -> document.addKeyword(fieldName, value);
	}

	public static DocumentCreationHelper singleNumber(
		String fieldName, double value) {

		return document -> document.addNumber(fieldName, value);
	}

	public static DocumentCreationHelper singleNumberSortable(
		String fieldName, double value) {

		return document -> document.addNumberSortable(fieldName, value);
	}

	public static DocumentCreationHelper singleText(
		String fieldName, String... values) {

		return document -> document.addText(fieldName, values);
	}

	public static DocumentCreationHelper twoKeywords(
		String fieldName1, String value1, String fieldName2, String value2) {

		return document -> {
			document.addKeyword(fieldName1, value1);
			document.addKeyword(fieldName2, value2);
		};
	}

}