/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
public abstract class BaseCommerceDiscountCalculation
	implements CommerceDiscountCalculation {

	protected List<CommerceDiscount> getOrderCommerceDiscountByHierarchy(
			long companyId, CommerceContext commerceContext,
			long commerceOrderTypeId, String target)
		throws PortalException {

		return _getOrderCommerceDiscountByHierarchy(
			companyId, CommerceUtil.getCommerceAccountId(commerceContext),
			commerceContext.getCommerceChannelId(), commerceOrderTypeId,
			target);
	}

	protected List<CommerceDiscount> getProductCommerceDiscountByHierarchy(
			long companyId, CommerceContext commerceContext,
			long commerceOrderTypeId, long cpDefinitionId, long cpInstanceId,
			String unitOfMeasureKey)
		throws PortalException {

		return _getProductCommerceDiscountByHierarchy(
			companyId, CommerceUtil.getCommerceAccountId(commerceContext),
			commerceContext.getCommerceChannelId(), commerceOrderTypeId,
			cpDefinitionId, cpInstanceId, unitOfMeasureKey);
	}

	@Reference
	protected AccountGroupLocalService accountGroupLocalService;

	@Reference
	protected CommerceChannelAccountEntryRelLocalService
		commerceChannelAccountEntryRelLocalService;

	@Reference
	protected CommerceDiscountLocalService commerceDiscountLocalService;

	private List<CommerceDiscount> _getDefaultCommerceDiscounts(
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			List<CommerceDiscount> commerceDiscounts)
		throws PortalException {

		if (commerceChannelAccountEntryRel == null) {
			return null;
		}

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			if (commerceDiscount.getCommerceDiscountId() ==
					commerceChannelAccountEntryRel.getClassPK()) {

				return Collections.singletonList(
					commerceDiscountLocalService.getCommerceDiscount(
						commerceChannelAccountEntryRel.getClassPK()));
			}
		}

		return null;
	}

	private List<CommerceDiscount> _getOrderCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String target)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceAccountId, commerceChannelId,
					CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		if ((commerceChannelAccountEntryRel != null) &&
			commerceChannelAccountEntryRel.isOverrideEligibility()) {

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelAccountEntryRel.getClassPK());

			if (commerceDiscount.isActive() &&
				target.equals(commerceDiscount.getTarget())) {

				return Collections.singletonList(commerceDiscount);
			}
		}

		List<CommerceDiscount> firstEligibleCommerceDiscounts =
			new ArrayList<>();

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountLocalService.
				getAccountAndChannelAndOrderTypeCommerceDiscounts(
					commerceAccountId, commerceChannelId, commerceOrderTypeId,
					target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountAndOrderTypeCommerceDiscounts(
					commerceAccountId, commerceOrderTypeId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		long[] commerceAccountGroupIds =
			accountGroupLocalService.getAccountGroupIds(commerceAccountId);

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelAndOrderTypeCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId,
					commerceOrderTypeId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndOrderTypeCommerceDiscount(
					commerceAccountGroupIds, commerceOrderTypeId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getChannelAndOrderTypeCommerceDiscounts(
					commerceChannelId, commerceOrderTypeId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getOrderTypeCommerceDiscounts(
				commerceOrderTypeId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				companyId, target);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		return firstEligibleCommerceDiscounts;
	}

	private List<CommerceDiscount> _getProductCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, long cpDefinitionId, long cpInstanceId,
			String unitOfMeasureKey)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceAccountId, commerceChannelId,
					CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		if ((commerceChannelAccountEntryRel != null) &&
			commerceChannelAccountEntryRel.isOverrideEligibility()) {

			CommerceDiscount defaultCommerceDiscount =
				commerceDiscountLocalService.fetchDefaultCommerceDiscount(
					commerceChannelAccountEntryRel.
						getCommerceChannelAccountEntryRelId(),
					cpDefinitionId, cpInstanceId, unitOfMeasureKey);

			if (defaultCommerceDiscount != null) {
				return Collections.singletonList(defaultCommerceDiscount);
			}
		}

		List<CommerceDiscount> firstEligibleCommerceDiscounts =
			new ArrayList<>();

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountLocalService.
				getAccountAndChannelAndOrderTypeCommerceDiscounts(
					commerceAccountId, commerceChannelId, commerceOrderTypeId,
					cpDefinitionId, cpInstanceId, unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId, cpDefinitionId,
				cpInstanceId, unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, cpDefinitionId, cpInstanceId,
				unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		long[] commerceAccountGroupIds =
			accountGroupLocalService.getAccountGroupIds(commerceAccountId);

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelAndOrderTypeCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId,
					commerceOrderTypeId, cpDefinitionId, cpInstanceId,
					unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId, cpDefinitionId,
					cpInstanceId, unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, cpDefinitionId, cpInstanceId,
				unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.
				getChannelAndOrderTypeCommerceDiscounts(
					commerceChannelId, commerceOrderTypeId, cpDefinitionId,
					cpInstanceId, unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getOrderTypeCommerceDiscounts(
				commerceOrderTypeId, cpDefinitionId, cpInstanceId,
				unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, cpDefinitionId, cpInstanceId,
				unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		commerceDiscounts =
			commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				companyId, cpDefinitionId, cpInstanceId, unitOfMeasureKey);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			List<CommerceDiscount> defaultCommerceDiscounts =
				_getDefaultCommerceDiscounts(
					commerceChannelAccountEntryRel, commerceDiscounts);

			if (defaultCommerceDiscounts != null) {
				return defaultCommerceDiscounts;
			}

			if (firstEligibleCommerceDiscounts.isEmpty()) {
				firstEligibleCommerceDiscounts.addAll(commerceDiscounts);
			}
		}

		return firstEligibleCommerceDiscounts;
	}

}