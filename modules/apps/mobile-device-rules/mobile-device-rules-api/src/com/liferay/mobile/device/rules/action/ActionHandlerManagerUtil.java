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

package com.liferay.mobile.device.rules.action;

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ActionHandlerManagerUtil {

	public static void applyActions(
			List<MDRAction> mdrActions, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		getActionHandlerManager().applyActions(mdrActions, request, response);
	}

	public static ActionHandler getActionHandler(String actionType) {
		return getActionHandlerManager().getActionHandler(actionType);
	}

	public static ActionHandlerManager getActionHandlerManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			ActionHandlerManagerUtil.class);

		return _instance._serviceTracker.getService();
	}

	public static Collection<ActionHandler> getActionHandlers() {
		return getActionHandlerManager().getActionHandlers();
	}

	public static Collection<String> getActionHandlerTypes() {
		return getActionHandlerManager().getActionHandlerTypes();
	}

	public static void registerActionHandler(ActionHandler actionHandler) {
		getActionHandlerManager().registerActionHandler(actionHandler);
	}

	public static ActionHandler unregisterActionHandler(String actionType) {
		return getActionHandlerManager().unregisterActionHandler(actionType);
	}

	public ActionHandlerManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ActionHandlerManager.class);

		_serviceTracker.open();
	}

	private static final ActionHandlerManagerUtil _instance =
		new ActionHandlerManagerUtil();

	private final ServiceTracker<ActionHandlerManager, ActionHandlerManager>
		_serviceTracker;

}