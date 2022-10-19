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
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountGroupRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupRelImpl;
import com.liferay.commerce.account.service.base.CommerceAccountGroupRelLocalServiceBaseImpl;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccountGroupRel",
	service = AopService.class
)
public class CommerceAccountGroupRelLocalServiceImpl
	extends CommerceAccountGroupRelLocalServiceBaseImpl {

	@Override
	public CommerceAccountGroupRel addCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceAccountGroupRel addCommerceAccountGroupRel(
			String className, long classPK, long commerceAccountGroupId,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return CommerceAccountGroupRelImpl.fromAccountGroupRel(
				_accountGroupRelLocalService.addAccountGroupRel(
					commerceAccountGroupId, className, classPK));
		}
		catch (DuplicateAccountGroupRelException
					duplicateAccountGroupRelException) {

			throw new DuplicateCommerceAccountGroupRelException(
				duplicateAccountGroupRelException);
		}
	}

	@Override
	public CommerceAccountGroupRel deleteCommerceAccountGroupRel(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		return CommerceAccountGroupRelImpl.fromAccountGroupRel(
			_accountGroupRelLocalService.deleteAccountGroupRel(
				_accountGroupRelLocalService.fetchAccountGroupRel(
					commerceAccountGroupRel.getCommerceAccountGroupRelId())));
	}

	@Override
	public CommerceAccountGroupRel deleteCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException {

		return CommerceAccountGroupRelImpl.fromAccountGroupRel(
			_accountGroupRelLocalService.deleteAccountGroupRel(
				commerceAccountGroupRelId));
	}

	@Override
	public void deleteCommerceAccountGroupRels(long commerceAccountGroupId) {
		_accountGroupRelLocalService.deleteAccountGroupRelsByAccountGroupId(
			commerceAccountGroupId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		_accountGroupRelLocalService.deleteAccountGroupRels(
			className, new long[] {classPK});
	}

	@Override
	public CommerceAccountGroupRel fetchCommerceAccountGroupRel(
		long commerceAccountGroupRelId) {

		return CommerceAccountGroupRelImpl.fromAccountGroupRel(
			_accountGroupRelLocalService.fetchAccountGroupRel(
				commerceAccountGroupRelId));
	}

	@Override
	public CommerceAccountGroupRel getCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException {

		return CommerceAccountGroupRelImpl.fromAccountGroupRel(
			_accountGroupRelLocalService.getAccountGroupRel(
				commerceAccountGroupRelId));
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		int start, int end) {

		return TransformUtil.transform(
			_accountGroupRelLocalService.getAccountGroupRels(start, end),
			CommerceAccountGroupRelImpl::fromAccountGroupRel);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return TransformUtil.transform(
			_accountGroupRelLocalService.getAccountGroupRelsByAccountGroupId(
				commerceAccountGroupId, start, end,
				_getAccountGroupRelOrderByComparator(orderByComparator)),
			CommerceAccountGroupRelImpl::fromAccountGroupRel);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return TransformUtil.transform(
			_accountGroupRelLocalService.getAccountGroupRels(
				className, classPK, start, end,
				_getAccountGroupRelOrderByComparator(orderByComparator)),
			CommerceAccountGroupRelImpl::fromAccountGroupRel);
	}

	@Override
	public int getCommerceAccountGroupRelsCount() {
		return _accountGroupRelLocalService.getAccountGroupRelsCount();
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId) {
		long accountGroupRelsCountByAccountGroupId =
			_accountGroupRelLocalService.
				getAccountGroupRelsCountByAccountGroupId(
					commerceAccountGroupId);

		return (int)accountGroupRelsCountByAccountGroupId;
	}

	@Override
	public int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return _accountGroupRelLocalService.getAccountGroupRelsCount(
			className, classPK);
	}

	private OrderByComparator<AccountGroupRel>
		_getAccountGroupRelOrderByComparator(
			OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new OrderByComparator<AccountGroupRel>() {

			@Override
			public int compare(
				AccountGroupRel accountGroupRel1,
				AccountGroupRel accountGroupRel2) {

				return orderByComparator.compare(
					CommerceAccountGroupRelImpl.fromAccountGroupRel(
						accountGroupRel1),
					CommerceAccountGroupRelImpl.fromAccountGroupRel(
						accountGroupRel2));
			}

		};
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

}