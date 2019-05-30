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

package com.liferay.frontend.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
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

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Robert Frampton
 */
public class DiffVersionComparatorTag extends ComponentRendererTag {

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

	public JSONObject createLanguageKeysJSONObject() {
		JSONObject languageKeysJSONObject = JSONFactoryUtil.createJSONObject();

		for (String languageKey : _LANGUAGE_KEYS) {
			languageKeysJSONObject.put(
				languageKey, LanguageUtil.get(request, languageKey));
		}

		return languageKeysJSONObject;
	}

	@Override
	public int doStartTag() {
		if (Validator.isNotNull(_languageId)) {
			_resourceURL.setParameter("languageId", _languageId);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
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

			putValue("diffVersions", diffVersionsJSONArray);
			putValue("diffVersionsCount", diffVersionsCount);
			putValue("languageKeys", createLanguageKeysJSONObject());
			putValue(
				"nextVersion",
				String.valueOf(_diffVersionsInfo.getNextVersion()));
			putValue("pathThemeImages", themeDisplay.getPathThemeImages());

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			putValue("portletNamespace", portletResponse.getNamespace());

			putValue("portletURL", _portletURL.toString());
			putValue(
				"previousVersion",
				String.valueOf(_diffVersionsInfo.getPreviousVersion()));
			putValue("resourceURL", _resourceURL.toString());
		}
		catch (PortalException | PortletException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		setTemplateNamespace("liferay.frontend.DiffVersionComparator.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "frontend-taglib/diff_version_comparator" +
			"/DiffVersionComparator.es";
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			JSONObject availableLocaleJSONObject = JSONUtil.put(
				"displayName",
				availableLocale.getDisplayName(themeDisplay.getLocale())
			).put(
				"languageId", LocaleUtil.toLanguageId(availableLocale)
			);

			availableLocalesJSONArray.put(availableLocaleJSONObject);
		}

		putValue("availableLocales", availableLocalesJSONArray);
	}

	public void setDiffHtmlResults(String diffHtmlResults) {
		putHTMLValue("diffHtmlResults", diffHtmlResults);
	}

	public void setDiffVersionsInfo(DiffVersionsInfo diffVersionsInfo) {
		_diffVersionsInfo = diffVersionsInfo;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;

		putValue("languageId", languageId);
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setResourceURL(PortletURL resourceURL) {
		_resourceURL = resourceURL;
	}

	public void setSourceVersion(double sourceVersion) {
		_sourceVersion = sourceVersion;

		putValue("sourceVersion", String.valueOf(sourceVersion));
		putValue(
			"sourceVersionLabel",
			LanguageUtil.format(request, "version-x", sourceVersion));
	}

	public void setTargetVersion(double targetVersion) {
		_targetVersion = targetVersion;

		putValue("targetVersion", String.valueOf(targetVersion));
		putValue(
			"targetVersionLabel",
			LanguageUtil.format(request, "version-x", targetVersion));
	}

	private static final String[] _LANGUAGE_KEYS = {
		"added", "deleted", "first-version", "format-changes", "last-version",
		"there-are-no-results", "you-are-comparing-these-versions"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DiffVersionComparatorTag.class);

	private Set<Locale> _availableLocales;
	private DiffVersionsInfo _diffVersionsInfo;
	private String _languageId;
	private PortletURL _portletURL;
	private PortletURL _resourceURL;
	private double _sourceVersion;
	private double _targetVersion;

}