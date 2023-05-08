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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.LowStockAction;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.stock.activity.CommerceLowStockActivity",
	service = DTOConverter.class
)
public class LowStockActionDTOConverter
	implements DTOConverter<CommerceLowStockActivity, LowStockAction> {

	@Override
	public String getContentType() {
		return LowStockAction.class.getSimpleName();
	}

	@Override
	public LowStockAction toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceLowStockActivity commerceLowStockActivity =
			_commerceLowStockActivityRegistry.getCommerceLowStockActivity(
				(String)dtoConverterContext.getId());

		return new LowStockAction() {
			{
				key = commerceLowStockActivity.getKey();
				label = LanguageUtils.getLanguageIdMap(
					commerceLowStockActivity.getLabelMap());
			}
		};
	}

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

}