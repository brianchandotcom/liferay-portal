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

package com.liferay.roles.admin.web.internal.group.type.contributor;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.roles.admin.group.type.contributor.GroupTypeContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = GroupTypeContributor.class)
public class OrganizationGroupTypeContributor implements GroupTypeContributor {

	@Override
	public String getClassName() {
		return Organization.class.getName();
	}

}