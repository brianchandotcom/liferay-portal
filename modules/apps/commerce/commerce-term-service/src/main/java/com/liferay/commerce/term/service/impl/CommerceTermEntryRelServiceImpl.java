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

package com.liferay.commerce.term.service.impl;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.base.CommerceTermEntryRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceTermEntryRel"
	},
	service = AopService.class
)
public class CommerceTermEntryRelServiceImpl
	extends CommerceTermEntryRelServiceBaseImpl {

	@Override
	public CommerceTermEntryRel addCommerceTermEntryRel(
			String className, long classPK, long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.UPDATE);

		return commerceTermEntryRelLocalService.addCommerceTermEntryRel(
			getUserId(), className, classPK, commerceTermEntryId);
	}

	@Override
	public void deleteCommerceTermEntryRel(long commerceTermEntryRelId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelLocalService.getCommerceTermEntryRel(
				commerceTermEntryRelId);

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceTermEntryRel.getCommerceTermEntryId(), ActionKeys.UPDATE);

		commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
			commerceTermEntryRel);
	}

	@Override
	public void deleteCommerceTermEntryRels(
			String className, long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.UPDATE);

		commerceTermEntryRelLocalService.deleteCommerceTermEntryRels(
			className, commerceTermEntryId);
	}

	@Override
	public void deleteCommerceTermEntryRelsByCommerceTermEntryId(
			long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.UPDATE);

		commerceTermEntryRelLocalService.deleteCommerceTermEntryRels(
			commerceTermEntryId);
	}

	@Override
	public CommerceTermEntryRel fetchCommerceTermEntryRel(
			String className, long classPK, long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.fetchCommerceTermEntryRel(
			className, classPK, commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceOrderTypeCommerceTermEntryRels(
			long commerceTermEntryId, String keywords, int start, int end)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.
			getCommerceOrderTypeCommerceTermEntryRels(
				commerceTermEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceTermEntryRelsCount(
			long commerceTermEntryId, String keywords)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.
			getCommerceOrderTypeCommerceTermEntryRelsCount(
				commerceTermEntryId, keywords);
	}

	@Override
	public CommerceTermEntryRel getCommerceTermEntryRel(
			long commerceTermEntryRelId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelLocalService.getCommerceTermEntryRel(
				commerceTermEntryRelId);

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceTermEntryRel.getCommerceTermEntryId(), ActionKeys.VIEW);

		return commerceTermEntryRel;
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
			long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.getCommerceTermEntryRels(
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
			long commerceTermEntryId, int start, int end,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.getCommerceTermEntryRels(
			commerceTermEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTermEntryRelsCount(long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryRelLocalService.getCommerceTermEntryRelsCount(
			commerceTermEntryId);
	}

	private static volatile ModelResourcePermission<CommerceTermEntry>
		_commerceTermEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceTermEntryRelServiceImpl.class,
				"_commerceTermEntryModelResourcePermission",
				CommerceTermEntry.class);

}