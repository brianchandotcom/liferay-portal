/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Crescenzo Rega
 */
public class PlacedOrderItemShipmentDTOConverterContext
	extends DefaultDTOConverterContext {

	public PlacedOrderItemShipmentDTOConverterContext(
		Object id, Locale locale, boolean supplierShipment) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_supplierShipment = supplierShipment;
	}

	public boolean isSupplierShipment() {
		return _supplierShipment;
	}

	private final boolean _supplierShipment;

}