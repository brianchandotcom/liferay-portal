/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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