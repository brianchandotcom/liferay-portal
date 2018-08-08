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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.activator.DDMFormWebConfigurationActivator;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormAdminDisplayContext;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormAdminFieldSetDisplayContext;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidatorErrorStatus;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorErrorStatus;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.autopropagated-parameters=currentTab",
		"com.liferay.portlet.css-class-wrapper=portlet-forms-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.icon=/admin/icons/form.png",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Forms", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/admin/",
		"javax.portlet.init-param.valid-paths=/metal/edit_form_instance.jsp",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDMFormAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setRenderRequestAttributes(renderRequest, renderResponse);
		}
		catch (Exception e) {
			Throwable cause = getRootCause(e);

			if (cause instanceof DDMFormValidationException) {
				hideDefaultErrorMessage(renderRequest);

				addSessionErrors(
					(DDMFormValidationException)cause, renderRequest);
			}
			else if (cause instanceof DDMFormLayoutValidationException) {
				hideDefaultErrorMessage(renderRequest);

				addSessionErrors(
					(DDMFormLayoutValidationException)cause, renderRequest);
			}
			else if (isSessionErrorException(e)) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}

				hideDefaultErrorMessage(renderRequest);

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				_log.error(e, e);

				throw new PortletException(e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	protected void addSessionError(
		DDMFormLayoutValidatorError ddmFormLayoutValidatorError,
		RenderRequest renderRequest) {

		DDMFormLayoutValidatorErrorStatus errorStatus =
			ddmFormLayoutValidatorError.getErrorStatus();

		Map<String, Object> properties =
			ddmFormLayoutValidatorError.getProperties();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		if (errorStatus ==
				DDMFormLayoutValidatorErrorStatus.
					MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION) {

			Set<String> fields = (Set<String>)properties.get("fields");

			Stream<String> stream = fields.stream();

			String argument = HtmlUtil.escape(
				stream.collect(Collectors.joining(StringPool.COMMA_AND_SPACE)));

			Object value = LanguageUtil.format(
				locale,
				"the-definition-field-name-x-was-defined-more-than-once",
				argument, false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else {
			SessionErrors.add(
				renderRequest, DDMFormLayoutValidationException.class);
		}
	}

	protected void addSessionError(
		DDMFormValidatorError ddmFormValidatorError,
		RenderRequest renderRequest) {

		DDMFormValidatorErrorStatus errorStatus =
			ddmFormValidatorError.getErrorStatus();

		Map<String, Object> properties = ddmFormValidatorError.getProperties();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		if (errorStatus ==
				DDMFormValidatorErrorStatus.
					MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			Object value = LanguageUtil.format(
				locale,
				"the-definition-field-name-x-was-defined-more-than-once", field,
				false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValidatorErrorStatus.
						 MUST_SET_FIELDS_FOR_FORM_EXCEPTION) {

			Object value = LanguageUtil.get(
				locale, "please-add-at-least-one-field");

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValidatorErrorStatus.
						 MUST_SET_OPTIONS_FOR_FIELD_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			Object value = LanguageUtil.format(
				locale, "at-least-one-option-should-be-set-for-field-x", field,
				false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValidatorErrorStatus.
						 MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			Object value = LanguageUtil.format(
				locale, "invalid-characters-were-defined-for-field-name-x",
				field, false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValidatorErrorStatus.
						 MUST_SET_VALID_VALIDATION_EXPRESSION_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			String expression = HtmlUtil.escape(
				MapUtil.getString(properties, "expression"));

			Object value = LanguageUtil.format(
				locale,
				"the-validation-expression-x-set-for-field-x-is-invalid",
				new String[] {expression, field}, false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValidatorErrorStatus.
						 MUST_SET_VALID_VISIBILITY_EXPRESSION_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			String expression = HtmlUtil.escape(
				MapUtil.getString(properties, "expression"));

			Object value = LanguageUtil.format(
				locale,
				"the-visibility-expression-x-set-for-field-x-is-invalid",
				new String[] {expression, field}, false);

			SessionErrors.add(renderRequest, errorStatus.name(), value);
		}
		else {
			SessionErrors.add(renderRequest, DDMFormValidationException.class);
		}
	}

	protected void addSessionErrors(
		DDMFormLayoutValidationException exception,
		RenderRequest renderRequest) {

		List<DDMFormLayoutValidatorError> ddmFormLayoutValidatorErrors =
			exception.getDDMFormLayoutValidatorErrors();

		for (DDMFormLayoutValidatorError ddmFormLayoutValidatorError :
				ddmFormLayoutValidatorErrors) {

			addSessionError(ddmFormLayoutValidatorError, renderRequest);
		}
	}

	protected void addSessionErrors(
		DDMFormValidationException exception, RenderRequest renderRequest) {

		List<DDMFormValidatorError> ddmFormValidatorErrors =
			exception.getDDMFormValidatorErrors();

		for (DDMFormValidatorError ddmFormValidatorError :
				ddmFormValidatorErrors) {

			addSessionError(ddmFormValidatorError, renderRequest);
		}
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			_portal.getHttpServletRequest(renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			_portal.getHttpServletResponse(renderResponse));
		ddmFormRenderingContext.setContainerId("settings");
		ddmFormRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMForm createSettingsDDMForm(
			long formInstanceId, ThemeDisplay themeDisplay)
		throws PortalException {

		DDMForm ddmForm = DDMFormFactory.create(DDMFormInstanceSettings.class);

		ddmForm.addAvailableLocale(themeDisplay.getLocale());
		ddmForm.setDefaultLocale(themeDisplay.getLocale());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		// Storage type

		DDMFormField ddmFormField = ddmFormFieldsMap.get("storageType");

		if (formInstanceId > 0) {
			ddmFormField.setReadOnly(true);
		}

		return ddmForm;
	}

	protected Throwable getRootCause(Throwable throwable) {
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}

		return throwable;
	}

	protected void setDDMFormRenderingContextDDMFormValues(
			DDMFormRenderingContext ddmFormRenderingContext, DDMForm ddmForm,
			long formInstanceId)
		throws PortalException {

		DDMFormInstance formInstance =
			_ddmFormInstanceLocalService.fetchFormInstance(formInstanceId);

		if (formInstance == null) {
			return;
		}

		DDMFormValues ddmFormValues = formInstance.getSettingsDDMFormValues();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
	}

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		String currentTab = ParamUtil.getString(
			renderRequest, "currentTab", "forms");

		if (currentTab.equals("element-set")) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new DDMFormAdminFieldSetDisplayContext(
					renderRequest, renderResponse,
					_addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
					_ddmFormBuilderContextFactory,
					_ddmFormWebConfigurationActivator.
						getDDMFormWebConfiguration(),
					_ddmFormInstanceRecordLocalService,
					_ddmFormInstanceRecordWriterTracker,
					_ddmFormInstanceService,
					_ddmFormInstanceVersionLocalService,
					_ddmFormFieldTypeServicesTracker,
					_ddmFormFieldTypesJSONSerializer, _ddmFormRenderer,
					_ddmFormValuesFactory, _ddmFormValuesMerger,
					_ddmStructureLocalService, _ddmStructureService,
					_jsonFactory, _npmResolver));
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long formInstanceId = ParamUtil.getLong(
				renderRequest, "formInstanceId");

			DDMForm ddmForm = createSettingsDDMForm(
				formInstanceId, themeDisplay);

			DDMFormRenderingContext ddmFormRenderingContext =
				createDDMFormRenderingContext(renderRequest, renderResponse);

			setDDMFormRenderingContextDDMFormValues(
				ddmFormRenderingContext, ddmForm, formInstanceId);

			DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
				DDMFormInstanceSettings.class);

			ddmFormLayout.setPaginationMode(DDMFormLayout.TABBED_MODE);

			String ddmFormHTML = _ddmFormRenderer.render(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

			renderRequest.setAttribute(
				DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML, ddmFormHTML);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new DDMFormAdminDisplayContext(
					renderRequest, renderResponse,
					_addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
					_ddmFormBuilderContextFactory,
					_ddmFormWebConfigurationActivator.
						getDDMFormWebConfiguration(),
					_ddmFormInstanceRecordLocalService,
					_ddmFormInstanceRecordWriterTracker,
					_ddmFormInstanceService,
					_ddmFormInstanceVersionLocalService,
					_ddmFormFieldTypeServicesTracker,
					_ddmFormFieldTypesJSONSerializer, _ddmFormRenderer,
					_ddmFormValuesFactory, _ddmFormValuesMerger,
					_ddmStructureLocalService, _ddmStructureService,
					_jsonFactory, _npmResolver));
		}
	}

	protected void unsetDDMFormWebConfigurationActivator(
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator) {

		_ddmFormWebConfigurationActivator = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAdminPortlet.class);

	@Reference
	private AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;

	@Reference
	private DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormFieldTypesJSONSerializer _ddmFormFieldTypesJSONSerializer;

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMFormInstanceRecordWriterTracker
		_ddmFormInstanceRecordWriterTracker;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMFormValuesMerger _ddmFormValuesMerger;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetDDMFormWebConfigurationActivator"
	)
	private volatile DDMFormWebConfigurationActivator
		_ddmFormWebConfigurationActivator;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

}