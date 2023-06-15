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

package com.liferay.portal.vulcan.util;

import com.liferay.petra.string.StringPool;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Brian Wing Shun Chan
 */
public class JaxRsLinkUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static String getJaxRsLink(
		Class<?> clazz, String methodName, UriInfo uriInfo, Object... values) {

		String basePath = UriInfoUtil.getBasePath(uriInfo);

		if (basePath.endsWith(StringPool.FORWARD_SLASH)) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			clazz
		).build();

		URI methodURI = UriBuilder.fromMethod(
			clazz, methodName
		).build(
			values
		);

		return basePath + resourceURI.toString() + methodURI.toString();
	}

	public static String getJaxRsLink(
		String applicationPath, Class<?> clazz, String methodName,
		UriInfo uriInfo, Object... values) {

		return UriInfoUtil.getBaseUriBuilder(
			applicationPath, uriInfo
		).path(
			clazz
		).path(
			clazz, methodName
		).build(
			values, false
		).toString();
	}

}