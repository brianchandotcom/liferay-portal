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

package com.liferay.commerce.internal.stock.activity;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = {
		"commerce.low.stock.activity.key=" + CommerceLowStockActivityImpl.KEY,
		"commerce.low.stock.activity.priority:Integer=10"
	},
	service = CommerceLowStockActivity.class
)
public class CommerceLowStockActivityImpl implements CommerceLowStockActivity {

	public static final String KEY = "default";

	@Override
	public void execute(CPInstance cpInstance) throws PortalException {
		if (cpInstance.isPublished()) {
			cpInstance.setPublished(false);

			_cpInstanceLocalService.updateCPInstance(cpInstance);
		}
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "set-as-unpublished");
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}