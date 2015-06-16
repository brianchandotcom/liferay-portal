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

package com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommand;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestPortlet.PORTLET_NAME,
		"mvc.command.name=" + TestMVCActionCommand2.TEST_ACTION_COMMAND_NAME
	},
	service = MVCActionCommand.class
)
public class TestMVCActionCommand2 implements MVCActionCommand {

	public static final String TEST_ACTION_COMMAND_ATTRIBUTE =
		"TEST_ACTION_COMMAND_ATTRIBUTE";

	public static final String TEST_ACTION_COMMAND_NAME =
		"TEST_ACTION_COMMAND_NAME";

	@Override
	public boolean processCommand(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		portletRequest.setAttribute(
			TEST_ACTION_COMMAND_ATTRIBUTE, TEST_ACTION_COMMAND_ATTRIBUTE);

		return true;
	}

}