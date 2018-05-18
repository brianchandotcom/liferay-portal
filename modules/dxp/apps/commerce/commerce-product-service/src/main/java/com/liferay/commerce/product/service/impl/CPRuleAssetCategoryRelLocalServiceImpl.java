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

import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.service.base.CPRuleAssetCategoryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPRuleAssetCategoryRelLocalServiceImpl
	extends CPRuleAssetCategoryRelLocalServiceBaseImpl {

	@Override
	public CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
			long cpRuleId, long assetCategoryId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpRuleAssetCategoryRelId = counterLocalService.increment();

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel =
			cpRuleAssetCategoryRelPersistence.create(cpRuleAssetCategoryRelId);

		cpRuleAssetCategoryRel.setGroupId(groupId);
		cpRuleAssetCategoryRel.setCompanyId(user.getCompanyId());
		cpRuleAssetCategoryRel.setUserId(user.getUserId());
		cpRuleAssetCategoryRel.setUserName(user.getFullName());
		cpRuleAssetCategoryRel.setCPRuleId(cpRuleId);
		cpRuleAssetCategoryRel.setAssetCategoryId(assetCategoryId);

		cpRuleAssetCategoryRelPersistence.update(cpRuleAssetCategoryRel);

		return cpRuleAssetCategoryRel;
	}

	@Override
	public void deleteCPRuleAssetCategoryRelsByAssetCategoryId(
			long assetCategoryId)
		throws PortalException {

		cpRuleAssetCategoryRelPersistence.removeByAssetCategoryId(
			assetCategoryId);
	}

	@Override
	public void deleteCPRuleAssetCategoryRelsByCPRuleId(long cpRuleId)
		throws PortalException {

		cpRuleAssetCategoryRelPersistence.removeByCPRuleId(cpRuleId);
	}

	@Override
	public long[] getAssetCategoryIds(long cpRuleId) {
		return ListUtil.toLongArray(
			cpRuleAssetCategoryRelPersistence.findByCPRuleId(cpRuleId),
			CPRuleAssetCategoryRel::getAssetCategoryId);
	}

	@Override
	public List<CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		long cpRuleId) {

		return cpRuleAssetCategoryRelPersistence.findByCPRuleId(cpRuleId);
	}

}