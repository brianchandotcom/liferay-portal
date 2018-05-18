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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRelLocalServiceImpl
	extends CommerceDiscountRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountRel addCommerceDiscountRel(
			long commerceDiscountId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceDiscountRelId = counterLocalService.increment();

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.create(commerceDiscountRelId);

		commerceDiscountRel.setGroupId(groupId);
		commerceDiscountRel.setCompanyId(user.getCompanyId());
		commerceDiscountRel.setUserId(user.getUserId());
		commerceDiscountRel.setUserName(user.getFullName());
		commerceDiscountRel.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRel.setClassName(className);
		commerceDiscountRel.setClassPK(classPK);

		commerceDiscountRelPersistence.update(commerceDiscountRel);

		return commerceDiscountRel;
	}

	@Override
	public void deleteCommerceDiscountRels(long commerceDiscountId)
		throws PortalException {

		commerceDiscountRelPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public void deleteCommerceDiscountRels(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		commerceDiscountRelPersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public long[] getClassPKs(long commerceDiscountId, String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return ListUtil.toLongArray(
			commerceDiscountRelPersistence.findByCD_CN(
				commerceDiscountId, classNameId),
			CommerceDiscountRel::getClassPK);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameId);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		OrderByComparator<CommerceDiscountRel> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRelsCount(
		long commerceDiscountId, String className) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceDiscountRelPersistence.countByCD_CN(
			commerceDiscountId, classNameId);
	}

}