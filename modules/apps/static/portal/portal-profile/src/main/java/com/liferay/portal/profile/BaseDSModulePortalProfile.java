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
		Set<String> supportedPortalProfileNames, String... componentNames) {

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