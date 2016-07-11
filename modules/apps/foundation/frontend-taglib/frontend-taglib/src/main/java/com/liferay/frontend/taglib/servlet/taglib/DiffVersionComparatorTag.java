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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class DiffVersionComparatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDiffHtmlResults(String diffHtmlResults) {
		_diffHtmlResults = diffHtmlResults;
	}

	public void setDiffVersionsInfo(DiffVersionsInfo diffVersionsInfo) {
		_diffVersionsInfo = diffVersionsInfo;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setResourceURL(PortletURL resourceURL) {
		_resourceURL = resourceURL;
	}

	public void setSourceVersion(double sourceVersion) {
		_sourceVersion = sourceVersion;
	}

	public void setTargetVersion(double targetVersion) {
		_targetVersion = targetVersion;
	}

	@Override
	protected void cleanUp() {
		_availableLocales = null;
		_diffHtmlResults = null;
		_diffVersionsInfo = null;
		_languageId = null;
		_portletURL = null;
		_resourceURL = null;
		_sourceVersion = 0;
		_targetVersion = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:availableLocales",
			_availableLocales);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:diffHtmlResults",
			_diffHtmlResults);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:diffVersionsInfo",
			_diffVersionsInfo);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:languageId", _languageId);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:portletURL", _portletURL);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:resourceURL",
			_resourceURL);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:sourceVersion",
			_sourceVersion);
		request.setAttribute(
			"liferay-frontend:diff-version-comparator:targetVersion",
			_targetVersion);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/diff_version_comparator/page.jsp";

	private Set<Locale> _availableLocales;
	private String _diffHtmlResults;
	private DiffVersionsInfo _diffVersionsInfo;
	private String _languageId;
	private PortletURL _portletURL;
	private PortletURL _resourceURL;
	private double _sourceVersion;
	private double _targetVersion;

}