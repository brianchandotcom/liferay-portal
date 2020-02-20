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

package com.liferay.account.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.rest.dto.v1_0.AccountUser;
import com.liferay.account.rest.internal.odata.entity.v1_0.AccountUserEntityModel;
import com.liferay.account.rest.resource.v1_0.AccountUserResource;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-user.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountUserResource.class
)
public class AccountUserResourceImpl
	extends BaseAccountUserResourceImpl implements EntityModelResource {

	@Override
	public Page<AccountUser> getAccountUsersPage(
			Long accountId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"accountEntryIds", String.valueOf(accountId)),
					BooleanClauseOccur.MUST);
			},
			filter, User.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toAccountUser(
				_userLocalService.getUserById(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _accountUserEntityModel;
	}

	@Override
	public AccountUser postAccountUser(Long accountId, AccountUser accountUser)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountId, contextUser.getUserId(), accountUser.getScreenName(),
				accountUser.getEmailAddress(),
				contextAcceptLanguage.getPreferredLocale(),
				accountUser.getFirstName(), accountUser.getMiddleName(),
				accountUser.getLastName(), _getPrefixId(accountUser),
				_getSuffixId(accountUser));

		return _toAccountUser(
			_userLocalService.getUser(accountEntryUserRel.getAccountUserId()));
	}

	private long _getListTypeId(String value, String type) {
		ListType listType = _listTypeLocalService.addListType(value, type);

		return listType.getListTypeId();
	}

	private long _getPrefixId(AccountUser accountUser) {
		return Optional.ofNullable(
			accountUser.getPrefix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_PREFIX)
		).orElse(
			0L
		);
	}

	private long _getSuffixId(AccountUser accountUser) {
		return Optional.ofNullable(
			accountUser.getSuffix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_SUFFIX)
		).orElse(
			0L
		);
	}

	private AccountUser _toAccountUser(User user) throws Exception {
		Contact contact = user.getContact();

		return new AccountUser() {
			{
				emailAddress = user.getEmailAddress();
				firstName = user.getFirstName();
				id = user.getUserId();
				lastName = user.getLastName();
				middleName = user.getMiddleName();
				screenName = user.getScreenName();

				setPrefix(
					() -> {
						long prefixId = contact.getPrefixId();

						if (prefixId <= 0) {
							return null;
						}

						ListType prefixListType =
							_listTypeLocalService.getListType(prefixId);

						return prefixListType.getName();
					});
				setSuffix(
					() -> {
						long suffixId = contact.getSuffixId();

						if (suffixId <= 0) {
							return null;
						}

						ListType suffixListType =
							_listTypeLocalService.getListType(suffixId);

						return suffixListType.getName();
					});
			}
		};
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private final AccountUserEntityModel _accountUserEntityModel =
		new AccountUserEntityModel();

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}