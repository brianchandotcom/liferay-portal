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

import java.io.Serializable;

import java.util.Map;

/**
 * @author Víctor Galán
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class ServiceContextUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		long groupId, String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			new Long[0], new String[0], null, groupId, null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Long[] assetCategoryIds, String[] assetTagNames, Long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			assetCategoryIds, assetTagNames, null, groupId, null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Long[] assetCategoryIds, String[] assetTagNames,
		Map<String, Serializable> expandoBridgeAttributes, Long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			assetCategoryIds, assetTagNames, expandoBridgeAttributes, groupId,
			null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Map<String, Serializable> expandoBridgeAttributes, long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			new Long[0], new String[0], expandoBridgeAttributes, groupId, null,
			viewableBy);
	}

}