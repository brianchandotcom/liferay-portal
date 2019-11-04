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
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class DiffVersionComparatorTag extends IncludeTag {

	public JSONObject createDiffVersionJSONObject(
			DiffVersion diffVersion, PortletURL sourceURL, PortletURL targetURL)
		throws PortalException {

		Date modifiedDate = diffVersion.getModifiedDate();

		String timeDescription = LanguageUtil.getTimeDescription(
			request, System.currentTimeMillis() - modifiedDate.getTime(), true);

		JSONObject diffVersionJSONObject = JSONUtil.put(
			"displayDate",
			LanguageUtil.format(request, "x-ago", timeDescription, false)
		).put(
			"inRange",
			(diffVersion.getVersion() > _sourceVersion) &&
			(diffVersion.getVersion() <= _targetVersion)
		);

		String diffVersionString = String.valueOf(diffVersion.getVersion());

		diffVersionJSONObject.put(
			"label",
			LanguageUtil.format(request, "version-x", diffVersionString));

		sourceURL.setParameter("sourceVersion", diffVersionString);

		diffVersionJSONObject.put("sourceURL", sourceURL.toString());

		if (Validator.isNotNull(_languageId)) {
			targetURL.setParameter("languageId", _languageId);
		}

		targetURL.setParameter("targetVersion", diffVersionString);

		diffVersionJSONObject.put("targetURL", targetURL.toString());

		User user = UserLocalServiceUtil.getUser(diffVersion.getUserId());

		diffVersionJSONObject.put(
			"userInitials", user.getInitials()
		).put(
			"userName", user.getFullName()
		).put(
			"version", diffVersionString
		);

		return diffVersionJSONObject;
	}

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public String getDiffHtmlResults() {
		return _diffHtmlResults;
	}

	public DiffVersionsInfo getDiffVersionsInfo() {
		return _diffVersionsInfo;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public PortletURL getResourceURL() {
		return _resourceURL;
	}

	public double getSourceVersion() {
		return _sourceVersion;
	}

	public double getTargetVersion() {
		return _targetVersion;
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
		super.cleanUp();

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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(_languageId)) {
			_resourceURL.setParameter("languageId", _languageId);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Object> data = new HashMap<>();

		try {
			if (_availableLocales != null) {
				JSONArray availableLocalesJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (Locale availableLocale : _availableLocales) {
					JSONObject availableLocaleJSONObject = JSONUtil.put(
						"displayName",
						availableLocale.getDisplayName(themeDisplay.getLocale())
					).put(
						"languageId", LocaleUtil.toLanguageId(availableLocale)
					);

					availableLocalesJSONArray.put(availableLocaleJSONObject);
				}

				data.put("availableLocales", availableLocalesJSONArray);
			}

			data.put("diffHtmlResults", _diffHtmlResults);

			RenderResponse renderResponse =
				(RenderResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			PortletURL sourceURL = PortletURLUtil.clone(
				_portletURL, renderResponse);

			sourceURL.setParameter(
				"targetVersion", String.valueOf(_targetVersion));

			PortletURL targetURL = PortletURLUtil.clone(
				_portletURL, renderResponse);

			targetURL.setParameter(
				"sourceVersion", String.valueOf(_sourceVersion));

			JSONArray diffVersionsJSONArray = JSONFactoryUtil.createJSONArray();

			int diffVersionsCount = 0;

			for (DiffVersion diffVersion :
					_diffVersionsInfo.getDiffVersions()) {

				JSONObject diffVersionJSONObject = createDiffVersionJSONObject(
					diffVersion, sourceURL, targetURL);

				if (diffVersionJSONObject.getBoolean("inRange")) {
					diffVersionsCount++;
				}

				diffVersionsJSONArray.put(diffVersionJSONObject);
			}

			data.put("diffVersions", diffVersionsJSONArray);

			data.put("diffVersionsCount", diffVersionsCount);

			data.put("languageId", _languageId);

			data.put(
				"nextVersion",
				String.valueOf(_diffVersionsInfo.getNextVersion()));

			data.put("portletURL", _portletURL.toString());

			data.put(
				"previousVersion",
				String.valueOf(_diffVersionsInfo.getPreviousVersion()));

			data.put("resourceURL", _resourceURL.toString());

			data.put("sourceVersion", String.valueOf(_sourceVersion));

			data.put("targetVersion", String.valueOf(_targetVersion));
		}
		catch (PortalException | PortletException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		httpServletRequest.setAttribute(
			"liferay-frontend:diff-version-comparator:data", data);
	}

	private static final String _PAGE = "/diff_version_comparator/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		DiffVersionComparatorTag.class);

	private Set<Locale> _availableLocales;
	private String _diffHtmlResults;
	private DiffVersionsInfo _diffVersionsInfo;
	private String _languageId;
	private PortletURL _portletURL;
	private PortletURL _resourceURL;
	private double _sourceVersion;
	private double _targetVersion;

}