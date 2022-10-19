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
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.commerce.account.configuration.CommerceAccountServiceConfiguration;
import com.liferay.commerce.account.exception.CommerceAccountTypeException;
import com.liferay.commerce.account.exception.CommerceAccountUserRelEmailAddressException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.commerce.account.model.impl.CommerceAccountUserRelImpl;
import com.liferay.commerce.account.service.base.CommerceAccountUserRelLocalServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccountUserRel",
	service = AopService.class
)
public class CommerceAccountUserRelLocalServiceImpl
	extends CommerceAccountUserRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelLocalService.addCommerceAccountUserRel(
				commerceAccountId, commerceAccountUserId, serviceContext);

		_updateRoles(
			commerceAccountUserRel.getCommerceAccountId(),
			commerceAccountUserRel.getCommerceAccountUserId(), roleIds);

		return commerceAccountUserRel;
	}

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(commerceAccountId, commerceAccountUserId);

		CommerceAccountUserRel commerceAccountUserRel =
			CommerceAccountUserRelImpl.fromAccountEntryUserRel(
				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					commerceAccountId, commerceAccountUserId));

		// Default roles

		commerceAccountUserRelLocalService.addDefaultRoles(
			commerceAccountUserId);

		return commerceAccountUserRel;
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds, ServiceContext serviceContext)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccountId);

		Group group = accountEntry.getAccountEntryGroup();

		if (group == null) {
			throw new PortalException();
		}

		if (userIds != null) {
			for (long userId : userIds) {
				User user = _userLocalService.getUser(userId);

				commerceAccountUserRelLocalService.addCommerceAccountUserRel(
					commerceAccountId, user.getUserId(), serviceContext);

				if (!ArrayUtil.contains(
						user.getGroupIds(), group.getGroupId())) {

					_userLocalService.addGroupUsers(
						group.getGroupId(), new long[] {userId});
				}

				if (!ArrayUtil.contains(
						user.getGroupIds(), serviceContext.getScopeGroupId())) {

					_userLocalService.addGroupUsers(
						serviceContext.getScopeGroupId(), new long[] {userId});
				}

				if (roleIds != null) {
					_userGroupRoleLocalService.addUserGroupRoles(
						user.getUserId(), group.getGroupId(), roleIds);
				}
			}
		}

		if (emailAddresses != null) {
			for (String emailAddress : emailAddresses) {
				commerceAccountUserRelLocalService.inviteUser(
					commerceAccountId, emailAddress, roleIds, StringPool.BLANK,
					serviceContext);
			}
		}
	}

	@Override
	public void addDefaultRoles(long userId) throws PortalException {
		CommerceAccountServiceConfiguration
			commerceAccountServiceConfiguration =
				_configurationProvider.getSystemConfiguration(
					CommerceAccountServiceConfiguration.class);

		String[] siteRoles = commerceAccountServiceConfiguration.siteRoles();

		if ((siteRoles == null) && ArrayUtil.isEmpty(siteRoles)) {
			return;
		}

		User user = _userLocalService.getUser(userId);

		Set<Role> roles = new HashSet<>();

		for (String siteRole : siteRoles) {
			Role role = _roleLocalService.fetchRole(
				user.getCompanyId(), siteRole);

			if ((role == null) || (role.getType() != RoleConstants.TYPE_SITE)) {
				continue;
			}

			roles.add(role);
		}

		Stream<Role> stream = roles.stream();

		long[] roleIds = stream.mapToLong(
			Role::getRoleId
		).toArray();

		List<CommerceAccountUserRel> commerceAccountUserRels =
			commerceAccountUserRelLocalService.
				getCommerceAccountUserRelsByCommerceAccountUserId(userId);

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			CommerceAccount commerceAccount =
				CommerceAccountImpl.fromAccountEntry(
					_accountEntryLocalService.getAccountEntry(
						commerceAccountUserRel.getCommerceAccountId()));

			_userGroupRoleLocalService.addUserGroupRoles(
				userId, commerceAccount.getCommerceAccountGroupId(), roleIds);
		}
	}

	@Override
	public CommerceAccountUserRel createCommerceAccountUserRel(
		CommerceAccountUserRelPK commerceAccountUserRelPK) {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.createAccountEntryUserRel(
				counterLocalService.increment());

		accountEntryUserRel.setAccountEntryId(
			commerceAccountUserRelPK.getCommerceAccountId());
		accountEntryUserRel.setAccountUserId(
			commerceAccountUserRelPK.getCommerceAccountUserId());

		return CommerceAccountUserRelImpl.fromAccountEntryUserRel(
			accountEntryUserRel);
	}

	@Override
	public CommerceAccountUserRel deleteCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				commerceAccountUserRel.getCommerceAccountId(),
				commerceAccountUserRel.getCommerceAccountUserId());

		return CommerceAccountUserRelImpl.fromAccountEntryUserRel(
			_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
				accountEntryUserRel));
	}

	@Override
	public CommerceAccountUserRel deleteCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.getAccountEntryUserRel(
				commerceAccountUserRelPK.getCommerceAccountId(),
				commerceAccountUserRelPK.getCommerceAccountUserId());

		return CommerceAccountUserRelImpl.fromAccountEntryUserRel(
			_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
				accountEntryUserRel.getAccountEntryUserRelId()));
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException {

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			commerceAccountId, userIds);

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.getAccountEntry(commerceAccountId));

		_userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, commerceAccount.getCommerceAccountGroupId());
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId) {

		_accountEntryUserRelLocalService.
			deleteAccountEntryUserRelsByAccountEntryId(commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId) {

		_accountEntryUserRelLocalService.
			deleteAccountEntryUserRelsByAccountUserId(userId);
	}

	@Override
	public CommerceAccountUserRel fetchCommerceAccountUserRel(
		CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return CommerceAccountUserRelImpl.fromAccountEntryUserRel(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				commerceAccountUserRelPK.getCommerceAccountId(),
				commerceAccountUserRelPK.getCommerceAccountUserId()));
	}

	@Override
	public CommerceAccountUserRel getCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		return CommerceAccountUserRelImpl.fromAccountEntryUserRel(
			_accountEntryUserRelLocalService.getAccountEntryUserRel(
				commerceAccountUserRelPK.getCommerceAccountId(),
				commerceAccountUserRelPK.getCommerceAccountUserId()));
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		int start, int end) {

		return TransformUtil.transform(
			_accountEntryUserRelLocalService.getAccountEntryUserRels(
				start, end),
			CommerceAccountUserRelImpl::fromAccountEntryUserRel);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId) {

		return TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(commerceAccountId),
			CommerceAccountUserRelImpl::fromAccountEntryUserRel);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId, int start, int end) {

		return TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					commerceAccountId, start, end),
			CommerceAccountUserRelImpl::fromAccountEntryUserRel);
	}

	@Override
	public List<CommerceAccountUserRel>
		getCommerceAccountUserRelsByCommerceAccountUserId(
			long commerceAccountUserId) {

		return TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(commerceAccountUserId),
			CommerceAccountUserRelImpl::fromAccountEntryUserRel);
	}

	@Override
	public int getCommerceAccountUserRelsCount() {
		return _accountEntryUserRelLocalService.getAccountEntryUserRelsCount();
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId) {
		return (int)
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsCountByAccountEntryId(commerceAccountId);
	}

	@Override
	public CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		User user = null;

		if (Validator.isNotNull(userExternalReferenceCode)) {
			user = _userLocalService.fetchUserByReferenceCode(
				serviceContext.getCompanyId(), userExternalReferenceCode);
		}

		if (user == null) {
			if (Validator.isNull(emailAddress)) {
				throw new CommerceAccountUserRelEmailAddressException();
			}

			user = _userLocalService.fetchUserByEmailAddress(
				serviceContext.getCompanyId(), emailAddress);
		}

		if (user == null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(commerceAccountId);

			Group group = accountEntry.getAccountEntryGroup();

			if (group == null) {
				throw new PortalException();
			}

			user = _userLocalService.addUserWithWorkflow(
				serviceContext.getUserId(), serviceContext.getCompanyId(), true,
				StringPool.BLANK, StringPool.BLANK, true, StringPool.BLANK,
				emailAddress, serviceContext.getLocale(), emailAddress,
				StringPool.BLANK, emailAddress, 0, 0, true, 1, 1, 1970,
				StringPool.BLANK,
				new long[] {
					group.getGroupId(), serviceContext.getScopeGroupId()
				},
				null, null, null, true, serviceContext);

			user.setExternalReferenceCode(userExternalReferenceCode);

			_userLocalService.updateUser(user);
		}

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelLocalService.addCommerceAccountUserRel(
				commerceAccountId, user.getUserId(), serviceContext);

		_updateRoles(
			commerceAccountUserRel.getCommerceAccountId(),
			commerceAccountUserRel.getCommerceAccountUserId(), roleIds);

		return commerceAccountUserRel;
	}

	@Override
	public CommerceAccountUserRel updateCommerceAccountUserRel(
		CommerceAccountUserRel commerceAccountUserRel) {

		throw new UnsupportedOperationException();
	}

	private void _updateRoles(
			long commerceAccountId, long userId, long[] roleIds)
		throws PortalException {

		if (roleIds != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(commerceAccountId);

			Group group = accountEntry.getAccountEntryGroup();

			if (group == null) {
				throw new PortalException();
			}

			_userGroupRoleLocalService.addUserGroupRoles(
				userId, group.getGroupId(), roleIds);
		}
	}

	private void _validate(long commerceAccountId, long commerceAccountUserId)
		throws PortalException {

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			_accountEntryLocalService.getAccountEntry(commerceAccountId));

		if (commerceAccount.isPersonalAccount()) {
			List<CommerceAccountUserRel> commerceAccountUserRels =
				getCommerceAccountUserRelsByCommerceAccountUserId(
					commerceAccountUserId);

			for (CommerceAccountUserRel curCommerceAccountUserRel :
					commerceAccountUserRels) {

				CommerceAccount curCommerceAccount =
					CommerceAccountImpl.fromAccountEntry(
						_accountEntryLocalService.getAccountEntry(
							curCommerceAccountUserRel.getCommerceAccountId()));

				if (curCommerceAccount.isPersonalAccount()) {
					throw new CommerceAccountTypeException();
				}
			}
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}