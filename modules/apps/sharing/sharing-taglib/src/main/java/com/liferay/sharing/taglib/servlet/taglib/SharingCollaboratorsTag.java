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

package com.liferay.sharing.taglib.servlet.taglib;

import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.taglib.internal.servlet.ServletContextUtil;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Alejandro Tardín
 */
public class SharingCollaboratorsTag extends BaseSharingTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		ResourceURL collaboratorsResourceURL = PortletURLFactoryUtil.create(
			request, SharingPortletKeys.SHARING, PortletRequest.RESOURCE_PHASE);

		collaboratorsResourceURL.setParameter("className", getClassName());
		collaboratorsResourceURL.setParameter(
			"classPK", String.valueOf(getClassPK()));

		collaboratorsResourceURL.setResourceID("/sharing/collaborators");

		httpServletRequest.setAttribute(
			"liferay-sharing:collaborators:classNameId",
			PortalUtil.getClassNameId(getClassName()));

		httpServletRequest.setAttribute(
			"liferay-sharing:collaborators:classPK", getClassPK());

		httpServletRequest.setAttribute(
			"liferay-sharing:collaborators:collaboratorsResourceURL",
			collaboratorsResourceURL.toString());
	}

	private static final String _PAGE = "/collaborators/page.jsp";

	private String _className;
	private long _classPK;

}