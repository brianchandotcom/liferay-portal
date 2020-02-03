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
import com.liferay.account.exception.AccountEntryDomainsException;
import com.liferay.account.exception.AccountEntryNameException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.base.AccountEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.validator.routines.DomainValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountEntry",
	service = AopService.class
)
public class AccountEntryLocalServiceImpl
	extends AccountEntryLocalServiceBaseImpl {

	@Override
	public void activateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(accountEntryIds, this::activateAccountEntry);
	}

	@Override
	public AccountEntry activateAccountEntry(AccountEntry accountEntry) {
		return updateStatus(accountEntry, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public AccountEntry activateAccountEntry(long accountEntryId)
		throws Exception {

		return activateAccountEntry(getAccountEntry(accountEntryId));
	}

	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status)
		throws PortalException {

		// Account entry

		long accountEntryId = counterLocalService.increment();

		AccountEntry accountEntry = accountEntryPersistence.create(
			accountEntryId);

		User user = userLocalService.getUser(userId);

		accountEntry.setCompanyId(user.getCompanyId());
		accountEntry.setUserId(user.getUserId());
		accountEntry.setUserName(user.getFullName());

		accountEntry.setParentAccountEntryId(parentAccountEntryId);

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			AccountEntry.class.getName(), "name");

		name = StringUtil.shorten(name, nameMaxLength);

		_validateName(name);

		accountEntry.setName(name);

		accountEntry.setDescription(description);

		domains = _validateDomains(domains);

		accountEntry.setDomains(StringUtil.merge(domains, StringPool.COMMA));

		_portal.updateImageId(
			accountEntry, true, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		accountEntry.setStatus(status);

		accountEntry = accountEntryPersistence.update(accountEntry);

		// Group

		groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			AccountEntry.class.getName(), accountEntryId,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, getLocalizationMap(name),
			null, GroupConstants.TYPE_SITE_PRIVATE, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AccountEntry.class.getName(), accountEntryId, false, false, false);

		return accountEntry;
	}

	@Override
	public void deactivateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(accountEntryIds, this::deactivateAccountEntry);
	}

	@Override
	public AccountEntry deactivateAccountEntry(AccountEntry accountEntry) {
		return updateStatus(accountEntry, WorkflowConstants.STATUS_INACTIVE);
	}

	@Override
	public AccountEntry deactivateAccountEntry(long accountEntryId)
		throws Exception {

		return deactivateAccountEntry(getAccountEntry(accountEntryId));
	}

	@Override
	public void deleteAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(
			accountEntryIds, accountEntryLocalService::deleteAccountEntry);
	}

	@Override
	public AccountEntry deleteAccountEntry(AccountEntry accountEntry)
		throws PortalException {

		// Account entry

		accountEntry = super.deleteAccountEntry(accountEntry);

		// Group

		groupLocalService.deleteGroup(accountEntry.getAccountEntryGroup());

		// Resources

		resourceLocalService.deleteResource(
			accountEntry.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			accountEntry.getAccountEntryId());

		return accountEntry;
	}

	@Override
	public AccountEntry deleteAccountEntry(long accountEntryId)
		throws PortalException {

		return deleteAccountEntry(getAccountEntry(accountEntryId));
	}

	@Override
	public List<AccountEntry> getAccountEntries(
		long companyId, int status, int start, int end,
		OrderByComparator<AccountEntry> obc) {

		return accountEntryPersistence.findByC_S(
			companyId, status, start, end, obc);
	}

	@Override
	public int getAccountEntriesCount(long companyId, int status) {
		return accountEntryPersistence.countByC_S(companyId, status);
	}

	@Override
	public BaseModelSearchResult<AccountEntry> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int cur, int delta, String orderByField, boolean reverse) {

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				companyId, keywords, params, cur, delta, orderByField,
				reverse));

		SearchHits searchHits = searchResponse.getSearchHits();

		List<AccountEntry> accountEntries = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long accountEntryId = document.getLong(Field.ENTRY_CLASS_PK);

				return getAccountEntry(accountEntryId);
			});

		return new BaseModelSearchResult<>(
			accountEntries, searchResponse.getTotalHits());
	}

	@Override
	public AccountEntry updateAccountEntry(
			Long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			byte[] logoBytes, int status)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.fetchByPrimaryKey(
			accountEntryId);

		accountEntry.setParentAccountEntryId(parentAccountEntryId);

		_validateName(name);

		accountEntry.setName(name);

		accountEntry.setDescription(description);

		domains = _validateDomains(domains);

		accountEntry.setDomains(StringUtil.merge(domains, StringPool.COMMA));

		_portal.updateImageId(
			accountEntry, !deleteLogo, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		accountEntry.setStatus(status);

		return accountEntryPersistence.update(accountEntry);
	}

	@Override
	public AccountEntry updateStatus(AccountEntry accountEntry, int status) {
		accountEntry.setStatus(status);

		return updateAccountEntry(accountEntry);
	}

	private SearchRequest _getSearchRequest(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int cur, int delta, String orderByField, boolean reverse) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			AccountEntry.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		).withSearchContext(
			searchContext -> _populateSearchContext(
				searchContext, companyId, keywords, params)
		);

		if (cur != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(cur);
			searchRequestBuilder.size(delta);
		}

		if (Validator.isNotNull(orderByField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort fieldSort = _sorts.field(
				_sortFieldBuilder.getSortField(
					AccountEntry.class.getName(), orderByField),
				sortOrder);

			searchRequestBuilder.sorts(fieldSort);
		}

		return searchRequestBuilder.build();
	}

	private void _performActions(
			long[] accountEntryIds,
			ActionableDynamicQuery.PerformActionMethod<AccountEntry>
				performActionMethod)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"accountEntryId", ArrayUtil.toArray(accountEntryIds))));
		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		actionableDynamicQuery.performActions();
	}

	private void _populateSearchContext(
		SearchContext searchContext, long companyId, String keywords,
		LinkedHashMap<String, Object> params) {

		searchContext.setCompanyId(companyId);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (MapUtil.isEmpty(params)) {
			return;
		}

		long[] accountUserIds = (long[])params.get("accountUserIds");

		if (ArrayUtil.isNotEmpty(accountUserIds)) {
			searchContext.setAttribute("accountUserIds", accountUserIds);
		}

		String[] domains = (String[])params.get("domains");

		if (ArrayUtil.isNotEmpty(domains)) {
			searchContext.setAttribute("domains", domains);
		}

		long parentAccountEntryId = GetterUtil.getLong(
			params.get("parentAccountEntryId"),
			AccountConstants.ACCOUNT_ENTRY_ID_ANY);

		if (parentAccountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_ANY) {
			searchContext.setAttribute(
				"parentAccountEntryId", parentAccountEntryId);
		}

		int status = GetterUtil.getInteger(
			params.get("status"), WorkflowConstants.STATUS_APPROVED);

		searchContext.setAttribute("status", status);
	}

	private String[] _validateDomains(String[] domains) throws PortalException {
		if (ArrayUtil.isEmpty(domains)) {
			return domains;
		}

		DomainValidator domainValidator = DomainValidator.getInstance();

		for (String domain : domains) {
			if (!domainValidator.isValid(domain)) {
				throw new AccountEntryDomainsException();
			}
		}

		return ArrayUtil.distinct(domains);
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new AccountEntryNameException();
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserFileUploadsSettings _userFileUploadsSettings;

}