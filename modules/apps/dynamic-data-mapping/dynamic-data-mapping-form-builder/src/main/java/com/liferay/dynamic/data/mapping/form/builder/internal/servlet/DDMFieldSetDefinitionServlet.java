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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-fieldset-definition",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFieldSetDefinitionServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-fieldset-definition/*"
	},
	service = Servlet.class
)
public class DDMFieldSetDefinitionServlet extends BaseDDMFormBuilderServlet {

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

		if (ddmStructureId == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		String languageId = ParamUtil.getString(request, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		LocaleThreadLocal.setThemeDisplayLocale(locale);

		Optional<DDMStructure> ddmStructureOptional = Optional.ofNullable(
			getDDMStructure(ddmStructureId));

		DDMFormBuilderContextResponse fieldContext =
			_ddmFormBuilderContextFactory.create(
				DDMFormBuilderContextRequest.with(
					ddmStructureOptional, request, response, locale, true));

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		ServletResponseUtil.write(
			response, jsonSerializer.serializeDeep(fieldContext.getContext()));
	}

	protected DDMStructure getDDMStructure(long ddmStructureId) {
		try {
			return _ddmStructureService.getStructure(ddmStructureId);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldSetDefinitionServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private JSONFactory _jsonFactory;

}