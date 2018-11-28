/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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