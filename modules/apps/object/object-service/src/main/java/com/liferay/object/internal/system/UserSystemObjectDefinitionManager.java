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

import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

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
@Component(service = SystemObjectDefinitionManager.class)
public class UserSystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		UserAccountResource userAccountResource = _buildUserAccountResource(
			user);

		UserAccount userAccount = userAccountResource.postUserAccount(
			_toUserAccount(values));

		setExtendedProperties(
			UserAccount.class.getName(), userAccount, user, values);

		return userAccount.getId();
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _userLocalService.deleteUser((User)baseModel);
	}

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _userLocalService.fetchUserByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _userLocalService.getUserByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public String getExternalReferenceCode(long primaryKey)
		throws PortalException {

		User user = _userLocalService.getUser(primaryKey);

		return user.getExternalReferenceCode();
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Admin.User", "headless-admin-user",
			"user-accounts", "v1.0");
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
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, "middleName",
				ObjectFieldConstants.DB_TYPE_STRING, "middle-name",
				"additionalName", false, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, "screenName",
				ObjectFieldConstants.DB_TYPE_STRING, "screen-name",
				"alternateName", true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, "email-address",
				"emailAddress", true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, "lastName",
				ObjectFieldConstants.DB_TYPE_STRING, "last-name", "familyName",
				true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, "firstName",
				ObjectFieldConstants.DB_TYPE_STRING, "first-name", "givenName",
				true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_DATE, "lastLoginDate",
				ObjectFieldConstants.DB_TYPE_DATE, "last-login-date",
				"lastLoginDate", false, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, "uuid_",
				ObjectFieldConstants.DB_TYPE_STRING, "uuid", "uuid", false,
				true));
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
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return UserTable.INSTANCE;
	}

	@Override
	public String getTitleObjectFieldName() {
		return "givenName";
	}

	@Override
	public Map<String, Object> getVariables(
		String contentType, ObjectDefinition objectDefinition,
		boolean oldValues, JSONObject payloadJSONObject) {

		Map<String, Object> variables = super.getVariables(
			contentType, objectDefinition, oldValues, payloadJSONObject);

		if (variables.containsKey("firstName")) {
			variables.put("givenName", variables.get("firstName"));
		}

		if (variables.containsKey("lastName")) {
			variables.put("familyName", variables.get("lastName"));
		}

		if (variables.containsKey("middleName")) {
			variables.put("additionalName", variables.get("middleName"));
		}

		if (variables.containsKey("screenName")) {
			variables.put("alternateName", variables.get("screenName"));
		}

		return variables;
	}

	@Override
	public int getVersion() {
		return 3;
	}

	@Override
	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception {

		UserAccountResource userAccountResource = _buildUserAccountResource(
			user);

		UserAccount userAccount = userAccountResource.patchUserAccount(
			primaryKey, _toUserAccount(values));

		setExtendedProperties(
			UserAccount.class.getName(), userAccount, user, values);
	}

	private UserAccountResource _buildUserAccountResource(User user) {
		UserAccountResource.Builder builder =
			_userAccountResourceFactory.create();

		return builder.checkPermissions(
			false
		).preferredLocale(
			user.getLocale()
		).user(
			user
		).build();
	}

	private UserAccount _toUserAccount(Map<String, Object> values) {
		return new UserAccount() {
			{
				additionalName = GetterUtil.getString(
					values.get("additionalName"));
				alternateName = GetterUtil.getString(
					values.get("alternateName"));
				emailAddress = GetterUtil.getString(values.get("emailAddress"));
				externalReferenceCode = GetterUtil.getString(
					values.get("externalReferenceCode"));
				familyName = GetterUtil.getString(values.get("familyName"));
				givenName = GetterUtil.getString(values.get("givenName"));
			}
		};
	}

	@Reference
	private UserAccountResource.Factory _userAccountResourceFactory;

	@Reference
	private UserLocalService _userLocalService;

}