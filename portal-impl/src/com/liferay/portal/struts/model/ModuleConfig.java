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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ModuleConfig {

	public void addActionForward(ActionForward actionForward) {
		_actionForwards.put(actionForward.getName(), actionForward);
	}

	public void addActionMapping(ActionMapping actionMapping) {
		_actionMappings.put(actionMapping.getPath(), actionMapping);
	}

	public ActionForward getActionForward(String name) {
		return _actionForwards.get(name);
	}

	public ActionMapping getActionMapping(String path) {
		return _actionMappings.get(path);
	}

	private final Map<String, ActionForward> _actionForwards = new HashMap<>();
	private final Map<String, ActionMapping> _actionMappings = new HashMap<>();

}