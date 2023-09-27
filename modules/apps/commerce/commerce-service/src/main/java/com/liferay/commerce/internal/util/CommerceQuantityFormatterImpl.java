/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceQuantityFormatter.class)
public class CommerceQuantityFormatterImpl
	implements CommerceQuantityFormatter {

	@Override
	public BigDecimal format(
			CPInstance cpInstance, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		return _format(
			_cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasure(
				cpInstance.getCPInstanceId(), unitOfMeasureKey),
			quantity);
	}

	@Override
	public BigDecimal format(
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
			BigDecimal quantity)
		throws PortalException {

		return _format(cpInstanceUnitOfMeasure, quantity);
	}

	@Override
	public BigDecimal format(
			long cpInstanceId, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		return _format(
			_cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasure(
				cpInstanceId, unitOfMeasureKey),
			quantity);
	}

	@Override
	public BigDecimal format(
			long companyId, BigDecimal quantity, String sku,
			String unitOfMeasureKey)
		throws PortalException {

		return _format(
			_cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasure(
				companyId, unitOfMeasureKey, sku),
			quantity);
	}

	private BigDecimal _format(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, BigDecimal quantity) {

		if (quantity == null) {
			return null;
		}

		if (cpInstanceUnitOfMeasure != null) {
			return quantity.setScale(
				cpInstanceUnitOfMeasure.getPrecision(), RoundingMode.HALF_UP);
		}

		return quantity.setScale(0, RoundingMode.HALF_UP);
	}

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}