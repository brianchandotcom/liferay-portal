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

package com.liferay.vldap.server.internal.servlet;

import com.liferay.vldap.server.internal.VLDAPServer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
@Component(service = {})
public class VLDAPConfigurator {

	@Activate
	protected void activate() throws Exception {
		_vldapServer = new VLDAPServer();

		_vldapServer.init();
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_vldapServer.destroy();
	}

	private VLDAPServer _vldapServer;

}