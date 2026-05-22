/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.ai.hub.rest.dto.v1_0.ProvisioningRequest;
import com.liferay.ai.hub.rest.dto.v1_0.UserAccount;
import com.liferay.ai.hub.rest.manager.v1_0.ProvisioningRequestManager;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Davyson Melo
 */
@Component(service = ProvisioningRequestManager.class)
public class ProvisioningRequestManagerImpl
	implements ProvisioningRequestManager {

	@Override
	public ProvisioningRequest postProvisioning(
			Company company, DTOConverterContext dtoConverterContext,
			ProvisioningRequest provisioningRequest)
		throws Exception {

		AccountEntry aiHubAccountEntry =
			_accountEntryService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", company.getCompanyId());

		ServiceContext serviceContext = ServiceContextBuilder.create(
			company.getGroupId(), dtoConverterContext.getHttpServletRequest(),
			null
		).build();

		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setUserId(dtoConverterContext.getUserId());

		AccountEntry customerAccountEntry = _getOrAddAccountEntry(
			provisioningRequest.getAccountEntryExternalReferenceCode(),
			provisioningRequest.getAccountEntryName(), serviceContext);

		long[] accountEntryIds = {
			aiHubAccountEntry.getAccountEntryId(),
			customerAccountEntry.getAccountEntryId()
		};

		_addServiceAccountUsers(
			customerAccountEntry.getAccountEntryId(), accountEntryIds, company,
			dtoConverterContext.getLocale(), serviceContext);

		_addUserAccounts(
			customerAccountEntry.getAccountEntryId(), accountEntryIds,
			dtoConverterContext.getLocale(), serviceContext,
			provisioningRequest.getUserAccounts());

		_addOAuth2Application(
			"ai-hub-oauth2-" + customerAccountEntry.getAccountEntryId(),
			provisioningRequest, dtoConverterContext.getUser(), serviceContext);

		_addQuotas(customerAccountEntry, serviceContext);

		return new ProvisioningRequest() {
			{
				setAccountEntryExternalReferenceCode(
					customerAccountEntry::getExternalReferenceCode);
				setAccountEntryId(customerAccountEntry::getAccountEntryId);
				setAccountEntryName(customerAccountEntry::getName);
				setLiferayDXPURL(provisioningRequest::getLiferayDXPURL);
				setUserAccounts(provisioningRequest::getUserAccounts);
			}
		};
	}

	private void _addOAuth2Application(
			String externalReferenceCode,
			ProvisioningRequest provisioningRequest, User user,
			ServiceContext serviceContext)
		throws Exception {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					externalReferenceCode, serviceContext.getCompanyId());

		if (oAuth2Application != null) {
			return;
		}

		String portalURL = provisioningRequest.getLiferayDXPURL();

		_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
			externalReferenceCode, user.getUserId(), user.getFullName(),
			Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
			"client_secret_post", user.getUserId(),
			OAuth2SecureRandomGenerator.generateClientId(),
			ClientProfile.HEADLESS_SERVER.id(),
			OAuth2SecureRandomGenerator.generateClientSecret(), null,
			Collections.emptyList(), portalURL, 0, null,
			provisioningRequest.getAccountEntryName(), null,
			Collections.singletonList(portalURL), false,
			Collections.emptyList(), false, serviceContext);
	}

	private void _addQuotaObjectEntry(
			AccountEntry accountEntry, String externalReferenceCode,
			ObjectDefinition objectDefinition, ServiceContext serviceContext)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, 0, objectDefinition.getObjectDefinitionId());

		if (objectEntry != null) {
			return;
		}

		_objectEntryLocalService.addObjectEntry(
			0, serviceContext.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"limit", _QUOTA_TOKEN_LIMIT
			).put(
				"r_accountToAIHubQuotas_accountEntryId",
				accountEntry.getAccountEntryId()
			).put(
				"usage", 0
			).build(),
			serviceContext);
	}

	private void _addQuotas(
			AccountEntry accountEntry, ServiceContext serviceContext)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_QUOTA", serviceContext.getCompanyId());

		_addQuotaObjectEntry(
			accountEntry, "guest-quota-" + accountEntry.getAccountEntryId(),
			objectDefinition, serviceContext);
		_addQuotaObjectEntry(
			accountEntry, "quota-" + accountEntry.getAccountEntryId(),
			objectDefinition, serviceContext);
	}

	private void _addServiceAccountUsers(
			long accountEntryId, long[] accountEntryIds, Company company,
			Locale locale, ServiceContext serviceContext)
		throws Exception {

		User serviceAccountUser = _getOrAddUser(
			company, locale, accountEntryId + "-service-account",
			serviceContext);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			accountEntryIds, new long[0], serviceAccountUser.getUserId());

		User guestServiceAccountUser = _getOrAddUser(
			company, locale, accountEntryId + "-guest-service-account",
			serviceContext);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			accountEntryIds, new long[0], guestServiceAccountUser.getUserId());
	}

	private User _addUser(
			long companyId, Locale locale, String screenName,
			String emailAddress, String firstName, String lastName, int type,
			ServiceContext serviceContext)
		throws Exception {

		return _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, true, null, null, false,
			screenName, emailAddress, locale, firstName, StringPool.BLANK,
			lastName, 0, 0, true, Calendar.JANUARY, 1, 1970, StringPool.BLANK,
			type, null, null, null, null, false, serviceContext);
	}

	private void _addUserAccounts(
			long accountEntryId, long[] accountEntryIds, Locale locale,
			ServiceContext serviceContext, UserAccount[] userAccounts)
		throws Exception {

		if (ArrayUtil.isEmpty(userAccounts)) {
			return;
		}

		Role role = _roleLocalService.getRole(
			serviceContext.getCompanyId(), "AI Hub Agent Manager");

		AccountRole accountRole1 =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		long accountRoleId = accountRole1.getAccountRoleId();

		Group group = _groupLocalService.getGroup(
			serviceContext.getCompanyId(), "AI Hub");

		List<User> users = _userLocalService.getGroupUsers(group.getGroupId());

		for (UserAccount userAccount : userAccounts) {
			User user = _userLocalService.fetchUserByEmailAddress(
				serviceContext.getCompanyId(), userAccount.getEmailAddress());

			if (user == null) {
				user = _addUser(
					serviceContext.getCompanyId(), locale,
					userAccount.getScreenName(), userAccount.getEmailAddress(),
					userAccount.getFirstName(), userAccount.getLastName(),
					UserConstants.TYPE_REGULAR, serviceContext);
			}

			if (!users.contains(user)) {
				_userLocalService.addGroupUsers(
					group.getGroupId(), new long[] {user.getUserId()});
			}

			_accountEntryUserRelLocalService.updateAccountEntryUserRels(
				accountEntryIds, new long[0], user.getUserId());

			List<AccountRole> accountRoles = ListUtil.filter(
				_accountRoleLocalService.getAccountRoles(
					accountEntryId, user.getUserId()),
				accountRole2 ->
					accountRole2.getAccountRoleId() == accountRoleId);

			if (ListUtil.isNotEmpty(accountRoles)) {
				continue;
			}

			_accountRoleLocalService.associateUser(
				accountEntryId, accountRoleId, user.getUserId());
		}
	}

	private AccountEntry _getOrAddAccountEntry(
			String externalReferenceCode, String name,
			ServiceContext serviceContext)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, serviceContext.getCompanyId());

		if (accountEntry != null) {
			return accountEntry;
		}

		return _accountEntryService.addAccountEntry(
			externalReferenceCode, serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, name, null, null,
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private User _getOrAddUser(
			Company company, Locale locale, String screenName,
			ServiceContext serviceContext)
		throws Exception {

		User user = _userLocalService.fetchUserByScreenName(
			company.getCompanyId(), screenName);

		if (user != null) {
			return user;
		}

		user = _addUser(
			company.getCompanyId(), locale, screenName,
			screenName + StringPool.AT + company.getMx(), screenName,
			screenName, UserConstants.TYPE_SERVICE_ACCOUNT, serviceContext);

		user.setPasswordReset(false);
		user.setEmailAddressVerified(true);

		return _userLocalService.updateUser(user);
	}

	private static final int _QUOTA_TOKEN_LIMIT = 33333333;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}