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

package com.liferay.site.initializer.testray.extra.java.function.util;

/**
 * @author José Abelenda
 */
public class PropsValues {

	public static final String TESTRAY_BASE_URL = PropsUtil.get(
		"testray.base.url");

	public static final String TESTRAY_BUCKET_NAME = PropsUtil.get(
		"testray.bucket.name");

	public static final String TESTRAY_PASSWORD = PropsUtil.get(
		"testray.password");

	public static final String TESTRAY_URL_API_KEY = PropsUtil.get(
		"testray.url.api.key");

	public static final String TESTRAY_USER = PropsUtil.get("testray.user");

}