/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.audiences.web.internal.servlet;

import com.liferay.frontend.js.audiences.AudiencesDefinitionProvider;
import com.liferay.frontend.js.audiences.ElementVariationsProvider;
import com.liferay.frontend.js.audiences.web.internal.util.BootstrapJavaScriptUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.audiences.web.internal.servlet.FrontendJSAudiencesWebServlet",
		"osgi.http.whiteboard.servlet.pattern=/audiences/*"
	},
	service = Servlet.class
)
public class FrontendJSAudiencesWebServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String[] parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		if (parts.length == 0) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else if (parts[parts.length - 1].startsWith("bootstrap.")) {
			_sendBootstrapJavaScript(
				httpServletRequest, httpServletResponse, parts);
		}
		else if (parts[parts.length - 1].startsWith("definition.")) {
			_sendAudiencesDefinitionJSON(
				httpServletRequest, httpServletResponse, parts);
		}
		else if (parts[parts.length - 1].startsWith("variations.")) {
			_sendElementVariationsJavaScript(
				httpServletRequest, httpServletResponse, parts);
		}
		else {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private Long _parsePlid(String plid) {
		try {
			return Long.valueOf(plid);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				"Unable to parse plid from " + plid, numberFormatException);

			return null;
		}
	}

	private void _sendAudiencesDefinitionJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String[] parts)
		throws IOException {

		if (parts.length != 2) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		KeyValuePair keyValuePair =
			_audiencesDefinitionProvider.getAudiencesDefinition(
				_portal.getCompanyId(httpServletRequest));

		if (keyValuePair == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String requestHash = HashedFilesUtil.getHash(parts[1]);

		if (!Objects.equals(keyValuePair.getKey(), requestHash)) {
			_sendRedirect(
				httpServletRequest, httpServletResponse, keyValuePair.getKey());

			return;
		}

		httpServletResponse.setContentType("application/json");
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "immutable, max-age=31536000, public");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print(keyValuePair.getValue());
	}

	private void _sendBootstrapJavaScript(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String[] parts)
		throws IOException {

		if (parts.length != 2) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String hash = BootstrapJavaScriptUtil.getHash();

		String requestHash = HashedFilesUtil.getHash(parts[1]);

		if (!Objects.equals(hash, requestHash)) {
			_sendRedirect(httpServletRequest, httpServletResponse, hash);

			return;
		}

		httpServletResponse.setContentType("application/javascript");
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "immutable, max-age=31536000, public");

		PrintWriter printWriter = httpServletResponse.getWriter();

		Long plid = _parsePlid(httpServletRequest.getParameter("plid"));

		if (plid == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		printWriter.print(
			BootstrapJavaScriptUtil.getJavaScript(
				httpServletRequest.getParameter("audiencesDefinitionHash"),
				httpServletRequest.getParameter("elementVariationsHash"),
				Boolean.parseBoolean(
					httpServletRequest.getParameter("enableLog")),
				plid));
	}

	private void _sendElementVariationsJavaScript(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String[] parts)
		throws IOException {

		if (parts.length != 3) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		Long plid = _parsePlid(parts[1]);

		if (plid == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		KeyValuePair keyValuePair =
			_elementVariationsProvider.getElementVariations(plid);

		if (keyValuePair == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String requestHash = HashedFilesUtil.getHash(parts[2]);

		if (!Objects.equals(keyValuePair.getKey(), requestHash)) {
			_sendRedirect(
				httpServletRequest, httpServletResponse, keyValuePair.getKey());

			return;
		}

		httpServletResponse.setContentType("application/javascript");
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "immutable, max-age=31536000, public");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print(keyValuePair.getValue());
	}

	private void _sendRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String hash)
		throws IOException {

		StringBuilder url = new StringBuilder();

		url.append(
			HashedFilesUtil.replaceHash(
				httpServletRequest.getRequestURI(), hash));

		String queryString = httpServletRequest.getQueryString();

		if (!Validator.isBlank(queryString)) {
			url.append(StringPool.QUESTION);
			url.append(queryString);
		}

		httpServletResponse.sendRedirect(url.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJSAudiencesWebServlet.class);

	@Reference
	private AudiencesDefinitionProvider _audiencesDefinitionProvider;

	@Reference
	private ElementVariationsProvider _elementVariationsProvider;

	@Reference
	private Portal _portal;

}