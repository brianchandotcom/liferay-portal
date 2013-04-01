/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class ETagFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (ParamUtil.getBoolean(request, _ETAG, true)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isEligibleForEtag(int responseStatusCode) {
		if ((responseStatusCode >= 200) && (responseStatusCode < 300)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		final int[] statusCode = {HttpServletResponse.SC_OK};

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response) {

				@Override
				public void sendError(int status) throws IOException {
					super.sendError(status);
					statusCode[0] = status;
				}

				@Override
				public void sendError(int status, String errorMessage)
					throws IOException {
					super.sendError(status, errorMessage);
					statusCode[0] = status;
				}

				@Override
				public void setStatus(int status) {
					super.setStatus(status);
					statusCode[0] = status;
				}

				@Override
				public void setStatus(int status, String statusMessage) {
					super.setStatus(status, statusMessage);
					statusCode[0] = status;
				}
			};

		processFilter(
			ETagFilter.class, request, bufferCacheServletResponse, filterChain);

		ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

		if (isEligibleForEtag(statusCode[0])) {
			if (!ETagUtil.processETag(request, response, byteBuffer)) {
				bufferCacheServletResponse.finishResponse();
				bufferCacheServletResponse.outputBuffer();
			}
		}
		else {
			bufferCacheServletResponse.finishResponse();
		}
	}

	private static final String _ETAG = "etag";

}