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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.taglib.internal.servlet.ServletContextUtil;
import com.liferay.sharing.taglib.internal.servlet.SharingJavaScriptFactoryUtil;
import com.liferay.sharing.taglib.internal.servlet.SharingPermissionUtil;
import com.liferay.sharing.taglib.internal.util.CollaboratorsUtil;

import java.util.HashMap;
import java.util.Map;

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

		setServletContext(ServletContextUtil.getServletContext());
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
		Map<String, Object> data = new HashMap<>();

		long classNameId = PortalUtil.getClassNameId(getClassName());

		HttpServletRequest parentHttpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)parentHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean canManageCollaborators = _canManageCollaborators(
			classNameId, getClassPK(), themeDisplay);

		if (canManageCollaborators) {
			SharingJavaScriptFactory sharingJavaScriptFactory =
				SharingJavaScriptFactoryUtil.getSharingJavaScriptFactory();

			sharingJavaScriptFactory.requestSharingJavascript();

			data.put("canManageCollaborators", true);
		}

		data.put("classNameId", classNameId);
		data.put("classPK", getClassPK());

		ResourceURL collaboratorsResourceURL = PortletURLFactoryUtil.create(
			parentHttpServletRequest, SharingPortletKeys.SHARING,
			PortletRequest.RESOURCE_PHASE);

		collaboratorsResourceURL.setParameter("className", getClassName());
		collaboratorsResourceURL.setParameter(
			"classPK", String.valueOf(getClassPK()));

		collaboratorsResourceURL.setResourceID("/sharing/collaborators");

		data.put(
			"collaboratorsResourceURL", collaboratorsResourceURL.toString());

		data.put(
			"initialData",
			CollaboratorsUtil.getCollaboratorsJSONObject(
				classNameId, getClassPK(), themeDisplay));

		httpServletRequest.setAttribute(
			"liferay-sharing:collaborators:data", data);
	}

	private boolean _canManageCollaborators(
		long classNameId, long classPK, ThemeDisplay themeDisplay) {

		SharingPermission sharingPermission =
			SharingPermissionUtil.getSharingPermission();

		try {
			return sharingPermission.containsManageCollaboratorsPermission(
				themeDisplay.getPermissionChecker(), classNameId, classPK,
				themeDisplay.getScopeGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private static final String _PAGE = "/collaborators/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SharingCollaboratorsTag.class);

	private String _className;
	private long _classPK;

}