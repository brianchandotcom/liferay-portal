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

package com.liferay.portal.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.PortalException;
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

	@Override
	public void applyActions(
			List<MDRAction> mdrActions, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		for (MDRAction mdrAction : mdrActions) {
			applyAction(mdrAction, request, response);
		}
	}

	@Override
	public ActionHandler getActionHandler(String actionType) {
		return _deviceActionHandlers.get(actionType);
	}

	@Override
	public Collection<ActionHandler> getActionHandlers() {
		return Collections.unmodifiableCollection(
			_deviceActionHandlers.values());
	}

	@Override
	public Collection<String> getActionHandlerTypes() {
		return _deviceActionHandlers.keySet();
	}

	@Override
	public void registerActionHandler(ActionHandler actionHandler) {
		ActionHandler oldActionHandler = _deviceActionHandlers.put(
			actionHandler.getType(), actionHandler);

		if ((oldActionHandler != null) && _log.isWarnEnabled()) {
			_log.warn(
				"Replacing existing action handler type " +
					actionHandler.getType());
		}
	}

	public void setActionHandlers(Collection<ActionHandler> actionHandlers) {
		for (ActionHandler actionHandler : actionHandlers) {
			registerActionHandler(actionHandler);
		}
	}

	@Override
	public ActionHandler unregisterActionHandler(String actionType) {
		return _deviceActionHandlers.remove(actionType);
	}

	protected void applyAction(
			MDRAction mdrAction, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		ActionHandler actionHandler = _deviceActionHandlers.get(
			mdrAction.getType());

		if (actionHandler != null) {
			actionHandler.applyAction(mdrAction, request, response);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"No action handler registered for type " + mdrAction.getType());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultActionHandlerManagerImpl.class);

	private final Map<String, ActionHandler> _deviceActionHandlers =
		new HashMap<String, ActionHandler>();

}