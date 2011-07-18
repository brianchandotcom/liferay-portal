/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.marketplace.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.marketplace.model.Module;
import com.liferay.portlet.marketplace.service.ModuleLocalServiceUtil;

import java.util.List;

/**
 * @author Ryan Park
 */
public class AppImpl extends AppBaseImpl {

	public AppImpl() {
	}

	public boolean isInstalled() throws PortalException, SystemException {
		List<Module> modules = ModuleLocalServiceUtil.getAppModules(getAppId());

		for (Module module : modules) {
			if (!PluginPackageUtil.isInstalled(module.getContextName())) {
				return false;
			}
		}

		return true;
	}

	public boolean isDownloaded() throws PortalException, SystemException {
		return DLStoreUtil.hasFile(
			getCompanyId(), CompanyConstants.SYSTEM, getFileName(),
			Store.DEFAULT_VERSION);
	}

	public String getFileName() {
		return "marketplace/" + getAppId() + StringPool.PERIOD + _EXTENSION;
	}

	private final String _EXTENSION = "lpkg";

}