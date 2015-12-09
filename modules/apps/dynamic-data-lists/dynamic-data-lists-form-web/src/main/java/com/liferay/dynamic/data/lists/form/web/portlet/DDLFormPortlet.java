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

package com.liferay.dynamic.data.lists.form.web.portlet;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldValueException;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.PortletPreferencesException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.application-type=full-page-application",
		"com.liferay.portlet.application-type=widget",
		"com.liferay.portlet.css-class-wrapper=portlet-forms-display",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.friendly-url-mapping=form",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Lists Form",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/display/",
		"javax.portlet.init-param.view-template=/display/view.jsp",
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDLFormPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setRenderRequestAttributes(renderRequest, renderResponse);
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

	protected String createCaptchaResourceURL(RenderResponse renderResponse) {
		ResourceURL resourceURL = renderResponse.createResourceURL();

		resourceURL.setResourceID("captcha");

		return resourceURL.toString();
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMForm ddmForm) {

		String languageId = ParamUtil.getString(renderRequest, "languageId");

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(renderResponse));
		ddmFormRenderingContext.setLocale(
			LocaleUtil.fromLanguageId(languageId));
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());
		ddmFormRenderingContext.setDDMFormValues(
			_ddmFormValuesFactory.create(renderRequest, ddmForm));

		return ddmFormRenderingContext;
	}

	protected DDMFormLayoutRow createFullColumnDDMFormLayoutRow(
		String ddmFormFieldName) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			DDMFormLayoutColumn.FULL, ddmFormFieldName);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, DDMFormRenderingException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRecordSetException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchStructureException.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				NoSuchStructureLayoutException.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PortletPreferencesException.MustBeStrict.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected DDMForm getDDMForm(
		RenderResponse renderResponse, DDMStructure ddmStructure,
		boolean requireCaptcha) {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		if (requireCaptcha) {
			DDMFormField captchaDDMFormField = new DDMFormField(
				_DDM_FORM_FIELD_NAME_CAPTCHA, "captcha");

			captchaDDMFormField.setProperty(
				"url", createCaptchaResourceURL(renderResponse));

			ddmForm.addDDMFormField(captchaDDMFormField);
		}

		return ddmForm;
	}

	protected String getDDMFormHTML(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();
		boolean requireCaptcha = isCaptchaRequired(recordSet);

		DDMForm ddmForm = getDDMForm(
			renderResponse, ddmStructure, requireCaptcha);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(
				renderRequest, renderResponse, ddmForm);

		DDMFormLayout ddmFormLayout = getDDMFormLayout(
			ddmStructure, requireCaptcha);

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	protected DDMFormLayout getDDMFormLayout(
			DDMStructure ddmStructure, boolean requireCaptcha)
		throws PortalException {

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		if (requireCaptcha) {
			DDMFormLayoutPage lastDDMFormLayoutPage = getLastDDMFormLayoutPage(
				ddmFormLayout);

			DDMFormLayoutRow ddmFormLayoutRow =
				createFullColumnDDMFormLayoutRow(_DDM_FORM_FIELD_NAME_CAPTCHA);

			lastDDMFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
		}

		return ddmFormLayout;
	}

	protected DDMFormLayoutPage getLastDDMFormLayoutPage(
		DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayout.getDDMFormLayoutPages();

		return ddmFormLayoutPages.get(ddmFormLayoutPages.size() - 1);
	}

	protected boolean isCaptchaRequired(DDLRecordSet recordSet) {
		String requireCaptcha = recordSet.getSettingsProperty(
			"requireCaptcha", Boolean.FALSE.toString());

		return GetterUtil.getBoolean(requireCaptcha);
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DDMFormRenderingException ||
			cause instanceof CaptchaTextException ||
			cause instanceof CaptchaMaxChallengesException ||
			cause instanceof NoSuchRecordSetException ||
			cause instanceof NoSuchStructureException ||
			cause instanceof NoSuchStructureLayoutException ||
			cause instanceof PortletPreferencesException ||
			cause instanceof PrincipalException ||
			cause instanceof StorageFieldValueException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		long recordSetId = PrefsParamUtil.getLong(
			PortletPreferencesFactoryUtil.getPortletSetup(renderRequest),
			renderRequest, "recordSetId");

		if (recordSetId == 0) {
			return;
		}

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		renderRequest.setAttribute(
			DDLFormWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);

		String ddmFormHTML = getDDMFormHTML(
			renderRequest, renderResponse, recordSet);

		renderRequest.setAttribute(
			DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML, ddmFormHTML);
	}

	private static final String _DDM_FORM_FIELD_NAME_CAPTCHA = "_CAPTCHA_";

	private static final Log _log = LogFactoryUtil.getLog(DDLFormPortlet.class);

	private volatile DDLRecordSetService _ddlRecordSetService;
	private volatile DDMFormRenderer _ddmFormRenderer;
	private volatile DDMFormValuesFactory _ddmFormValuesFactory;

}