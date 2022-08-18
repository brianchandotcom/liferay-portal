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

package com.liferay.commerce.discount.internal;

import com.liferay.commerce.account.util.CommerceAccountHelper;
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
import java.util.stream.Stream;

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
			long commerceOrderTypeId, long cpDefinitionId, long cpInstanceId)
		throws PortalException {

		return _getProductCommerceDiscountByHierarchy(
			companyId, CommerceUtil.getCommerceAccountId(commerceContext),
			commerceContext.getCommerceChannelId(), commerceOrderTypeId,
			cpDefinitionId, cpInstanceId);
	}

	@Reference
	protected CommerceAccountHelper commerceAccountHelper;

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

		Stream<CommerceDiscount> commerceDiscountsStream =
			commerceDiscounts.stream();

		if (commerceDiscountsStream.mapToLong(
				CommerceDiscount::getCommerceDiscountId
			).anyMatch(
				commerceDiscountId ->
					commerceDiscountId ==
						commerceChannelAccountEntryRel.getClassPK()
			)) {

			return Collections.singletonList(
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelAccountEntryRel.getClassPK()));
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
			commerceAccountHelper.getCommerceAccountGroupIds(commerceAccountId);

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
			long commerceOrderTypeId, long cpDefinitionId, long cpInstanceId)
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
					cpDefinitionId, cpInstanceId);

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
					cpDefinitionId, cpInstanceId);

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
				cpInstanceId);

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
				commerceAccountId, cpDefinitionId, cpInstanceId);

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
			commerceAccountHelper.getCommerceAccountGroupIds(commerceAccountId);

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelAndOrderTypeCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId,
					commerceOrderTypeId, cpDefinitionId, cpInstanceId);

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
					cpInstanceId);

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
				commerceAccountGroupIds, cpDefinitionId, cpInstanceId);

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
					cpInstanceId);

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
				commerceOrderTypeId, cpDefinitionId, cpInstanceId);

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
				commerceChannelId, cpDefinitionId, cpInstanceId);

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
				companyId, cpDefinitionId, cpInstanceId);

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