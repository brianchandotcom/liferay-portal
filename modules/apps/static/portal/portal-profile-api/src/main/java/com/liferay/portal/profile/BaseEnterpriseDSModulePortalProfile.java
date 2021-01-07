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

package com.liferay.portal.profile;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

	static {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		boolean dxp = false;

		try {
			classLoader.loadClass(
				"com.liferay.portal.ee.license.LCSLicenseManager");

			dxp = true;
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
		}

		_DXP = dxp;
	}

	private ComponentContext _componentContext;
	private Set<String> _supportedPortalProfileNames;

}