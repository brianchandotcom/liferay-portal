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
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.base.CommerceAccountOrganizationRelServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountOrganizationRelServiceImpl
	extends CommerceAccountOrganizationRelServiceBaseImpl {

	@Override
	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		return commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(
				commerceAccountId, organizationId, serviceContext);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds, serviceContext);
	}

	@Override
	public void deleteCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK =
			new CommerceAccountOrganizationRelPK(
				commerceAccountId, organizationId);

		commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public CommerceAccountOrganizationRel fetchCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException {

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			commerceAccountOrganizationRelLocalService.
				fetchCommerceAccountOrganizationRel(
					commerceAccountOrganizationRelPK);

		if (commerceAccountOrganizationRel != null) {
			_commerceAccountModelResourcePermission.check(
				getPermissionChecker(),
				commerceAccountOrganizationRelPK.getCommerceAccountId(),
				CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);
		}

		return commerceAccountOrganizationRel;
	}

	@Override
	public CommerceAccountOrganizationRel getCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(),
			commerceAccountOrganizationRelPK.getCommerceAccountId(),
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRel(commerceAccountOrganizationRelPK);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(long commerceAccountId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(
				long commerceAccountId, int start, int end)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId, start, end);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRelsByOrganizationId(
				long organizationId, int start, int end)
		throws PortalException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationId(
				organizationId, start, end);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
			long organizationId)
		throws PortalException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId,
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);

		return commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsCount(commerceAccountId);
	}

	private static volatile ModelResourcePermission<CommerceAccount>
		_commerceAccountModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceAccountOrganizationRelServiceImpl.class,
				"_commerceAccountModelResourcePermission",
				CommerceAccount.class);

}