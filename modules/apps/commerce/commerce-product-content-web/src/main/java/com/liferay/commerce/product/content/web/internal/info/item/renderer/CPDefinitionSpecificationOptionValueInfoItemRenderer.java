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

package com.liferay.commerce.product.content.web.internal.info.item.renderer;

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	service = {
		CPDefinitionSpecificationOptionValueInfoItemRenderer.class,
		InfoItemRenderer.class
	}
)
public class CPDefinitionSpecificationOptionValueInfoItemRenderer
	implements InfoItemRenderer<CPDefinitionSpecificationOptionValue> {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "product-specification");
	}

	@Override
	public void render(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (cpDefinitionSpecificationOptionValue == null) {
			return;
		}

		try {
			httpServletRequest.setAttribute(
				CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUE,
				cpDefinitionSpecificationOptionValue);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer" +
						"/cp_definition_specification_option_value/page.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)"
	)
	private ServletContext _servletContext;

}