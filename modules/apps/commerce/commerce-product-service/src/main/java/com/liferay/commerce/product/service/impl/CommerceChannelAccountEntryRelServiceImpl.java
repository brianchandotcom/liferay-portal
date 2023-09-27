/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.base.CommerceChannelAccountEntryRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceChannelAccountEntryRel"
	},
	service = AopService.class
)
public class CommerceChannelAccountEntryRelServiceImpl
	extends CommerceChannelAccountEntryRelServiceBaseImpl {

	@Override
	public CommerceChannelAccountEntryRel addCommerceChannelAccountEntryRel(
			long accountEntryId, String className, long classPK,
			long commerceChannelId, boolean overrideEligibility,
			double priority, int type)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.UPDATE);

		return commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				getUserId(), accountEntryId, className, classPK,
				commerceChannelId, overrideEligibility, priority, type);
	}

	@Override
	public void deleteCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceChannelAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		commerceChannelAccountEntryRelLocalService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel);
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		if (commerceChannelAccountEntryRel != null) {
			_accountEntryModelResourcePermission.check(
				getPermissionChecker(),
				commerceChannelAccountEntryRel.getAccountEntryId(),
				ActionKeys.VIEW);
		}

		return commerceChannelAccountEntryRel;
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long accountEntryId, long commerceChannelId, int type)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					accountEntryId, commerceChannelId, type);

		if (commerceChannelAccountEntryRel != null) {
			_accountEntryModelResourcePermission.check(
				getPermissionChecker(),
				commerceChannelAccountEntryRel.getAccountEntryId(),
				ActionKeys.VIEW);
		}

		return commerceChannelAccountEntryRel;
	}

	@Override
	public CommerceChannelAccountEntryRel getCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceChannelAccountEntryRel.getAccountEntryId(),
			ActionKeys.VIEW);

		return commerceChannelAccountEntryRel;
	}

	@Override
	public List<CommerceChannelAccountEntryRel>
			getCommerceChannelAccountEntryRels(
				long accountEntryId, int type, int start, int end,
				OrderByComparator<CommerceChannelAccountEntryRel>
					orderByComparator)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return commerceChannelAccountEntryRelLocalService.
			getCommerceChannelAccountEntryRels(
				accountEntryId, type, start, end, orderByComparator);
	}

	@Override
	public List<CommerceChannelAccountEntryRel>
		getCommerceChannelAccountEntryRels(
			String className, long classPK, long commerceChannelId, int type) {

		return commerceChannelAccountEntryRelLocalService.
			getCommerceChannelAccountEntryRels(
				className, classPK, commerceChannelId, type);
	}

	@Override
	public int getCommerceChannelAccountEntryRelsCount(
			long accountEntryId, int type)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return commerceChannelAccountEntryRelLocalService.
			getCommerceChannelAccountEntryRelsCount(accountEntryId, type);
	}

	@Override
	public CommerceChannelAccountEntryRel updateCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId, long commerceChannelId,
			long classPK, boolean overrideEligibility, double priority)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceChannelAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		return commerceChannelAccountEntryRelLocalService.
			updateCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId(),
				commerceChannelId, classPK, overrideEligibility, priority);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_accountEntryModelResourcePermission;

}