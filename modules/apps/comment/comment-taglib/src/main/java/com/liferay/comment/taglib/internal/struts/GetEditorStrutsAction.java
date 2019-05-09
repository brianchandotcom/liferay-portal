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

package com.liferay.comment.taglib.internal.struts;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.NamespaceServletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "path=/portal/comment/discussion/get_editor",
	service = StrutsAction.class
)
public class GetEditorStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String namespace = ParamUtil.getString(httpServletRequest, "namespace");

		HttpServletRequest namespacedHttpServletRequest =
			new NamespaceServletRequest(
				httpServletRequest, StringPool.BLANK, namespace);

		namespacedHttpServletRequest.setAttribute(
			"aui:form:portletNamespace", namespace);

		String contents = ParamUtil.getString(
			namespacedHttpServletRequest, "contents");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:editor:contents", contents);

		String name = ParamUtil.getString(namespacedHttpServletRequest, "name");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:editor:name", name);

		String onChangeMethod = ParamUtil.getString(
			namespacedHttpServletRequest, "onChangeMethod");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:editor:onChangeMethod", onChangeMethod);

		String placeholder = ParamUtil.getString(
			namespacedHttpServletRequest, "placeholder");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:editor:placeholder", placeholder);

		String portletId = ParamUtil.getString(
			namespacedHttpServletRequest, "portletId");

		namespacedHttpServletRequest.setAttribute(
			WebKeys.PORTLET_ID, portletId);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/discussion/editor_resource.jsp");

		requestDispatcher.include(
			namespacedHttpServletRequest, httpServletResponse);

		return null;
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.comment.taglib)")
	private ServletContext _servletContext;

}