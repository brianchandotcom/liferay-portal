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

package com.liferay.object.internal.system;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = SystemObjectDefinitionMetadata.class)
public class UserSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("user");
	}

	@Override
	public Class<?> getModelClass() {
		return User.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField(
				"Text", "String", "email-address", "emailAddress", true),
			createObjectField(
				"Text", "String", "first-name", "firstName", true),
			createObjectField(
				"Text", "String", "middle-name", "middleName", false),
			createObjectField(
				"Text", "uuid_", "String", "uuid", "uuid", false));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("users");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return UserTable.INSTANCE.userId;
	}

	@Override
	public String getRESTContextPath() {
		return "headless-admin-user/v1.0/user-accounts";
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return UserTable.INSTANCE;
	}

	@Override
	public int getVersion() {
		return 1;
	}

}