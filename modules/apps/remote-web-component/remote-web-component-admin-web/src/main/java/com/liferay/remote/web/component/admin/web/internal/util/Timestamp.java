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

package com.liferay.remote.web.component.admin.web.internal.util;

import java.time.Instant;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Raymond Augé
 */
public class Timestamp {

	public static String[] append(String[] urls, Instant now) {
		return Stream.of(
			(urls != null) ? urls : _EMPTY
		).filter(
			Objects::nonNull
		).map(
			String::trim
		).filter(
			url -> !url.isEmpty()
		).map(
			url -> {
				if (url.indexOf('?') > -1) {
					return url + "&ts=" + now.toEpochMilli();
				}

				return url + "?ts=" + now.toEpochMilli();
			}
		).toArray(
			String[]::new
		);
	}

	private Timestamp() {
	}

	private static final String[] _EMPTY = new String[0];

}