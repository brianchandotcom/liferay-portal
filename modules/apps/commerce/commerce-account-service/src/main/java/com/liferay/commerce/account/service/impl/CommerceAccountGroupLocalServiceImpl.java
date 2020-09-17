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

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.CommerceAccountGroupNameException;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountException;
import com.liferay.commerce.account.exception.SystemCommerceAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.base.CommerceAccountGroupLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

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

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validate(companyId, 0, name, externalReferenceCode);

		long commerceAccountGroupId = counterLocalService.increment();

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupPersistence.create(commerceAccountGroupId);

		commerceAccountGroup.setCompanyId(companyId);
		commerceAccountGroup.setUserId(user.getUserId());
		commerceAccountGroup.setUserName(user.getFullName());
		commerceAccountGroup.setName(name);
		commerceAccountGroup.setType(type);
		commerceAccountGroup.setSystem(system);
		commerceAccountGroup.setExternalReferenceCode(externalReferenceCode);
		commerceAccountGroup.setExpandoBridgeAttributes(serviceContext);

		commerceAccountGroup = commerceAccountGroupPersistence.update(
			commerceAccountGroup);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), CommerceAccountGroup.class.getName(),
			commerceAccountGroup.getCommerceAccountGroupId(), false, false,
			false);

		return commerceAccountGroup;
	}

	@Override
	public void checkGuestCommerceAccountGroup(long companyId)
		throws PortalException {

		int count = commerceAccountGroupPersistence.countByC_T(
			companyId, CommerceAccountConstants.ACCOUNT_GROUP_TYPE_GUEST);

		if (count > 0) {
			return;
		}

		Role role = roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = userLocalService.getRoleUserIds(role.getRoleId());

		if (userIds.length == 0) {
			throw new NoSuchUserException(
				StringBundler.concat(
					"No user exists in company ", companyId, " with role ",
					role.getName()));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userIds[0]);

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupLocalService.addCommerceAccountGroup(
				companyId, CommerceAccountConstants.ACCOUNT_GROUP_NAME_GUEST,
				CommerceAccountConstants.ACCOUNT_GROUP_TYPE_GUEST, true, null,
				serviceContext);

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

		// Commerce account rels

		commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
				commerceAccountGroup.getCommerceAccountGroupId());

		// Commerce account group generic rels

		commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			commerceAccountGroup.getCommerceAccountGroupId());

		// Commerce account group

		commerceAccountGroupPersistence.remove(commerceAccountGroup);

		// Resources

		resourceLocalService.deleteResource(
			commerceAccountGroup, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceAccountGroup.getCommerceAccountGroupId());

		return commerceAccountGroup;
	}

	@Override
	public CommerceAccountGroup deleteCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupPersistence.findByPrimaryKey(
				commerceAccountGroupId);

		return commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroup);
	}

	@Override
	public CommerceAccountGroup fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceAccountGroupPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public List<CommerceAccountGroup> getCommerceAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return commerceAccountGroupPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAccountGroup>
		getCommerceAccountGroupsByCommerceAccountId(long commerceAccountId) {

		List<CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				commerceAccountGroupCommerceAccountRelPersistence.
					findByCommerceAccountId(commerceAccountId);

		if (commerceAccountGroupCommerceAccountRels.isEmpty()) {
			return new ArrayList<>();
		}

		Stream<CommerceAccountGroupCommerceAccountRel> stream =
			commerceAccountGroupCommerceAccountRels.stream();

		long[] commerceAccountGroupIds = stream.mapToLong(
			CommerceAccountGroupCommerceAccountRel::getCommerceAccountGroupId
		).toArray();

		return commerceAccountGroupPersistence.findByCommerceAccountGroupIds(
			commerceAccountGroupIds);
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId) {
		return commerceAccountGroupPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return commerceAccountGroupFinder.findAccountUserIdsFromAccountGroupIds(
			commerceAccountGroupIds, start, end);
	}

	@Override
	public List<CommerceAccountGroup> searchCommerceAccountGroups(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceAccountGroups(searchContext);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		searchContext.setKeywords(keywords);

		return searchCommerceAccountGroupsCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccountGroup updateCommerceAccountGroup(
			long commerceAccountGroupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupPersistence.findByPrimaryKey(
				commerceAccountGroupId);

		if (commerceAccountGroup.isSystem()) {
			throw new SystemCommerceAccountGroupException();
		}

		validate(
			serviceContext.getCompanyId(),
			commerceAccountGroup.getCommerceAccountGroupId(), name,
			commerceAccountGroup.getExternalReferenceCode());

		commerceAccountGroup.setName(name);
		commerceAccountGroup.setExpandoBridgeAttributes(serviceContext);

		return commerceAccountGroupPersistence.update(commerceAccountGroup);
	}

	protected SearchContext buildSearchContext(
		long companyId, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CommerceAccountGroup> getCommerceAccountGroups(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceAccountGroup> commerceAccountGroups = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceAccountGroupId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceAccountGroup commerceAccountGroup =
				commerceAccountGroupPersistence.fetchByPrimaryKey(
					commerceAccountGroupId);

			if (commerceAccountGroup == null) {
				commerceAccountGroups = null;

				Indexer<CommerceAccountGroup> indexer =
					IndexerRegistryUtil.getIndexer(CommerceAccountGroup.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceAccountGroup != null) {
				commerceAccountGroups.add(commerceAccountGroup);
			}
		}

		return commerceAccountGroups;
	}

	protected List<CommerceAccountGroup> searchCommerceAccountGroups(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceAccountGroup> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceAccountGroup.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceAccountGroup> commerceAccountGroups =
				getCommerceAccountGroups(hits);

			if (commerceAccountGroups != null) {
				return commerceAccountGroups;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommerceAccountGroupsCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceAccountGroup> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceAccountGroup.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
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
			commerceAccountGroupPersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if ((commerceAccountGroup != null) &&
			(commerceAccountGroup.getCommerceAccountGroupId() !=
				commerceAccountGroupId)) {

			throw new DuplicateCommerceAccountException(
				"There is another commerce account group with external " +
					"reference code " + externalReferenceCode);
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

}