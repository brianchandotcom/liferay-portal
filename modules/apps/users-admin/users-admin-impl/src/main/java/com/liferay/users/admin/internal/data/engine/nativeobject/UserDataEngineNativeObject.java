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

package com.liferay.users.admin.internal.data.engine.nativeobject;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.DataEngineNativeObjectField;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(service = DataEngineNativeObject.class)
public class UserDataEngineNativeObject implements DataEngineNativeObject {

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public List<DataEngineNativeObjectField> getDataEngineNativeObjectFields() {
		return ListUtil.fromArray(
			new DataEngineNativeObjectField(
				UserTable.INSTANCE.emailAddress, "Email Address", null),
			new DataEngineNativeObjectField(
				UserTable.INSTANCE.firstName, "First Name", null),
			new DataEngineNativeObjectField(
				UserTable.INSTANCE.jobTitle, "Job Title", null),
			new DataEngineNativeObjectField(
				UserTable.INSTANCE.lastName, "Last Name", null),
			new DataEngineNativeObjectField(
				UserTable.INSTANCE.middleName, "Middle Name", null));
	}

	@Override
	public String getName() {
		return "User";
	}

}