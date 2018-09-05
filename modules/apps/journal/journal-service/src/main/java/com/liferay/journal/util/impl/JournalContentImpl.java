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

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.index.IndexEncoder;
import com.liferay.portal.kernel.cache.index.PortalCacheIndexer;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.Objects;

import javax.portlet.RenderRequest;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Michael Young
 */
@Component(service = {IdentifiableOSGiService.class, JournalContent.class})
public class JournalContentImpl
	implements IdentifiableOSGiService, JournalContent {

	@Override
	public void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_portalCache.removeAll();
	}

	@Override
	public void clearCache(
		long groupId, String articleId, String ddmTemplateKey) {

		_journalArticlePortalCacheIndexer.removeKeys(
			JournalContentArticleKeyIndexEncoder.encode(
				groupId, articleId, ddmTemplateKey));

		if (ClusterInvokeThreadLocal.isEnabled()) {
			try {
				ClusterableInvokerUtil.invokeOnCluster(
					ClusterInvokeAcceptor.class, this, _clearArticleCacheMethod,
					new Object[] {groupId, articleId, ddmTemplateKey});
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
		}
	}

	@Override
	public void clearCache(String ddmTemplateKey) {
		_journalTemplatePortalCacheIndexer.removeKeys(ddmTemplateKey);

		if (ClusterInvokeThreadLocal.isEnabled()) {
			try {
				ClusterableInvokerUtil.invokeOnCluster(
					ClusterInvokeAcceptor.class, this,
					_clearTemplateCacheMethod, new Object[] {ddmTemplateKey});
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
		}
	}

	@Override
	public String getContent(
		long groupId, String articleId, String viewMode, String languageId) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, null,
			_getDefaultThemeDisplay());
	}

	@Override
	public String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		PortletRequestModel portletRequestModel) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, portletRequestModel,
			_getDefaultThemeDisplay());
	}

	@Override
	public String getContent(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, PortletRequestModel portletRequestModel) {

		return getContent(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			portletRequestModel, _getDefaultThemeDisplay());
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

		return null;
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
		JournalArticle article, String ddmTemplateKey, String viewMode,
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		long groupId = article.getGroupId();
		String articleId = article.getArticleId();
		double version = article.getVersion();

		articleId = StringUtil.toUpperCase(GetterUtil.getString(articleId));
		ddmTemplateKey = StringUtil.toUpperCase(
			GetterUtil.getString(ddmTemplateKey));

		long layoutSetId = 0;
		boolean secure = false;

		if (themeDisplay != null) {
			try {
				if (!_journalArticleModelResourcePermission.contains(
						themeDisplay.getPermissionChecker(), article,
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

		if (Validator.isNull(ddmTemplateKey)) {
			ddmTemplateKey = article.getDDMTemplateKey();
		}

		JournalContentKey journalContentKey = new JournalContentKey(
			groupId, articleId, version, ddmTemplateKey, layoutSetId, viewMode,
			languageId, page, secure);

		JournalArticleDisplay articleDisplay = _portalCache.get(
			journalContentKey);

		boolean lifecycleRender = false;

		if (portletRequestModel != null) {
			lifecycleRender = RenderRequest.RENDER_PHASE.equals(
				portletRequestModel.getLifecycle());
		}

		if ((articleDisplay == null) || !lifecycleRender) {
			articleDisplay = getArticleDisplay(
				article, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);

			if ((articleDisplay != null) && articleDisplay.isCacheable() &&
				lifecycleRender) {

				_portalCache.put(journalContentKey, articleDisplay);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"getDisplay for {", groupId, ", ", articleId, ", ",
					ddmTemplateKey, ", ", viewMode, ", ", languageId, ", ",
					page, "} takes ", stopWatch.getTime(), " ms"));
		}

		return articleDisplay;
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, double version, String ddmTemplateKey,
		String viewMode, String languageId, int page,
		PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay) {

		try {
			JournalArticle article = _journalArticleLocalService.getArticle(
				groupId, articleId, version);

			return getDisplay(
				article, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get display for ", groupId, StringPool.BLANK,
						articleId, StringPool.BLANK, languageId),
					pe);
			}

			return null;
		}
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
			portletRequestModel, _getDefaultThemeDisplay());
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		return getDisplay(
			article, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, themeDisplay);
	}

	@Override
	public JournalArticleDisplay getDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, PortletRequestModel portletRequestModel) {

		return getDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, _getDefaultThemeDisplay());
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

	@Override
	public String getOSGiServiceIdentifier() {
		return JournalContent.class.getName();
	}

	protected JournalArticleDisplay getArticleDisplay(
		JournalArticle article, String ddmTemplateKey, String viewMode,
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return null;
		}

		Date now = new Date();

		Date displayDate = article.getDisplayDate();
		Date expirationDate = article.getExpirationDate();

		if (((displayDate != null) && displayDate.after(now)) ||
			((expirationDate != null) && expirationDate.before(now))) {

			return null;
		}

		try {
			return _journalArticleLocalService.getArticleDisplay(
				article, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get display for ", article.toString(),
						StringPool.SPACE, languageId),
					e);
			}

			return null;
		}
	}

	protected JournalArticleDisplay getArticleDisplay(
		long groupId, String articleId, String ddmTemplateKey, String viewMode,
		String languageId, int page, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Get article display {", groupId, ", ", articleId, ", ",
						ddmTemplateKey, "}"));
			}

			return _journalArticleLocalService.getArticleDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId, page,
				portletRequestModel, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get display for ", groupId, StringPool.SPACE,
						articleId, StringPool.SPACE, languageId));
			}

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<JournalContentKey, JournalArticleDisplay>)
				multiVMPool.getPortalCache(CACHE_NAME);

		_journalArticlePortalCacheIndexer = new PortalCacheIndexer<>(
			new JournalContentArticleKeyIndexEncoder(), _portalCache);
		_journalTemplatePortalCacheIndexer = new PortalCacheIndexer<>(
			new JournalContentTemplateKeyIndexEncoder(), _portalCache);
	}

	protected static final String CACHE_NAME = JournalContent.class.getName();

	private ThemeDisplay _getDefaultThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		return serviceContext.getThemeDisplay();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentImpl.class);

	private static final Method _clearArticleCacheMethod;
	private static final Method _clearTemplateCacheMethod;
	private static PortalCacheIndexer
		<String, JournalContentKey, JournalArticleDisplay>
			_journalArticlePortalCacheIndexer;
	private static PortalCacheIndexer
		<String, JournalContentKey, JournalArticleDisplay>
			_journalTemplatePortalCacheIndexer;
	private static PortalCache<JournalContentKey, JournalArticleDisplay>
		_portalCache;

	static {
		try {
			_clearArticleCacheMethod = JournalContent.class.getMethod(
				"clearCache", long.class, String.class, String.class);

			_clearTemplateCacheMethod = JournalContent.class.getMethod(
				"clearCache", String.class);
		}
		catch (NoSuchMethodException nsme) {
			throw new ExceptionInInitializerError(nsme);
		}
	}

	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	private static class JournalContentArticleKeyIndexEncoder
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

	private static class JournalContentKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			JournalContentKey journalContentKey = (JournalContentKey)obj;

			if ((journalContentKey._groupId == _groupId) &&
				Objects.equals(journalContentKey._articleId, _articleId) &&
				(journalContentKey._version == _version) &&
				Objects.equals(
					journalContentKey._ddmTemplateKey, _ddmTemplateKey) &&
				(journalContentKey._layoutSetId == _layoutSetId) &&
				Objects.equals(journalContentKey._viewMode, _viewMode) &&
				Objects.equals(journalContentKey._languageId, _languageId) &&
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

	private static class JournalContentTemplateKeyIndexEncoder
		implements IndexEncoder<String, JournalContentKey> {

		@Override
		public String encode(JournalContentKey journalContentKey) {
			return journalContentKey._ddmTemplateKey;
		}

	}

}