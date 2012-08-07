/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.image;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;
import java.util.concurrent.Future;

/**
 * The GhostScript utility class.
 *
 * @author Ivica Cardic
 */
public class GhostScriptUtil {

	/**
	 * Executes the <code>gs</code> command in GhostScript.
	 *
	 * @param  arguments the command arguments being passed to <code>gs
	 *         </code>
	 * @throws Exception if an unexpected error occurred while executing command
	 */
	public static Future<?> convert(List<String> arguments) throws Exception {
		return _ghostScript.convert(arguments);
	}

	public static boolean isEnabled() {
		return _ghostScript.isEnabled();
	}

	public static void reset() {
		_ghostScript.reset();
	}

	public void setGhostScript(GhostScript ghostScript) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ghostScript = ghostScript;
	}

	private static GhostScript _ghostScript;

}