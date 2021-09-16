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

package com.liferay.remote.app.web.internal.deployer;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployment;

import javax.portlet.Portlet;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryDeploymentImpl implements RemoteAppEntryDeployment {

	public RemoteAppEntryDeploymentImpl(
		ServiceRegistration<Portlet> portletServiceRegistration,
		ServiceRegistration<ConfigurationAction>
			configurationActionServiceRegistration) {

		_portletServiceRegistration = portletServiceRegistration;
		_configurationActionServiceRegistration =
			configurationActionServiceRegistration;
	}

	@Override
	public void undeploy() {
		if (_configurationActionServiceRegistration != null) {
			_configurationActionServiceRegistration.unregister();
		}

		if (_portletServiceRegistration != null) {
			_portletServiceRegistration.unregister();
		}
	}

	private final ServiceRegistration<ConfigurationAction>
		_configurationActionServiceRegistration;
	private final ServiceRegistration<Portlet> _portletServiceRegistration;

}