/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.profile;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

/**
 * @author Hai Yu
 */
public class BaseEnterpriseDSModulePortalProfile implements PortalProfile {

	@Override
	public void activate() {
		if (!_DXP) {
			_componentContext.enableComponent(null);

			return;
		}

		BundleContext bundleContext = _componentContext.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.portal.license.enterprise.app")) {

				_componentContext.enableComponent(null);

				return;
			}
		}
	}

	@Override
	public Set<String> getPortalProfileNames() {
		return _supportedPortalProfileNames;
	}

	protected void init(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = componentContext.getBundleContext();

		Bundle bundle = bundleContext.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		_supportedPortalProfileNames = new HashSet<>();

		String liferayEnterpriseApp = headers.get("Liferay-Enterprise-App");

		if (liferayEnterpriseApp == null) {
			return;
		}

		if (!_startingBundleSymbolicNames.add(bundle.getSymbolicName())) {
			_startingBundleSymbolicNames.remove(bundle.getSymbolicName());

			return;
		}

		if (liferayEnterpriseApp.contains("dxp.only=true")) {
			_supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}
		else {
			_supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_CE);
			_supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}

		_supportedPortalProfileNames.add(bundle.getSymbolicName());
	}

	private static final boolean _DXP;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEnterpriseDSModulePortalProfile.class);

	private static final Set<String> _startingBundleSymbolicNames =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	static {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		boolean dxp = false;

		try {
			classLoader.loadClass(
				"com.liferay.portal.ee.license.LCSLicenseManager");

			dxp = true;
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(reflectiveOperationException);
			}
		}

		_DXP = dxp;
	}

	private ComponentContext _componentContext;
	private Set<String> _supportedPortalProfileNames;

}