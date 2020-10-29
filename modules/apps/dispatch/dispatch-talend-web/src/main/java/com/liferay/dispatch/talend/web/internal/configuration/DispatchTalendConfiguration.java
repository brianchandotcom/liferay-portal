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

package com.liferay.dispatch.talend.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(category = "dispatch")
@Meta.OCD(
	id = "com.liferay.dispatch.talend.web.internal.configuration.DispatchTalendConfiguration",
	localization = "content/Language",
	name = "dispatch-talend-configuration-name"
)
public interface DispatchTalendConfiguration {

	@Meta.AD(
		deflt = ".zip,.rar,.jar,.properties", name = "file-extensions",
		required = false
	)
	public String[] fileExtensions();

	@Meta.AD(deflt = "50242880", name = "file-max-size", required = false)
	public long fileMaxSize();

}