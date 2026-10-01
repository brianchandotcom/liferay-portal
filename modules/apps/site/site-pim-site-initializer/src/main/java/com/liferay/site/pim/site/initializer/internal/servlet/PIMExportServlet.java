/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.servlet;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.pim.site.initializer.connector.PIMConnector;
import com.liferay.site.pim.site.initializer.connector.PIMConnectorRegistry;
import com.liferay.site.pim.site.initializer.exception.PIMConnectorException;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Stefano Motta
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.site.pim.site.initializer.internal.servlet.PIMExportServlet",
		"osgi.http.whiteboard.servlet.pattern=/pim/export/*",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class PIMExportServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(
				httpServletRequest.getSession());
		}

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = _portal.getUser(httpServletRequest);

			if ((user == null) || user.isGuestUser()) {
				_sendError(
					_language.get(
						httpServletRequest,
						"you-do-not-have-permission-to-access-the-requested-" +
							"resource"),
					httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED);

				return;
			}

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
			PrincipalThreadLocal.setName(user.getUserId());

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, "pim-products.json",
				_export(httpServletRequest), ContentTypes.APPLICATION_JSON,
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		catch (PIMConnectorException pimConnectorException) {
			_sendError(
				_language.get(
					httpServletRequest, pimConnectorException.getMessage()),
				httpServletResponse, HttpServletResponse.SC_BAD_REQUEST);
		}
		catch (Exception exception) {
			_log.error(exception);

			_sendError(
				_language.get(
					httpServletRequest, "an-unexpected-error-occurred"),
				httpServletResponse,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private byte[] _export(HttpServletRequest httpServletRequest)
		throws IOException, PortalException {

		ObjectEntry objectEntry = _objectEntryService.getObjectEntry(
			ParamUtil.getLong(httpServletRequest, "objectEntryId"));

		PIMConnector pimConnector = _pimConnectorRegistry.getPIMConnector(
			MapUtil.getString(objectEntry.getValues(), "key"));

		if (pimConnector == null) {
			throw new PIMConnectorException(
				"unable-to-get-a-pim-connector-with-the-given-key");
		}

		String products = pimConnector.export(objectEntry);

		return products.getBytes(StringPool.UTF8);
	}

	private void _sendError(
			String errorMessage, HttpServletResponse httpServletResponse,
			int status)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(status);

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.put(
				"error", errorMessage
			).toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PIMExportServlet.class);

	@Reference
	private Language _language;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private PIMConnectorRegistry _pimConnectorRegistry;

	@Reference
	private Portal _portal;

}