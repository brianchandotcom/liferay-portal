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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.DDMFormPagesTemplateContextFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-context-provider",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormContextProviderServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-context-provider/*"
	},
	service = Servlet.class
)
public class DDMFormContextProviderServlet extends HttpServlet {

	protected List<Object> createDDMFormPagesTemplateContext(
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace) {

		try {
			DDMForm ddmForm = getDDMForm(request);

			DDMFormValues ddmFormValues = getDDMFormValues(request, ddmForm);

			Locale defaultLocale = ddmForm.getDefaultLocale();

			DDMFormRenderingContext ddmFormRenderingContext =
				createDDMFormRenderingContext(
					request, response, ddmFormValues, defaultLocale,
					portletNamespace);

			_prepareThreadLocal(request, defaultLocale);

			DDMFormLayout ddmFormLayout = getDDMFormLayout(request);

			DDMFormPagesTemplateContextFactory
				ddmFormPagesTemplateContextFactory =
					new DDMFormPagesTemplateContextFactory(
						ddmForm, ddmFormLayout, ddmFormRenderingContext);

			ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
				_ddmFormEvaluator);
			ddmFormPagesTemplateContextFactory.
				setDDMFormFieldTypeServicesTracker(
					_ddmFormFieldTypeServicesTracker);

			return ddmFormPagesTemplateContextFactory.create();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		HttpServletRequest request, HttpServletResponse response,
		DDMFormValues ddmFormValues, Locale locale, String portletNamespace) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		ddmFormRenderingContext.setHttpServletRequest(request);
		ddmFormRenderingContext.setHttpServletResponse(response);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(portletNamespace);

		return ddmFormRenderingContext;
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String portletNamespace = ParamUtil.getString(
			request, "portletNamespace");

		List<Object> ddmFormPagesTemplateContext =
			createDDMFormPagesTemplateContext(
				request, response, portletNamespace);

		if (ddmFormPagesTemplateContext == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			response,
			jsonSerializer.serializeDeep(ddmFormPagesTemplateContext));
	}

	protected DDMForm getDDMForm(HttpServletRequest request)
		throws PortalException {

		String serializedDDMForm = ParamUtil.getString(
			request, "serializedDDMForm");

		return _ddmFormJSONDeserializer.deserialize(serializedDDMForm);
	}

	protected DDMFormLayout getDDMFormLayout(HttpServletRequest request)
		throws PortalException {

		String serializedDDMFormLayout = ParamUtil.getString(
			request, "serializedDDMFormLayout");

		return _ddmFormLayoutJSONDeserializer.deserialize(
			serializedDDMFormLayout);
	}

	protected DDMFormValues getDDMFormValues(
			HttpServletRequest request, DDMForm ddmForm)
		throws PortalException {

		String serializedDDMFormValues = ParamUtil.getString(
			request, "serializedDDMFormValues");

		return _ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, serializedDDMFormValues);
	}

	private void _prepareThreadLocal(HttpServletRequest request, Locale locale)
		throws Exception, PortalException {

		User user = PortalUtil.getUser(request);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		LocaleThreadLocal.setThemeDisplayLocale(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormContextProviderServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;

	@Reference
	private DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

}