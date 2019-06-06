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

package com.liferay.portal.cache.multiple.internal.portal.profile;

import com.liferay.portal.cache.PortalCacheBootstrapLoaderFactory;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.cache.multiple.internal.ClusterLinkPortalCacheReplicatorFactory;
import com.liferay.portal.cache.multiple.internal.PortalCacheManagerUtil;
import com.liferay.portal.cache.multiple.internal.bootstrap.ClusterLinkBootstrapLoaderHelperUtil;
import com.liferay.portal.cache.multiple.internal.bootstrap.ClusterLinkPortalCacheBootstrapLoaderFactory;
import com.liferay.portal.cache.multiple.internal.cluster.link.ClusterLinkPortalCacheClusterChannelFactory;
import com.liferay.portal.cache.multiple.internal.cluster.link.PortalCacheClusterLink;
import com.liferay.portal.cache.multiple.internal.cluster.link.messaging.ClusterLinkMessagingConfigurator;
import com.liferay.portal.cache.multiple.internal.cluster.link.messaging.ClusterLinkPortalCacheClusterListener;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
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

			BundleContext bundleContext = componentContext.getBundleContext();

			bundleContext.registerService(
				PortalCacheBootstrapLoaderFactory.class,
				ProxyFactory.newDummyInstance(
					PortalCacheBootstrapLoaderFactory.class),
				new HashMapDictionary<>());

			bundleContext.registerService(
				PortalCacheReplicatorFactory.class,
				ProxyFactory.newDummyInstance(
					PortalCacheReplicatorFactory.class),
				new HashMapDictionary<>());
		}

		init(
			componentContext, supportedPortalProfileNames,
			ClusterLinkBootstrapLoaderHelperUtil.class.getName(),
			ClusterLinkMessagingConfigurator.class.getName(),
			ClusterLinkPortalCacheBootstrapLoaderFactory.class.getName(),
			ClusterLinkPortalCacheClusterChannelFactory.class.getName(),
			ClusterLinkPortalCacheClusterListener.class.getName(),
			ClusterLinkPortalCacheReplicatorFactory.class.getName(),
			PortalCacheClusterLink.class.getName(),
			PortalCacheManagerUtil.class.getName());
	}

	@Reference
	private ClusterLink _clusterLink;

}