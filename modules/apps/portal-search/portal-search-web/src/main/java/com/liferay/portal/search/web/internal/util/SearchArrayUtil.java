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

package com.liferay.portal.search.web.internal.util;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public class SearchArrayUtil {

	public static Optional<String[]> maybe(String[] texts) {
		if (ArrayUtil.isEmpty(texts)) {
			return Optional.empty();
		}

		return Optional.of(texts);
	}

}