/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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