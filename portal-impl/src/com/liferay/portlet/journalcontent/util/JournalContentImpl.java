/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.util;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Michael Young
 */
public class JournalContentImpl implements JournalContent {

	public void clearCache() {
		portalCache.removeAll();
	}

	public void clearCache(
		long groupId, String articleId, String templateId) {

		clearCache();
	}

	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, null, xmlRequest);
	}

	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	public String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, String xmlRequest) {

		return getContent(
			groupId, articleId, templateId, viewMode, languageId, null,
			xmlRequest);
	}

	public String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			null);
	}

	public String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay, String xmlRequest) {

		JournalArticleDisplay articleDisplay = getDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			1, xmlRequest);

		if (articleDisplay != null) {
			return articleDisplay.getContent();
		}
		else {
			return null;
		}
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, null, 1,
			xmlRequest);
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, viewMode, languageId, themeDisplay, 1);
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay, int page) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay, page,
			null);
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, String xmlRequest) {

		return getDisplay(
			groupId, articleId, templateId, viewMode, languageId, null, 1,
			xmlRequest);
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			1, null);
	}

	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest) {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		long layoutSetId = 0;
		boolean secure = false;

		if (themeDisplay != null) {
			try {
				Layout layout = themeDisplay.getLayout();

				LayoutSet layoutSet = layout.getLayoutSet();

				layoutSetId = layoutSet.getLayoutSetId();
			}
			catch (Exception e) {
			}

			secure = themeDisplay.isSecure();
		}

		String key = encodeKey(
			groupId, articleId, templateId, layoutSetId, viewMode, languageId,
			page, secure);

		JournalArticleDisplay articleDisplay =
			(JournalArticleDisplay)portalCache.get(key);

		boolean lifecycleRender = isLifecycleRender(themeDisplay, xmlRequest);

		if ((articleDisplay == null) || !lifecycleRender) {
			articleDisplay = getArticleDisplay(
				groupId, articleId, templateId, viewMode, languageId, page,
				xmlRequest, themeDisplay);

			if ((articleDisplay != null) && (articleDisplay.isCacheable()) &&
				(lifecycleRender)) {

				portalCache.put(key, articleDisplay);
			}
		}

		try {
			if ((PropsValues.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED) &&
				(articleDisplay != null) && (themeDisplay != null) &&
				(!JournalArticlePermission.contains(
					themeDisplay.getPermissionChecker(), groupId, articleId,
					ActionKeys.VIEW))) {

				articleDisplay = null;
			}
		}
		catch (Exception e) {
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"getDisplay for {" + groupId + ", " + articleId + ", " +
					templateId + ", " + viewMode + ", " + languageId + ", " +
						page + "} takes " + stopWatch.getTime() + " ms");
		}

		return articleDisplay;
	}

	protected String encodeKey(
		long groupId, String articleId, String templateId, long layoutSetId,
		String viewMode, String languageId, int page, boolean secure) {

		StringBundler sb = new StringBundler();

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(groupId));
		sb.append(ARTICLE_SEPARATOR);
		sb.append(articleId);
		sb.append(TEMPLATE_SEPARATOR);
		sb.append(templateId);

		if (layoutSetId > 0) {
			sb.append(LAYOUT_SET_SEPARATOR);
			sb.append(StringUtil.toHexString(layoutSetId));
		}

		if (Validator.isNotNull(viewMode)) {
			sb.append(VIEW_MODE_SEPARATOR);
			sb.append(viewMode);
		}

		if (Validator.isNotNull(languageId)) {
			sb.append(LANGUAGE_SEPARATOR);
			sb.append(languageId);
		}

		if (page > 0) {
			sb.append(PAGE_SEPARATOR);
			sb.append(StringUtil.toHexString(page));
		}

		sb.append(SECURE_SEPARATOR);
		sb.append(secure);

		return sb.toString();
	}

	protected JournalArticleDisplay getArticleDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, int page, String xmlRequest,
		ThemeDisplay themeDisplay) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Get article display {" + groupId + ", " + articleId +
						", " + templateId + "}");
			}

			return JournalArticleLocalServiceUtil.getArticleDisplay(
				groupId, articleId, templateId, viewMode, languageId, page,
				xmlRequest, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get display for " + groupId + " " +
						articleId + " " + languageId);
			}

			return null;
		}
	}

	protected boolean isLifecycleRender(
		ThemeDisplay themeDisplay, String xmlRequest) {

		if (themeDisplay != null) {
			return themeDisplay.isLifecycleRender();
		}
		else if (Validator.isNotNull(xmlRequest)) {
			Matcher matcher = lifecycleRenderPhasePatern.matcher(xmlRequest);

			return matcher.find();
		}
		else {
			return false;
		}
	}

	protected static Pattern lifecycleRenderPhasePatern = Pattern.compile(
		"<lifecycle>\\s*RENDER_PHASE\\s*</lifecycle>");
	protected static PortalCache portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

	private static Log _log = LogFactoryUtil.getLog(JournalContentImpl.class);

}