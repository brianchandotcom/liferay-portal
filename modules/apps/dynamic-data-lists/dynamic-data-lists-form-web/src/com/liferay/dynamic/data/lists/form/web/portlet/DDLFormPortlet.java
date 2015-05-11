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

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetService;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLayoutException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.util.log4j.Log4JUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=dynamic-data-lists-form",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Lists Form Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDLFormPortletKeys.DDL_FORM,
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

	@Activate
	protected void activate() {
		Class<? extends MVCPortlet> clazz = getClass();

		initLogger(clazz.getClassLoader());
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

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

		return ddmFormRenderingContext;
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
				renderRequest, PrincipalException.class.getName())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getDDMFormHTML(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDMStructure ddmStructure)
		throws PortalException {

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(renderRequest, renderResponse);

		return _ddmFormRenderer.render(
			ddmStructure.getDDMForm(), ddmStructure.getDDMFormLayout(),
			ddmFormRenderingContext);
	}

	protected void initLogger(ClassLoader classLoader) {
		Log4JUtil.configureLog4J(
			classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DDMFormRenderingException ||
			cause instanceof NoSuchRecordSetException ||
			cause instanceof NoSuchStructureException ||
			cause instanceof NoSuchStructureLayoutException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

	@Reference
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		long recordSetId = PrefsParamUtil.getLong(
			renderRequest.getPreferences(), renderRequest, "recordSetId");

		if (recordSetId == 0) {
			return;
		}

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		renderRequest.setAttribute(
			WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);

		String ddmFormHTML = getDDMFormHTML(
			renderRequest, renderResponse, recordSet.getDDMStructure());

		renderRequest.setAttribute(
			WebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML, ddmFormHTML);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDLFormPortlet.class);

	private DDLRecordSetService _ddlRecordSetService;
	private DDMFormRenderer _ddmFormRenderer;

}