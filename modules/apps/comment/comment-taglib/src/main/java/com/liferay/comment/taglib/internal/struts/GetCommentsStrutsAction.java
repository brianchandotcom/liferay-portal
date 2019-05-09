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
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "path=/portal/comment/discussion/get_comments",
	service = StrutsAction.class
)
public class GetCommentsStrutsAction implements StrutsAction {

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

		String className = ParamUtil.getString(
			namespacedHttpServletRequest, "className");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:className", className);

		long classPK = ParamUtil.getLong(
			namespacedHttpServletRequest, "classPK");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:classPK", String.valueOf(classPK));

		boolean hideControls = ParamUtil.getBoolean(
			namespacedHttpServletRequest, "hideControls");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:hideControls",
			String.valueOf(hideControls));

		int index = ParamUtil.getInteger(namespacedHttpServletRequest, "index");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:index", String.valueOf(index));

		String portletId = ParamUtil.getString(
			namespacedHttpServletRequest, "portletId");

		namespacedHttpServletRequest.setAttribute(
			WebKeys.PORTLET_ID, portletId);

		String randomNamespace = ParamUtil.getString(
			namespacedHttpServletRequest, "randomNamespace");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:randomNamespace", randomNamespace);

		boolean ratingsEnabled = ParamUtil.getBoolean(
			namespacedHttpServletRequest, "ratingsEnabled");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:ratingsEnabled",
			String.valueOf(ratingsEnabled));

		int rootIndexPage = ParamUtil.getInteger(
			namespacedHttpServletRequest, "rootIndexPage");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:rootIndexPage",
			String.valueOf(rootIndexPage));

		long userId = ParamUtil.getLong(namespacedHttpServletRequest, "userId");

		namespacedHttpServletRequest.setAttribute(
			"liferay-comment:discussion:userId", String.valueOf(userId));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/discussion/page_resources.jsp");

		requestDispatcher.include(
			namespacedHttpServletRequest, httpServletResponse);

		return null;
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.comment.taglib)")
	private ServletContext _servletContext;

}