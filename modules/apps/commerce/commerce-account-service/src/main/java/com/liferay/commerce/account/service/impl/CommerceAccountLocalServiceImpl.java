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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRelTable;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.CommerceAccountNameException;
import com.liferay.commerce.account.exception.CommerceAccountOrdersException;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.commerce.account.service.base.CommerceAccountLocalServiceBaseImpl;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
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

		Role role = roleLocalService.getRole(
			serviceContext.getCompanyId(),
			CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		// Commerce account user rels

		commerceAccountUserRelLocalService.addCommerceAccountUserRels(
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

		User user = userLocalService.getUser(serviceContext.getUserId());

		parentCommerceAccountId = getParentCommerceAccountId(
			serviceContext.getCompanyId(), parentCommerceAccountId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validate(serviceContext.getCompanyId(), 0, name, externalReferenceCode);

		AccountEntry accountEntry = accountEntryLocalService.addAccountEntry(
			user.getUserId(), parentCommerceAccountId, name, null, null, email,
			null, taxId, CommerceAccountImpl.toAccountEntryType(type),
			CommerceAccountImpl.toAccountEntryStatus(active), serviceContext);

		if (externalReferenceCode != null) {
			accountEntry.setExternalReferenceCode(externalReferenceCode);

			accountEntry = accountEntryLocalService.updateAccountEntry(
				accountEntry);
		}

		// Resources

		resourceLocalService.addResources(
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
					accountEntryLocalService.fetchAccountEntryByReferenceCode(
						serviceContext.getCompanyId(), externalReferenceCode));

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

		User user = userLocalService.getUser(userId);

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

		commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccount.getCommerceAccountId(), userId, serviceContext);

		return commerceAccount;
	}

	@Override
	public CommerceAccount createCommerceAccount(long commerceAccountId) {
		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.createAccountEntry(commerceAccountId));
	}

	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			(AccountEntry)accountEntryLocalService.createPersistedModel(
				primaryKeyObj));
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAccount deleteCommerceAccount(
			CommerceAccount commerceAccount)
		throws PortalException {

		long commerceAccountId = commerceAccount.getCommerceAccountId();

		// Commerce account organization rels

		commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByCommerceAccountId(
				commerceAccountId);

		// Commerce account user rels

		commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);

		Group commerceAccountGroup =
			commerceAccountLocalService.getCommerceAccountGroup(
				commerceAccountId);

		// Commerce account user roles

		userGroupRoleLocalService.deleteUserGroupRolesByGroupId(
			commerceAccountGroup.getGroupId());

		// Commerce account

		try {
			accountEntryLocalService.deleteAccountEntry(commerceAccountId);
		}
		catch (ModelListenerException modelListenerException) {
			throw new CommerceAccountOrdersException(modelListenerException);
		}

		// Resources

		//	TODO Check permissions

		resourceLocalService.deleteResource(
			commerceAccount, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(commerceAccountId);

		return commerceAccount;
	}

	@Override
	public CommerceAccount deleteCommerceAccount(long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.deleteAccountEntry(commerceAccountId));
	}

	@Override
	public void deleteCommerceAccounts(long companyId) throws PortalException {
		accountEntryLocalService.deleteAccountEntriesByCompanyId(companyId);
	}

	@Override
	public void deleteLogo(long commerceAccountId) throws PortalException {
		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		_portal.updateImageId(accountEntry, false, null, "logoId", 0, 0, 0);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		CommerceAccount commerceAccount = (CommerceAccount)persistedModel;

		return accountEntryLocalService.deletePersistedModel(
			accountEntryLocalService.getPersistedModel(
				commerceAccount.getPrimaryKeyObj()));
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
			accountEntryLocalService.fetchAccountEntryByReferenceCode(
				companyId, externalReferenceCode));
	}

	@Override
	public CommerceAccount fetchCommerceAccount(long commerceAccountId) {
		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.fetchAccountEntry(commerceAccountId));
	}

	@Override
	public CommerceAccount fetchCommerceAccountByReferenceCode(
		long companyId, String externalReferenceCode) {

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.fetchAccountEntryByReferenceCode(
				companyId, externalReferenceCode));
	}

	@Override
	public CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.getAccountEntry(commerceAccountId));
	}

	@Override
	public CommerceAccount getCommerceAccount(
			long userId, long commerceAccountId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			_fetchUserAccountEntry(userId, commerceAccountId));
	}

	@Override
	public Group getCommerceAccountGroup(long commerceAccountId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
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
			accountEntryLocalService.getAccountEntries(start, end),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public int getCommerceAccountsCount() {
		return accountEntryLocalService.getAccountEntriesCount();
	}

	@Override
	public CommerceAccount getGuestCommerceAccount(long companyId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.getGuestAccountEntry(companyId));
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			(AccountEntry)accountEntryLocalService.getPersistedModel(
				primaryKeyObj));
	}

	@Override
	public CommerceAccount getPersonalCommerceAccount(long userId)
		throws PortalException {

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.fetchPersonAccountEntry(userId));

		if (commerceAccount != null) {
			return commerceAccount;
		}

		User user = userLocalService.getUser(userId);

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
			accountEntryLocalService.dslQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(
						AccountEntryTable.INSTANCE),
					userId, parentCommerceAccountId, keywords,
					CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
					CommerceAccountImpl.toAccountEntryStatus(active)
				).limit(
					start, end
				)),
			CommerceAccountImpl::fromAccountEntry);
	}

	@Override
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, int start, int end)
		throws PortalException {

		return TransformUtil.transform(
			accountEntryLocalService.dslQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(
						AccountEntryTable.INSTANCE),
					userId, parentCommerceAccountId, keywords,
					CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
					WorkflowConstants.STATUS_ANY
				).limit(
					start, end
				)),
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

		return accountEntryLocalService.dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					AccountEntryTable.INSTANCE.accountEntryId),
				userId, parentCommerceAccountId, keywords,
				CommerceAccountImpl.toAccountEntryTypes(commerceSiteType),
				CommerceAccountImpl.toAccountEntryStatus(active)));
	}

	@Override
	public List<CommerceAccount> searchCommerceAccounts(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active, int start, int end, Sort sort)
		throws PortalException {

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"parentAccountEntryId", parentCommerceAccountId
			).put(
				"status", () -> CommerceAccountImpl.toAccountEntryStatus(active)
			).put(
				"type", CommerceAccountImpl.toAccountEntryType(type)
			).build();

		String fieldName = null;
		boolean reverse = false;

		if (sort != null) {
			fieldName = sort.getFieldName();
			reverse = sort.isReverse();
		}

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			accountEntryLocalService.searchAccountEntries(
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
			accountEntryLocalService.searchAccountEntries(
				companyId, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"type", CommerceAccountImpl.toAccountEntryType(type)
				).put(
					"status",
					() -> CommerceAccountImpl.toAccountEntryStatus(active)
				).build(),
				QueryUtil.ALL_POS, 0, null, false);

		return baseModelSearchResult.getLength();
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount setActive(long commerceAccountId, boolean active)
		throws PortalException {

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		accountEntry.setStatus(
			CommerceAccountImpl.toAccountEntryStatus(active));

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.updateAccountEntry(accountEntry));
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

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
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

		validate(
			serviceContext.getCompanyId(), accountEntry.getAccountEntryId(),
			name, externalReferenceCode);

		accountEntry = accountEntryLocalService.updateAccountEntry(
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

		accountEntry = accountEntryLocalService.updateAccountEntry(
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
			accountEntryLocalService.updateDefaultBillingAddressId(
				commerceAccountId, commerceAddressId));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateDefaultShippingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		return CommerceAccountImpl.fromAccountEntry(
			accountEntryLocalService.updateDefaultShippingAddressId(
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
			accountEntryLocalService.updateStatus(commerceAccountId, status));
	}

	protected long getParentCommerceAccountId(
		long companyId, long parentCommerceAccountId) {

		if (parentCommerceAccountId !=
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID) {

			// Ensure parent account exists and belongs to the proper
			// company

			CommerceAccount parentCommerceAccount =
				CommerceAccountImpl.fromAccountEntry(
					accountEntryLocalService.fetchAccountEntry(
						parentCommerceAccountId));

			if ((parentCommerceAccount == null) ||
				(companyId != parentCommerceAccount.getCompanyId())) {

				parentCommerceAccountId =
					CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID;
			}
		}

		return parentCommerceAccountId;
	}

	protected void validate(
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
			accountEntryLocalService.fetchAccountEntryByReferenceCode(
				companyId, externalReferenceCode));

		if ((commerceAccount != null) &&
			(commerceAccount.getCommerceAccountId() != commerceAccountId)) {

			throw new DuplicateCommerceAccountException(
				"There is another commerce account with external reference " +
					"code " + externalReferenceCode);
		}
	}

	private AccountEntry _fetchUserAccountEntry(
		long userId, long accountEntryId) {

		// TODO Remove when other services are bridged

		JoinStep joinStep = DSLQueryFactoryUtil.selectDistinct(
			AccountEntryTable.INSTANCE
		).from(
			UserTable.INSTANCE
		).leftJoinOn(
			AccountEntryUserRelTable.INSTANCE,
			AccountEntryUserRelTable.INSTANCE.accountUserId.eq(
				UserTable.INSTANCE.userId)
		);

		Predicate accountEntryTablePredicate =
			AccountEntryTable.INSTANCE.accountEntryId.eq(
				AccountEntryUserRelTable.INSTANCE.accountEntryId
			).or(
				AccountEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			);

		Long[] organizationIds = _getOrganizationIds(userId);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			joinStep = joinStep.leftJoinOn(
				AccountEntryOrganizationRelTable.INSTANCE,
				AccountEntryOrganizationRelTable.INSTANCE.organizationId.in(
					organizationIds));

			accountEntryTablePredicate = accountEntryTablePredicate.or(
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					AccountEntryOrganizationRelTable.INSTANCE.accountEntryId));
		}

		joinStep = joinStep.leftJoinOn(
			AccountEntryTable.INSTANCE, accountEntryTablePredicate);

		DSLQuery dslQuery = joinStep.where(
			UserTable.INSTANCE.userId.eq(
				userId
			).and(
				AccountEntryTable.INSTANCE.type.neq(
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)
			).and(
				AccountEntryTable.INSTANCE.accountEntryId.eq(accountEntryId)
			)
		).limit(
			0, 1
		);

		List<AccountEntry> accountEntries = accountEntryLocalService.dslQuery(
			dslQuery);

		if (accountEntries.isEmpty()) {
			return null;
		}

		return accountEntries.get(0);
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, long userId, Long parentAccountId,
			String keywords, String[] types, Integer status)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			UserTable.INSTANCE
		).leftJoinOn(
			AccountEntryUserRelTable.INSTANCE,
			AccountEntryUserRelTable.INSTANCE.accountUserId.eq(
				UserTable.INSTANCE.userId)
		);

		Long[] organizationIds = _getOrganizationIds(userId);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			joinStep = joinStep.leftJoinOn(
				AccountEntryOrganizationRelTable.INSTANCE,
				AccountEntryOrganizationRelTable.INSTANCE.organizationId.in(
					organizationIds));
		}

		Predicate accountEntryPredicate =
			AccountEntryTable.INSTANCE.accountEntryId.eq(
				AccountEntryUserRelTable.INSTANCE.accountEntryId
			).or(
				AccountEntryTable.INSTANCE.userId.eq(userId)
			);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			accountEntryPredicate = accountEntryPredicate.or(
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					AccountEntryOrganizationRelTable.INSTANCE.accountEntryId));
		}

		joinStep = joinStep.leftJoinOn(
			AccountEntryTable.INSTANCE, accountEntryPredicate);

		return joinStep.where(
			() -> {
				Predicate predicate = UserTable.INSTANCE.userId.eq(userId);

				if (parentAccountId != null) {
					predicate = predicate.and(
						AccountEntryTable.INSTANCE.parentAccountEntryId.eq(
							parentAccountId));
				}

				if (Validator.isNotNull(keywords)) {
					predicate = predicate.and(
						Predicate.withParentheses(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									AccountEntryTable.INSTANCE.name),
								_customSQL.keywords(keywords, true))));
				}

				if (types != null) {
					predicate = predicate.and(
						AccountEntryTable.INSTANCE.type.in(types));
				}

				if ((status != null) &&
					(status != WorkflowConstants.STATUS_ANY)) {

					predicate = predicate.and(
						AccountEntryTable.INSTANCE.status.eq(status));
				}

				return predicate;
			});
	}

	private Long[] _getOrganizationIds(long userId) {
		List<Organization> organizations =
			organizationLocalService.getUserOrganizations(userId);

		ListIterator<Organization> listIterator = organizations.listIterator();

		while (listIterator.hasNext()) {
			Organization organization = listIterator.next();

			for (Organization curOrganization :
					organizationLocalService.getOrganizations(
						organization.getCompanyId(),
						organization.getTreePath() + "%")) {

				listIterator.add(curOrganization);
			}
		}

		Stream<Organization> stream = organizations.stream();

		return stream.map(
			Organization::getOrganizationId
		).distinct(
		).toArray(
			Long[]::new
		);
	}

	@ServiceReference(type = CommerceAccountRoleHelper.class)
	private CommerceAccountRoleHelper _commerceAccountRoleHelper;

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}