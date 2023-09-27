/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model.impl;

import com.liferay.marketplace.internal.bundle.BundleManagerUtil;
import com.liferay.marketplace.model.Module;
import com.liferay.marketplace.service.AppLocalServiceUtil;
import com.liferay.marketplace.service.ModuleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
public class AppImpl extends AppBaseImpl {

	@Override
	public String[] addContextName(String contextName) {
		if (_contextNames == null) {
			_contextNames = new String[] {contextName};
		}
		else {
			_contextNames = ArrayUtil.append(_contextNames, contextName);
		}

		return _contextNames;
	}

	@Override
	public String[] getContextNames() {
		if (_contextNames != null) {
			return _contextNames;
		}

		List<Module> modules = ModuleLocalServiceUtil.getModules(getAppId());

		List<String> contextNames = new ArrayList<>(modules.size());

		for (Module module : modules) {
			if (Validator.isNull(module.getContextName())) {
				continue;
			}

			contextNames.add(module.getContextName());
		}

		_contextNames = contextNames.toArray(new String[0]);

		return _contextNames;
	}

	@Override
	public String getFileDir() {
		return _DIR_NAME;
	}

	@Override
	public String getFileName() {
		return getAppId() + StringPool.PERIOD + _EXTENSION;
	}

	@Override
	public String getFilePath() {
		return getFileDir() + StringPool.SLASH + getFileName();
	}

	@Override
	public boolean isDownloaded() throws PortalException {
		return AppLocalServiceUtil.isDownloaded(this);
	}

	@Override
	public boolean isInstalled() {
		List<Module> modules = ModuleLocalServiceUtil.getModules(getAppId());

		if (modules.isEmpty()) {
			return false;
		}

		for (Module module : modules) {
			if (Validator.isNotNull(module.getBundleSymbolicName())) {
				if (!BundleManagerUtil.isInstalled(
						module.getBundleSymbolicName(),
						module.getBundleVersion())) {

					return false;
				}
			}
			else if (Validator.isNotNull(module.getContextName())) {
				if (!PluginPackageUtil.isInstalled(module.getContextName())) {
					return false;
				}
			}
		}

		return true;
	}

	private static final String _DIR_NAME = "marketplace";

	private static final String _EXTENSION = "lpkg";

	private String[] _contextNames;

}