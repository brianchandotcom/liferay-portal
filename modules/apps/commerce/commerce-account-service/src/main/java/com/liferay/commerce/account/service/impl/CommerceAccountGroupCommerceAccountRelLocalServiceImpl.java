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

import com.liferay.commerce.account.exception.DuplicateCommerceAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.base.CommerceAccountGroupCommerceAccountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupCommerceAccountRelLocalServiceImpl
	extends CommerceAccountGroupCommerceAccountRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId,
				ServiceContext serviceContext)
		throws PortalException {

		return addCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId, null, serviceContext);
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId,
				String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel =
				commerceAccountGroupCommerceAccountRelPersistence.fetchByC_C(
					commerceAccountGroupId, commerceAccountId);

		if (commerceAccountGroupCommerceAccountRel != null) {
			throw new DuplicateCommerceAccountGroupCommerceAccountRelException(
				"Account already associated to AccountGroup");
		}

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceAccountGroupCommerceAccountRelId =
			counterLocalService.increment();

		commerceAccountGroupCommerceAccountRel =
			commerceAccountGroupCommerceAccountRelPersistence.create(
				commerceAccountGroupCommerceAccountRelId);

		commerceAccountGroupCommerceAccountRel.setCompanyId(
			user.getCompanyId());
		commerceAccountGroupCommerceAccountRel.setUserId(user.getUserId());
		commerceAccountGroupCommerceAccountRel.setUserName(user.getFullName());

		commerceAccountGroupCommerceAccountRel.setCommerceAccountGroupId(
			commerceAccountGroupId);
		commerceAccountGroupCommerceAccountRel.setCommerceAccountId(
			commerceAccountId);
		commerceAccountGroupCommerceAccountRel.setExternalReferenceCode(
			externalReferenceCode);

		return commerceAccountGroupCommerceAccountRelPersistence.update(
			commerceAccountGroupCommerceAccountRel);
	}

	@Override
	public void deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
		long commerceAccountGroupId) {

		commerceAccountGroupCommerceAccountRelPersistence.
			removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
		fetchCommerceAccountGroupCommerceAccountRel(
			long commerceAccountGroupId, long commerceAccountId) {

		return commerceAccountGroupCommerceAccountRelPersistence.fetchByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
			getCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId)
		throws PortalException {

		return commerceAccountGroupCommerceAccountRelPersistence.findByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	@Override
	public List<CommerceAccountGroupCommerceAccountRel>
		getCommerceAccountGroupCommerceAccountRels(
			long commerceAccountGroupId, int start, int end) {

		return commerceAccountGroupCommerceAccountRelPersistence.
			findByCommerceAccountGroupId(commerceAccountGroupId, start, end);
	}

	@Override
	public int getCommerceAccountGroupCommerceAccountRelsCount(
		long commerceAccountGroupId) {

		return commerceAccountGroupCommerceAccountRelPersistence.
			countByCommerceAccountGroupId(commerceAccountGroupId);
	}

}