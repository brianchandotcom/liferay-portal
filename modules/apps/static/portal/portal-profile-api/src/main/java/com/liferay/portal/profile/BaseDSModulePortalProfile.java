/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

/**
 * @author Shuyang Zhou
 */
public class BaseDSModulePortalProfile implements PortalProfile {

	@Override
	public void activate() {
		for (String componentName : _componentNames) {
			_componentContext.enableComponent(componentName);
		}
	}

	@Override
	public Set<String> getPortalProfileNames() {
		return _supportedPortalProfileNames;
	}

	protected void init(
		ComponentContext componentContext,
		Collection<String> supportedPortalProfileNames,
		String... componentNames) {

		_componentContext = componentContext;

		_supportedPortalProfileNames = new HashSet<>(
			supportedPortalProfileNames);

		BundleContext bundleContext = componentContext.getBundleContext();

		Bundle bundle = bundleContext.getBundle();

		_supportedPortalProfileNames.add(bundle.getSymbolicName());

		_componentNames = componentNames;
	}

	private ComponentContext _componentContext;
	private String[] _componentNames;
	private Set<String> _supportedPortalProfileNames;

}