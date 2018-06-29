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

package com.liferay.marketplace.internal.lpkg.deployer;

import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Ryan Park
 */
@Component(immediate = true)
public class LPKGDeployerRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) throws Exception {
		bundleContext.addBundleListener(_bundleListener);

		Map<Bundle, List<Bundle>> deployedLPKGBundles =
			_lpkgDeployer.getDeployedLPKGBundles();

		for (Map.Entry<Bundle, List<Bundle>> entry :
				deployedLPKGBundles.entrySet()) {

			_register(entry.getKey());
		}
	}

	@Deactivate
	public void deactivate(BundleContext bundleContext) {
		bundleContext.removeBundleListener(_bundleListener);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.marketplace.service)(release.schema.version=2.0.2))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private void _doRegister(Bundle lpkgBundle) throws Exception {
		URL url = lpkgBundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return;
		}

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

		String[] bundleStrings = StringUtil.split(
			properties.getProperty("bundles"));

		for (String bundleString : bundleStrings) {
			String[] bundleStringParts = StringUtil.split(
				bundleString, CharPool.POUND);

			String bundleSymbolicName = bundleStringParts[0];
			String bundleVersion = bundleStringParts[1];
			String contextName = bundleStringParts[2];

			_moduleLocalService.addModule(
				app.getAppId(), bundleSymbolicName, bundleVersion, contextName);
		}

		String[] contextNames = StringUtil.split(
			properties.getProperty("context-names"));

		for (String contextName : contextNames) {
			_moduleLocalService.addModule(
				app.getAppId(), contextName, StringPool.BLANK, contextName);
		}
	}

	private void _register(Bundle lpkgBundle) {
		try {
			_doRegister(lpkgBundle);
		}
		catch (Exception e) {
			_log.error(
				"Unable to track installed app " +
					lpkgBundle.getSymbolicName() + " with Marketplace",
				e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGDeployerRegistrar.class);

	@Reference
	private AppLocalService _appLocalService;

	private final BundleListener _bundleListener = new BundleListener() {

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			if (bundleEvent.getType() == BundleEvent.STARTED) {
				_register(bundleEvent.getBundle());
			}
		}

	};

	@Reference
	private LPKGDeployer _lpkgDeployer;

	@Reference
	private ModuleLocalService _moduleLocalService;

}