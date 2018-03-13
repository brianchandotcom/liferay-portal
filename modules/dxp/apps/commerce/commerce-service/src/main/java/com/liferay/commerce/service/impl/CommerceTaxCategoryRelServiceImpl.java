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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.service.base.CommerceTaxCategoryRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryRelServiceImpl
	extends CommerceTaxCategoryRelServiceBaseImpl {

	@Override
	public CommerceTaxCategoryRel addCommerceTaxCategoryRel(
			long commerceTaxCategoryId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		checkModel(className, classPK);

		return commerceTaxCategoryRelLocalService.addCommerceTaxCategoryRel(
			commerceTaxCategoryId, className, classPK, serviceContext);
	}

	@Override
	public void deleteCommerceTaxCategoryRel(long commercetaxCategoryRelId)
		throws PortalException {

		CommerceTaxCategoryRel commerceTaxCategoryRel =
			commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRel(
				commercetaxCategoryRelId);

		checkModel(
			commerceTaxCategoryRel.getClassName(),
			commerceTaxCategoryRel.getClassPK());

		commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRel(
			commerceTaxCategoryRel);
	}

	@Override
	public List<CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {

		return commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoryRelsCount(String className, long classPK) {
		return
			commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRelsCount(
				className, classPK);
	}

	protected void checkModel(String className, long classPK)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			CPDefinitionPermission.check(
				getPermissionChecker(), classPK, ActionKeys.UPDATE);
		}
	}

}