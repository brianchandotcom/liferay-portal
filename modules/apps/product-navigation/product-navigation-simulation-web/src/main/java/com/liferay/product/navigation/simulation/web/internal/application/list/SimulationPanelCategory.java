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

package com.liferay.product.navigation.simulation.web.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.product.navigation.simulation.constants.ProductNavigationSimulationConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.HIDDEN,
		"panel.category.order:Integer=500"
	},
	service = PanelCategory.class
)
public class SimulationPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return ProductNavigationSimulationConstants.
			SIMULATION_PANEL_CATEGORY_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "simulation");
	}

}