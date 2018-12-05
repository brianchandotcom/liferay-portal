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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public class StrutsActionAdapter extends BaseStrutsAction {

	public StrutsActionAdapter(Action action, ActionMapping actionMapping) {
		_action = action;
		_actionMapping = actionMapping;
	}

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			PortalClassLoaderUtil.getClassLoader());

		try {
			ActionForward actionForward = _action.execute(
				_actionMapping, request, response);

			if (actionForward == null) {
				return null;
			}

			return actionForward.getPath();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final Action _action;
	private final ActionMapping _actionMapping;

}