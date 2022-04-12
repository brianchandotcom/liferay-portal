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

import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupTable;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.CommerceAccountGroupNameException;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountException;
import com.liferay.commerce.account.exception.SystemCommerceAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRelTable;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupImpl;
import com.liferay.commerce.account.service.base.CommerceAccountGroupLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupLocalServiceImpl
	extends CommerceAccountGroupLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccountGroup addCommerceAccountGroup(
			long companyId, String name, int type, boolean system,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		// Commerce Account Group

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validate(companyId, 0, name, externalReferenceCode);

		AccountGroup accountGroup = _accountGroupLocalService.addAccountGroup(
			serviceContext.getUserId(), null, name);

		accountGroup.setDefaultAccountGroup(system);
		accountGroup.setType(CommerceAccountGroupImpl.toAccountGroupType(type));
		accountGroup.setExternalReferenceCode(externalReferenceCode);
		accountGroup.setExpandoBridgeAttributes(serviceContext);

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupImpl.fromAccountGroup(
				_accountGroupLocalService.updateAccountGroup(accountGroup));

		// Resources

		resourceLocalService.addResources(
			accountGroup.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			accountGroup.getUserId(), CommerceAccountGroup.class.getName(),
			commerceAccountGroup.getCommerceAccountGroupId(), false, false,
			false);

		return commerceAccountGroup;
	}

	@Override
	public void checkGuestCommerceAccountGroup(long companyId)
		throws PortalException {

		if (_accountGroupLocalService.hasDefaultAccountGroup(companyId)) {
			return;
		}

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupImpl.fromAccountGroup(
				_accountGroupLocalService.checkGuestAccountGroup(companyId));

		User user = userLocalService.getDefaultUser(companyId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(user.getUserId());

		commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup.getCommerceAccountGroupId(),
				CommerceAccountConstants.ACCOUNT_ID_GUEST, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAccountGroup deleteCommerceAccountGroup(
			CommerceAccountGroup commerceAccountGroup)
		throws PortalException {

		if (commerceAccountGroup.isSystem()) {
			throw new SystemCommerceAccountGroupException();
		}

		// Commerce account group generic rels

		commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			commerceAccountGroup.getCommerceAccountGroupId());

		// Commerce account group

		_accountGroupLocalService.deleteAccountGroup(
			commerceAccountGroup.getCommerceAccountGroupId());

		// Resources

		resourceLocalService.deleteResource(
			commerceAccountGroup, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		_expandoRowLocalService.deleteRows(
			commerceAccountGroup.getCommerceAccountGroupId());

		return commerceAccountGroup;
	}

	@Override
	public CommerceAccountGroup deleteCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		return CommerceAccountGroupImpl.fromAccountGroup(
			_accountGroupLocalService.deleteAccountGroup(
				commerceAccountGroupId));
	}

	@Override
	public CommerceAccountGroup fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return CommerceAccountGroupImpl.fromAccountGroup(
			_accountGroupLocalService.fetchAccountGroupByExternalReferenceCode(
				companyId, externalReferenceCode));
	}

	@Override
	public CommerceAccountGroup fetchCommerceAccountGroup(
		long commerceAccountGroupId) {

		return CommerceAccountGroupImpl.fromAccountGroup(
			_accountGroupLocalService.fetchAccountGroup(
				commerceAccountGroupId));
	}

	@Override
	public CommerceAccountGroup getCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		return CommerceAccountGroupImpl.fromAccountGroup(
			_accountGroupLocalService.getAccountGroup(commerceAccountGroupId));
	}

	@Override
	public List<CommerceAccountGroup> getCommerceAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return TransformUtil.transform(
			_accountGroupLocalService.getAccountGroups(
				companyId, start, end,
				new OrderByComparator<AccountGroup>() {

					@Override
					public int compare(
						AccountGroup accountGroup1,
						AccountGroup accountGroup2) {

						return orderByComparator.compare(
							CommerceAccountGroupImpl.fromAccountGroup(
								accountGroup1),
							CommerceAccountGroupImpl.fromAccountGroup(
								accountGroup2));
					}

				}),
			CommerceAccountGroupImpl::fromAccountGroup);
	}

	@Override
	public List<CommerceAccountGroup>
		getCommerceAccountGroupsByCommerceAccountId(
			long commerceAccountId, int start, int end) {

		List<CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				commerceAccountGroupCommerceAccountRelLocalService.
					getCommerceAccountGroupCommerceAccountRelsByCommerceAccountId(
						commerceAccountId, start, end);

		if (commerceAccountGroupCommerceAccountRels.isEmpty()) {
			return new ArrayList<>();
		}

		Stream<CommerceAccountGroupCommerceAccountRel> stream =
			commerceAccountGroupCommerceAccountRels.stream();

		long[] commerceAccountGroupIds = stream.mapToLong(
			CommerceAccountGroupCommerceAccountRel::getCommerceAccountGroupId
		).toArray();

		return TransformUtil.transform(
			_accountGroupLocalService.getAccountGroupsByAccountGroupId(
				commerceAccountGroupIds),
			CommerceAccountGroupImpl::fromAccountGroup);
	}

	@Override
	public int getCommerceAccountGroupsByCommerceAccountIdCount(
		long commerceAccountId) {

		return commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRelsCountByCommerceAccountId(
				commerceAccountId);
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId) {
		return _accountGroupLocalService.getAccountGroupsCount(companyId);
	}

	@Override
	public List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.selectDistinct(
			AccountEntryUserRelTable.INSTANCE.accountUserId
		).from(
			AccountEntryUserRelTable.INSTANCE
		).innerJoinON(
			CommerceAccountGroupCommerceAccountRelTable.INSTANCE,
			CommerceAccountGroupCommerceAccountRelTable.INSTANCE.
				commerceAccountId.eq(
					AccountEntryUserRelTable.INSTANCE.accountEntryId)
		).where(
			CommerceAccountGroupCommerceAccountRelTable.INSTANCE.
				commerceAccountGroupId.in(
					ArrayUtil.toLongArray(commerceAccountGroupIds))
		).limit(
			start, end
		);

		return _accountGroupLocalService.dslQuery(dslQuery);
	}

	@Override
	public List<CommerceAccountGroup> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		OrderByComparator<AccountGroup> orderByComparator = null;

		if (sort != null) {
			String fieldName = sort.getFieldName();

			if (Field.isSortableFieldName(fieldName)) {
				fieldName = StringUtil.removeSubstring(
					fieldName,
					StringPool.UNDERLINE + Field.SORTABLE_FIELD_SUFFIX);
			}

			orderByComparator = OrderByComparatorFactoryUtil.create(
				AccountGroupTable.INSTANCE.getTableName(), fieldName,
				!sort.isReverse());
		}

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				companyId, keywords, start, end, orderByComparator);

		return TransformUtil.transform(
			baseModelSearchResult.getBaseModels(),
			CommerceAccountGroupImpl::fromAccountGroup);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws PortalException {

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			_accountGroupLocalService.searchAccountGroups(
				companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		return baseModelSearchResult.getLength();
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccountGroup updateCommerceAccountGroup(
			long commerceAccountGroupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		AccountGroup accountGroup = _accountGroupLocalService.getAccountGroup(
			commerceAccountGroupId);

		if (accountGroup.isDefaultAccountGroup()) {
			throw new SystemCommerceAccountGroupException();
		}

		validate(
			serviceContext.getCompanyId(), accountGroup.getAccountGroupId(),
			name, accountGroup.getExternalReferenceCode());

		accountGroup.setName(name);
		accountGroup.setExpandoBridgeAttributes(serviceContext);

		return CommerceAccountGroupImpl.fromAccountGroup(
			_accountGroupLocalService.updateAccountGroup(accountGroup));
	}

	protected void validate(
			long companyId, long commerceAccountGroupId, String name,
			String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceAccountGroupNameException();
		}

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupImpl.fromAccountGroup(
				_accountGroupLocalService.
					fetchAccountGroupByExternalReferenceCode(
						companyId, externalReferenceCode));

		if ((commerceAccountGroup != null) &&
			(commerceAccountGroup.getCommerceAccountGroupId() !=
				commerceAccountGroupId)) {

			throw new DuplicateCommerceAccountException(
				"There is another commerce account group with external " +
					"reference code " + externalReferenceCode);
		}
	}

	@ServiceReference(type = AccountGroupLocalService.class)
	private AccountGroupLocalService _accountGroupLocalService;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

}