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

package com.liferay.portal.search.web.internal.portlet.shared.task;

import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.web.internal.util.SearchArrayUtil;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;

import java.util.Optional;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = PortletSharedRequestHelper.class)
public class PortletSharedRequestHelperImpl
	implements PortletSharedRequestHelper {

	@Override
	public <T> Optional<T> getAttribute(
		String name, RenderRequest renderRequest) {

		return Optional.ofNullable(
			getAttribute(name, getSharedRequest(renderRequest)));
	}

	@Override
	public String getCompleteURL(RenderRequest renderRequest) {
		return SearchHttpUtil.getCompleteOriginalURL(
			portal.getHttpServletRequest(renderRequest));
	}

	@Override
	public Optional<String> getParameter(
		String name, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		return SearchStringUtil.maybe(httpServletRequest.getParameter(name));
	}

	@Override
	public Optional<String[]> getParameterValues(
		String name, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		return SearchArrayUtil.maybe(
			httpServletRequest.getParameterValues(name));
	}

	@Override
	public void setAttribute(
		String name, Object attributeValue, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		httpServletRequest.setAttribute(name, attributeValue);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(
		String name, HttpServletRequest httpServletRequest) {

		return (T)httpServletRequest.getAttribute(name);
	}

	protected HttpServletRequest getSharedRequest(RenderRequest renderRequest) {
		return portal.getOriginalServletRequest(
			portal.getHttpServletRequest(renderRequest));
	}

	@Reference
	protected Portal portal;

}