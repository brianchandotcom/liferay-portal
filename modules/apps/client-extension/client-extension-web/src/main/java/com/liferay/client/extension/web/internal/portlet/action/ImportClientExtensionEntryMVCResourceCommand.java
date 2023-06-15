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

import com.liferay.client.extension.exception.ClientExtensionEntryNameException;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryService;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminPortletKeys;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebConstants;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"javax.portlet.name=" + ClientExtensionAdminPortletKeys.CLIENT_EXTENSION_ADMIN,
		"mvc.command.name=/client_extension_admin/import_client_extension_entry"
	},
	service = MVCResourceCommand.class
)
public class ImportClientExtensionEntryMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			_import(resourceRequest);

			SessionMessages.add(
				resourceRequest, "requestProcessed",
				"your-request-completed-successfully");

			_sendResponse(null, null, resourceRequest, resourceResponse);
		}
		catch (ClientExtensionEntryNameException
					clientExtensionEntryNameException) {

			_sendResponse(
				"client-extension-name-is-required", null, resourceRequest,
				resourceResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(clientExtensionEntryNameException);
			}
		}
		catch (ClientExtensionEntryTypeException
					clientExtensionEntryTypeException) {

			_sendResponse(
				"client-extension-type-is-invalid", null, resourceRequest,
				resourceResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(clientExtensionEntryTypeException);
			}
		}
		catch (ClientExtensionEntryTypeSettingsException
					clientExtensionEntryTypeSettingsException) {

			_sendResponse(
				clientExtensionEntryTypeSettingsException.getMessageKey(),
				clientExtensionEntryTypeSettingsException.getMessageArguments(),
				resourceRequest, resourceResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(clientExtensionEntryTypeSettingsException);
			}
		}
		catch (JSONException jsonException) {
			_sendResponse(
				"import-file-format-is-not-valid-or-has-and-unsupported-" +
					"version-number",
				null, resourceRequest, resourceResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}
		catch (Exception exception) {
			_sendResponse(
				"an-error-occurred", null, resourceRequest, resourceResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _import(ResourceRequest resourceRequest)
		throws IOException, PortalException, PortletException {

		for (Part part : resourceRequest.getParts()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(part.getInputStream()));

			if (jsonObject.getInt("version", -1) !=
					ClientExtensionAdminWebConstants.EXPORT_VERSION) {

				throw new JSONException("Invalid version number");
			}

			JSONObject clientExtensionEntriesJSONObject =
				jsonObject.getJSONObject("clientExtensionEntries");

			for (String externalReferenceCode :
					clientExtensionEntriesJSONObject.keySet()) {

				jsonObject = clientExtensionEntriesJSONObject.getJSONObject(
					externalReferenceCode);

				ClientExtensionEntry clientExtensionEntry =
					_clientExtensionEntryService.
						fetchClientExtensionEntryByExternalReferenceCode(
							_portal.getCompanyId(resourceRequest),
							externalReferenceCode);

				_validate(clientExtensionEntry, jsonObject);

				if (clientExtensionEntry == null) {
					_clientExtensionEntryService.addClientExtensionEntry(
						externalReferenceCode,
						jsonObject.getString("description"),
						_localization.getLocalizationMap(
							jsonObject.getString("name")),
						jsonObject.getString("properties"),
						jsonObject.getString("sourceCodeURL"),
						jsonObject.getString("type"),
						jsonObject.getString("typeSettings"));
				}
				else {
					_clientExtensionEntryService.updateClientExtensionEntry(
						clientExtensionEntry.getClientExtensionEntryId(),
						jsonObject.getString("description"),
						_localization.getLocalizationMap(
							jsonObject.getString("name")),
						jsonObject.getString("properties"),
						jsonObject.getString("sourceCodeURL"),
						jsonObject.getString("typeSettings"));
				}
			}
		}
	}

	private void _sendResponse(
			String errorKey, Object[] errorArguments,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		if (errorKey == null) {
			jsonObject.put("error", false);
		}
		else {
			String message;

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			if (errorArguments == null) {
				message = _language.get(httpServletRequest, errorKey);
			}
			else {
				message = _language.format(
					httpServletRequest, errorKey, errorArguments);
			}

			jsonObject.put("error", message);
		}

		String json = jsonObject.toString();

		resourceResponse.setContentLength(json.length());

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);
		resourceResponse.setStatus(HttpServletResponse.SC_OK);

		PrintWriter printWriter = resourceResponse.getWriter();

		printWriter.write(json);
	}

	private void _validate(
			ClientExtensionEntry clientExtensionEntry, JSONObject jsonObject)
		throws JSONException {

		if (Validator.isNull(jsonObject.getString("name"))) {
			throw new JSONException("Missing name field");
		}

		String type = jsonObject.getString("type");

		if (Validator.isNull(type)) {
			throw new JSONException("Missing or empty type field");
		}

		if ((clientExtensionEntry != null) &&
			!Objects.equals(clientExtensionEntry.getType(), type)) {

			throw new JSONException("Update type mismatched: " + type);
		}

		if (Validator.isNull(jsonObject.getString("typeSettings"))) {
			throw new JSONException("Missing or empty typeSettings field");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportClientExtensionEntryMVCResourceCommand.class);

	@Reference
	private ClientExtensionEntryService _clientExtensionEntryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}