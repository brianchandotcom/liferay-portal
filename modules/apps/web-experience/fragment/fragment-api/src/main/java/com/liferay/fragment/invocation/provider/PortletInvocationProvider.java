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

package com.liferay.fragment.invocation.provider;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A service which provides a capability to configure, validate and render
 * a portlet from a fragment entry HTML using custom tags.
 *
 * @author Pavel Savinov
 */
public interface PortletInvocationProvider {

	/**
	 * Portlet invocation tag prefix to be used it fragment HMTL content.
	 */
	public static final String INVOCATION_TAG_PREFIX = "lfr-portlet-";

	/**
	 * Returns target portlet configuration URL.
	 *
	 * @return Configuration portlet URL.
	 * @throws PortalException
	 */
	public PortletURL getConfigurationPortletURL() throws PortalException;

	/**
	 * Returns portlet invocation alias to be used in fragment HTML content,
	 * for example, with alias "navigation":
	 *
	 * <code>&lt;lfr-portlet-navigation /&gt;</code>
	 *
	 * @return Fragment portlet invocation alias.
	 */
	public String getFragmentInvocationAlias();

	/**
	 * Returns required attributes array.
	 *
	 * @return Required attributes array.
	 */
	public String[] getRequiredAttributes();

	/**
	 * Renders target portlet in the fragment HTML using specific context and
	 * returs the render result as string.
	 *
	 * @param request the servlet request to render target portlet with.
	 * @param response the servlet response to render target portlet with.
	 * @param contextJSONObject the context to render target portlet with.
	 * @return Render result.
	 * @throws FragmentEntryContentException
	 */
	public String render(
			HttpServletRequest request, HttpServletResponse response,
			JSONObject contextJSONObject)
		throws FragmentEntryContentException;

}