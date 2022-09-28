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

package com.liferay.server.scripting;

import com.liferay.portal.kernel.scripting.ScriptingException;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public interface ServerScripting {

	public void execute(
			Map<String, Object> inputObjects, String language, String script)
		throws ScriptingException;

}