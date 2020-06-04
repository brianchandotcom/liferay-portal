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

package com.liferay.change.tracking.web.internal.display.company;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.Writer;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class CompanyCTDisplayRenderer implements CTDisplayRenderer<Company> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, Company company) {

		return null;
	}

	@Override
	public Class<Company> getModelClass() {
		return Company.class;
	}

	@Override
	public String getTitle(Locale locale, Company company)
		throws PortalException {

		return company.getName();
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.get(resourceBundle, "company");
	}

	@Override
	public void render(DisplayContext<Company> displayContext)
		throws Exception {

		HttpServletResponse httpServletResponse =
			displayContext.getHttpServletResponse();

		Writer writer = httpServletResponse.getWriter();

		writer.write("<p><b>");

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		writer.write(_language.get(httpServletRequest, "company-id"));

		writer.write("</b>: ");

		Company company = displayContext.getModel();

		writer.write(String.valueOf(company.getCompanyId()));

		writer.write("</p><p><b>");
		writer.write(_language.get(httpServletRequest, "name"));
		writer.write("</b>: ");
		writer.write(HtmlUtil.escape(company.getName()));
		writer.write("</p>");
	}

	@Reference
	private Language _language;

}