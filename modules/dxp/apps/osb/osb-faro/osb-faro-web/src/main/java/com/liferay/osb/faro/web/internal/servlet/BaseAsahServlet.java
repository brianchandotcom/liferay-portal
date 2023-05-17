/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.servlet;

import com.liferay.osb.faro.engine.client.util.EngineServiceURLUtil;
import com.liferay.osb.faro.engine.client.util.TokenUtil;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.util.FaroProjectThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author Matthew Kong
 */
public abstract class BaseAsahServlet extends HttpServlet {

	protected URI buildURI(HttpServletRequest httpServletRequest, String path)
		throws URISyntaxException {

		URIBuilder uriBuilder = new URIBuilder(
			EngineServiceURLUtil.getBackendURL(
				FaroProjectThreadLocal.getFaroProject(), path));

		Map<String, String[]> requestParameterMap =
			httpServletRequest.getParameterMap();

		requestParameterMap.forEach(
			(key, valueArray) -> {
				for (String value : valueArray) {
					uriBuilder.addParameter(key, value);
				}
			});

		return uriBuilder.build();
	}

	protected String getProjectId() {
		FaroProject faroProject = FaroProjectThreadLocal.getFaroProject();

		return faroProject.getProjectId();
	}

	protected String getSecuritySignature(URI uri) {
		String url = uri.toString();

		return DigestUtils.sha256Hex(
			StringUtil.merge(
				Arrays.asList(
					TokenUtil.getOSBAsahSecurityToken(),
					url.substring(0, url.lastIndexOf(uri.getPath()))),
				StringPool.BLANK));
	}

	protected static final String ASAH_PROJECT_ID_HEADER =
		"OSB-Asah-Project-ID";

	protected static final String ASAH_SECURITY_SIGNATURE_HEADER =
		"OSB-Asah-Faro-Backend-Security-Signature";

	private static final long serialVersionUID = 1L;

}