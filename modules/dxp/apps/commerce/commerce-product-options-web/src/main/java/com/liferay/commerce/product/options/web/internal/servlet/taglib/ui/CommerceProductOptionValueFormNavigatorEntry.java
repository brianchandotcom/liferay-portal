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

package com.liferay.commerce.product.options.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {"form.navigator.entry.order:Integer=90"},
	service = FormNavigatorEntry.class
)
public class CommerceProductOptionValueFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommerceProductOption>
	 {

		@Override
		public String getCategoryKey() {
			return CommerceProductOptionFormNavigatorConstants.
				CATEGORY_KEY_COMMERCE_PRODUCT_VALUE;
		}

		@Override
		public String getFormNavigatorId() {
			return CommerceProductOptionFormNavigatorConstants.
				FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION;
		}

		@Override
		public String getKey() {
			return "value";
		}

		@Override
		public String getLabel(Locale locale) {
			return "value";
		}

		@Override
		protected String getJspPath() {
			return "/product_option/value.jsp";
		}

		 @Override
		 public boolean isVisible(User user, CommerceProductOption formModelBean) {
			 return formModelBean != null;
		 }
	 }