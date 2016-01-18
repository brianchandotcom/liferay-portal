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

package com.liferay.portal.security.auth.bundle.authtokenwhitelistutil;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestMVCResourceCommand.TEST_PORTLET_ID,
		"mvc.command.name=" + TestMVCResourceCommand.TEST_MVC_COMMAND_NAME,
		"portlet.add.default.resource.check.whitelist.mvc.action=1",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = MVCResourceCommand.class
)
public class TestMVCResourceCommand implements MVCResourceCommand {

	public static final String TEST_MVC_COMMAND_NAME =
		"TEST_MVC_RESOURCE_COMMAND_NAME";

	public static final String TEST_PORTLET_ID = PortletKeys.PORTAL;

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		return false;
	}

}