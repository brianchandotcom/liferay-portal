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

package com.liferay.account.internal.object.system;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(service = SystemObjectDefinitionManager.class)
public class AccountEntrySystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		AccountResource accountResource = _buildAccountResource(user);

		Account account = accountResource.postAccount(_toAccount(values));

		setExtendedProperties(Account.class.getName(), account, user, values);

		return account.getId();
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _accountEntryLocalService.deleteAccountEntry(
			(AccountEntry)baseModel);
	}

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _accountEntryLocalService.
			fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _accountEntryLocalService.getAccountEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public String getExternalReferenceCode(long primaryKey)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			primaryKey);

		return accountEntry.getExternalReferenceCode();
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Admin.User", "headless-admin-user", "accounts",
			"v1.0");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("account");
	}

	@Override
	public Class<?> getModelClass() {
		return AccountEntry.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, "name", "name", true,
				true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, "description",
				"description", false, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, "type", "type", true,
				true));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("accounts");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return AccountEntryTable.INSTANCE.accountEntryId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return AccountEntryTable.INSTANCE;
	}

	@Override
	public String getTitleObjectFieldName() {
		return "name";
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception {

		AccountResource accountResource = _buildAccountResource(user);

		Account account = accountResource.patchAccount(
			primaryKey, _toAccount(values));

		setExtendedProperties(Account.class.getName(), account, user, values);
	}

	private AccountResource _buildAccountResource(User user) {
		AccountResource.Builder builder = _accountResourceFactory.create();

		return builder.checkPermissions(
			false
		).preferredLocale(
			user.getLocale()
		).user(
			user
		).build();
	}

	private Account _toAccount(Map<String, Object> values) {
		return new Account() {
			{
				description = GetterUtil.getString(values.get("description"));
				externalReferenceCode = GetterUtil.getString(
					values.get("externalReferenceCode"));
				name = GetterUtil.getString(values.get("name"));
				type = Account.Type.create(
					StringUtil.toLowerCase(
						GetterUtil.getString(values.get("type"))));
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountResource.Factory _accountResourceFactory;

}