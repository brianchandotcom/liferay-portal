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

package com.liferay.portal.cluster.multiple.internal.portal.profile;

import com.liferay.portal.cluster.multiple.internal.ClusterExecutorImpl;
import com.liferay.portal.cluster.multiple.internal.ClusterLinkImpl;
import com.liferay.portal.cluster.multiple.internal.ClusterMasterExecutorImpl;
import com.liferay.portal.cluster.multiple.internal.ClusterMasterTokenTransitionListenerTracker;
import com.liferay.portal.cluster.multiple.internal.jgroups.JGroupsClusterChannelFactory;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
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
	protected void activate(ComponentContext componentContext) {
		Set<String> supportedPortalProfileNames = null;

		if (GetterUtil.getBoolean(_props.get(PropsKeys.CLUSTER_LINK_ENABLED))) {
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
				ClusterLink.class,
				ProxyFactory.newDummyInstance(ClusterLink.class),
				new HashMapDictionary<>());

			bundleContext.registerService(
				ClusterExecutor.class,
				ProxyFactory.newDummyInstance(ClusterExecutor.class),
				new HashMapDictionary<>());

			bundleContext.registerService(
				ClusterMasterExecutor.class,
				new ClusterMasterExecutor() {

					@Override
					public void addClusterMasterTokenTransitionListener(
						ClusterMasterTokenTransitionListener
							clusterMasterTokenAcquisitionListener) {
					}

					@Override
					public <T> NoticeableFuture<T> executeOnMaster(
						MethodHandler methodHandler) {

						return null;
					}

					@Override
					public boolean isEnabled() {
						return false;
					}

					@Override
					public boolean isMaster() {
						return true;
					}

					@Override
					public void removeClusterMasterTokenTransitionListener(
						ClusterMasterTokenTransitionListener
							clusterMasterTokenAcquisitionListener) {
					}

				},
				new HashMapDictionary<>());
		}

		init(
			componentContext, supportedPortalProfileNames,
			JGroupsClusterChannelFactory.class.getName(),
			ClusterExecutorImpl.class.getName(),
			ClusterLinkImpl.class.getName(),
			ClusterMasterExecutorImpl.class.getName(),
			ClusterMasterTokenTransitionListenerTracker.class.getName());
	}

	@Reference
	private Props _props;

}