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

import com.liferay.account.exception.DuplicateAccountGroupRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.exception.NoSuchAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelImpl;
import com.liferay.commerce.account.service.base.CommerceAccountGroupCommerceAccountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.util.TransformUtil;

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

		try {
			return CommerceAccountGroupCommerceAccountRelImpl.
				fromAccountGroupRel(
					accountGroupRelLocalService.addAccountGroupRel(
						commerceAccountGroupId, AccountEntry.class.getName(),
						commerceAccountId));
		}
		catch (DuplicateAccountGroupRelException
					duplicateAccountGroupRelException) {

			throw new DuplicateCommerceAccountGroupCommerceAccountRelException(
				duplicateAccountGroupRelException);
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId,
				String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId, serviceContext);
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
			deleteCommerceAccountGroupCommerceAccountRel(
				CommerceAccountGroupCommerceAccountRel
					commerceAccountGroupCommerceAccountRel)
		throws PortalException {

		return CommerceAccountGroupCommerceAccountRelImpl.fromAccountGroupRel(
			accountGroupRelLocalService.deleteAccountGroupRel(
				commerceAccountGroupCommerceAccountRel.
					getCommerceAccountGroupCommerceAccountRelId()));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public void deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
		long commerceAccountGroupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
		fetchCommerceAccountGroupCommerceAccountRel(
			long commerceAccountGroupId, long commerceAccountId) {

		return CommerceAccountGroupCommerceAccountRelImpl.fromAccountGroupRel(
			accountGroupRelLocalService.fetchAccountGroupRel(
				commerceAccountGroupId, AccountEntry.class.getName(),
				commerceAccountId));
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
			getCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupCommerceAccountRelId)
		throws PortalException {

		return CommerceAccountGroupCommerceAccountRelImpl.fromAccountGroupRel(
			accountGroupRelLocalService.getAccountGroupRel(
				commerceAccountGroupCommerceAccountRelId));
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel
			getCommerceAccountGroupCommerceAccountRel(
				long commerceAccountGroupId, long commerceAccountId)
		throws PortalException {

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel =
				fetchCommerceAccountGroupCommerceAccountRel(
					commerceAccountGroupId, commerceAccountId);

		if (commerceAccountGroupCommerceAccountRel == null) {
			throw new NoSuchAccountGroupCommerceAccountRelException();
		}

		return commerceAccountGroupCommerceAccountRel;
	}

	@Override
	public List<CommerceAccountGroupCommerceAccountRel>
		getCommerceAccountGroupCommerceAccountRels(long commerceAccountId) {

		return TransformUtil.transform(
			accountGroupRelLocalService.getAccountGroupRels(
				AccountEntry.class.getName(), commerceAccountId),
			CommerceAccountGroupCommerceAccountRelImpl::fromAccountGroupRel);
	}

	@Override
	public List<CommerceAccountGroupCommerceAccountRel>
		getCommerceAccountGroupCommerceAccountRels(
			long commerceAccountGroupId, int start, int end) {

		return TransformUtil.transform(
			accountGroupRelLocalService.getAccountGroupRelsByAccountGroupId(
				commerceAccountGroupId, start, end, null),
			CommerceAccountGroupCommerceAccountRelImpl::fromAccountGroupRel);
	}

	@Override
	public int getCommerceAccountGroupCommerceAccountRelsCount(
		long commerceAccountGroupId) {

		long accountGroupRelsCountByAccountGroupId =
			accountGroupRelLocalService.
				getAccountGroupRelsCountByAccountGroupId(
					commerceAccountGroupId);

		return (int)accountGroupRelsCountByAccountGroupId;
	}

}