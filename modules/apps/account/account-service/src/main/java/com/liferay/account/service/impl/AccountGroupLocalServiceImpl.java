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

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountGroupNameException;
import com.liferay.account.exception.DuplicateAccountGroupExternalReferenceCodeException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.base.AccountGroupLocalServiceBaseImpl;
import com.liferay.account.service.persistence.AccountGroupRelPersistence;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroup",
	service = AopService.class
)
public class AccountGroupLocalServiceImpl
	extends AccountGroupLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountGroup addAccountGroup(
			long userId, String description, String name)
		throws PortalException {

		_validateName(name);

		long accountGroupId = counterLocalService.increment();

		AccountGroup accountGroup = accountGroupPersistence.create(
			accountGroupId);

		User user = _userLocalService.getUser(userId);

		accountGroup.setCompanyId(user.getCompanyId());
		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setDefaultAccountGroup(false);
		accountGroup.setDescription(description);
		accountGroup.setName(name);

		accountGroup.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);

		_resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AccountGroup.class.getName(), accountGroupId, false, false, false);

		return accountGroupPersistence.update(accountGroup);
	}

	@Override
	public AccountGroup checkGuestAccountGroup(long companyId)
		throws PortalException {

		AccountGroup accountGroup = accountGroupPersistence.fetchByC_D_First(
			companyId, true, null);

		if (accountGroup != null) {
			return accountGroup;
		}

		accountGroup = createAccountGroup(counterLocalService.increment());

		accountGroup.setCompanyId(companyId);

		User user = _userLocalService.getDefaultUser(companyId);

		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setDefaultAccountGroup(true);
		accountGroup.setDescription(
			"This account group is used for guest users.");
		accountGroup.setName(AccountConstants.ACCOUNT_GROUP_NAME_GUEST);

		_resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AccountGroup.class.getName(), accountGroup.getAccountGroupId(),
			false, false, false);

		return accountGroupPersistence.update(accountGroup);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AccountGroup deleteAccountGroup(AccountGroup accountGroup)
		throws PortalException {

		accountGroupPersistence.remove(accountGroup);

		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelPersistence.findByAccountGroupId(
				accountGroup.getAccountGroupId());

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			_accountGroupRelPersistence.remove(accountGroupRel);
		}

		_resourceLocalService.deleteResource(
			accountGroup.getCompanyId(), AccountGroup.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			accountGroup.getAccountGroupId());

		return accountGroup;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public AccountGroup deleteAccountGroup(long accountGroupId)
		throws PortalException {

		return deleteAccountGroup(
			accountGroupLocalService.getAccountGroup(accountGroupId));
	}

	@Override
	public List<AccountGroup> getAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return accountGroupPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AccountGroup> getAccountGroupsByAccountGroupId(
		long[] accountGroupIds) {

		return accountGroupPersistence.findByAccountGroupId(accountGroupIds);
	}

	@Override
	public int getAccountGroupsCount(long companyId) {
		return accountGroupPersistence.countByCompanyId(companyId);
	}

	@Override
	public AccountGroup getDefaultAccountGroup(long companyId) {
		return accountGroupPersistence.fetchByC_D_First(companyId, true, null);
	}

	@Override
	public boolean hasDefaultAccountGroup(long companyId) {
		int count = accountGroupPersistence.countByC_D(companyId, true);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public BaseModelSearchResult<AccountGroup> searchAccountGroups(
		long companyId, String keywords, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return searchAccountGroups(
			companyId, keywords, null, start, end, orderByComparator);
	}

	@Override
	public BaseModelSearchResult<AccountGroup> searchAccountGroups(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<AccountGroup> orderByComparator) {

		try {
			SearchContext searchContext = _buildSearchContext(
				companyId, start, end, orderByComparator);

			searchContext.setKeywords(keywords);

			if (MapUtil.isNotEmpty(params)) {
				long[] accountEntryIds = (long[])params.get("accountEntryIds");

				if (ArrayUtil.isNotEmpty(accountEntryIds)) {
					searchContext.setAttribute(
						"accountEntryIds", accountEntryIds);
				}

				long permissionUserId = GetterUtil.getLong(
					params.get("permissionUserId"));

				if (permissionUserId != GetterUtil.DEFAULT_LONG) {
					searchContext.setUserId(permissionUserId);
				}
			}

			return _searchAccountGroups(searchContext);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name)
		throws PortalException {

		_validateName(name);

		AccountGroup accountGroup = accountGroupPersistence.fetchByPrimaryKey(
			accountGroupId);

		accountGroup.setDescription(description);
		accountGroup.setName(name);

		return accountGroupPersistence.update(accountGroup);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountGroup updateExternalReferenceCode(
			AccountGroup accountGroup, String externalReferenceCode)
		throws PortalException {

		if (Objects.equals(
				accountGroup.getExternalReferenceCode(),
				externalReferenceCode)) {

			return accountGroup;
		}

		_validateExternalReferenceCode(
			accountGroup.getAccountGroupId(), externalReferenceCode);

		accountGroup.setExternalReferenceCode(externalReferenceCode);

		return updateAccountGroup(accountGroup);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountGroup updateExternalReferenceCode(
			long accountGroupId, String externalReferenceCode)
		throws PortalException {

		return updateExternalReferenceCode(
			getAccountGroup(accountGroupId), externalReferenceCode);
	}

	private SearchContext _buildSearchContext(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (ArrayUtil.isNotEmpty(orderByFields)) {
				searchContext.setSorts(
					SortFactoryUtil.getSort(
						AccountGroup.class, orderByFields[0],
						orderByComparator.isAscending() ? "asc" : "desc"));
			}
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private BaseModelSearchResult<AccountGroup> _searchAccountGroups(
			SearchContext searchContext)
		throws PortalException {

		Indexer<AccountGroup> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			AccountGroup.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<AccountGroup> accountGroups = TransformUtil.transform(
				hits.toList(),
				document -> {
					long accountGroupId = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					AccountGroup accountGroup =
						accountGroupPersistence.fetchByPrimaryKey(
							accountGroupId);

					if (accountGroup == null) {
						long companyId = GetterUtil.getLong(
							document.get(Field.COMPANY_ID));

						indexer.delete(companyId, document.getUID());
					}

					return accountGroup;
				});

			if (accountGroups != null) {
				return new BaseModelSearchResult<>(
					accountGroups, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	private void _validateExternalReferenceCode(
			long accountGroupId, String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		AccountGroup accountGroup = getAccountGroup(accountGroupId);

		accountGroup = fetchAccountGroupByExternalReferenceCode(
			accountGroup.getCompanyId(), externalReferenceCode);

		if (accountGroup == null) {
			return;
		}

		if (accountGroup.getAccountGroupId() != accountGroupId) {
			throw new DuplicateAccountGroupExternalReferenceCodeException();
		}
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new AccountGroupNameException("Name is null");
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

	@Reference
	private AccountGroupRelPersistence _accountGroupRelPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}