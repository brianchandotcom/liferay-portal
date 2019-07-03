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

package com.liferay.portal.configuration.cluster.internal.portal.profile;

import com.liferay.portal.configuration.cluster.internal.ConfigurationSynchronousConfigurationListener;
import com.liferay.portal.configuration.cluster.internal.messaging.ConfigurationClusterConfigurator;
import com.liferay.portal.configuration.cluster.internal.messaging.ConfigurationMessageListener;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	public void activate(ComponentContext componentContext) {
		Set<String> supportedPortalProfileNames = null;

		if (_clusterLink.isEnabled()) {
			supportedPortalProfileNames = new HashSet<>();

			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_CE);
			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}
		else {
			supportedPortalProfileNames = Collections.emptySet();
		}

		init(
			componentContext, supportedPortalProfileNames,
			ConfigurationClusterConfigurator.class.getName(),
			ConfigurationMessageListener.class.getName(),
			ConfigurationSynchronousConfigurationListener.class.getName());
	}

	@Reference
	private ClusterLink _clusterLink;

}