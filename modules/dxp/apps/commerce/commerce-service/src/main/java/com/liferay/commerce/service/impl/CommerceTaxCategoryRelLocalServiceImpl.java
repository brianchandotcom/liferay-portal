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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.service.base.CommerceTaxCategoryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryRelLocalServiceImpl
	extends CommerceTaxCategoryRelLocalServiceBaseImpl {

	@Override
	public CommerceTaxCategoryRel addCommerceTaxCategoryRel(
			long commerceTaxCategoryId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryLocalService.getCommerceTaxCategory(
				commerceTaxCategoryId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceTaxCategoryRelId = counterLocalService.increment();

		CommerceTaxCategoryRel commerceTaxCategoryRel =
			commerceTaxCategoryRelPersistence.create(commerceTaxCategoryRelId);

		commerceTaxCategoryRel.setGroupId(commerceTaxCategory.getGroupId());
		commerceTaxCategoryRel.setCompanyId(user.getCompanyId());
		commerceTaxCategoryRel.setUserId(user.getUserId());
		commerceTaxCategoryRel.setUserName(user.getFullName());
		commerceTaxCategoryRel.setCommerceTaxCategoryId(
			commerceTaxCategory.getCommerceTaxCategoryId());
		commerceTaxCategoryRel.setClassName(className);
		commerceTaxCategoryRel.setClassPK(classPK);

		commerceTaxCategoryRelPersistence.update(commerceTaxCategoryRel);

		return commerceTaxCategoryRel;
	}

	@Override
	public void deleteCommerceTaxCategoryRels(long commerceTaxCategoryId) {
		commerceTaxCategoryRelPersistence.removeByCommerceTaxCategoryId(
			commerceTaxCategoryId);
	}

	@Override
	public void deleteCommerceTaxCategoryRels(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		commerceTaxCategoryRelPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public List<CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceTaxCategoryRelPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoryRelsCount(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceTaxCategoryRelPersistence.countByC_C(
			classNameId, classPK);
	}

}