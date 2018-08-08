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
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormDisplayContext;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorErrorStatus;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=form",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.application-type=full-page-application",
		"com.liferay.portlet.application-type=widget",
		"com.liferay.portlet.css-class-wrapper=portlet-forms-display",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.friendly-url-mapping=form",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Mapping Form",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/display/",
		"javax.portlet.init-param.view-template=/display/view.jsp",
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDMFormPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			super.processAction(actionRequest, actionResponse);
		}
		catch (Exception e) {
			_portal.copyRequestParameters(actionRequest, actionResponse);

			Throwable cause = getRootCause(e);

			hideDefaultErrorMessage(actionRequest);

			if (cause instanceof DDMFormValuesValidationException) {
				addSessionErrors(
					(DDMFormValuesValidationException)cause, actionRequest);

				if (cause instanceof
						DDMFormValuesValidationException.RequiredValue) {

					SessionErrors.add(actionRequest, cause.getClass(), cause);
				}
				else {
					SessionErrors.add(
						actionRequest, DDMFormValuesValidationException.class);
				}
			}
			else {
				SessionErrors.add(actionRequest, cause.getClass(), cause);
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			if (_addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
					isSharedLayout(themeDisplay)) {

				saveParametersInSession(actionRequest);
			}
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setRenderRequestAttributes(renderRequest, renderResponse);

			DDMFormDisplayContext ddmFormPortletDisplayContext =
				(DDMFormDisplayContext)renderRequest.getAttribute(
					WebKeys.PORTLET_DISPLAY_CONTEXT);

			checkFormIsNotRestricted(
				renderRequest, renderResponse, ddmFormPortletDisplayContext);
		}
		catch (Exception e) {
			if (isSessionErrorException(e)) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}

				hideDefaultErrorMessage(renderRequest);

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	protected void addSessionError(
		DDMFormValuesValidatorError ddmFormValuesValidatorError,
		ActionRequest actionRequest) {

		DDMFormValuesValidatorErrorStatus errorStatus =
			ddmFormValuesValidatorError.getErrorStatus();

		Map<String, Object> properties =
			ddmFormValuesValidatorError.getProperties();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		if (errorStatus ==
				DDMFormValuesValidatorErrorStatus.
					MUST_SET_VALID_VALUE_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			Object value = LanguageUtil.format(
				locale, "validation-failed-for-field-x", field, false);

			SessionErrors.add(actionRequest, errorStatus.name(), value);
		}
		else if (errorStatus ==
					 DDMFormValuesValidatorErrorStatus.
						 REQUIRED_VALUE_EXCEPTION) {

			String field = HtmlUtil.escape(
				MapUtil.getString(properties, "field"));

			Object value = LanguageUtil.format(
				locale, "no-value-is-defined-for-field-x", field, false);

			SessionErrors.add(actionRequest, errorStatus.name(), value);
		}
		else {
			SessionErrors.add(
				actionRequest, DDMFormValuesValidationException.class);
		}
	}

	protected void addSessionErrors(
		DDMFormValuesValidationException exception,
		ActionRequest actionRequest) {

		List<DDMFormValuesValidatorError> ddmFormValuesValidatorErrors =
			exception.getDDMFormValuesValidatorErrors();

		for (DDMFormValuesValidatorError ddmFormValuesValidatorError :
				ddmFormValuesValidatorErrors) {

			addSessionError(ddmFormValuesValidatorError, actionRequest);
		}
	}

	protected void checkFormIsNotRestricted(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDMFormDisplayContext ddmFormDisplayContext)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMFormInstance formInstance = ddmFormDisplayContext.getFormInstance();

		if (formInstance == null) {
			return;
		}

		DDMFormInstanceSettings formInstanceSettings =
			formInstance.getSettingsModel();

		Layout layout = themeDisplay.getLayout();

		if (formInstanceSettings.requireAuthentication() &&
			!layout.isPrivateLayout()) {

			throw new PrincipalException.MustBeAuthenticated(
				themeDisplay.getUserId());
		}
	}

	protected Throwable getRootCause(Throwable throwable) {
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}

		return throwable;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		return false;
	}

	protected void saveParametersInSession(ActionRequest actionRequest) {
		long formInstanceId = ParamUtil.getLong(
			actionRequest, "formInstanceId");

		if (formInstanceId > 0) {
			PortletSession portletSession = actionRequest.getPortletSession();

			portletSession.setAttribute("formInstanceId", formInstanceId);
			portletSession.setAttribute("shared", Boolean.TRUE);
		}
	}

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		DDMFormDisplayContext ddmFormDisplayContext = new DDMFormDisplayContext(
			renderRequest, renderResponse, _ddmFormInstanceLocalService,
			_ddmFormInstanceRecordVersionLocalService, _ddmFormInstanceService,
			_ddmFormInstanceVersionLocalService, _ddmFormRenderer,
			_ddmFormValuesFactory, _ddmFormValuesMerger, _groupLocalService,
			_workflowDefinitionLinkLocalService);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddmFormDisplayContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMFormPortlet.class);

	@Reference
	private AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

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

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}