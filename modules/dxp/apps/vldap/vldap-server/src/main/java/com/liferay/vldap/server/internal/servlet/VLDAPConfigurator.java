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

package com.liferay.vldap.server.internal.servlet;

import com.liferay.vldap.server.internal.VLDAPServer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
@Component
public class VLDAPConfigurator {

	@Activate
	public void activate() throws Exception {
		_vldapServer = new VLDAPServer();

		_vldapServer.init();
	}

	@Deactivate
	public void deactivate() throws Exception {
		_vldapServer.destroy();
	}

	private VLDAPServer _vldapServer;

}