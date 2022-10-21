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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = SystemObjectDefinitionMetadata.class)
public class UserSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _userLocalService.deleteUser((User)baseModel);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _userLocalService.getUserByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public String getExternalReferenceCode(long primaryKey)
		throws PortalException {

		User user = _userLocalService.getUser(primaryKey);

		return user.getExternalReferenceCode();
	}

	@Override
	public String getJaxRsApplicationName() {
		return "Liferay.Headless.Admin.User";
	}

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
				"Text", "String", "email-address", "emailAddress", true, true),
			createObjectField(
				"Text", "firstName", "String", "first-name", "givenName", true,
				true),
			createObjectField(
				"Text", "middleName", "String", "middle-name", "additionalName",
				false, true),
			createObjectField(
				"Text", "uuid_", "String", "uuid", "uuid", false, true));
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

	@Reference
	private UserLocalService _userLocalService;

}