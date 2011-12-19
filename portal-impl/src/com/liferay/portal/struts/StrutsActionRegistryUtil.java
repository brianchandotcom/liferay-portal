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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;

import java.util.Map;

import org.apache.struts.action.Action;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class StrutsActionRegistryUtil {

	public static Action getAction(String path) {
		return getStrutsActionRegistry().getAction(path);
	}

	public static Map<String, Action> getActions() {
		return getStrutsActionRegistry().getActions();
	}

	public static StrutsActionRegistry getStrutsActionRegistry() {
		return _strutsActionRegistry;
	}

	public static void register(String path, StrutsAction strutsAction) {
		getStrutsActionRegistry().register(path, strutsAction);
	}

	public static void register(
		String path, StrutsPortletAction strutsPortletAction) {

		getStrutsActionRegistry().register(path, strutsPortletAction);
	}

	public static void unregister(String path) {
		getStrutsActionRegistry().unregister(path);
	}

	public void setStrutsActionRegistry(
		StrutsActionRegistry strutsActionRegistry) {

		_strutsActionRegistry = strutsActionRegistry;
	}

	private static StrutsActionRegistry _strutsActionRegistry;

}