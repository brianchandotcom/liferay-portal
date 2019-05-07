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

package com.liferay.frontend.compatibility.ie.servlet.filter;

import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"servlet-context-name=",
		"servlet-filter-name=IE MimeType Compatibility Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class IEMimeTypeCompatibilityFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _browserSniffer.isIe(httpServletRequest);
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		processFilter(
			IEMimeTypeCompatibilityFilter.class.getName(), httpServletRequest,
			new IEMimeTypeCompatibilityResponseWrapper(httpServletResponse),
			filterChain);
	}

	@Reference
	private BrowserSniffer _browserSniffer;

	private static class IEMimeTypeCompatibilityResponseWrapper
		extends HttpServletResponseWrapper {

		@Override
		public void setContentType(String contentType) {
			if (contentType.equals(ContentTypes.IMAGE_X_MS_BMP)) {
				contentType = ContentTypes.IMAGE_BMP;
			}

			super.setContentType(contentType);
		}

		private IEMimeTypeCompatibilityResponseWrapper(
			HttpServletResponse httpServletResponse) {

			super(httpServletResponse);
		}

	}

}