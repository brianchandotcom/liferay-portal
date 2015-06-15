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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * @author Michael C. Han
 * @author Sergio González
 */
public interface ActionCommand {

	public static final String ACTION_COMMAND_POSTFIX = "ActionCommand";

	public static final String ACTION_PACKAGE_NAME = "action.package.prefix";

	public static final ActionCommand EMPTY = new ActionCommand() {

		@Override
		public boolean processCommand(
			ActionRequest actionRequest, ActionResponse actionResponse) {

			return false;
		}

	};

	public boolean processCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException;

}