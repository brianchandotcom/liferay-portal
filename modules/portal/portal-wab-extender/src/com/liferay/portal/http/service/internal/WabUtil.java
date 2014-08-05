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

package com.liferay.portal.http.service.internal;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 */
public class WabUtil {

	public static String getWebContextName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String webContextName = headers.get("Web-ContextName");

		if (Validator.isNotNull(webContextName)) {
			return webContextName;
		}

		String webContextPath = getWebContextPath(bundle);

		if (Validator.isNotNull(webContextPath) &&
			webContextPath.startsWith(StringPool.SLASH)) {

			webContextName = webContextPath.substring(1);
		}

		return webContextName;
	}

	public static String getWebContextPath(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		return headers.get("Web-ContextPath");
	}

	public static boolean isFragmentBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

		if (fragmentHost == null) {
			return false;
		}

		return true;
	}

	public static boolean isNotActiveBundle(Bundle bundle) {
		int state = bundle.getState();

		if ((state & Bundle.ACTIVE) == Bundle.ACTIVE) {
			return false;
		}

		return true;
	}

}