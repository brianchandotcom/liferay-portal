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
 * @author Pavel Savinov
 */
public interface PortletInvocationProvider {

	public static final String INVOCATION_TAG_PREFIX = "lfr-portlet-";

	public PortletURL getConfigurationPortletURL() throws PortalException;

	public String getFragmentInvocationAlias();

	public String[] getRequiredAttributes();

	public String render(
			HttpServletRequest request, HttpServletResponse response,
			JSONObject contextJSONObject)
		throws FragmentEntryContentException;

}