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
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelImpl;
import com.liferay.commerce.account.service.base.CommerceAccountOrganizationRelLocalServiceBaseImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccountOrganizationRel",
	service = AopService.class
)
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
			_accountEntryOrganizationRelLocalService.
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

		_accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRels(commerceAccountId, organizationIds);
	}

	@Override
	public CommerceAccountOrganizationRel createCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			_accountEntryOrganizationRelLocalService.
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
	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			_accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					commerceAccountOrganizationRel.getCommerceAccountId(),
					commerceAccountOrganizationRel.getOrganizationId());

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public CommerceAccountOrganizationRel deleteCommerceAccountOrganizationRel(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws PortalException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRel(
					commerceAccountOrganizationRelPK.getCommerceAccountId(),
					commerceAccountOrganizationRelPK.getOrganizationId());

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws PortalException {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRelsByAccountEntryId(
				commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRelsByOrganizationId(organizationId);
	}

	@Override
	public CommerceAccountOrganizationRel fetchCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		return CommerceAccountOrganizationRelImpl.
			fromAccountEntryOrganizationRel(
				_accountEntryOrganizationRelLocalService.
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
				_accountEntryOrganizationRelLocalService.
					getAccountEntryOrganizationRel(
						commerceAccountOrganizationRelPK.getCommerceAccountId(),
						commerceAccountOrganizationRelPK.getOrganizationId()));
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(int start, int end) {

		return TransformUtil.transform(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(start, end),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(long commerceAccountId) {

		return TransformUtil.transform(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(commerceAccountId),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels(
			long commerceAccountId, int start, int end) {

		return TransformUtil.transform(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(commerceAccountId, start, end),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRelsByOrganizationId(
			long organizationId, int start, int end) {

		return TransformUtil.transform(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsByOrganizationId(
					organizationId, start, end),
			CommerceAccountOrganizationRelImpl::
				fromAccountEntryOrganizationRel);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount() {
		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount();
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId) {
		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount(commerceAccountId);
	}

	@Override
	public CommerceAccountOrganizationRel updateCommerceAccountOrganizationRel(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		throw new UnsupportedOperationException();
	}

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

}