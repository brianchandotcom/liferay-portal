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

package com.liferay.marketplace.lpkg.deployer;

import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.marketplace.util.ContextUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.net.URL;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class LPKGDeployerRegistrar {

	@Activate
	public void activate() throws Exception {
		Map<Bundle, List<Bundle>> deployedLPKGBundles =
			_lpkgDeployer.getDeployedLPKGBundles();

		for (Map.Entry<Bundle, List<Bundle>> entry :
				deployedLPKGBundles.entrySet()) {

			_register(entry.getKey(), entry.getValue());
		}
	}

	private void _register(Bundle lpkgBundle, List<Bundle> bundles)
		throws Exception {

		URL url = lpkgBundle.getEntry("liferay-marketplace.properties");

		Properties properties = PropertiesUtil.load(
			url.openStream(), StringPool.ISO_8859_1);

		long remoteAppId = GetterUtil.getLong(
			properties.getProperty("remote-app-id"));
		String version = properties.getProperty("version");

		if ((remoteAppId <= 0) || Validator.isNull(version)) {
			return;
		}

		String title = properties.getProperty("title");
		String description = properties.getProperty("description");
		String category = properties.getProperty("category");
		String iconURL = properties.getProperty("icon-url");
		boolean required = GetterUtil.getBoolean(
			properties.getProperty("required"));

		App app = _appLocalService.updateApp(
			0, remoteAppId, title, description, category, iconURL, version,
			required, null);

		_moduleLocalService.deleteModules(app.getAppId());

		if (!bundles.isEmpty()) {
			for (Bundle bundle : bundles) {
				Dictionary<String, String> headers = bundle.getHeaders();

				String contextName = ContextUtil.getContextName(
					GetterUtil.getString(headers.get("Web-ContextPath")));

				_moduleLocalService.addModule(
					app.getUserId(), app.getAppId(), bundle.getSymbolicName(),
					String.valueOf(bundle.getVersion()), contextName);
			}
		}
		else {
			String[] bundleStrings = StringUtil.split(
				properties.getProperty("bundles"));

			for (String bundleString : bundleStrings) {
				String[] bundleStringParts = StringUtil.split(
					bundleString, CharPool.POUND);

				String bundleSymbolicName = bundleStringParts[0];
				String bundleVersion = bundleStringParts[1];
				String contextName = bundleStringParts[2];

				_moduleLocalService.addModule(
					0, app.getAppId(), bundleSymbolicName, bundleVersion,
					contextName);
			}

			String[] contextNames = StringUtil.split(
				properties.getProperty("context-names"));

			for (String contextName : contextNames) {
				_moduleLocalService.addModule(
					0, app.getAppId(), contextName, StringPool.BLANK,
					contextName);
			}
		}
	}

	@Reference
	private AppLocalService _appLocalService;

	@Reference
	private LPKGDeployer _lpkgDeployer;

	@Reference
	private ModuleLocalService _moduleLocalService;

}