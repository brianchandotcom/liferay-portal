/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelListener.class)
public class CPInstanceUnitOfMeasureModelListener
	extends BaseModelListener<CPInstanceUnitOfMeasure> {

	@Override
	public void onAfterCreate(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {
		int cpInstanceUnitOfMeasuresCount =
			_cpInstanceUnitOfMeasureLocalService.
				getCPInstanceUnitOfMeasuresCount(
					cpInstanceUnitOfMeasure.getCPInstanceId());

		if (cpInstanceUnitOfMeasuresCount != 1) {
			return;
		}

		_updateUnitOfMeasureKey(
			cpInstanceUnitOfMeasure.getCPInstanceId(),
			cpInstanceUnitOfMeasure.getIncrementalOrderQuantity(),
			cpInstanceUnitOfMeasure.getKey(), null, StringPool.BLANK);
	}

	@Override
	public void onAfterUpdate(
			CPInstanceUnitOfMeasure originalCPInstanceUnitOfMeasure,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws ModelListenerException {

		String key = cpInstanceUnitOfMeasure.getKey();

		if (key.equals(originalCPInstanceUnitOfMeasure.getKey()) &&
			BigDecimalUtil.eq(
				cpInstanceUnitOfMeasure.getIncrementalOrderQuantity(),
				originalCPInstanceUnitOfMeasure.
					getIncrementalOrderQuantity())) {

			return;
		}

		int cpInstanceUnitOfMeasuresCount =
			_cpInstanceUnitOfMeasureLocalService.
				getCPInstanceUnitOfMeasuresCount(
					cpInstanceUnitOfMeasure.getCPInstanceId());

		if (cpInstanceUnitOfMeasuresCount != 1) {
			return;
		}

		_updateUnitOfMeasureKey(
			originalCPInstanceUnitOfMeasure.getCPInstanceId(),
			cpInstanceUnitOfMeasure.getIncrementalOrderQuantity(),
			cpInstanceUnitOfMeasure.getKey(),
			originalCPInstanceUnitOfMeasure.getIncrementalOrderQuantity(),
			originalCPInstanceUnitOfMeasure.getKey());
	}

	private void _updateUnitOfMeasureKey(
		long cpInstanceId, BigDecimal incrementalOrderQuantity, String key,
		BigDecimal originalIncrementalOrderQuantity, String originalKey) {

		int[] orderStatuses = ArrayUtil.append(
			CommerceOrderConstants.ORDER_STATUSES_OPEN,
			CommerceOrderConstants.ORDER_STATUSES_PENDING,
			CommerceOrderConstants.ORDER_STATUSES_PROCESSING,
			CommerceOrderConstants.ORDER_STATUSES_SHIPPING);

		for (CommerceOrderItem commerceOrderItem :
				_commerceOrderItemLocalService.getCommerceOrderItems(
					cpInstanceId, orderStatuses, originalKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			BigDecimal quantity = commerceOrderItem.getQuantity();

			if (BigDecimalUtil.gt(
					originalIncrementalOrderQuantity, BigDecimal.ZERO)) {

				quantity = quantity.divide(
					originalIncrementalOrderQuantity, RoundingMode.HALF_UP);
			}

			commerceOrderItem.setQuantity(
				quantity.multiply(incrementalOrderQuantity));
			commerceOrderItem.setUnitOfMeasureKey(key);

			_commerceOrderItemLocalService.updateCommerceOrderItem(
				commerceOrderItem);
		}
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}