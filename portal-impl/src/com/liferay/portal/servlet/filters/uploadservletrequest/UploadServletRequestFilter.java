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

package com.liferay.portal.servlet.filters.uploadservletrequest;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletInstanceFactoryUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Preston Crary
 */
public class UploadServletRequestFilter extends BasePortalFilter {

	public static final String COPY_MULTIPART_STREAM_TO_FILE =
		UploadServletRequestFilter.class.getName() +
			"#COPY_MULTIPART_STREAM_TO_FILE";

	@Override
	public void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		UploadServletRequest uploadServletRequest = null;

		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

			String portletId = ParamUtil.getString(request, "p_p_id");

			if (Validator.isNotNull(portletId)) {
				long companyId = PortalUtil.getCompanyId(request);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletId);

				if (portlet != null) {
					ServletContext servletContext =
						(ServletContext)request.getAttribute(WebKeys.CTX);

					InvokerPortlet invokerPortlet =
						PortletInstanceFactoryUtil.create(
							portlet, servletContext);

					LiferayPortletConfig liferayPortletConfig =
						(LiferayPortletConfig)invokerPortlet.getPortletConfig();

					if (invokerPortlet.isStrutsPortlet() ||
						liferayPortletConfig.isCopyRequestParameters() ||
						!liferayPortletConfig.isWARFile()) {

						request.setAttribute(
							UploadServletRequestFilter.
								COPY_MULTIPART_STREAM_TO_FILE,
							Boolean.FALSE);
					}
				}
			}

			uploadServletRequest = PortalUtil.getUploadServletRequest(request);
		}

		if (uploadServletRequest == null) {
			processFilter(
				UploadServletRequestFilter.class.getName(), request, response,
				filterChain);
		}
		else {
			try {
				processFilter(
					UploadServletRequestFilter.class.getName(),
					uploadServletRequest, response, filterChain);
			}
			finally {
				uploadServletRequest.cleanUp();
			}
		}
	}

}