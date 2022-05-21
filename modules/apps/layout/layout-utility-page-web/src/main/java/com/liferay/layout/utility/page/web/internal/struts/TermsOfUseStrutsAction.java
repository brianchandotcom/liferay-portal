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

package com.liferay.layout.utility.page.web.internal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "path=/portal/terms_of_use",
	service = StrutsAction.class
)
public class TermsOfUseStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/terms_of_use/terms_of_use.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);

		return null;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.utility.page.web)"
	)
	private ServletContext _servletContext;

}