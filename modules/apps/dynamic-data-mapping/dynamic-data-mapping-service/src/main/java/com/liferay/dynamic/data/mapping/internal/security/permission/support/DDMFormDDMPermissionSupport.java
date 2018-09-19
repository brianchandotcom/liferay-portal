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

package com.liferay.dynamic.data.mapping.internal.security.permission.support;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"default.model.resource.name=true",
		"model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance"
	},
	service = DDMStructurePermissionSupport.class
)
public class DDMFormDDMPermissionSupport
	implements DDMStructurePermissionSupport {

	@Override
	public String getResourceName() {
		return DDMConstants.RESOURCE_NAME;
	}

}