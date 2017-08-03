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

import com.liferay.portal.cache.multiple.internal.ClusterLinkPortalCacheReplicatorFactory;
import com.liferay.portal.cache.multiple.internal.bootstrap.ClusterLinkBootstrapLoaderHelperUtil;
import com.liferay.portal.cache.multiple.internal.bootstrap.ClusterLinkPortalCacheBootstrapLoaderFactory;
import com.liferay.portal.cache.multiple.internal.cluster.link.ClusterLinkPortalCacheClusterChannelFactory;
import com.liferay.portal.cache.multiple.internal.cluster.link.PortalCacheClusterLink;
import com.liferay.portal.cache.multiple.internal.cluster.link.messaging.ClusterLinkMessagingConfigurator;
import com.liferay.portal.cache.multiple.internal.cluster.link.messaging.ClusterLinkPortalCacheClusterListener;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;

import java.util.Collections;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	public void activate(ComponentContext componentContext) {
		init(
			componentContext,
			Collections.singleton(PortalProfile.PORTAL_PROFILE_NAME_DXP),
			ClusterLinkBootstrapLoaderHelperUtil.class.getName(),
			ClusterLinkMessagingConfigurator.class.getName(),
			ClusterLinkPortalCacheBootstrapLoaderFactory.class.getName(),
			ClusterLinkPortalCacheClusterChannelFactory.class.getName(),
			ClusterLinkPortalCacheClusterListener.class.getName(),
			ClusterLinkPortalCacheReplicatorFactory.class.getName(),
			PortalCacheClusterLink.class.getName());
	}

}