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

package com.liferay.portal.comment.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.NamespaceServletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author     Adolfo Pérez
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.comment.taglib.internal.action.
 *             GetCommentsStrutsAction}
 */
@Deprecated
@OSGiBeanProperties(
	property = "path=/portal/comment/get_comments", service = StrutsAction.class
)
public class GetCommentsStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String namespace = ParamUtil.getString(request, "namespace");

		HttpServletRequest namespacedRequest = new NamespaceServletRequest(
			request, StringPool.BLANK, namespace);

		namespacedRequest.setAttribute("aui:form:portletNamespace", namespace);

		String className = ParamUtil.getString(namespacedRequest, "className");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:className", className);

		long classPK = ParamUtil.getLong(namespacedRequest, "classPK");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:classPK", String.valueOf(classPK));

		boolean hideControls = ParamUtil.getBoolean(
			namespacedRequest, "hideControls");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:hideControls", String.valueOf(hideControls));

		int index = ParamUtil.getInteger(namespacedRequest, "index");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:index", String.valueOf(index));

		String portletId = ParamUtil.getString(namespacedRequest, "portletId");

		namespacedRequest.setAttribute(WebKeys.PORTLET_ID, portletId);

		String randomNamespace = ParamUtil.getString(
			namespacedRequest, "randomNamespace");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:randomNamespace", randomNamespace);

		boolean ratingsEnabled = ParamUtil.getBoolean(
			namespacedRequest, "ratingsEnabled");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:ratingsEnabled",
			String.valueOf(ratingsEnabled));

		int rootIndexPage = ParamUtil.getInteger(
			namespacedRequest, "rootIndexPage");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:rootIndexPage",
			String.valueOf(rootIndexPage));

		long userId = ParamUtil.getLong(namespacedRequest, "userId");

		namespacedRequest.setAttribute(
			"liferay-ui:discussion:userId", String.valueOf(userId));

		RequestDispatcher requestDispatcher =
			namespacedRequest.getRequestDispatcher(
				"/html/taglib/ui/discussion/page_resources.jsp");

		requestDispatcher.include(namespacedRequest, response);

		return null;
	}

}