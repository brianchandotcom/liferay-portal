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

package com.liferay.asset.kernel.util;

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author     Julio Camarero
 * @deprecated As of 7.0.0
 */
@Deprecated
public abstract class BaseAssetEntryQueryProcessor
	implements AssetEntryQueryProcessor {

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getTitle(Locale locale) {
		return StringPool.BLANK;
	}

	/**
	 * @throws     IOException
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {
	}

}