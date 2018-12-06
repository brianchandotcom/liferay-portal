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

package com.liferay.portal.struts.model;

import com.liferay.portal.struts.Action;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ActionMapping {

	public ActionMapping(
		ModuleConfig moduleConfig, String forward, String path, Action action) {

		_moduleConfig = moduleConfig;
		_forward = forward;
		_path = path;
		_action = action;
	}

	public void addActionForward(ActionForward actionForward) {
		_actionForwards.put(actionForward.getName(), actionForward);
	}

	public Action getAction() {
		return _action;
	}

	public ActionForward getActionForward(String name) {
		ActionForward actionForward = _actionForwards.get(name);

		if (actionForward != null) {
			return actionForward;
		}

		return _moduleConfig.getActionForward(name);
	}

	public String getForward() {
		return _forward;
	}

	public String getPath() {
		return _path;
	}

	private final Action _action;
	private final Map<String, ActionForward> _actionForwards = new HashMap<>();
	private final String _forward;
	private final ModuleConfig _moduleConfig;
	private final String _path;

}