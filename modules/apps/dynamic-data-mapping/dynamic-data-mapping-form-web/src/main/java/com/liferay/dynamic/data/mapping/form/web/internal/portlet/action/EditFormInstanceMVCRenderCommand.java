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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.activator.DDMFormWebConfigurationActivator;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormAdminDisplayContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Optional;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bruno Basto
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=/admin/edit_form_instance"
	}
)
public class EditFormInstanceMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		DDMFormWebConfiguration ddmFormWebConfiguration =
			_ddmFormWebConfigurationActivator.getDDMFormWebConfiguration();

		if (ddmFormWebConfiguration.enableExperimentalInterface()) {
			DDMFormAdminDisplayContext ddmFormAdminDisplayContext =
				(DDMFormAdminDisplayContext)renderRequest.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

			long ddmStructureId;

			try {
				ddmStructureId = ddmFormAdminDisplayContext.getDDMStructureId();

				String serializedFormBuilderContext = getFormBuilderContext(
					ddmStructureId,
					PortalUtil.getHttpServletRequest(renderRequest));

				renderRequest.setAttribute(
					"serializedFormBuilderContext",
					serializedFormBuilderContext);
			} catch (PortalException e) {
				e.printStackTrace();
			}

			renderRequest.setAttribute(
				"mainRequire",
				_npmResolver.resolveModuleName("dynamic-data-mapping-form-web") +
					" as main");

			return "/metal/edit_form_instance.jsp";
		}

		return "/admin/edit_form_instance.jsp";
	}

	protected String getFormBuilderContext(
		long ddmStructureId, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serializedFormBuilderContext = ParamUtil.getString(
			request, "serializedFormBuilderContext");

		if (Validator.isNotNull(serializedFormBuilderContext)) {
			return serializedFormBuilderContext;
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		Optional<DDMStructure> ddmStructureOptional = Optional.ofNullable(
			_ddmStructureLocalService.fetchDDMStructure(ddmStructureId));

		Locale locale = themeDisplay.getSiteDefaultLocale();

		if (ddmStructureOptional.isPresent()) {
			DDMStructure ddmStructure = ddmStructureOptional.get();

			DDMForm ddmForm = ddmStructure.getDDMForm();

			locale = ddmForm.getDefaultLocale();
		}

		DDMFormBuilderContextResponse formBuilderContextResponse =
			_ddmFormBuilderContextFactory.create(
				DDMFormBuilderContextRequest.with(
					ddmStructureOptional, themeDisplay.getRequest(),
					themeDisplay.getResponse(), locale, true));

		return jsonSerializer.serializeDeep(
			formBuilderContextResponse.getContext());
	}

	@Reference(unbind = "-")
	protected void setDDMFormBuilderContextFactory(
		DDMFormBuilderContextFactory ddmFormBuilderContextFactory) {

		_ddmFormBuilderContextFactory = ddmFormBuilderContextFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected void unsetDDMFormWebConfigurationActivator(
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator) {

		_ddmFormWebConfigurationActivator = null;
	}

	private static DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;
	private static DDMStructureLocalService _ddmStructureLocalService;
	private static JSONFactory _jsonFactory;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetDDMFormWebConfigurationActivator"
	)
	private volatile DDMFormWebConfigurationActivator
		_ddmFormWebConfigurationActivator;

	@Reference
	private NPMResolver _npmResolver;

}