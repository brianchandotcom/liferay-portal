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

package com.liferay.commerce.product.internal.option;

import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.option.type.display.order:Integer=200",
		"commerce.option.type.key=" + SelectCommerceOptionTypeImpl.KEY
	},
	service = CommerceOptionType.class
)
public class SelectCommerceOptionTypeImpl
	extends BaseCommerceOptionTypeImpl implements CommerceOptionType {

	public static final String KEY = "select";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "select-from-list");
	}

	@Override
	public void render(
			long cpDefinitionOptionRelId, long cpDefinitionOptionValueRelId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {
	}

	@Reference
	private Language _language;

}