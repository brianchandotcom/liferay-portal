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

package com.liferay.portal.scripting.javascript.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.scripting.javascript.configuration.JavaScriptExecutorConfiguration",
	localization = "content/Language",
	name = "%javascript.executor.configuration.name"
)
public interface JavaScriptExecutorConfiguration {

	@Meta.AD(
		deflt = "com.liferay.portal.kernel.scripting.ScriptingUtil,com.liferay.portal.scripting.internal.ScriptingImpl,java.lang.System",
		required = false
	)
	public String forbiddenClassNames();

}