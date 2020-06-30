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

import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.base.CommerceAccountOrganizationRelLocalServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountOrganizationRelLocalServiceImpl
	extends CommerceAccountOrganizationRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK =
			new CommerceAccountOrganizationRelPK(
				commerceAccountId, organizationId);

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			commerceAccountOrganizationRelPersistence.create(
				commerceAccountOrganizationRelPK);

		commerceAccountOrganizationRel.setCommerceAccountId(commerceAccountId);
		commerceAccountOrganizationRel.setOrganizationId(organizationId);
		commerceAccountOrganizationRel.setCompanyId(user.getCompanyId());
		commerceAccountOrganizationRel.setUserId(user.getUserId());
		commerceAccountOrganizationRel.setUserName(user.getFullName());

		return commerceAccountOrganizationRelPersistence.update(
			commerceAccountOrganizationRel);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			ServiceContext serviceContext)
		throws PortalException {

		if (organizationIds == null) {
			return;
		}

		for (long organizationId : organizationIds) {
			commerceAccountOrganizationRelLocalService.
				addCommerceAccountOrganizationRel(
					commerceAccountId, organizationId, serviceContext);
		}
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException {

		for (long organizationId : organizationIds) {
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK =
				new CommerceAccountOrganizationRelPK(
					commerceAccountId, organizationId);

			commerceAccountOrganizationRelPersistence.remove(
				commerceAccountOrganizationRelPK);
		}
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		commerceAccountOrganizationRelPersistence.removeByCommerceAccountId(
			commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		commerceAccountOrganizationRelPersistence.removeByOrganizationId(
			organizationId);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(long commerceAccountId) {

		return commerceAccountOrganizationRelPersistence.
			findByCommerceAccountId(commerceAccountId);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(
			long commerceAccountId, int start, int end) {

		return commerceAccountOrganizationRelPersistence.
			findByCommerceAccountId(commerceAccountId, start, end);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRelsByOrganizationId(
			long organizationId, int start, int end) {

		return commerceAccountOrganizationRelPersistence.findByOrganizationId(
			organizationId, start, end);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return commerceAccountOrganizationRelPersistence.countByOrganizationId(
			organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId) {
		return commerceAccountOrganizationRelPersistence.
			countByCommerceAccountId(commerceAccountId);
	}

}