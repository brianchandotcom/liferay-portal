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

package com.liferay.portal.verify.model;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;

/**
 * @author Miguel Pastor
 */
public class RoleVerifiableModel implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return Role.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "roleId";
	}

	@Override
	public String getTableName() {
		return "Role_";
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}