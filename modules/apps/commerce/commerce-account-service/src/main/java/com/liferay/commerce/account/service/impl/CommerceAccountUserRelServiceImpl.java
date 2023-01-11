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

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.base.CommerceAccountUserRelServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceAccountUserRel"
	},
	service = AopService.class
)
public class CommerceAccountUserRelServiceImpl
	extends CommerceAccountUserRelServiceBaseImpl {

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		return commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds, ServiceContext serviceContext)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		commerceAccountUserRelLocalService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	@Override
	public void deleteCommerceAccountUserRel(
			long commerceAccountId, long userId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		CommerceAccountUserRelPK commerceAccountUserRelPK =
			new CommerceAccountUserRelPK(commerceAccountId, userId);

		commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public void deleteCommerceAccountUserRels(long commerceAccountId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		commerceAccountUserRelLocalService.deleteCommerceAccountUserRels(
			commerceAccountId, userIds);
	}

	@Override
	public CommerceAccountUserRel fetchCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		CommerceAccountUserRel commerceAccountUserRel =
			commerceAccountUserRelLocalService.fetchCommerceAccountUserRel(
				commerceAccountUserRelPK);

		if (commerceAccountUserRel != null) {
			_accountEntryModelResourcePermission.check(
				getPermissionChecker(),
				commerceAccountUserRelPK.getCommerceAccountId(),
				AccountActionKeys.VIEW_USERS);
		}

		return commerceAccountUserRel;
	}

	@Override
	public CommerceAccountUserRel getCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceAccountUserRelPK.getCommerceAccountId(),
			AccountActionKeys.VIEW_USERS);

		return commerceAccountUserRelLocalService.getCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
			long commerceAccountId, int start, int end)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.VIEW_USERS);

		return commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.VIEW_USERS);

		return commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount(commerceAccountId);
	}

	@Override
	public CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			AccountActionKeys.ASSIGN_USERS);

		return commerceAccountUserRelLocalService.inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

}