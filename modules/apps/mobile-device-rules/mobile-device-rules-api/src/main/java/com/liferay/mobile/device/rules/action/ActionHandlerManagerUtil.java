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
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Edward Han
 * @author Máté Thurzó
 */
public class ActionHandlerManagerUtil {

	public static void applyActions(
			List<MDRAction> mdrActions, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		getActionHandlerManager().applyActions(
			mdrActions, httpServletRequest, httpServletResponse);
	}

	public static ActionHandler getActionHandler(String actionType) {
		return getActionHandlerManager().getActionHandler(actionType);
	}

	public static ActionHandlerManager getActionHandlerManager() {
		return _actionHandlerManagerUtil._getActionHandlerManager();
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

	private ActionHandlerManagerUtil() {
		Bundle bundle = FrameworkUtil.getBundle(ActionHandlerManagerUtil.class);

		_serviceTracker = ServiceTrackerFactory.open(
			bundle, ActionHandlerManager.class);
	}

	private ActionHandlerManager _getActionHandlerManager() {
		return _serviceTracker.getService();
	}

	private static final ActionHandlerManagerUtil _actionHandlerManagerUtil =
		new ActionHandlerManagerUtil();

	private final ServiceTracker<ActionHandlerManager, ActionHandlerManager>
		_serviceTracker;

}