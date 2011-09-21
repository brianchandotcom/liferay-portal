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

package com.liferay.portal.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.ActionHandlerManager;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward C. Han
 */
public class DefaultActionHandlerManagerImpl implements ActionHandlerManager {
	public void applyActions(
			List<MDRAction> actions, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		for (MDRAction action : actions) {
			applyAction(action, request, response);
		}
	}

	public ActionHandler getActionHandler(String actionType) {
		return _deviceActionHandlers.get(actionType);
	}

	public Collection<ActionHandler> getActionHandlers() {
		return Collections.unmodifiableCollection(
			_deviceActionHandlers.values());
	}

	public Collection<String> getActionHandlerTypes() {
		return _deviceActionHandlers.keySet();
	}

	public void registerActionHandler(ActionHandler actionHandler) {
		ActionHandler oldActionHandler = _deviceActionHandlers.put(
			actionHandler.getType(), actionHandler);

		if (oldActionHandler != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"ActionHandler with key: " + actionHandler.getType() +
					" has already been registered. Replacing with " +
					actionHandler);
			}
		}
	}

	public void setActionHandlers(Collection<ActionHandler> actionHandlers) {
		for (ActionHandler actionHandler : actionHandlers) {
			registerActionHandler(actionHandler);
		}
	}

	public ActionHandler unregisterActionHandler(String actionType) {
		return _deviceActionHandlers.remove(actionType);
	}

	protected void applyAction(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		ActionHandler actionHandler = _deviceActionHandlers.get(action.getType());

		if (actionHandler != null) {
			actionHandler.applyAction(action, request, response);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No ActionHandler registered for type: " +
					action.getType());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultActionHandlerManagerImpl.class);

	private Map<String, ActionHandler> _deviceActionHandlers =
		new HashMap<String, ActionHandler>();
}
