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

package com.liferay.headless.commerce.admin.account.internal.resource.v1_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountRole;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountMemberDTOConverter;
import com.liferay.headless.commerce.admin.account.internal.util.v1_0.AccountMemberUtil;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountMemberResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-member.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountMemberResource.class, NestedFieldSupport.class}
)
public class AccountMemberResourceImpl
	extends BaseAccountMemberResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteAccountByExternalReferenceCodeAccountMember(
			String externalReferenceCode, Long userId)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		_commerceAccountUserRelService.deleteCommerceAccountUserRel(
			accountEntry.getAccountEntryId(), userId);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountIdAccountMember(Long id, Long userId)
		throws Exception {

		_commerceAccountUserRelService.deleteCommerceAccountUserRel(id, userId);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public AccountMember getAccountByExternalReferenceCodeAccountMember(
			String externalReferenceCode, Long userId)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		CommerceAccountUserRel commerceAccountUserRel =
			_commerceAccountUserRelService.getCommerceAccountUserRel(
				new CommerceAccountUserRelPK(
					accountEntry.getAccountEntryId(), userId));

		return _accountMemberDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountUserRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Page<AccountMember>
			getAccountByExternalReferenceCodeAccountMembersPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_commerceAccountUserRelService.getCommerceAccountUserRels(
				accountEntry.getAccountEntryId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commerceAccountUserRelService.getCommerceAccountUserRelsCount(
				accountEntry.getAccountEntryId());

		return Page.of(
			_toAccountMembers(commerceAccountUserRels), pagination, totalItems);
	}

	@Override
	public AccountMember getAccountIdAccountMember(Long id, Long userId)
		throws Exception {

		return _accountMemberDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				new CommerceAccountUserRelPK(id, userId),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Account.class, value = "accountMembers")
	@Override
	public Page<AccountMember> getAccountIdAccountMembersPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_commerceAccountUserRelService.getCommerceAccountUserRels(
				id, pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceAccountUserRelService.getCommerceAccountUserRelsCount(id);

		return Page.of(
			_toAccountMembers(commerceAccountUserRels), pagination, totalItems);
	}

	@Override
	public Response patchAccountByExternalReferenceCodeAccountMember(
			String externalReferenceCode, Long userId,
			AccountMember accountMember)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		_updateCommerceAccountUserRel(
			accountEntry, _userLocalService.getUser(userId), accountMember);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchAccountIdAccountMember(
			Long id, Long userId, AccountMember accountMember)
		throws Exception {

		_updateCommerceAccountUserRel(
			_accountEntryLocalService.getAccountEntry(id),
			_userLocalService.getUser(userId), accountMember);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public AccountMember postAccountByExternalReferenceCodeAccountMember(
			String externalReferenceCode, AccountMember accountMember)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		CommerceAccountUserRel commerceAccountUserRel =
			AccountMemberUtil.addCommerceAccountUserRel(
				_commerceAccountUserRelService, accountMember, accountEntry,
				AccountMemberUtil.getUser(
					_userLocalService, accountMember,
					contextCompany.getCompanyId()),
				_serviceContextHelper.getServiceContext());

		return _accountMemberDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountUserRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public AccountMember postAccountIdAccountMember(
			Long id, AccountMember accountMember)
		throws Exception {

		CommerceAccountUserRel commerceAccountUserRel =
			AccountMemberUtil.addCommerceAccountUserRel(
				_commerceAccountUserRelService, accountMember,
				_accountEntryLocalService.getAccountEntry(id),
				AccountMemberUtil.getUser(
					_userLocalService, accountMember,
					contextCompany.getCompanyId()),
				_serviceContextHelper.getServiceContext());

		return _accountMemberDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountUserRel.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<AccountMember> _toAccountMembers(
			List<CommerceAccountUserRel> commerceAccountUserRels)
		throws Exception {

		List<AccountMember> accountMembers = new ArrayList<>();

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			accountMembers.add(
				_accountMemberDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						commerceAccountUserRel.getPrimaryKey(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return accountMembers;
	}

	private void _updateCommerceAccountUserRel(
			AccountEntry accountEntry, User user, AccountMember accountMember)
		throws Exception {

		_userGroupRoleLocalService.deleteUserGroupRoles(
			user.getUserId(),
			new long[] {accountEntry.getAccountEntryGroupId()});

		AccountRole[] accountRoles = accountMember.getAccountRoles();

		if (accountRoles != null) {
			Stream<AccountRole> accountRoleStream = Arrays.stream(accountRoles);

			long[] roleIds = accountRoleStream.mapToLong(
				AccountRole::getRoleId
			).toArray();

			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), accountEntry.getAccountEntryGroupId(),
				roleIds);
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AccountMemberDTOConverter _accountMemberDTOConverter;

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}