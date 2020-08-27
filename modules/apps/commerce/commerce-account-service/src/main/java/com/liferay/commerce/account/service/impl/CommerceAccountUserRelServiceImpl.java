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

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.base.CommerceAccountUserRelServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountUserRelServiceImpl
	extends CommerceAccountUserRelServiceBaseImpl {

	@Override
	public CommerceAccountUserRel addCommerceAccountUserRel(
			long commerceAccountId, long commerceAccountUserId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

		return commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds, ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

		commerceAccountUserRelLocalService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	@Override
	public void deleteCommerceAccountUserRel(
			long commerceAccountId, long userId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

		CommerceAccountUserRelPK commerceAccountUserRelPK =
			new CommerceAccountUserRelPK(commerceAccountId, userId);

		commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public void deleteCommerceAccountUserRels(long commerceAccountId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

		commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

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
			_commerceAccountModelResourcePermission.check(
				getPermissionChecker(),
				commerceAccountUserRelPK.getCommerceAccountId(),
				CommerceAccountActionKeys.VIEW_MEMBERS);
		}

		return commerceAccountUserRel;
	}

	@Override
	public CommerceAccountUserRel getCommerceAccountUserRel(
			CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(),
			commerceAccountUserRelPK.getCommerceAccountId(),
			CommerceAccountActionKeys.VIEW_MEMBERS);

		return commerceAccountUserRelLocalService.getCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels(
			long commerceAccountId, int start, int end)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.VIEW_MEMBERS);

		return commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.VIEW_MEMBERS);

		return commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount(commerceAccountId);
	}

	@Override
	public CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_MEMBERS);

		return commerceAccountUserRelLocalService.inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceAccount>
		_commerceAccountModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceAccountUserRelServiceImpl.class,
				"_commerceAccountModelResourcePermission",
				CommerceAccount.class);

}