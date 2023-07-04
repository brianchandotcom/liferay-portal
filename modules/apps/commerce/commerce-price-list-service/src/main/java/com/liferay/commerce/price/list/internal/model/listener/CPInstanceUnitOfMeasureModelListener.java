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

package com.liferay.commerce.price.list.internal.model.listener;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ModelListener.class)
public class CPInstanceUnitOfMeasureModelListener
	extends BaseModelListener<CPInstanceUnitOfMeasure> {

	@Override
	public void onAfterRemove(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws ModelListenerException {

		try {
			CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
				cpInstanceUnitOfMeasure.getCPInstanceId());

			if (cpInstance != null) {
				if (_isLastCPInstanceUnitOfMeasure(
						cpInstanceUnitOfMeasure.getCPInstanceId())) {

					List<CommercePriceEntry> commercePriceEntries =
						_commercePriceEntryLocalService.getCommercePriceEntries(
							cpInstance.getCPInstanceUuid(),
							cpInstanceUnitOfMeasure.
								getIncrementalOrderQuantity(),
							cpInstanceUnitOfMeasure.getKey());

					for (CommercePriceEntry commercePriceEntry :
							commercePriceEntries) {

						commercePriceEntry.setQuantity(BigDecimal.ONE);
						commercePriceEntry.setUnitOfMeasureKey(null);

						_commercePriceEntryLocalService.
							updateCommercePriceEntry(commercePriceEntry);

						List<CommerceTierPriceEntry> commerceTierPriceEntries =
							_commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS);

						for (CommerceTierPriceEntry commerceTierPriceEntry :
								commerceTierPriceEntries) {

							commerceTierPriceEntry.setMinQuantity(
								BigDecimal.ONE);

							_commerceTierPriceEntryLocalService.
								updateCommerceTierPriceEntry(
									commerceTierPriceEntry);
						}
					}
				}
				else {
					_commercePriceEntryLocalService.deleteCommercePriceEntries(
						cpInstance.getCPInstanceUuid(),
						cpInstanceUnitOfMeasure.getIncrementalOrderQuantity(),
						cpInstanceUnitOfMeasure.getKey());
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			CPInstanceUnitOfMeasure originalCPInstanceUnitOfMeasure,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws ModelListenerException {

		try {
			String originalUnitOfMeasureKey =
				originalCPInstanceUnitOfMeasure.getKey();
			String unitOfMeasureKey = cpInstanceUnitOfMeasure.getKey();

			BigDecimal originalIncrementalOrderQuantity =
				originalCPInstanceUnitOfMeasure.getIncrementalOrderQuantity();
			BigDecimal incrementalOrderQuantity =
				cpInstanceUnitOfMeasure.getIncrementalOrderQuantity();

			int compare = originalIncrementalOrderQuantity.compareTo(
				incrementalOrderQuantity);

			if (!StringUtil.equals(
					originalUnitOfMeasureKey, unitOfMeasureKey) ||
				(compare != 0)) {

				CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
					cpInstanceUnitOfMeasure.getCPInstanceId());

				if (cpInstance != null) {
					List<CommercePriceEntry> commercePriceEntries =
						_commercePriceEntryLocalService.getCommercePriceEntries(
							cpInstance.getCPInstanceUuid(),
							originalIncrementalOrderQuantity,
							originalUnitOfMeasureKey);

					ServiceContext serviceContext =
						ServiceContextThreadLocal.getServiceContext();

					if (serviceContext == null) {
						serviceContext = new ServiceContext();

						serviceContext.setUserId(
							originalCPInstanceUnitOfMeasure.getUserId());
					}

					for (CommercePriceEntry commercePriceEntry :
							commercePriceEntries) {

						_commercePriceEntryLocalService.
							updateCommercePriceEntry(
								commercePriceEntry.getCommercePriceEntryId(),
								commercePriceEntry.getPrice(),
								commercePriceEntry.getPriceOnApplication(),
								commercePriceEntry.getPromoPrice(),
								unitOfMeasureKey, serviceContext);

						List<CommerceTierPriceEntry> commerceTierPriceEntries =
							_commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS);

						for (CommerceTierPriceEntry commerceTierPriceEntry :
								commerceTierPriceEntries) {

							commerceTierPriceEntry.setMinQuantity(
								incrementalOrderQuantity);

							_commerceTierPriceEntryLocalService.
								updateCommerceTierPriceEntry(
									commerceTierPriceEntry);
						}
					}
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private boolean _isLastCPInstanceUnitOfMeasure(long cpInstanceId)
		throws PortalException {

		int cpInstanceUnitOfMeasuresCount =
			_cpInstanceUnitOfMeasureLocalService.
				getCPInstanceUnitOfMeasuresCount(cpInstanceId);

		if (cpInstanceUnitOfMeasuresCount == 0) {
			return true;
		}

		return false;
	}

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}