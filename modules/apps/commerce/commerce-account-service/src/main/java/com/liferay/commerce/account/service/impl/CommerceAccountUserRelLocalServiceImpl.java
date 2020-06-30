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

import com.liferay.commerce.account.configuration.CommerceAccountServiceConfiguration;
import com.liferay.commerce.account.exception.CommerceAccountTypeException;
import com.liferay.commerce.account.exception.CommerceAccountUserRelEmailAddressException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.base.CommerceAccountUserRelLocalServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountUserRelLocalServiceImpl
	extends CommerceAccountUserRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelLocalService.addCommerceAccountUserRel(
				commerceAccountId, commerceAccountUserId, serviceContext);

		updateRoles(
			commerceAccountUserRel.getCommerceAccountId(),
			commerceAccountUserRel.getCommerceAccountUserId(), roleIds);

		return commerceAccountUserRel;
	}

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId,
			ServiceContext serviceContext)
		throws PortalException {

		validate(commerceAccountId, commerceAccountUserId);

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceAccountUserRelPK commerceAccountUserRelPK =
			new CommerceAccountUserRelPK(
				commerceAccountId, commerceAccountUserId);

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelPersistence.fetchByPrimaryKey(
				commerceAccountUserRelPK);

		if (commerceAccountUserRel == null) {
			commerceAccountUserRel = commerceAccountUserRelPersistence.create(
				commerceAccountUserRelPK);
		}

		commerceAccountUserRel.setCommerceAccountId(commerceAccountId);
		commerceAccountUserRel.setCommerceAccountUserId(commerceAccountUserId);
		commerceAccountUserRel.setCompanyId(user.getCompanyId());
		commerceAccountUserRel.setUserId(user.getUserId());
		commerceAccountUserRel.setUserName(user.getFullName());

		commerceAccountUserRel = commerceAccountUserRelPersistence.update(
			commerceAccountUserRel);

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

		Group group = commerceAccountLocalService.getCommerceAccountGroup(
			commerceAccountId);

		if (userIds != null) {
			for (long userId : userIds) {
				User user = userLocalService.getUser(userId);

				commerceAccountUserRelLocalService.addCommerceAccountUserRel(
					commerceAccountId, user.getUserId(), serviceContext);

				if (!ArrayUtil.contains(
						user.getGroupIds(), group.getGroupId())) {

					userLocalService.addGroupUsers(
						group.getGroupId(), new long[] {userId});
				}

				if (!ArrayUtil.contains(
						user.getGroupIds(), serviceContext.getScopeGroupId())) {

					userLocalService.addGroupUsers(
						serviceContext.getScopeGroupId(), new long[] {userId});
				}

				if (roleIds != null) {
					userGroupRoleLocalService.addUserGroupRoles(
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

		User user = userLocalService.getUser(userId);

		Set<Role> roles = new HashSet<>();

		for (String siteRole : siteRoles) {
			Role role = roleLocalService.fetchRole(
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
			commerceAccountUserRelPersistence.findByCommerceAccountUserId(
				userId);

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			CommerceAccount commerceAccount =
				commerceAccountLocalService.getCommerceAccount(
					commerceAccountUserRel.getCommerceAccountId());

			userGroupRoleLocalService.addUserGroupRoles(
				userId, commerceAccount.getCommerceAccountGroupId(), roleIds);
		}
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException {

		for (long userId : userIds) {
			CommerceAccountUserRelPK commerceAccountUserRelPK =
				new CommerceAccountUserRelPK(commerceAccountId, userId);

			commerceAccountUserRelPersistence.remove(commerceAccountUserRelPK);
		}

		CommerceAccount commerceAccount =
			commerceAccountLocalService.getCommerceAccount(commerceAccountId);

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, commerceAccount.getCommerceAccountGroupId());
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId) {

		List<CommerceAccountUserRel> commerceAccountUserRels =
			commerceAccountUserRelPersistence.findByCommerceAccountId(
				commerceAccountId);

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			commerceAccountUserRelPersistence.remove(commerceAccountUserRel);
		}
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId) {

		commerceAccountUserRelPersistence.removeByCommerceAccountUserId(userId);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId) {

		return commerceAccountUserRelPersistence.findByCommerceAccountId(
			commerceAccountId);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
		long commerceAccountId, int start, int end) {

		return commerceAccountUserRelPersistence.findByCommerceAccountId(
			commerceAccountId, start, end);
	}

	@Override
	public List<CommerceAccountUserRel>
		getCommerceAccountUserRelsByCommerceAccountUserId(
			long commerceAccountUserId) {

		return commerceAccountUserRelPersistence.findByCommerceAccountUserId(
			commerceAccountUserId);
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId) {
		return commerceAccountUserRelPersistence.countByCommerceAccountId(
			commerceAccountId);
	}

	@Override
	public CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		User user = null;

		if (Validator.isNotNull(userExternalReferenceCode)) {
			user = userLocalService.fetchUserByReferenceCode(
				serviceContext.getCompanyId(), userExternalReferenceCode);
		}

		if (user == null) {
			if (Validator.isNull(emailAddress)) {
				throw new CommerceAccountUserRelEmailAddressException();
			}

			user = userLocalService.fetchUserByEmailAddress(
				serviceContext.getCompanyId(), emailAddress);
		}

		if (user == null) {
			Group group = commerceAccountLocalService.getCommerceAccountGroup(
				commerceAccountId);

			user = userLocalService.addUserWithWorkflow(
				serviceContext.getUserId(), serviceContext.getCompanyId(), true,
				StringPool.BLANK, StringPool.BLANK, true, StringPool.BLANK,
				emailAddress, 0, StringPool.BLANK, serviceContext.getLocale(),
				emailAddress, StringPool.BLANK, emailAddress, 0, 0, true, 1, 1,
				1970, StringPool.BLANK,
				new long[] {
					group.getGroupId(), serviceContext.getScopeGroupId()
				},
				null, null, null, true, serviceContext);

			user.setExternalReferenceCode(userExternalReferenceCode);

			userLocalService.updateUser(user);
		}

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelLocalService.addCommerceAccountUserRel(
				commerceAccountId, user.getUserId(), serviceContext);

		updateRoles(
			commerceAccountUserRel.getCommerceAccountId(),
			commerceAccountUserRel.getCommerceAccountUserId(), roleIds);

		return commerceAccountUserRel;
	}

	protected void updateRoles(
			long commerceAccountId, long userId, long[] roleIds)
		throws PortalException {

		Group group = commerceAccountLocalService.getCommerceAccountGroup(
			commerceAccountId);

		if (roleIds != null) {
			userGroupRoleLocalService.addUserGroupRoles(
				userId, group.getGroupId(), roleIds);
		}
	}

	protected void validate(long commerceAccountId, long commerceAccountUserId)
		throws PortalException {

		CommerceAccount commerceAccount =
			commerceAccountLocalService.getCommerceAccount(commerceAccountId);

		if (commerceAccount.isPersonalAccount()) {
			CommerceAccountUserRel commerceAccountUserRel =
				commerceAccountUserRelPersistence.
					fetchByCommerceAccountId_First(commerceAccountId, null);

			if ((commerceAccountUserRel != null) &&
				(commerceAccountUserRel.getCommerceAccountUserId() ==
					commerceAccountUserId)) {

				throw new CommerceAccountTypeException();
			}

			List<CommerceAccountUserRel> commerceAccountUserRels =
				commerceAccountUserRelPersistence.findByCommerceAccountUserId(
					commerceAccountUserId);

			for (CommerceAccountUserRel curCommerceAccountUserRel :
					commerceAccountUserRels) {

				CommerceAccount curCommerceAccount =
					commerceAccountLocalService.getCommerceAccount(
						curCommerceAccountUserRel.getCommerceAccountId());

				if (curCommerceAccount.isPersonalAccount()) {
					throw new CommerceAccountTypeException();
				}
			}
		}
	}

	@ServiceReference(type = ConfigurationProvider.class)
	private ConfigurationProvider _configurationProvider;

}