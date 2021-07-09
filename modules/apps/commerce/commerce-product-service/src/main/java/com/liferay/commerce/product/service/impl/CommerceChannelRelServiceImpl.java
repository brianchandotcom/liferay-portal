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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.base.CommerceChannelRelServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelRelServiceImpl
	extends CommerceChannelRelServiceBaseImpl {

	@Override
	public CommerceChannelRel addCommerceChannelRel(
			String className, long classPK, long commerceChannelId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelRelLocalService.addCommerceChannelRel(
			className, classPK, commerceChannelId, serviceContext);
	}

	@Override
	public void deleteCommerceChannelRel(long commerceChannelRelId)
		throws PortalException {

		CommerceChannelRel commerceChannelRel =
			commerceChannelRelLocalService.getCommerceChannelRel(
				commerceChannelRelId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelRel.getCommerceChannelId(),
			ActionKeys.UPDATE);

		commerceChannelRelLocalService.deleteCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public void deleteCommerceChannelRels(String className, long classPK)
		throws PortalException {

		List<CommerceChannelRel> commerceChannelRels =
			commerceChannelRelLocalService.getCommerceChannelRels(
				className, classPK, StringPool.BLANK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			commerceChannelRelLocalService.deleteCommerceChannelRel(
				commerceChannelRel.getCommerceChannelRelId());
		}
	}

	@Override
	public CommerceChannelRel fetchCommerceChannelRel(
			String className, long classPK, long commerceChannelId)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.VIEW);

		return commerceChannelRelLocalService.fetchCommerceChannelRel(
			className, classPK, commerceChannelId);
	}

	@Override
	public CommerceChannelRel getCommerceChannelRel(long commerceChannelRelId)
		throws PortalException {

		CommerceChannelRel commerceChannelRel =
			commerceChannelRelLocalService.getCommerceChannelRel(
				commerceChannelRelId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelRel.getCommerceChannelId(),
			ActionKeys.VIEW);

		return commerceChannelRelLocalService.getCommerceChannelRel(
			commerceChannelRelId);
	}

	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
			long commerceChannelId, int start, int end,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.VIEW);

		return commerceChannelRelLocalService.getCommerceChannelRels(
			commerceChannelId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws PortalException {

		return commerceChannelRelService.getCommerceChannelRels(
			className, classPK, null, start, end);
	}

	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
			String className, long classPK, String name, int start, int end)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(), classPK, ActionKeys.VIEW);

			return commerceChannelRelFinder.findByC_C(
				className, classPK, name, start, end);
		}

		return commerceChannelRelFinder.findByC_C(
			className, classPK, name, start, end, true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceChannelRel> getCommerceChannelRels(
		String className, long classPK, String classPKField, String name,
		int start, int end) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getCommerceChannelRelsCount(long commerceChannelId)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.VIEW);

		return commerceChannelRelLocalService.getCommerceChannelRelsCount(
			commerceChannelId);
	}

	@Override
	public int getCommerceChannelRelsCount(String className, long classPK)
		throws PortalException {

		return commerceChannelRelService.getCommerceChannelRelsCount(
			className, classPK, StringPool.BLANK);
	}

	@Override
	public int getCommerceChannelRelsCount(
			String className, long classPK, String name)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(), classPK, ActionKeys.VIEW);

			return commerceChannelRelFinder.countByC_C(
				className, classPK, name);
		}

		return commerceChannelRelFinder.countByC_C(
			className, classPK, name, true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceChannelRelsCount(
		String className, long classPK, String classPKField, String name) {

		throw new UnsupportedOperationException();
	}

	private static volatile ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceChannelRelServiceImpl.class,
				"_commerceChannelModelResourcePermission",
				CommerceChannel.class);
	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceChannelRelServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}