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

package com.liferay.dynamic.data.lists.display.web.portlet;

import com.liferay.dynamic.data.lists.display.web.constants.DDLDisplayPortletKeys;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=dynamic-data-lists-display",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Lists Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDLDisplayPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDLDisplayPortlet extends MVCPortlet {

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

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchRecordSetException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchRecordSetException ||
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

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		long recordSetId = PrefsParamUtil.getLong(
			renderRequest.getPreferences(), renderRequest, "recordSetId");

		DDLRecordSet recordSet = null;

		if (recordSetId > 0) {
			recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

			renderRequest.setAttribute(
				WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLDisplayPortlet.class);

	private DDLRecordSetService _ddlRecordSetService;

}