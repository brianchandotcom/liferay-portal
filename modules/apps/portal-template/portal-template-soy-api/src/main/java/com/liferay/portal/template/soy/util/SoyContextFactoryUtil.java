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

package com.liferay.portal.template.soy.util;

import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class SoyContextFactoryUtil {

	public static SoyContext createSoyContext() {
		return _soyContextFactory.createSoyContext();
	}

	public static SoyContext createSoyContext(Map<String, Object> context) {
		return _soyContextFactory.createSoyContext(context);
	}

	public static SoyContextFactory getSoyContextFactory() {
		if (_soyContextFactory != null) {
			return _soyContextFactory;
		}

		throw new NullPointerException("Soy context factory is null");
	}

	public static void setSoyContextFactory(
		SoyContextFactory soyContextFactory) {

		if (_soyContextFactory != null) {
			return;
		}

		_soyContextFactory = soyContextFactory;
	}

	private static SoyContextFactory _soyContextFactory;

}