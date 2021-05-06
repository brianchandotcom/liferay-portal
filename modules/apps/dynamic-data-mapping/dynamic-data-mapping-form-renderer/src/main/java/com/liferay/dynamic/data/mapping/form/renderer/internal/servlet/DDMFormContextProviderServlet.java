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
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		createContext(httpServletRequest, httpServletResponse);

		super.service(httpServletRequest, httpServletResponse);
	}

	protected void createContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);
		}
		catch (ActionException actionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(actionException, actionException);
			}
		}
	}

	protected List<Object> createDDMFormPagesTemplateContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portletNamespace) {

		try {
			Locale locale = LocaleUtil.fromLanguageId(
				LanguageUtil.getLanguageId(httpServletRequest));

			DDMFormRenderingContext ddmFormRenderingContext =
				createDDMFormRenderingContext(
					httpServletRequest, httpServletResponse, locale,
					portletNamespace);

			DDMFormTemplateContextProcessor ddmFormTemplateContextProcessor =
				createDDMFormTemplateContextProcessor(httpServletRequest);

			ddmFormRenderingContext.setDDMFormInstanceId(
				ddmFormTemplateContextProcessor.getDDMFormInstanceId());
			ddmFormRenderingContext.setDDMFormValues(
				ddmFormTemplateContextProcessor.getDDMFormValues());
			ddmFormRenderingContext.setGroupId(
				ddmFormTemplateContextProcessor.getGroupId());

			_prepareThreadLocal(locale);

			DDMFormPagesTemplateContextFactory
				ddmFormPagesTemplateContextFactory =
					new DDMFormPagesTemplateContextFactory(
						ddmFormTemplateContextProcessor.getDDMForm(),
						ddmFormTemplateContextProcessor.getDDMFormLayout(),
						ddmFormRenderingContext,
						_ddmStructureLayoutLocalService,
						_ddmStructureLocalService, _jsonFactory);

			ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
				_ddmFormEvaluator);
			ddmFormPagesTemplateContextFactory.
				setDDMFormFieldTypeServicesTracker(
					_ddmFormFieldTypeServicesTracker);

			return ddmFormPagesTemplateContextFactory.create();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Locale locale,
		String portletNamespace) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(portletNamespace);
		ddmFormRenderingContext.setReturnFullContext(
			ParamUtil.getBoolean(httpServletRequest, "returnFullContext"));

		return ddmFormRenderingContext;
	}

	protected DDMFormTemplateContextProcessor
			createDDMFormTemplateContextProcessor(
				HttpServletRequest httpServletRequest)
		throws Exception {

		String serializedFormContext = ParamUtil.getString(
			httpServletRequest, "serializedFormContext");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedFormContext);

		return new DDMFormTemplateContextProcessor(
			jsonObject, ParamUtil.getString(httpServletRequest, "languageId"));
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String portletNamespace = ParamUtil.getString(
			httpServletRequest, "portletNamespace");

		List<Object> ddmFormPagesTemplateContext =
			createDDMFormPagesTemplateContext(
				httpServletRequest, httpServletResponse, portletNamespace);

		if (ddmFormPagesTemplateContext == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse,
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
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}