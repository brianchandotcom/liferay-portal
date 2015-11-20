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

package com.liferay.journal.util.impl;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.index.IndexEncoder;
import com.liferay.portal.kernel.cache.index.PortalCacheIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;

import java.io.Serializable;

import javax.portlet.RenderRequest;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Michael Young
 */
@Component(service = JournalContent.class)
@DoPrivileged
public class JournalContentImpl implements JournalContent {

	@Override
	public void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_getPortalCache().removeAll();
	}

	@Override
	public void clearCache(
		long groupId, String articleId, String ddmTemplateKey) {

		_getPortalCacheIndexer().removeKeys(
			JournalContentKeyIndexEncoder.encode(
				groupId, articleId, ddmTemplateKey));
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

		JournalContentKey journalContentKey = new JournalContentKey(
			groupId, articleId, version, ddmTemplateKey, layoutSetId, viewMode,
			languageId, page, secure);

		JournalArticleDisplay articleDisplay = _getPortalCache().get(
			journalContentKey);

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

				_getPortalCache().put(journalContentKey, articleDisplay);
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

			return _journalArticleLocalService.getArticleDisplay(
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

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	protected static final String CACHE_NAME = JournalContent.class.getName();

	private PortalCache<JournalContentKey, JournalArticleDisplay>
			_getPortalCache() {

		if (_portalCache == null) {
			_portalCache = MultiVMPoolUtil.getPortalCache(CACHE_NAME);
		}

		return _portalCache;
	}

	private PortalCacheIndexer<String, JournalContentKey, JournalArticleDisplay>
		_getPortalCacheIndexer() {

		if (_portalCacheIndexer == null) {
			_portalCacheIndexer = new PortalCacheIndexer<>(
				new JournalContentKeyIndexEncoder(), _getPortalCache());
		}

		return _portalCacheIndexer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentImpl.class);

	private static PortalCache<JournalContentKey, JournalArticleDisplay>
		_portalCache;
	private static PortalCacheIndexer
		<String, JournalContentKey, JournalArticleDisplay> _portalCacheIndexer;

	private volatile JournalArticleLocalService _journalArticleLocalService;

	private static class JournalContentKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			JournalContentKey journalContentKey = (JournalContentKey)obj;

			if ((journalContentKey._groupId == _groupId) &&
				Validator.equals(journalContentKey._articleId, _articleId) &&
				(journalContentKey._version == _version) &&
				Validator.equals(
					journalContentKey._ddmTemplateKey, _ddmTemplateKey) &&
				(journalContentKey._layoutSetId == _layoutSetId) &&
				Validator.equals(journalContentKey._viewMode, _viewMode) &&
				Validator.equals(journalContentKey._languageId, _languageId) &&
				(journalContentKey._page == _page) &&
				(journalContentKey._secure == _secure)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _groupId);

			hashCode = HashUtil.hash(hashCode, _articleId);
			hashCode = HashUtil.hash(hashCode, _version);
			hashCode = HashUtil.hash(hashCode, _ddmTemplateKey);
			hashCode = HashUtil.hash(hashCode, _layoutSetId);
			hashCode = HashUtil.hash(hashCode, _viewMode);
			hashCode = HashUtil.hash(hashCode, _languageId);
			hashCode = HashUtil.hash(hashCode, _page);

			return HashUtil.hash(hashCode, _secure);
		}

		private JournalContentKey(
			long groupId, String articleId, double version,
			String ddmTemplateKey, long layoutSetId, String viewMode,
			String languageId, int page, boolean secure) {

			_groupId = groupId;
			_articleId = articleId;
			_version = version;
			_ddmTemplateKey = ddmTemplateKey;
			_layoutSetId = layoutSetId;
			_viewMode = viewMode;
			_languageId = languageId;
			_page = page;
			_secure = secure;
		}

		private static final long serialVersionUID = 1L;

		private final String _articleId;
		private final String _ddmTemplateKey;
		private final long _groupId;
		private final String _languageId;
		private final long _layoutSetId;
		private final int _page;
		private final boolean _secure;
		private final double _version;
		private final String _viewMode;

	}

	private static class JournalContentKeyIndexEncoder
		implements IndexEncoder<String, JournalContentKey> {

		public static String encode(
			long groupId, String articleId, String ddmTemplateKey) {

			StringBundler sb = new StringBundler(5);

			sb.append(groupId);
			sb.append(StringPool.UNDERLINE);
			sb.append(articleId);
			sb.append(StringPool.UNDERLINE);
			sb.append(ddmTemplateKey);

			return sb.toString();
		}

		@Override
		public String encode(JournalContentKey journalContentKey) {
			return encode(
				journalContentKey._groupId, journalContentKey._articleId,
				journalContentKey._ddmTemplateKey);
		}

	}

}