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

package com.liferay.portal.profile.activator.internal;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.profile.activator.ProfileActivator;

import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class ProfileActivatorImpl implements ProfileActivator {

	@Activate
	public void activate(BundleContext bundleContext) {
		String[] portalProfiles = StringUtil.split(
			bundleContext.getProperty(PORTAL_PROFILES));

		if (portalProfiles.length == 0) {
			String name = ReleaseInfo.getName();

			if (name.contains("Community")) {
				_portalProfiles = new String[] {CE_PROFILE};
			}
			else {
				_portalProfiles = new String[] {EE_PROFILE};
			}
		}
		else {
			_portalProfiles = portalProfiles;
		}
	}

	@Override
	public void activateByProfiles(
		ComponentContext componentContext, Set<String> profiles,
		String... componentNames) {

		BundleContext bundleContext = componentContext.getBundleContext();

		Bundle bundle = bundleContext.getBundle();

		String symbolicName = bundle.getSymbolicName();

		for (String portalProfile : _portalProfiles) {
			if (profiles.contains(portalProfile) ||
				symbolicName.equals(portalProfile)) {

				for (String componentName : componentNames) {
					componentContext.enableComponent(componentName);
				}

				return;
			}
		}
	}

	private String[] _portalProfiles;

}