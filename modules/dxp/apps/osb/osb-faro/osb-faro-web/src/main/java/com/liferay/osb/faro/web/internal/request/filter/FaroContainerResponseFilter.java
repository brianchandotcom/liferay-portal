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

package com.liferay.osb.faro.web.internal.request.filter;

import com.liferay.osb.faro.util.FaroRequestAudit;
import com.liferay.osb.faro.util.FaroThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URI;

import java.util.List;
import java.util.Map;

import javax.annotation.Priority;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Shinn Lok
 */
@Priority(1)
public class FaroContainerResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(
		ContainerRequestContext containerRequestContext,
		ContainerResponseContext containerResponseContext) {

		FaroRequestAudit faroRequestAudit =
			FaroThreadLocal.getFaroRequestAudit();

		if ((faroRequestAudit == null) || !faroRequestAudit.isEnabled()) {
			return;
		}

		faroRequestAudit.setEndTime(System.currentTimeMillis());
		faroRequestAudit.setMethod(containerRequestContext.getMethod());
		faroRequestAudit.setStatusCode(containerResponseContext.getStatus());
		faroRequestAudit.setURLPath(
			getURLPath(containerRequestContext.getUriInfo()));

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.quote(
					faroRequestAudit.toString(), System.lineSeparator()));
		}
	}

	protected String getURLPath(UriInfo uriInfo) {
		StringBundler sb = new StringBundler();

		URI uri = uriInfo.getBaseUri();

		sb.append(uri.getPath());

		sb.append(StringPool.FORWARD_SLASH);
		sb.append(uriInfo.getPath());

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		if (queryParameters.isEmpty()) {
			return sb.toString();
		}

		sb.append(StringPool.QUESTION);

		for (Map.Entry<String, List<String>> entry :
				queryParameters.entrySet()) {

			for (String value : entry.getValue()) {
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(value);
				sb.append(StringPool.AMPERSAND);
			}
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroContainerResponseFilter.class);

}