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

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán Grande
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/portal_instances/add_instance"
	},
	service = MVCActionCommand.class
)
public class AddInstanceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_addInstance(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String errorMessage = "an-unexpected-error-occurred";

			if (exception instanceof CompanyMxException) {
				errorMessage = "please-enter-a-valid-mail-domain";
			}
			else if (exception instanceof CompanyVirtualHostException) {
				errorMessage = "please-enter-a-valid-virtual-host";
			}
			else if (exception instanceof CompanyWebIdException) {
				errorMessage = "please-enter-a-valid-web-id";
			}

			jsonObject.put(
				"error",
				_language.get(actionRequest.getLocale(), errorMessage));

			hideDefaultSuccessMessage(actionRequest);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private void _addInstance(ActionRequest actionRequest) throws Exception {
		String webId = ParamUtil.getString(actionRequest, "webId");
		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname");
		String mx = ParamUtil.getString(actionRequest, "mx");
		int maxUsers = ParamUtil.getInteger(actionRequest, "maxUsers");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		Company company = _companyService.addCompany(
			webId, virtualHostname, mx, maxUsers, active);

		String siteInitializerKey = ParamUtil.getString(
			actionRequest, "siteInitializerKey");
		ServletContext servletContext =
			(ServletContext)actionRequest.getAttribute(WebKeys.CTX);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					company.getCompanyId())) {

			_portalInstancesLocalService.initializePortalInstance(
				company.getCompanyId(), siteInitializerKey, servletContext);
		}

		_synchronizePortalInstances();
	}

	private void _synchronizePortalInstances() {
		_portalInstancesLocalService.synchronizePortalInstances();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInstanceMVCActionCommand.class);

	@Reference
	private CompanyService _companyService;

	@Reference
	private Language _language;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}