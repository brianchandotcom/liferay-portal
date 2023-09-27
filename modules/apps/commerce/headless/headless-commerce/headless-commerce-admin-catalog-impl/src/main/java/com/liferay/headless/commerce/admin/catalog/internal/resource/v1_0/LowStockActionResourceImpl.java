/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.LowStockAction;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.LowStockActionResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/low-stock-action.properties",
	scope = ServiceScope.PROTOTYPE, service = LowStockActionResource.class
)
public class LowStockActionResourceImpl extends BaseLowStockActionResourceImpl {

	@Override
	public Page<LowStockAction> getLowStockActionsPage() throws Exception {
		return Page.of(
			transform(
				_commerceLowStockActivityRegistry.
					getCommerceLowStockActivities(),
				this::_toLowStockAction));
	}

	private LowStockAction _toLowStockAction(
			CommerceLowStockActivity commerceLowStockActivity)
		throws Exception {

		return _lowStockActionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, commerceLowStockActivity.getKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.LowStockActionDTOConverter)"
	)
	private DTOConverter<CommerceLowStockActivity, LowStockAction>
		_lowStockActionDTOConverter;

}