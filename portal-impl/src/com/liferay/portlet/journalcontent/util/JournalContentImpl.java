/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
@DoPrivileged
public class JournalContentImpl implements JournalContent {

	@Override
	public void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		portalCache.removeAll();
	}

	@Override
	public void clearCache(
		long groupId, String articleId, String ddmTemplateKey) {

		clearCache();
	}

	@Override
	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, null, xmlRequest);
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, String xmlRequest) {

		return getContent(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, null,
			xmlRequest);
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			themeDisplay, null);
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay, String xmlRequest) {

		JournalArticleDisplay articleDisplay = getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			themeDisplay, 1, xmlRequest);

		if (articleDisplay != null) {
			return articleDisplay.getContent();
		}
		else {
			return null;
		}
	}

	@Override
	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, double version, String ddmTemplateKey,
		String viewMode, String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest) {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		articleId = GetterUtil.getString(articleId).toUpperCase();
		ddmTemplateKey = GetterUtil.getString(ddmTemplateKey).toUpperCase();

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
			groupId, articleId, version, ddmTemplateKey, layoutSetId, viewMode,
			languageId, page, secure);

		JournalArticleDisplay articleDisplay = portalCache.get(key);

		boolean lifecycleRender = isLifecycleRender(themeDisplay, xmlRequest);

		if ((articleDisplay == null) || !lifecycleRender) {
			articleDisplay = getArticleDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId, page,
				xmlRequest, themeDisplay);

			if ((articleDisplay != null) && articleDisplay.isCacheable() &&
				lifecycleRender) {

				portalCache.put(key, articleDisplay);
			}
		}

		try {
			if (PropsValues.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED &&
				(articleDisplay != null) && (themeDisplay != null) &&
				!JournalArticlePermission.contains(
					themeDisplay.getPermissionChecker(), groupId, articleId,
					ActionKeys.VIEW)) {

				articleDisplay = null;
			}
		}
		catch (Exception e) {
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"getDisplay for {" + groupId + ", " + articleId + ", " +
					ddmTemplateKey + ", " + viewMode + ", " + languageId +
						", " + page + "} takes " + stopWatch.getTime() + " ms");
		}

		return articleDisplay;
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, null, 1,
			xmlRequest);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, String xmlRequest) {

		return getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, null, 1,
			xmlRequest);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			themeDisplay, 1, null);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest) {

		return getDisplay(
			groupId, articleId, 0, ddmTemplateKey, viewMode, languageId,
			themeDisplay, 1, xmlRequest);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, viewMode, languageId, themeDisplay, 1);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay, int page) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay, page,
			null);
	}

	protected String encodeKey(
		long groupId, String articleId, double version, String ddmTemplateKey,
		long layoutSetId, String viewMode, String languageId, int page,
		boolean secure) {

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.toHexString(groupId));
		sb.append(ARTICLE_SEPARATOR);
		sb.append(articleId);
		sb.append(VERSION_SEPARATOR);
		sb.append(version);
		sb.append(TEMPLATE_SEPARATOR);
		sb.append(ddmTemplateKey);

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
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, int page, String xmlRequest,
		ThemeDisplay themeDisplay) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Get article display {" + groupId + ", " + articleId +
						", " + ddmTemplateKey + "}");
			}

			return JournalArticleLocalServiceUtil.getArticleDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId, page,
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
			Matcher matcher = lifecycleRenderPhasePattern.matcher(xmlRequest);

			return matcher.find();
		}
		else {
			return false;
		}
	}

	protected static final String CACHE_NAME = JournalContent.class.getName();

	protected static Pattern lifecycleRenderPhasePattern = Pattern.compile(
		"<lifecycle>\\s*RENDER_PHASE\\s*</lifecycle>");
	protected static PortalCache<String, JournalArticleDisplay> portalCache =
		MultiVMPoolUtil.getCache(CACHE_NAME);

	private static Log _log = LogFactoryUtil.getLog(JournalContentImpl.class);

}