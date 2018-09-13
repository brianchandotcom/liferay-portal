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
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

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

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		createContext(request, response);

		super.service(request, response);
	}

	protected void createContext(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (ActionException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae, ae);
			}
		}
	}

	protected List<Object> createDDMFormPagesTemplateContext(
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace) {

		try {
			String languageId = LanguageUtil.getLanguageId(request);

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			DDMFormRenderingContext ddmFormRenderingContext =
				createDDMFormRenderingContext(
					request, response, locale, portletNamespace);

			DDMFormTemplateContextProcessor ddmFormTemplateContextProcessor =
				createDDMFormTemplateContextProcessor(request);

			DDMFormValues ddmFormValues =
				ddmFormTemplateContextProcessor.getDDMFormValues();

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setGroupId(
				ddmFormTemplateContextProcessor.getGroupId());

			_prepareThreadLocal(locale);

			DDMForm ddmForm = ddmFormTemplateContextProcessor.getDDMForm();

			DDMFormLayout ddmFormLayout =
				ddmFormTemplateContextProcessor.getDDMFormLayout();

			DDMFormPagesTemplateContextFactory
				ddmFormPagesTemplateContextFactory =
					new DDMFormPagesTemplateContextFactory(
						ddmForm, ddmFormLayout, ddmFormRenderingContext);

			ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
				_ddmFormEvaluator);
			ddmFormPagesTemplateContextFactory.
				setDDMFormFieldTypeServicesTracker(
					_ddmFormFieldTypeServicesTracker);
			ddmFormPagesTemplateContextFactory.setJSONFactory(_jsonFactory);

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
		HttpServletRequest request, HttpServletResponse response, Locale locale,
		String portletNamespace) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(request);
		ddmFormRenderingContext.setHttpServletResponse(response);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(portletNamespace);

		return ddmFormRenderingContext;
	}

	protected DDMFormTemplateContextProcessor
			createDDMFormTemplateContextProcessor(HttpServletRequest request)
		throws Exception {

		String serializedFormContext = ParamUtil.getString(
			request, "serializedFormContext");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedFormContext);

		return new DDMFormTemplateContextProcessor(
			jsonObject, ParamUtil.getString(request, "languageId"));
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

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

	private void _prepareThreadLocal(Locale locale)
		throws Exception, PortalException {

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
	private JSONFactory _jsonFactory;

}