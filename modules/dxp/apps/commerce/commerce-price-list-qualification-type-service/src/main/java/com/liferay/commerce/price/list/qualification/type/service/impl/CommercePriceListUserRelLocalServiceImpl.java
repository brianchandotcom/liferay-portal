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

package com.liferay.commerce.price.list.qualification.type.service.impl;

import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.base.CommercePriceListUserRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListUserRelLocalServiceImpl
	extends CommercePriceListUserRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListUserRel addCommercePriceListUserRel(
			long commercePriceListQualificationTypeRelId, String className,
			long classPK, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceListUserRelId = counterLocalService.increment();

		CommercePriceListUserRel commercePriceListUserRel =
			commercePriceListUserRelPersistence.create(
				commercePriceListUserRelId);

		commercePriceListUserRel.setUuid(serviceContext.getUuid());
		commercePriceListUserRel.setGroupId(groupId);
		commercePriceListUserRel.setCompanyId(user.getCompanyId());
		commercePriceListUserRel.setUserId(user.getUserId());
		commercePriceListUserRel.setUserName(user.getFullName());
		commercePriceListUserRel.setCommercePriceListQualificationTypeRelId(
			commercePriceListQualificationTypeRelId);
		commercePriceListUserRel.setClassName(className);
		commercePriceListUserRel.setClassPK(classPK);
		commercePriceListUserRel.setExpandoBridgeAttributes(serviceContext);

		return commercePriceListUserRelPersistence.update(
			commercePriceListUserRel);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeId) {

		commercePriceListUserRelPersistence.
			removeByCommercePriceListQualificationTypeRelId(
				commercePriceListQualificationTypeId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		commercePriceListUserRelPersistence.removeByC_C(
			commercePriceListQualificationTypeRelId, classNameId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		commercePriceListUserRelPersistence.removeByC_C_C(
			commercePriceListQualificationTypeRelId, classNameId, classPK);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.findByC_C(
			commercePriceListQualificationTypeRelId, classNameId);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.findByC_C(
			commercePriceListQualificationTypeRelId, classNameId, start, end,
			orderByComparator);
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commercePriceListUserRelPersistence.countByC_C(
			commercePriceListQualificationTypeRelId, classNameId);
	}

	@Override
	public CommercePriceListUserRel updateCommercePriceListUserRel(
			long commercePriceListUserRelId,
			long commercePriceListQualificationTypeRelId,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListUserRel commercePriceListUserRel =
			commercePriceListUserRelPersistence.findByPrimaryKey(
				commercePriceListUserRelId);

		commercePriceListUserRel.setCommercePriceListQualificationTypeRelId(
			commercePriceListQualificationTypeRelId);
		commercePriceListUserRel.setExpandoBridgeAttributes(serviceContext);

		return commercePriceListUserRelPersistence.update(
			commercePriceListUserRel);
	}

}