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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=1",
		"javax.portlet.name=" + TestMVCActionCommand.TEST_PORTLET_ID,
		"mvc.command.name=" + TestMVCActionCommand.TEST_MVC_COMMAND_NAME,
		"portlet.add.default.resource.check.whitelist.mvc.action=1",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = MVCActionCommand.class
)
public class TestMVCActionCommand implements MVCActionCommand {

	public static final String TEST_MVC_COMMAND_NAME =
		"TEST_MVC_ACTION_COMMAND_NAME";

	public static final String TEST_PORTLET_ID = PortletKeys.PORTAL;

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		return false;
	}

}