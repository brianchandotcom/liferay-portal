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

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.sharepoint.methods.Method;
import com.liferay.portal.sharepoint.methods.MethodFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Bruno Farache
 */
public class SharepointServlet extends HttpServlet {

	@Override
	public void doGet(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					httpServletRequest.getHeader(HttpHeaders.USER_AGENT), " ",
					httpServletRequest.getMethod(), " ",
					httpServletRequest.getRequestURI()));
		}

		try {
			String uri = httpServletRequest.getRequestURI();

			if (uri.equals("/_vti_inf.html")) {
				vtiInfHtml(httpServletResponse);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void doPost(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			String uri = httpServletRequest.getRequestURI();

			if (uri.equals("/_vti_bin/shtml.dll/_vti_rpc") ||
				uri.equals("/sharepoint/_vti_bin/_vti_aut/author.dll")) {

				HttpSession session = httpServletRequest.getSession();

				User user = (User)session.getAttribute(WebKeys.USER);

				SharepointRequest sharepointRequest = new SharepointRequest(
					httpServletRequest, httpServletResponse, user);

				Method method = MethodFactory.create(sharepointRequest);

				String rootPath = method.getRootPath(sharepointRequest);

				if (rootPath == null) {
					throw new SharepointException("Unabled to get root path");
				}

				// LPS-12922

				if (_log.isInfoEnabled()) {
					_log.info("Original root path " + rootPath);
				}

				rootPath = WebDAVUtil.stripManualCheckInRequiredPath(rootPath);
				rootPath = WebDAVUtil.stripOfficeExtension(rootPath);
				rootPath = SharepointUtil.stripService(rootPath, true);

				if (_log.isInfoEnabled()) {
					_log.info("Modified root path " + rootPath);
				}

				sharepointRequest.setRootPath(rootPath);

				sharepointRequest.setSharepointStorage(
					SharepointUtil.getStorage(rootPath));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							httpServletRequest.getHeader(
								HttpHeaders.USER_AGENT),
							" ", method.getMethodName(), " ", uri, " ",
							rootPath));
				}

				method.process(sharepointRequest);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							httpServletRequest.getHeader(
								HttpHeaders.USER_AGENT),
							" ", httpServletRequest.getMethod(), " ", uri));
				}
			}
		}
		catch (SharepointException se) {
			_log.error(se, se);
		}
	}

	protected void vtiInfHtml(HttpServletResponse httpServletResponse)
		throws Exception {

		StringBundler sb = new StringBundler(13);

		sb.append("<!-- FrontPage Configuration Information");
		sb.append(StringPool.NEW_LINE);
		sb.append(" FPVersion=\"6.0.2.9999\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPShtmlScriptUrl=\"_vti_bin/shtml.dll/_vti_rpc\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPAuthorScriptUrl=\"_vti_bin/_vti_aut/author.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPAdminScriptUrl=\"_vti_bin/_vti_adm/admin.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("TPScriptUrl=\"_vti_bin/owssvr.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("-->");

		ServletResponseUtil.write(httpServletResponse, sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointServlet.class);

}