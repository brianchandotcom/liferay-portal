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

import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.service.base.CommerceTaxCategoryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 */
public class CommerceTaxCategoryRelLocalServiceImpl
	extends CommerceTaxCategoryRelLocalServiceBaseImpl {

	@Override
	public void deleteCommerceTaxCategoryRel(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		commerceTaxCategoryRelPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public void deleteCommerceTaxCategoryRels(long commerceTaxCategoryId) {
		commerceTaxCategoryRelPersistence.removeByCommerceTaxCategoryId(
			commerceTaxCategoryId);
	}

	@Override
	public CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceTaxCategoryRelPersistence.fetchByC_C(
			classNameId, classPK);
	}

	@Override
	public CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
			long commerceTaxCategoryId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceTaxCategoryRel commerceTaxCategoryRel =
			fetchCommerceTaxCategoryRel(className, classPK);

		if (commerceTaxCategoryRel == null) {
			long commerceTaxCategoryRelId = counterLocalService.increment();

			commerceTaxCategoryRel = commerceTaxCategoryRelPersistence.create(
				commerceTaxCategoryRelId);

			commerceTaxCategoryRel.setGroupId(serviceContext.getScopeGroupId());
			commerceTaxCategoryRel.setCompanyId(user.getCompanyId());
			commerceTaxCategoryRel.setUserId(user.getUserId());
			commerceTaxCategoryRel.setUserName(user.getFullName());
			commerceTaxCategoryRel.setClassName(className);
			commerceTaxCategoryRel.setClassPK(classPK);
		}

		commerceTaxCategoryRel.setCommerceTaxCategoryId(commerceTaxCategoryId);

		commerceTaxCategoryRelPersistence.update(commerceTaxCategoryRel);

		return commerceTaxCategoryRel;
	}

}