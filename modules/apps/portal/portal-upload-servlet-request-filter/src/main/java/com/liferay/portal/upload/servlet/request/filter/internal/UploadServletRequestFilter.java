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

package com.liferay.portal.upload.servlet.request.filter.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.upload.LiferayInputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Upload Servlet Request Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class UploadServletRequestFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String contentType = httpServletRequest.getHeader(
			HttpHeaders.CONTENT_TYPE);

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

			return true;
		}

		return false;
	}

	@Override
	public void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		int fileSizeThreshold = 0;
		String location = null;
		long maxRequestSize = 0;
		long maxFileSize = 0;

		if (Validator.isNotNull(portletId)) {
			Portlet portlet = _portletLocalService.getPortletById(
				_portal.getCompanyId(httpServletRequest), portletId);

			if (portlet != null) {
				FilterConfig filterConfig = getFilterConfig();

				InvokerPortlet invokerPortlet =
					PortletInstanceFactoryUtil.create(
						portlet, filterConfig.getServletContext());

				LiferayPortletConfig liferayPortletConfig =
					(LiferayPortletConfig)invokerPortlet.getPortletConfig();

				if (liferayPortletConfig.isCopyRequestParameters() ||
					!liferayPortletConfig.isWARFile()) {

					httpServletRequest.setAttribute(
						LiferayInputStream.COPY_MULTIPART_STREAM_TO_FILE,
						Boolean.FALSE);
				}

				fileSizeThreshold = portlet.getMultipartFileSizeThreshold();
				location = portlet.getMultipartLocation();
				maxRequestSize = portlet.getMultipartMaxRequestSize();
				maxFileSize = portlet.getMultipartMaxFileSize();
			}
		}

		UploadServletRequest uploadServletRequest =
			_portal.getUploadServletRequest(
				httpServletRequest, fileSizeThreshold, location, maxRequestSize,
				maxFileSize);

		try {
			processFilter(
				UploadServletRequestFilter.class.getName(),
				uploadServletRequest, httpServletResponse, filterChain);
		}
		finally {
			uploadServletRequest.cleanUp();
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}