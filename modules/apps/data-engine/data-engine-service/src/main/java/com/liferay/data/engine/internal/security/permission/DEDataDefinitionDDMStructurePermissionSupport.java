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

package com.liferay.data.engine.internal.security.permission;

import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.data.engine.model.DEDataDefinition",
	service = {
		DDMStructurePermissionSupport.class,
		DEDataDefinitionDDMStructurePermissionSupport.class
	}
)
public class DEDataDefinitionDDMStructurePermissionSupport
	implements DDMStructurePermissionSupport {

	@Override
	public String getResourceName() {
		return "com.liferay.data.engine";
	}

}