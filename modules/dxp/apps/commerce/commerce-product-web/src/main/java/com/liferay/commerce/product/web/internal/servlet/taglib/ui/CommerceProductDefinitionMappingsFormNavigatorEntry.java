/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {"form.navigator.entry.order:Integer=100"},
	service = FormNavigatorEntry.class
)
public class CommerceProductDefinitionMappingsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommerceProductDefinition>
	implements FormNavigatorEntry<CommerceProductDefinition> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_COMMERCE_PRODUCT_MAPPINGS;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION;
	}

	@Override
	public String getKey() {
		return "mappings";
	}

	@Override
	public String getLabel(Locale locale) {
		return "mappings";
	}

	@Override
	protected String getJspPath() {
		return "/commerce_product_definitions/product/mappings.jsp";
	}

}