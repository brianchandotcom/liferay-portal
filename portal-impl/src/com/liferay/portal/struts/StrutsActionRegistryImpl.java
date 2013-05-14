/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts.action.Action;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class StrutsActionRegistryImpl implements StrutsActionRegistry {

	@Override
	public Action getAction(String path) {
		Action action = _actions.get(path);

		if (action != null) {
			return action;
		}

		for (Map.Entry<String, Action> entry : _actions.entrySet()) {
			if (path.startsWith(entry.getKey())) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public Map<String, Action> getActions() {
		return _actions;
	}

	@Override
	public void register(String path, StrutsAction strutsAction) {
		Action action = new ActionAdapter(strutsAction);

		_actions.put(path, action);
	}

	@Override
	public void register(String path, StrutsPortletAction strutsPortletAction) {
		Action action = new PortletActionAdapter(strutsPortletAction);

		_actions.put(path, action);
	}

	@Override
	public void unregister(String path) {
		_actions.remove(path);
	}

	private static Map<String, Action> _actions =
		new ConcurrentHashMap<String, Action>();

}