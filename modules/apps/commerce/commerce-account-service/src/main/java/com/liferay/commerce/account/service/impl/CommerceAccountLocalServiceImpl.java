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

package com.liferay.commerce.account.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.CommerceAccountNameException;
import com.liferay.commerce.account.exception.CommerceAccountOrdersException;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalService;
import com.liferay.commerce.account.service.base.CommerceAccountLocalServiceBaseImpl;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccount",
	service = AopService.class
)
public class CommerceAccountLocalServiceImpl
	extends CommerceAccountLocalServiceBaseImpl {

	@Override
	public CommerceAccount addBusinessCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, boolean active, String externalReferenceCode,
			long[] userIds, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		// Commerce Account

		CommerceAccount commerceAccount =
			commerceAccountLocalService.addCommerceAccount(
				name, parentCommerceAccountId, email, taxId,
				CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS, active,
				externalReferenceCode, serviceContext);

		// Check commerce account roles

		_commerceAccountRoleHelper.checkCommerceAccountRoles(serviceContext);

		Role role = _roleLocalService.getRole(
			serviceContext.getCompanyId(),
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		// Commerce account user rels

		_commerceAccountUserRelLocalService.addCommerceAccountUserRels(
			commerceAccount.getCommerceAccountId(), userIds, emailAddresses,
			new long[] {role.getRoleId()}, serviceContext);

		return commerceAccount;
	}

	@Override
	public CommerceAccount addCommerceAccount(CommerceAccount commerceAccount) {
		throw new UnsupportedOperationException();
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount addCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, int type, boolean active,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		// Commerce Account

		User user = _userLocalService.getUser(serviceContext.getUserId());

		parentCommerceAccountId = _getParentCommerceAccountId(
			serviceContext.getCompanyId(), parentCommerceAccountId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		_validate(
			serviceContext.getCompanyId(), 0, name, externalReferenceCode);

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			user.getUserId(), parentCommerceAccountId, name, null, null, email,
			null, taxId, CommerceAccountImpl.toAccountEntryType(type),
			CommerceAccountImpl.toAccountEntryStatus(active), serviceContext);

		if (externalReferenceCode != null) {
			accountEntry.setExternalReferenceCode(externalReferenceCode);

			accountEntry = _accountEntryLocalService.updateAccountEntry(
				accountEntry);
		}

		// Resources

		_resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), CommerceAccount.class.getName(),
			accountEntry.getAccountEntryId(), false, false, false);

		return CommerceAccountImpl.fromAccountEntry(accountEntry);
	}

	@Override
	public CommerceAccount addOrUpdateCommerceAccount(
			String name, long parentCommerceAccountId, boolean logo,
			byte[] logoBytes, String email, String taxId, int type,
			boolean active, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CommerceAccount commerceAccount =
				CommerceAccountImpl.fromAccountEntry(
					_accountEntryLocalService.
						fetchAccountEntryByExternalReferenceCode(
							serviceContext.getCompanyId(),
							externalReferenceCode));

			if (commerceAccount != null) {
				return commerceAccountLocalService.updateCommerceAccount(
					commerceAccount.getCommerceAccountId(), name, logo,
					logoBytes, email, taxId, active, serviceContext);
			}
		}

		return commerceAccountLocalService.addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	@Override
	public CommerceAccount addPersonalCommerceAccount(
			long userId, String taxId, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		serviceContext.setUserId(userId);

		// Commerce account

		CommerceAccount commerceAccount =
			commerceAccountLocalService.addCommerceAccount(
				user.getFullName(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				user.getEmailAddress(), taxId,
				CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL, true,
				externalReferenceCode, serviceContext);

		// Commerce account user rel

		_commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccount.getCommerceAccountId(), userId, serviceContext);

		return commerceAccount;
	}

	@Override
	public CommerceAccount createCommerceAccount(long commerceAccountId) {
		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.createAccountEntry(commerceAccountId));
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAccount deleteCommerceAccount(
			CommerceAccount commerceAccount)
		throws PortalException {

		long commerceAccountId = commerceAccount.getCommerceAccountId();

		// Commerce account organization rels

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByCommerceAccountId(
				commerceAccountId);

		// Commerce account user rels

		_commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);

		Group commerceAccountGroup =
			commerceAccountLocalService.getCommerceAccountGroup(
				commerceAccountId);

		// Commerce account user roles

		_userGroupRoleLocalService.deleteUserGroupRolesByGroupId(
			commerceAccountGroup.getGroupId());

		// Commerce account

		try {
			_accountEntryLocalService.deleteAccountEntry(commerceAccountId);
		}
		catch (ModelListenerException modelListenerException) {
			throw new CommerceAccountOrdersException(modelListenerException);
		}

		// Resources

		// TODO Check permissions

		_resourceLocalService.deleteResource(
			commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(commerceAccount.getCommerceAccountId()));

		// Expando

		_expandoRowLocalService.deleteRows(commerceAccountId);

		return commerceAccount;
	}

	@Override
	public CommerceAccount deleteCommerceAccount(long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.deleteAccountEntry(commerceAccountId));
	}

	@Override
	public void deleteCommerceAccounts(long companyId) throws PortalException {
		_accountEntryLocalService.deleteAccountEntriesByCompanyId(companyId);
	}

	@Override
	public void deleteLogo(long commerceAccountId) throws PortalException {
		_portal.updateImageId(
			_accountEntryLocalService.getAccountEntry(commerceAccountId), false,
			null, "logoId", 0, 0, 0);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccount fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				companyId, externalReferenceCode));
	}

	@Override
	public CommerceAccount fetchCommerceAccount(long commerceAccountId) {
		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchAccountEntry(commerceAccountId));
	}

	@Override
	public CommerceAccount fetchCommerceAccountByReferenceCode(
		long companyId, String externalReferenceCode) {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				companyId, externalReferenceCode));
	}

	@Override
	public CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.getAccountEntry(commerceAccountId));
	}

	@Override
	public CommerceAccount getCommerceAccount(
			long userId, long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchUserAccountEntry(
				userId, commerceAccountId));
	}

	@Override
	public Group getCommerceAccountGroup(long commerceAccountId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		Group group = accountEntry.getAccountEntryGroup();

		if (group != null) {
			return group;
		}

		throw new PortalException();
	}

	@Override
	public List<CommerceAccount> getCommerceAccounts(int start, int end) {
		return TransformUtil.transform(
			_accountEntryLocalService.getAccountEntries(start, end),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public int getCommerceAccountsCount() {
		return _accountEntryLocalService.getAccountEntriesCount();
	}

	@Override
	public CommerceAccount getGuestCommerceAccount(long companyId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.getGuestAccountEntry(companyId));
	}

	@Override
	public CommerceAccount getPersonalCommerceAccount(long userId)
		throws PortalException {

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchPersonAccountEntry(userId));

		if (commerceAccount != null) {
			return commerceAccount;
		}

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setUserId(userId);

		return commerceAccountLocalService.addPersonalCommerceAccount(
			userId, StringPool.BLANK, StringPool.BLANK, serviceContext);
	}

	@Override
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active, int start, int end)
		throws PortalException {

		return TransformUtil.transform(
			_accountEntryLocalService.getUserAccountEntries(
				userId, parentCommerceAccountId, keywords,
				CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
				CommerceAccountImpl.toAccountEntryStatus(active), start, end),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, int start, int end)
		throws PortalException {

		return TransformUtil.transform(
			_accountEntryLocalService.getUserAccountEntries(
				userId, parentCommerceAccountId, keywords,
				CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
				WorkflowConstants.STATUS_ANY, start, end),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws PortalException {

		return commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords, null);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws PortalException {

		return _accountEntryLocalService.getUserAccountEntriesCount(
			userId, parentCommerceAccountId, keywords,
			CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
			CommerceAccountImpl.toAccountEntryStatus(active));
	}

	@Override
	public List<CommerceAccount> search(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active, int start, int end, Sort sort)
		throws PortalException {

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"parentAccountEntryId", parentCommerceAccountId
			).put(
				"status", () -> CommerceAccountImpl.toAccountEntryStatus(active)
			).put(
				"types",
				new String[] {CommerceAccountImpl.toAccountEntryType(type)}
			).build();

		String fieldName = null;
		boolean reverse = false;

		if (sort != null) {
			fieldName = sort.getFieldName();
			reverse = sort.isReverse();
		}

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				companyId, keywords, params, start, end - start, fieldName,
				reverse);

		return TransformUtil.transform(
			baseModelSearchResult.getBaseModels(),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws PortalException {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				companyId, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"status",
					() -> CommerceAccountImpl.toAccountEntryStatus(active)
				).put(
					"types",
					new String[] {CommerceAccountImpl.toAccountEntryType(type)}
				).build(),
				QueryUtil.ALL_POS, 0, null, false);

		return baseModelSearchResult.getLength();
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount setActive(long commerceAccountId, boolean active)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		accountEntry.setStatus(
			CommerceAccountImpl.toAccountEntryStatus(active));

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.updateAccountEntry(accountEntry));
	}

	@Override
	public CommerceAccount updateCommerceAccount(
		CommerceAccount commerceAccount) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, null,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		if (defaultBillingAddressId == -1) {
			defaultBillingAddressId = accountEntry.getDefaultBillingAddressId();
		}

		if (defaultShippingAddressId == -1) {
			defaultShippingAddressId =
				accountEntry.getDefaultShippingAddressId();
		}

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		// Using this method will skip default address validation.
		// Use updateDefault*Address if you want validation

		_validate(
			serviceContext.getCompanyId(), accountEntry.getAccountEntryId(),
			name, externalReferenceCode);

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry.getAccountEntryId(),
			accountEntry.getParentAccountEntryId(), name,
			accountEntry.getDescription(), !logo,
			accountEntry.getDomainsArray(), email, logoBytes, taxId,
			CommerceAccountImpl.toAccountEntryStatus(active), serviceContext);

		accountEntry.setDefaultBillingAddressId(defaultBillingAddressId);
		accountEntry.setDefaultShippingAddressId(defaultShippingAddressId);

		if (Validator.isNotNull(externalReferenceCode)) {
			accountEntry.setExternalReferenceCode(externalReferenceCode);
		}

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry);

		return CommerceAccountImpl.fromAccountEntry(accountEntry);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active, -1,
			-1, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateDefaultBillingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.updateDefaultBillingAddressId(
				commerceAccountId, commerceAddressId));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateDefaultShippingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.updateDefaultShippingAddressId(
				commerceAccountId, commerceAddressId));
	}

	/**
	 * @bridged
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateStatus(
			long userId, long commerceAccountId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.updateStatus(commerceAccountId, status));
	}

	private long _getParentCommerceAccountId(
		long companyId, long parentCommerceAccountId) {

		if (parentCommerceAccountId !=
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID) {

			// Ensure parent account exists and belongs to the proper
			// company

			CommerceAccount parentCommerceAccount =
				CommerceAccountImpl.fromAccountEntry(
					_accountEntryLocalService.fetchAccountEntry(
						parentCommerceAccountId));

			if ((parentCommerceAccount == null) ||
				(companyId != parentCommerceAccount.getCompanyId())) {

				parentCommerceAccountId =
					CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID;
			}
		}

		return parentCommerceAccountId;
	}

	private void _validate(
			long companyId, long commerceAccountId, String name,
			String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceAccountNameException();
		}

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				companyId, externalReferenceCode));

		if ((commerceAccount != null) &&
			(commerceAccount.getCommerceAccountId() != commerceAccountId)) {

			throw new DuplicateCommerceAccountException(
				"There is another commerce account with external reference " +
					"code " + externalReferenceCode);
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

	@Reference
	private CommerceAccountRoleHelper _commerceAccountRoleHelper;

	@Reference
	private CommerceAccountUserRelLocalService
		_commerceAccountUserRelLocalService;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}