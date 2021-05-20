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

package com.liferay.commerce.account.service.impl;

import com.liferay.commerce.account.exception.DuplicateCommerceAccountGroupRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.base.CommerceAccountGroupRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupRelLocalServiceImpl
	extends CommerceAccountGroupRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountGroupRel addCommerceAccountGroupRel(
			String className, long classPK, long commerceAccountGroupId,
			ServiceContext serviceContext)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		CommerceAccountGroupRel commerceAccountGroupRel =
			commerceAccountGroupRelPersistence.fetchByC_C_C(
				classNameId, classPK, commerceAccountGroupId);

		if (commerceAccountGroupRel != null) {
			throw new DuplicateCommerceAccountGroupRelException();
		}

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceAccountGroupRelId = counterLocalService.increment();

		commerceAccountGroupRel = commerceAccountGroupRelPersistence.create(
			commerceAccountGroupRelId);

		commerceAccountGroupRel.setCompanyId(user.getCompanyId());
		commerceAccountGroupRel.setUserId(user.getUserId());
		commerceAccountGroupRel.setUserName(user.getFullName());
		commerceAccountGroupRel.setClassNameId(classNameId);
		commerceAccountGroupRel.setClassPK(classPK);
		commerceAccountGroupRel.setCommerceAccountGroupId(
			commerceAccountGroupId);

		return commerceAccountGroupRelPersistence.update(
			commerceAccountGroupRel);
	}

	@Override
	public void deleteCommerceAccountGroupRels(long commerceAccountGroupId) {
		commerceAccountGroupRelPersistence.removeByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		commerceAccountGroupRelPersistence.removeByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return commerceAccountGroupRelPersistence.findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return commerceAccountGroupRelPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId) {
		return commerceAccountGroupRelPersistence.countByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return commerceAccountGroupRelPersistence.countByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

}