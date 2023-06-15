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

package com.liferay.client.extension.web.internal.portlet.action;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryService;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminPortletKeys;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.PrintWriter;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"javax.portlet.name=" + ClientExtensionAdminPortletKeys.CLIENT_EXTENSION_ADMIN,
		"mvc.command.name=/client_extension_admin/export_client_extension_entry"
	},
	service = MVCResourceCommand.class
)
public class ExportClientExtensionEntryMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			Company company = _portal.getCompany(resourceRequest);

			String externalReferenceCode = resourceRequest.getParameter(
				"externalReferenceCode");

			ClientExtensionEntry clientExtensionEntry =
				_clientExtensionEntryService.
					fetchClientExtensionEntryByExternalReferenceCode(
						company.getCompanyId(), externalReferenceCode);

			if (clientExtensionEntry == null) {
				resourceResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			DateFormat dateFormat = DateUtil.getISO8601Format();

			jsonObject.put(
				"clientExtensionEntries",
				_getClientExtensionEntriesJSONObject(clientExtensionEntry)
			).put(
				"company", _getCompanyJSONObject(company)
			).put(
				"exportDate", dateFormat.format(new Date())
			).put(
				"user", _getUserJSONObject(_portal.getUser(resourceRequest))
			).put(
				"version", ClientExtensionAdminWebConstants.EXPORT_VERSION
			);

			String json = jsonObject.toString();

			resourceResponse.setContentLength(json.length());

			resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(resourceResponse);

			liferayPortletResponse.setHeader(
				"Content-Disposition",
				"attachment; filename=\"client-extension-entry." +
					clientExtensionEntry.getExternalReferenceCode() +
						".json\"");

			PrintWriter printWriter = resourceResponse.getWriter();

			printWriter.write(json);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			resourceResponse.setStatus(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private JSONObject _getClientExtensionEntriesJSONObject(
		ClientExtensionEntry... clientExtensionEntries) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (ClientExtensionEntry clientExtensionEntry :
				clientExtensionEntries) {

			jsonObject.put(
				clientExtensionEntry.getExternalReferenceCode(),
				_getClientExtensionEntryJSONObject(clientExtensionEntry));
		}

		return jsonObject;
	}

	private JSONObject _getClientExtensionEntryJSONObject(
		ClientExtensionEntry clientExtensionEntry) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"description", clientExtensionEntry.getDescription()
		).put(
			"name", clientExtensionEntry.getName()
		).put(
			"properties", clientExtensionEntry.getProperties()
		).put(
			"sourceCodeURL", clientExtensionEntry.getSourceCodeURL()
		).put(
			"type", clientExtensionEntry.getType()
		).put(
			"typeSettings", clientExtensionEntry.getTypeSettings()
		);

		return jsonObject;
	}

	private JSONObject _getCompanyJSONObject(Company company) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"id", company.getCompanyId()
		).put(
			"name", company.getName()
		).put(
			"virtualHostName", company.getVirtualHostname()
		).put(
			"webId", company.getWebId()
		);

		return jsonObject;
	}

	private JSONObject _getUserJSONObject(User user) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"fullName", user.getFullName()
		).put(
			"id", user.getUserId()
		).put(
			"screenName", user.getScreenName()
		);

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportClientExtensionEntryMVCResourceCommand.class);

	@Reference
	private ClientExtensionEntryService _clientExtensionEntryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}