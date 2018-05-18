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

import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.base.CPRuleUserSegmentRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPRuleUserSegmentRelLocalServiceImpl
	extends CPRuleUserSegmentRelLocalServiceBaseImpl {

	@Override
	public CPRuleUserSegmentRel addCPRuleUserSegmentRel(
			long cpRuleId, long commerceUserSegmentEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpRuleUserSegmentRelId = counterLocalService.increment();

		CPRuleUserSegmentRel cpRuleUserSegmentRel =
			cpRuleUserSegmentRelPersistence.create(cpRuleUserSegmentRelId);

		cpRuleUserSegmentRel.setGroupId(groupId);
		cpRuleUserSegmentRel.setCompanyId(user.getCompanyId());
		cpRuleUserSegmentRel.setUserId(user.getUserId());
		cpRuleUserSegmentRel.setUserName(user.getFullName());
		cpRuleUserSegmentRel.setCPRuleId(cpRuleId);
		cpRuleUserSegmentRel.setCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);

		cpRuleUserSegmentRelPersistence.update(cpRuleUserSegmentRel);

		return cpRuleUserSegmentRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
			CPRuleUserSegmentRel cpRuleUserSegmentRel)
		throws PortalException {

		cpRuleUserSegmentRelPersistence.remove(cpRuleUserSegmentRel);

		return cpRuleUserSegmentRel;
	}

	@Override
	public CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
			long cpRuleUserSegmentRelId)
		throws PortalException {

		CPRuleUserSegmentRel cpRuleUserSegmentRel =
			cpRuleUserSegmentRelPersistence.findByPrimaryKey(
				cpRuleUserSegmentRelId);

		return cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRel(
			cpRuleUserSegmentRel);
	}

	@Override
	public void deleteCPRuleUserSegmentRelsByCommerceUserSegmentEntryId(
			long commerceUserSegmentEntryId)
		throws PortalException {

		cpRuleUserSegmentRelPersistence.removeByCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);
	}

	@Override
	public void deleteCPRuleUserSegmentRelsByCPRuleId(long cpRuleId)
		throws PortalException {

		cpRuleUserSegmentRelPersistence.removeByCPRuleId(cpRuleId);
	}

	@Override
	public List<CPRuleUserSegmentRel> getCPRuleUserSegmentRels(long cpRuleId) {
		return cpRuleUserSegmentRelPersistence.findByCPRuleId(
			cpRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		long cpRuleId, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {

		return cpRuleUserSegmentRelPersistence.findByCPRuleId(
			cpRuleId, start, end, orderByComparator);
	}

	@Override
	public int getCPRuleUserSegmentRelsCount(long cpRuleId) {
		return cpRuleUserSegmentRelPersistence.countByCPRuleId(cpRuleId);
	}

}