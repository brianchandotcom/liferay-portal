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

package com.liferay.friendly.url.taglib.servlet.taglib;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.friendly.url.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Adolfo Pérez
 */
public class InputTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getInputAddon() {
		return _inputAddon;
	}

	public String getName() {
		return _name;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setInputAddon(String inputAddon) {
		_inputAddon = inputAddon;
	}

	public void setName(String name) {
		_name = name;
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
		_inputAddon = null;
		_name = _DEFAULT_NAME;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:className", getClassName());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:classPK", getClassPK());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:friendlyURLMaxLength",
			_FRIENDLY_URL_MAX_LENGTH);
		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:friendlyURLXML", _getFriendlyURLXML());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:inputAddon", getInputAddon());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:input:name", getName());
	}

	private String _getFriendlyURLXML() {
		try {
			FriendlyURLEntry mainFriendlyURLEntry =
				FriendlyURLEntryLocalServiceUtil.getMainFriendlyURLEntry(
					PortalUtil.getClassNameId(getClassName()), getClassPK());

			return mainFriendlyURLEntry.getUrlTitleMapAsXML();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final String _DEFAULT_NAME = "friendlyURL";

	private static final int _FRIENDLY_URL_MAX_LENGTH = 255;

	private static final String _PAGE = "/input/page.jsp";

	private String _className;
	private long _classPK;
	private String _inputAddon;
	private String _name = _DEFAULT_NAME;

}