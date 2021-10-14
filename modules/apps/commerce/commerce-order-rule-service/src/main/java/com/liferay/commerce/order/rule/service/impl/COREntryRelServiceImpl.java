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

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.base.COREntryRelServiceBaseImpl;
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
		"json.web.service.context.path=COREntryRel"
	},
	service = AopService.class
)
public class COREntryRelServiceImpl extends COREntryRelServiceBaseImpl {

	@Override
	public COREntryRel addCOREntryRel(
			String className, long classPK, long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.UPDATE);

		return corEntryRelLocalService.addCOREntryRel(
			getUserId(), className, classPK, corEntryId);
	}

	@Override
	public void deleteCOREntryRel(long corEntryRelId) throws PortalException {
		COREntryRel corEntryRel = corEntryRelLocalService.getCOREntryRel(
			corEntryRelId);

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryRel.getCOREntryId(),
			ActionKeys.UPDATE);

		corEntryRelLocalService.deleteCOREntryRel(corEntryRel);
	}

	@Override
	public void deleteCOREntryRels(String className, long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.UPDATE);

		corEntryRelLocalService.deleteCOREntryRels(className, corEntryId);
	}

	@Override
	public void deleteCOREntryRelsByCOREntryId(long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.UPDATE);

		corEntryRelLocalService.deleteCOREntryRels(corEntryId);
	}

	@Override
	public COREntryRel fetchCOREntryRel(
			String className, long classPK, long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.fetchCOREntryRel(
			className, classPK, corEntryId);
	}

	@Override
	public List<COREntryRel> getAccountEntryCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getAccountEntryCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getAccountEntryCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getAccountEntryCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public List<COREntryRel> getAccountGroupCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getAccountGroupCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getAccountGroupCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getAccountGroupCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public List<COREntryRel> getCommerceChannelCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCommerceChannelCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceChannelCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCommerceChannelCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public List<COREntryRel> getCommerceOrderTypeCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCommerceOrderTypeCOREntryRels(
			corEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCommerceOrderTypeCOREntryRelsCount(
			corEntryId, keywords);
	}

	@Override
	public COREntryRel getCOREntryRel(long corEntryRelId)
		throws PortalException {

		COREntryRel corEntryRel = corEntryRelLocalService.getCOREntryRel(
			corEntryRelId);

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryRel.getCOREntryId(),
			ActionKeys.VIEW);

		return corEntryRel;
	}

	@Override
	public List<COREntryRel> getCOREntryRels(long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCOREntryRels(corEntryId);
	}

	@Override
	public List<COREntryRel> getCOREntryRels(
			long corEntryId, int start, int end,
			OrderByComparator<COREntryRel> orderByComparator)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCOREntryRels(
			corEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCOREntryRelsCount(long corEntryId) throws PortalException {
		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryRelLocalService.getCOREntryRelsCount(corEntryId);
	}

	private static volatile ModelResourcePermission<COREntry>
		_corEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				COREntryRelServiceImpl.class,
				"_corEntryModelResourcePermission", COREntry.class);

}