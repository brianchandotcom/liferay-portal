/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.audiences.web.internal.servlet;

import com.liferay.frontend.js.audiences.AudiencesDefinitionProvider;
import com.liferay.frontend.js.audiences.ElementVariationsProvider;
import com.liferay.frontend.js.audiences.HashedContent;
import com.liferay.frontend.js.audiences.web.internal.util.BootstrapJavaScriptUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
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

		String contentType = null;
		HashedContent hashedContent = null;

		String[] parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		if ((parts.length == 2) && parts[1].startsWith("bootstrap.")) {
			Long plid = _getPlid(httpServletRequest.getParameter("plid"));

			if (plid != null) {
				contentType = ContentTypes.APPLICATION_JAVASCRIPT;
				hashedContent = BootstrapJavaScriptUtil.getHashedContent(
					httpServletRequest.getParameter("audiencesDefinitionHash"),
					httpServletRequest.getParameter("elementVariationsHash"),
					Boolean.parseBoolean(
						httpServletRequest.getParameter("enableLog")),
					plid);
			}
		}
		else if ((parts.length == 2) && parts[1].startsWith("definition.")) {
			contentType = ContentTypes.APPLICATION_JSON;
			hashedContent = _audiencesDefinitionProvider.getHashedContent(
				_portal.getCompanyId(httpServletRequest));
		}
		else if ((parts.length == 3) && parts[2].startsWith("variations.")) {
			Long plid = _getPlid(parts[1]);

			if (plid != null) {
				contentType = ContentTypes.APPLICATION_JAVASCRIPT;
				hashedContent = _elementVariationsProvider.getHashedContent(
					plid);
			}
		}

		if (hashedContent == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String hash = hashedContent.getHash();

		String requestHash = HashedFilesUtil.getHash(
			httpServletRequest.getPathInfo());

		if (!Objects.equals(hash, requestHash)) {
			StringBundler sb = new StringBundler(3);

			sb.append(
				HashedFilesUtil.replaceHash(
					httpServletRequest.getRequestURI(), hash));

			String queryString = httpServletRequest.getQueryString();

			if (!Validator.isBlank(queryString)) {
				sb.append(StringPool.QUESTION);
				sb.append(queryString);
			}

			httpServletResponse.sendRedirect(sb.toString());

			return;
		}

		httpServletResponse.setContentType(contentType);
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "immutable, max-age=31536000, public");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print(hashedContent.getContent());
	}

	private Long _getPlid(String plid) {
		try {
			return Long.valueOf(plid);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				"Unable to get plid from " + plid, numberFormatException);

			return null;
		}
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