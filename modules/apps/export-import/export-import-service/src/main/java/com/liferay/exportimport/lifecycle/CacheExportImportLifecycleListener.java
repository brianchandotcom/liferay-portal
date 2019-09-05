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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.BaseExportImportLifecycleListener;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

/**
 * @author     Máté Thurzó
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class CacheExportImportLifecycleListener
	extends BaseExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	protected void clearCache() {
		CacheUtil.clearCache();
		PermissionCacheUtil.clearCache();
	}

	@Override
	protected void onLayoutImportProcessFinished(
		PortletDataContext portletDataContext) {

		clearCache();
	}

	@Override
	protected void onPortletImportProcessFinished(
		PortletDataContext portletDataContext) {

		clearCache();
	}

}