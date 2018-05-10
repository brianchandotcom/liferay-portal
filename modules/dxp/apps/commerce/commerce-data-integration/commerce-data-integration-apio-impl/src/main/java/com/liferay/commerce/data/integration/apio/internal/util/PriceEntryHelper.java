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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.apio.architect.functional.Try;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = PriceEntryHelper.class)
public class PriceEntryHelper {

	public static String getProductName(CommercePriceEntry commercePriceEntry) {
		CPInstance cpInstance = _getCPInstance(commercePriceEntry);

		CPDefinition cpDefinition = Try.fromFallible(
			() -> cpInstance.getCPDefinition()
		).getUnchecked();

		return cpDefinition.getName();
	}

	public static String getSKU(CommercePriceEntry commercePriceEntry) {
		CPInstance cpInstance = _getCPInstance(commercePriceEntry);

		return cpInstance.getSku();
	}

	public CommercePriceEntry getCommercePriceEntry(Long commercePriceEntryId) {
		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry == null) {
			throw new NotFoundException(
				"Unable to find price entry with ID " + commercePriceEntryId);
		}

		return commercePriceEntry;
	}

	private static CPInstance _getCPInstance(
		CommercePriceEntry commercePriceEntry) {

		return Try.fromFallible(
			() -> commercePriceEntry.getCPInstance()
		).getUnchecked();
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

}