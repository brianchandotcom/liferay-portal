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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.commerce.order.rule.service.base.CommerceOrderRuleEntryRelServiceBaseImpl;
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
		"json.web.service.context.path=CommerceOrderRuleEntryRel"
	},
	service = AopService.class
)
public class CommerceOrderRuleEntryRelServiceImpl
	extends CommerceOrderRuleEntryRelServiceBaseImpl {

	@Override
	public CommerceOrderRuleEntryRel addCommerceOrderRuleEntryRel(
			String className, long classPK, long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId,
			ActionKeys.UPDATE);

		return commerceOrderRuleEntryRelLocalService.
			addCommerceOrderRuleEntryRel(
				getUserId(), className, classPK, commerceOrderRuleEntryId);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws PortalException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			commerceOrderRuleEntryRelLocalService.getCommerceOrderRuleEntryRel(
				commerceOrderRuleEntryRelId);

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId(),
			ActionKeys.UPDATE);

		commerceOrderRuleEntryRelLocalService.deleteCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRel);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId,
			ActionKeys.UPDATE);

		commerceOrderRuleEntryRelLocalService.deleteCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId);
	}

	@Override
	public CommerceOrderRuleEntryRel fetchCommerceOrderRuleEntryRel(
			String className, long classPK, long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			fetchCommerceOrderRuleEntryRel(
				className, classPK, commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getAccountEntryCommerceOrderRuleEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getAccountEntryCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getAccountEntryCommerceOrderRuleEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getAccountEntryCommerceOrderRuleEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getAccountGroupCommerceOrderRuleEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getAccountGroupCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getAccountGroupCommerceOrderRuleEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getAccountGroupCommerceOrderRuleEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceChannelCommerceOrderRuleEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceChannelCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceChannelCommerceOrderRuleEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceChannelCommerceOrderRuleEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public CommerceOrderRuleEntryRel getCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws PortalException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			commerceOrderRuleEntryRelLocalService.getCommerceOrderRuleEntryRel(
				commerceOrderRuleEntryRelId);

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId(),
			ActionKeys.VIEW);

		return commerceOrderRuleEntryRel;
	}

	@Override
	public List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
			long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRels(commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
			long commerceOrderRuleEntryId, int start, int end,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderRuleEntryRelsCount(long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRelsCount(commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceOrderTypeCommerceOrderRuleEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceOrderTypeCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceOrderRuleEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId, ActionKeys.VIEW);

		return commerceOrderRuleEntryRelLocalService.
			getCommerceOrderTypeCommerceOrderRuleEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	private static volatile ModelResourcePermission<CommerceOrderRuleEntry>
		_commerceOrderRuleEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderRuleEntryRelServiceImpl.class,
				"_commerceOrderRuleEntryModelResourcePermission",
				CommerceOrderRuleEntry.class);

}