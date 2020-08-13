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

package com.liferay.headless.common.spi.service.context;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class ServiceContextRequestUtil {

	public static ServiceContext createServiceContext(
		long groupId, HttpServletRequest httpServletRequest,
		String viewableBy) {

		return createServiceContext(
			new Long[0], new String[0], null, groupId, httpServletRequest,
			viewableBy);
	}

	public static ServiceContext createServiceContext(
		Long[] assetCategoryIds, String[] assetTagNames,
		Map<String, Serializable> expandoBridgeAttributes, Long groupId,
		HttpServletRequest httpServletRequest, String viewableBy) {

		ServiceContext serviceContext = new ServiceContext();

		if (httpServletRequest != null) {
			Map<String, String> headers = new HashMap<>();

			Enumeration<String> enumeration =
				httpServletRequest.getHeaderNames();

			while (enumeration.hasMoreElements()) {
				String header = enumeration.nextElement();

				String value = httpServletRequest.getHeader(header);

				headers.put(header, value);
			}

			serviceContext.setHeaders(headers);
		}

		if (StringUtil.equalsIgnoreCase(viewableBy, "anyone")) {
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAddGroupPermissions(true);
		}
		else if (StringUtil.equalsIgnoreCase(viewableBy, "members")) {
			serviceContext.setAddGuestPermissions(false);
			serviceContext.setAddGroupPermissions(true);
		}
		else {
			serviceContext.setAddGuestPermissions(false);
			serviceContext.setAddGroupPermissions(false);
		}

		if (assetCategoryIds != null) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toArray(assetCategoryIds));
		}

		if (assetTagNames != null) {
			serviceContext.setAssetTagNames(assetTagNames);
		}

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		if (groupId != null) {
			serviceContext.setScopeGroupId(groupId);
		}

		return serviceContext;
	}

	public static ServiceContext createServiceContext(
		Map<String, Serializable> expandoBridgeAttributes, long groupId,
		HttpServletRequest httpServletRequest, String viewableBy) {

		return createServiceContext(
			new Long[0], new String[0], expandoBridgeAttributes, groupId,
			httpServletRequest, viewableBy);
	}

}