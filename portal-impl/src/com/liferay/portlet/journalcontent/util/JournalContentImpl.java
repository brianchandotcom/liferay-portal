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

package com.liferay.portlet.journalcontent.util;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import javax.portlet.RenderRequest;

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
		PortletRequestModel portletRequestModel) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, portletRequestModel,
			null);
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, PortletRequestModel portletRequestModel) {

		return getContent(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			portletRequestModel, null);
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		JournalArticleDisplay articleDisplay = getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, themeDisplay);

		if (articleDisplay != null) {
			return articleDisplay.getContent();
		}
		else {
			return null;
		}
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			(PortletRequestModel)null, themeDisplay);
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
		String viewMode, String languageId, int page,
		PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		articleId = StringUtil.toUpperCase(GetterUtil.getString(articleId));
		ddmTemplateKey = StringUtil.toUpperCase(
			GetterUtil.getString(ddmTemplateKey));

		long layoutSetId = 0;
		boolean secure = false;

		if (themeDisplay != null) {
			try {
				if (!JournalArticlePermission.contains(
						themeDisplay.getPermissionChecker(), groupId, articleId,
						ActionKeys.VIEW)) {

					return null;
				}
			}
			catch (Exception e) {
			}

			LayoutSet layoutSet = themeDisplay.getLayoutSet();

			layoutSetId = layoutSet.getLayoutSetId();

			secure = themeDisplay.isSecure();
		}

		String key = encodeKey(
			groupId, articleId, version, ddmTemplateKey, layoutSetId, viewMode,
			languageId, page, secure);

		JournalArticleDisplay articleDisplay = portalCache.get(key);

		boolean lifecycleRender = false;

		if (portletRequestModel != null) {
			lifecycleRender = RenderRequest.RENDER_PHASE.equals(
				portletRequestModel.getLifecycle());
		}

		if ((articleDisplay == null) || !lifecycleRender) {
			articleDisplay = getArticleDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);

			if ((articleDisplay != null) && articleDisplay.isCacheable() &&
				lifecycleRender) {

				portalCache.put(key, articleDisplay);
			}
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
		int page, ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, page,
			(PortletRequestModel)null, themeDisplay);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		PortletRequestModel portletRequestModel) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, 1,
			portletRequestModel, null);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, 0, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, themeDisplay);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, PortletRequestModel portletRequestModel) {

		return getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, null);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, 1,
			(PortletRequestModel)null, themeDisplay);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, viewMode, languageId, 1, themeDisplay);
	}

	protected String encodeKey(
		long groupId, String articleId, double version, String ddmTemplateKey,
		long layoutSetId, String viewMode, String languageId, int page,
		boolean secure) {

		StringBundler sb = new StringBundler(17);

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
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Get article display {" + groupId + ", " + articleId +
						", " + ddmTemplateKey + "}");
			}

			return JournalArticleLocalServiceUtil.getArticleDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);
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

	protected static final String CACHE_NAME = JournalContent.class.getName();

	protected static PortalCache<String, JournalArticleDisplay> portalCache =
		MultiVMPoolUtil.getCache(CACHE_NAME);

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentImpl.class);

}