/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jaxrs.context;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public interface ContextDataInjectorBuilder {

	public ContextDataInjectorBuilder acceptLanguage(
		AcceptLanguage acceptLanguage);

	public ContextDataInjector build();

	public ContextDataInjectorBuilder company(Company company);

	public ContextDataInjectorBuilder httpServletRequest(
		HttpServletRequest httpServletRequest);

	public ContextDataInjectorBuilder httpServletResponse(
		HttpServletResponse httpServletResponse);

	public ContextDataInjectorBuilder uriInfo(UriInfo uriInfo);

	public ContextDataInjectorBuilder user(User user);

}