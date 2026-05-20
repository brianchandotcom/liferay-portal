/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.api;

import com.liferay.osb.faro.engine.client.constants.OSBAsahHeaderConstants;
import com.liferay.osb.faro.engine.client.util.EngineServiceURLUtil;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.context.GroupInfo;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Nilton Vieira
 */
public abstract class BaseWebhookFaroController extends BaseFaroController {

	protected Response post(
			GroupInfo groupInfo, String path, String requestBody)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(
				groupInfo.getGroupId());

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			URIBuilder uriBuilder = new URIBuilder(
				EngineServiceURLUtil.getPublisherURL(faroProject, path));

			URI uri = uriBuilder.build();

			HttpPost httpPost = new HttpPost(uri);

			httpPost.setEntity(
				new ByteArrayEntity(
					requestBody.getBytes(StandardCharsets.UTF_8)));
			httpPost.setHeader(
				OSBAsahHeaderConstants.PROJECT_ID, faroProject.getProjectId());
			httpPost.setHeader("content-type", "application/json");

			closeableHttpClient.execute(httpPost);

			return Response.status(
				Response.Status.OK
			).build();
		}
		catch (URISyntaxException uriSyntaxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(uriSyntaxException);
			}

			throw new WebApplicationException(
				Response.status(
					Response.Status.BAD_REQUEST
				).build());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWebhookFaroController.class);

}