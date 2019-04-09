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

package com.liferay.connected.app.web.internal;

import com.liferay.connected.app.ConnectedApp;
import com.liferay.connected.app.ConnectedAppManager;
import com.liferay.connected.app.ConnectedAppProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ConnectedAppManager.class)
public class ConnectedAppManagerImpl implements ConnectedAppManager {

	@Override
	public ConnectedApp getConnectedApp(User user, String key) {
		for (ConnectedApp connectedApp : getConnectedApps(user)) {
			if (key.equals(connectedApp.getKey())) {
				return connectedApp;
			}
		}

		return null;
	}

	@Override
	public List<ConnectedApp> getConnectedApps(User user) {
		List<ConnectedApp> connectedApps = new ArrayList<>();

		for (ConnectedAppProvider connectedAppProvider : _serviceTrackerList) {
			try {
				ConnectedApp connectedApp =
					connectedAppProvider.getConnectedApp(user);

				if (connectedApp != null) {
					connectedApps.add(connectedApp);
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return connectedApps;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, ConnectedAppProvider.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConnectedAppManagerImpl.class);

	private ServiceTrackerList<ConnectedAppProvider, ConnectedAppProvider>
		_serviceTrackerList;

}