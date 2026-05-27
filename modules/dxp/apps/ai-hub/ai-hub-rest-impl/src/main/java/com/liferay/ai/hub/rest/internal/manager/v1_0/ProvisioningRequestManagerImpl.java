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
import com.liferay.petra.function.transform.TransformUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

		ServiceContext serviceContext = ServiceContextBuilder.create(
			company.getGroupId(), dtoConverterContext.getHttpServletRequest(),
			null
		).build();

		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setUserId(dtoConverterContext.getUserId());

		AccountEntry customerAccountEntry = _addCustomerAccountEntry(
			provisioningRequest.getAccountEntryExternalReferenceCode(),
			provisioningRequest.getAccountEntryName(), serviceContext);

		_addQuotas(customerAccountEntry, serviceContext);

		AccountEntry aiHubAccountEntry =
			_accountEntryService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", company.getCompanyId());

		List<User> users = _addRegularUsers(
			aiHubAccountEntry, customerAccountEntry,
			dtoConverterContext.getLocale(), serviceContext,
			provisioningRequest.getUserAccounts());

		_addServiceAccountUsers(
			aiHubAccountEntry, company, customerAccountEntry,
			dtoConverterContext.getLocale(), provisioningRequest,
			serviceContext);

		return new ProvisioningRequest() {
			{
				setAccountEntryExternalReferenceCode(
					customerAccountEntry::getExternalReferenceCode);
				setAccountEntryId(customerAccountEntry::getAccountEntryId);
				setAccountEntryName(customerAccountEntry::getName);
				setLiferayDXPURL(provisioningRequest::getLiferayDXPURL);
				setUserAccounts(
					() -> TransformUtil.transformToArray(
						users, user -> _toUserAccount(user),
						UserAccount.class));
			}
		};
	}

	private AccountEntry _addCustomerAccountEntry(
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

	private void _addOAuth2Application(
			AccountEntry customerAccountEntry,
			ProvisioningRequest provisioningRequest, User user,
			ServiceContext serviceContext)
		throws Exception {

		String externalReferenceCode =
			customerAccountEntry.getAccountEntryId() +
				"-ai-hub-oauth2-application";

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					externalReferenceCode, serviceContext.getCompanyId());

		if (oAuth2Application != null) {
			return;
		}

		_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
			externalReferenceCode, user.getUserId(), user.getFullName(),
			List.of(GrantType.CLIENT_CREDENTIALS), "client_secret_post",
			user.getUserId(), OAuth2SecureRandomGenerator.generateClientId(),
			ClientProfile.HEADLESS_SERVER.id(),
			OAuth2SecureRandomGenerator.generateClientSecret(), null, List.of(),
			provisioningRequest.getLiferayDXPURL(), 0, null,
			provisioningRequest.getAccountEntryName(), null, null, false,
			Arrays.asList("Liferay.AI.Hub.REST.everything"), false,
			serviceContext);
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

	private List<User> _addRegularUsers(
			AccountEntry aiHubAccountEntry, AccountEntry customerAccountEntry,
			Locale locale, ServiceContext serviceContext,
			UserAccount[] userAccounts)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			serviceContext.getCompanyId(), "AI Hub");

		List<User> users = new ArrayList<>();

		for (UserAccount userAccount : userAccounts) {
			User user = _addUser(
				serviceContext.getCompanyId(), locale,
				userAccount.getScreenName(), userAccount.getEmailAddress(),
				userAccount.getFirstName(), userAccount.getLastName(),
				UserConstants.TYPE_REGULAR, serviceContext);

			_userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {user.getUserId()});

			_associateUserToAccountEntries(
				aiHubAccountEntry, customerAccountEntry, user);

			users.add(user);
		}

		return users;
	}

	private void _addServiceAccountUsers(
			AccountEntry aiHubAccountEntry, Company company,
			AccountEntry customerAccountEntry, Locale locale,
			ProvisioningRequest provisioningRequest,
			ServiceContext serviceContext)
		throws Exception {

		long customerAccountEntryId = customerAccountEntry.getAccountEntryId();

		User guestServiceAccountUser = _addUser(
			company, locale, customerAccountEntryId + "-guest-service-account",
			serviceContext);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			new long[] {
				aiHubAccountEntry.getAccountEntryId(), customerAccountEntryId
			},
			new long[0], guestServiceAccountUser.getUserId());

		User serviceAccountUser = _addUser(
			company, locale, customerAccountEntryId + "-service-account",
			serviceContext);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			new long[] {
				aiHubAccountEntry.getAccountEntryId(), customerAccountEntryId
			},
			new long[0], serviceAccountUser.getUserId());

		_addOAuth2Application(
			customerAccountEntry, provisioningRequest, serviceAccountUser,
			serviceContext);
	}

	private User _addUser(
			Company company, Locale locale, String screenName,
			ServiceContext serviceContext)
		throws Exception {

		return _addUser(
			company.getCompanyId(), locale, screenName,
			screenName + StringPool.AT + company.getMx(), screenName,
			screenName, UserConstants.TYPE_SERVICE_ACCOUNT, serviceContext);
	}

	private User _addUser(
			long companyId, Locale locale, String screenName,
			String emailAddress, String firstName, String lastName, int type,
			ServiceContext serviceContext)
		throws Exception {

		User user = null;

		if (Validator.isNull(screenName)) {
			user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);
		}
		else {
			user = _userLocalService.fetchUserByScreenName(
				companyId, screenName);
		}

		if (user != null) {
			return user;
		}

		user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, true, null, null, false,
			screenName, emailAddress, locale, firstName, StringPool.BLANK,
			lastName, 0, 0, true, Calendar.JANUARY, 1, 1970, StringPool.BLANK,
			type, null, null, null, null, false, serviceContext);

		if (!user.isServiceAccountUser()) {
			return user;
		}

		user.setPasswordReset(false);
		user.setEmailAddressVerified(true);

		return _userLocalService.updateUser(user);
	}

	private void _associateUserToAccountEntries(
			AccountEntry aiHubAccountEntry, AccountEntry customerAccountEntry,
			User user)
		throws Exception {

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			new long[] {
				aiHubAccountEntry.getAccountEntryId(),
				customerAccountEntry.getAccountEntryId()
			},
			new long[0], user.getUserId());

		Role role = _roleLocalService.getRole(
			aiHubAccountEntry.getCompanyId(), "AI Hub Agent Manager");

		AccountRole accountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		if (!_accountRoleLocalService.hasUserAccountRole(
				customerAccountEntry.getAccountEntryId(),
				accountRole.getAccountRoleId(), user.getUserId())) {

			_accountRoleLocalService.associateUser(
				customerAccountEntry.getAccountEntryId(),
				accountRole.getAccountRoleId(), user.getUserId());
		}
	}

	private UserAccount _toUserAccount(User user) {
		return new UserAccount() {
			{
				setEmailAddress(user::getEmailAddress);
				setFirstName(user::getFirstName);
				setLastName(user::getLastName);
				setScreenName(user::getScreenName);
			}
		};
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