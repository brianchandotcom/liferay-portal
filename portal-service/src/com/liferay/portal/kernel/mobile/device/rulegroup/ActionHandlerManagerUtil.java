/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ActionHandlerManagerUtil {
	public static void applyActions(
			List<MDRAction> actions, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		_actionHandlerManager.applyActions(actions, request, response);
	}

	public static ActionHandler getActionHandler(String actionType) {
		return _actionHandlerManager.getActionHandler(actionType);
	}

	public static Collection<ActionHandler> getActionHandlers() {
		return _actionHandlerManager.getActionHandlers();
	}

	public static Collection<String> getActionHandlerTypes() {
		return _actionHandlerManager.getActionHandlerTypes();
	}

	public static void registerActionHandler(ActionHandler actionHandler) {
		_actionHandlerManager.registerActionHandler(actionHandler);
	}

	public void setActionHandlerManager(
			ActionHandlerManager actionHandlerManager) {

		_actionHandlerManager = actionHandlerManager;
	}

	public static ActionHandler unregisterActionHandler(String actionType) {
		return _actionHandlerManager.unregisterActionHandler(actionType);
	}

	private static ActionHandlerManager _actionHandlerManager;
}
