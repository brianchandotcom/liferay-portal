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

import java.util.Optional;

import javax.portlet.RenderRequest;

/**
 * @author André de Oliveira
 */
public interface PortletSharedRequestHelper {

	public <T> Optional<T> getAttribute(
		String name, RenderRequest renderRequest);

	public String getCompleteURL(RenderRequest renderRequest);

	public Optional<String> getParameter(
		String name, RenderRequest renderRequest);

	public Optional<String[]> getParameterValues(
		String name, RenderRequest renderRequest);

	public void setAttribute(
		String name, Object attributeValue, RenderRequest renderRequest);

}