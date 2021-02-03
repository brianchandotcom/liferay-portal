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

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelImpl;
import com.liferay.commerce.account.service.base.CommerceAccountOrganizationRelLocalServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountOrganizationRelLocalServiceImpl
	extends CommerceAccountOrganizationRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccountOrganizationRel addCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId,
			ServiceContext serviceContext)
		throws PortalException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					commerceAccountId, organizationId);

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			ServiceContext serviceContext)
		throws PortalException {

		accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRels(
			commerceAccountId, organizationIds);
	}

	@Override
	public CommerceAccountOrganizationRel createCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			accountEntryOrganizationRelLocalService.
				createAccountEntryOrganizationRel(
					counterLocalService.increment());

		accountEntryOrganizationRel.setAccountEntryId(
			commerceAccountOrganizationRelPK.getCommerceAccountId());
		accountEntryOrganizationRel.setOrganizationId(
			commerceAccountOrganizationRelPK.getOrganizationId());

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return createCommerceAccountOrganizationRel(
			(CommerceAccountOrganizationRelPK)primaryKeyObj);
	}

	@Override
	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					commerceAccountOrganizationRel.getCommerceAccountId(),
					commerceAccountOrganizationRel.getOrganizationId());

		accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRel(
					commerceAccountOrganizationRelPK.getCommerceAccountId(),
					commerceAccountOrganizationRelPK.getOrganizationId());

		accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException {

		accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRelsByAccountEntryId(
				commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRelsByOrganizationId(organizationId);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			(CommerceAccountOrganizationRel)persistedModel;

		return deleteCommerceAccountOrganizationRel(
			commerceAccountOrganizationRel.getPrimaryKey());
	}

	@Override
	public CommerceAccountOrganizationRel fetchCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(
				accountEntryOrganizationRelLocalService.
					fetchAccountEntryOrganizationRel(
						commerceAccountOrganizationRelPK.getCommerceAccountId(),
						commerceAccountOrganizationRelPK.getOrganizationId()));
	}

	@Override
	public CommerceAccountOrganizationRel getCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException {

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(
				accountEntryOrganizationRelLocalService.
					getAccountEntryOrganizationRel(
						commerceAccountOrganizationRelPK.getCommerceAccountId(),
						commerceAccountOrganizationRelPK.getOrganizationId()));
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(int start, int end) {

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(start, end);

		return TransformUtil.transform(
			accountEntryOrganizationRels,
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(long commerceAccountId) {

		return TransformUtil.transform(
			accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(commerceAccountId),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(
			long commerceAccountId, int start, int end) {

		return TransformUtil.transform(
			accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(commerceAccountId, start, end),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRelsByOrganizationId(
			long organizationId, int start, int end) {

		return TransformUtil.transform(
			accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsByOrganizationId(
					organizationId, start, end),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount() {
		return accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount();
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId) {
		return accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount(commerceAccountId);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK =
			(CommerceAccountOrganizationRelPK)primaryKeyObj;

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(
				accountEntryOrganizationRelLocalService.
					getAccountEntryOrganizationRel(
						commerceAccountOrganizationRelPK.getCommerceAccountId(),
						commerceAccountOrganizationRelPK.getOrganizationId()));
	}

	@Override
	public CommerceAccountOrganizationRel updateCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		throw new UnsupportedOperationException();
	}

}