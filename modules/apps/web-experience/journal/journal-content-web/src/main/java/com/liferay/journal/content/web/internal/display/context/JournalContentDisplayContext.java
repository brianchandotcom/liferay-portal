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

package com.liferay.journal.content.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.asset.addon.entry.common.ContentMetadataAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.common.ContentMetadataAssetAddonEntryTracker;
import com.liferay.journal.content.asset.addon.entry.common.UserToolAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.common.UserToolAssetAddonEntryTracker;
import com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public JournalContentDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_journalContentPortletInstanceConfiguration =
			journalContentPortletInstanceConfiguration;

		if (Validator.isNull(getPortletResource()) && !isShowArticle()) {
			portletRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
		else if (isShowArticle() &&
				 (portletResponse instanceof RenderResponse)) {

			RenderResponse renderResponse = (RenderResponse)portletResponse;

			JournalArticleDisplay articleDisplay = getArticleDisplay();

			renderResponse.setTitle(articleDisplay.getTitle());
		}
	}

	public void clearCache() throws PortalException {
		String articleId = getArticleId();

		if (Validator.isNotNull(articleId)) {
			JournalContent journalContent =
				(JournalContent)_portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			journalContent.clearCache(
				getArticleGroupId(), getArticleId(), getDDMTemplateKey());
		}
	}

	public JournalArticle getArticle() {
		if (_article != null) {
			return _article;
		}

		_article = (JournalArticle)_portletRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		if (_article != null) {
			return _article;
		}

		long articleResourcePrimKey = ParamUtil.getLong(
			_portletRequest, "articleResourcePrimKey");

		if (articleResourcePrimKey > 0) {
			_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
				articleResourcePrimKey, WorkflowConstants.STATUS_ANY, true);
		}
		else {
			_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
				getArticleGroupId(), getArticleId(),
				WorkflowConstants.STATUS_ANY);
		}

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		_articleDisplay = (JournalArticleDisplay)_portletRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY);

		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (article.isApproved()) {
			JournalContent journalContent =
				(JournalContent) _portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			_articleDisplay = journalContent.getDisplay(
				article.getGroupId(), article.getArticleId(), null, null,
				themeDisplay.getLanguageId(), 1,
				new PortletRequestModel(_portletRequest, _portletResponse),
				themeDisplay);
		}
		else {
			try {
				_articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						article, null, null, themeDisplay.getLanguageId(), 1,
						new PortletRequestModel(
							_portletRequest, _portletResponse),
						themeDisplay);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return _articleDisplay;
	}

	public long getArticleGroupId() {
		if (_articleGroupId != null) {
			return _articleGroupId;
		}

		_articleGroupId = ParamUtil.getLong(
			_portletRequest, "groupId",
			_journalContentPortletInstanceConfiguration.groupId());

		if (_articleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_articleGroupId = themeDisplay.getScopeGroupId();
		}

		return _articleGroupId;
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = ParamUtil.getString(
			_portletRequest, "articleId",
			_journalContentPortletInstanceConfiguration.articleId());

		return _articleId;
	}

	public long getAssetEntryId() {
		JournalArticle article = getArticle();

		if (article == null) {
			return 0;
		}

		long classPK = JournalArticleAssetRenderer.getClassPK(article);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), classPK);

		return assetEntry.getEntryId();
	}

	public AssetRenderer<JournalArticle> getAssetRenderer()
		throws PortalException {

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		if (assetRendererFactory == null) {
			return null;
		}

		return assetRendererFactory.getAssetRenderer(
			JournalArticleAssetRenderer.getClassPK(article));
	}

	public DDMStructure getDDMStructure() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		return article.getDDMStructure();
	}

	public DDMTemplate getDDMTemplate() {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((articleDisplay == null) ||
			Validator.isNull(articleDisplay.getDDMTemplateKey())) {

			return null;
		}

		try {
			_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				articleDisplay.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				getDDMTemplateKey(), true);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to obtain ddm template for article " +
					articleDisplay.getId());
		}

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey",
			_journalContentPortletInstanceConfiguration.ddmTemplateKey());

		if (Validator.isNotNull(_ddmTemplateKey)) {
			return _ddmTemplateKey;
		}

		List<DDMTemplate> ddmTemplates = getDDMTemplates();

		if (!ddmTemplates.isEmpty()) {
			JournalArticle article = getArticle();

			_ddmTemplateKey = article.getDDMTemplateKey();
		}

		return _ddmTemplateKey;
	}

	public List<DDMTemplate> getDDMTemplates() {
		if (_ddmTemplates != null) {
			return _ddmTemplates;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return Collections.emptyList();
		}

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					article.getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					article.getDDMStructureKey(), true);

			_ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), true);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to obtain ddm temmplate for article " +
					article.getId());
		}

		return _ddmTemplates;
	}

	public List<ContentMetadataAssetAddonEntry>
		getEnabledContentMetadataAssetAddonEntries() {

		List<ContentMetadataAssetAddonEntry> contentMetadataAssetAddonEntries =
			ListUtil.filter(
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntries(),
				new PredicateFilter<ContentMetadataAssetAddonEntry>() {

					@Override
					public boolean filter(
						ContentMetadataAssetAddonEntry
							contentMetadataAssetAddonEntry) {

						return contentMetadataAssetAddonEntry.isEnabled();
					}

				});

		return ListUtil.sort(
			contentMetadataAssetAddonEntries, _assetAddonEntryComparator);
	}

	public List<UserToolAssetAddonEntry> getEnabledUserToolAssetAddonEntries() {
		List<UserToolAssetAddonEntry> userToolAssetAddonEntries =
			ListUtil.filter(
				UserToolAssetAddonEntryTracker.getUserToolAssetAddonEntries(),
				new PredicateFilter<UserToolAssetAddonEntry>() {

					@Override
					public boolean filter(
						UserToolAssetAddonEntry userToolAssetAddonEntry) {

						return userToolAssetAddonEntry.isEnabled();
					}

				});

		return ListUtil.sort(
			userToolAssetAddonEntries, _assetAddonEntryComparator);
	}

	public JournalArticle getLatestArticle() {
		if (_latestArticle != null) {
			return _latestArticle;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if (articleDisplay == null) {
			return null;
		}

		_latestArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(
			articleDisplay.getGroupId(), articleDisplay.getArticleId(),
			WorkflowConstants.STATUS_ANY);

		return _latestArticle;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_portletRequest, "portletResource");

		return _portletResource;
	}

	public List<ContentMetadataAssetAddonEntry>
		getSelectedContentMetadataAssetAddonEntries() {

		if (_contentMetadataAssetAddonEntries != null) {
			return _contentMetadataAssetAddonEntries;
		}

		_contentMetadataAssetAddonEntries = new ArrayList<>();

		String contentMetadataAssetAddonEntryKeysKeysString =
			_journalContentPortletInstanceConfiguration.
				contentMetadataAssetAddonEntryKeys();

		if (Validator.isNull(contentMetadataAssetAddonEntryKeysKeysString)) {
			return _contentMetadataAssetAddonEntries;
		}

		String[] contentMetadataAssetAddonEntryKeys = StringUtil.split(
			contentMetadataAssetAddonEntryKeysKeysString);

		for (String contentMetadataAssetAddonEntryKey :
				contentMetadataAssetAddonEntryKeys) {

			ContentMetadataAssetAddonEntry contentMetadataAssetAddonEntry =
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntry(
						contentMetadataAssetAddonEntryKey);

			if (contentMetadataAssetAddonEntry != null) {
				_contentMetadataAssetAddonEntries.add(
					contentMetadataAssetAddonEntry);
			}
		}

		_portletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_portletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _contentMetadataAssetAddonEntries;
	}

	public long[] getSelectedGroupIds() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.getScopeGroupId() == themeDisplay.getSiteGroupId()) {
			return PortalUtil.getSharedContentSiteGroupIds(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				themeDisplay.getUserId());
		}

		return new long[] {themeDisplay.getScopeGroupId()};
	}

	public List<UserToolAssetAddonEntry>
		getSelectedUserToolAssetAddonEntries() {

		if (_userToolAssetAddonEntries != null) {
			return _userToolAssetAddonEntries;
		}

		_userToolAssetAddonEntries = new ArrayList<>();

		String userToolAssetAddonEntryKeysString =
			_journalContentPortletInstanceConfiguration.
				userToolAssetAddonEntryKeys();

		if (Validator.isNull(userToolAssetAddonEntryKeysString)) {
			return _userToolAssetAddonEntries;
		}

		String[] userToolAssetAddonEntryKeys = StringUtil.split(
			userToolAssetAddonEntryKeysString);

		for (String userToolAssetAddonEntryKey : userToolAssetAddonEntryKeys) {
			UserToolAssetAddonEntry userToolAssetAddonEntry =
				UserToolAssetAddonEntryTracker.getUserToolAssetAddonEntry(
					userToolAssetAddonEntryKey);

			if (userToolAssetAddonEntry != null) {
				_userToolAssetAddonEntries.add(userToolAssetAddonEntry);
			}
		}

		_portletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_portletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _userToolAssetAddonEntries;
	}

	public String getURLEdit() {
		try {
			AssetRendererFactory<JournalArticle> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);

			JournalArticle article = getArticle();

			AssetRenderer<JournalArticle> latestArticleAssetRenderer =
				assetRendererFactory.getAssetRenderer(
					article.getResourcePrimKey());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletURL redirectURL = PortletURLFactoryUtil.create(
				_portletRequest, JournalContentPortletKeys.JOURNAL_CONTENT,
				PortletRequest.RENDER_PHASE);

			redirectURL.setParameter(
				"mvcPath", "/update_journal_article_redirect.jsp");

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			redirectURL.setParameter(
				"referringPortletResource", portletDisplay.getId());

			redirectURL.setWindowState(LiferayWindowState.POP_UP);

			PortletURL portletURL = latestArticleAssetRenderer.getURLEdit(
				(LiferayPortletRequest)_portletRequest, null,
				LiferayWindowState.POP_UP, redirectURL);

			portletURL.setParameter(
				"hideDefaultSuccessMessage", Boolean.TRUE.toString());
			portletURL.setParameter("showHeader", Boolean.FALSE.toString());

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to get edit URL", e);

			return StringPool.BLANK;
		}
	}

	public String getURLEditTemplate() {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletURL portletURL = PortletURLFactoryUtil.create(
				_portletRequest,
				PortletProviderUtil.getPortletId(
					DDMTemplate.class.getName(), PortletProvider.Action.EDIT),
				PortletRequest.RENDER_PHASE);

			DDMTemplate ddmTemplate = getDDMTemplate();

			if (ddmTemplate == null) {
				return StringPool.BLANK;
			}

			portletURL.setParameter(
				"hideDefaultSuccessMessage", Boolean.TRUE.toString());
			portletURL.setParameter("mvcPath", "/edit_template.jsp");
			portletURL.setParameter("navigationStartsOn", "SELECT_TEMPLATE");

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			PortletURL redirectURL = PortletURLFactoryUtil.create(
				_portletRequest, portletDisplay.getId(),
				PortletRequest.RENDER_PHASE);

			redirectURL.setParameter(
				"mvcPath", "/update_journal_article_redirect.jsp");
			redirectURL.setParameter(
				"referringPortletResource", portletDisplay.getId());
			redirectURL.setWindowState(LiferayWindowState.POP_UP);

			portletURL.setParameter("redirect", redirectURL.toString());

			portletURL.setParameter("showBackURL", Boolean.FALSE.toString());
			portletURL.setParameter(
				"showCacheableInput", Boolean.TRUE.toString());
			portletURL.setParameter(
				"groupId", String.valueOf(ddmTemplate.getGroupId()));
			portletURL.setParameter(
				"refererPortletName",
				PortletProviderUtil.getPortletId(
					JournalArticle.class.getName(),
					PortletProvider.Action.EDIT));
			portletURL.setParameter(
				"templateId", String.valueOf(ddmTemplate.getTemplateId()));
			portletURL.setParameter("showHeader", Boolean.FALSE.toString());
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to get edit template URL", e);

			return StringPool.BLANK;
		}
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		JournalArticle article = getArticle();

		if (article != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_hasViewPermission = JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), article.getGroupId(),
				article.getArticleId(), ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public void incrementViewCounter() throws PortalException {
		JournalArticle article = getArticle();
		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((article == null) || !hasViewPermission() ||
			(articleDisplay == null) || isExpired() ||
			!isEnableViewCountIncrement()) {

			return;
		}

		AssetEntryServiceUtil.incrementViewCounter(
			JournalArticle.class.getName(),
			articleDisplay.getResourcePrimKey());
	}

	public boolean isEnableViewCountIncrement() {
		if (_enableViewCountIncrement != null) {
			return _enableViewCountIncrement;
		}

		if (Validator.isNotNull(
				_journalContentPortletInstanceConfiguration.
					enableViewCountIncrement())) {

			_enableViewCountIncrement = GetterUtil.getBoolean(
				_journalContentPortletInstanceConfiguration.
					enableViewCountIncrement());
		}
		else {
			_enableViewCountIncrement =
				PropsValues.ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED;
		}

		return _enableViewCountIncrement;
	}

	public boolean isExpired() {
		if (_expired != null) {
			return _expired;
		}

		JournalArticle article = getArticle();

		_expired = article.isExpired();

		if (!_expired) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(new Date())) {
				_expired = true;
			}
		}

		return _expired;
	}

	public boolean isPrint() {
		if (_print != null) {
			return _print;
		}

		_print = false;

		String viewMode = ParamUtil.getString(_portletRequest, "viewMode");

		if (viewMode.equals(Constants.PRINT)) {
			_print = true;
		}

		return _print;
	}

	public boolean isShowAddArticleIcon() throws PortalException {
		if (_showAddArticleIcon != null) {
			return _showAddArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_showAddArticleIcon = false;

		if (!isShowSelectArticleIcon()) {
			return _showAddArticleIcon;
		}

		_showAddArticleIcon = JournalPermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ActionKeys.ADD_ARTICLE);

		return _showAddArticleIcon;
	}

	public boolean isShowArticle() throws PortalException {
		if (_showArticle != null) {
			return _showArticle;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			_showArticle = false;

			return _showArticle;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if (articleDisplay == null) {
			_showArticle = false;

			return _showArticle;
		}

		if (!hasViewPermission() || isExpired() || article.isScheduled() ||
			article.isPending()) {

			_showArticle = false;

			return _showArticle;
		}

		_showArticle = true;

		return _showArticle;
	}

	public boolean isShowEditArticleIcon() {
		if (_showEditArticleIcon != null) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		_showEditArticleIcon = false;

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_showEditArticleIcon = JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), latestArticle.getGroupId(),
				latestArticle.getArticleId(), ActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to check permissions for article " +
					latestArticle.getId());
		}

		return _showEditArticleIcon;
	}

	public boolean isShowEditTemplateIcon() {
		if (_showEditTemplateIcon != null) {
			return _showEditTemplateIcon;
		}

		_showEditTemplateIcon = false;

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return _showEditTemplateIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			_showEditTemplateIcon = DDMTemplatePermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ddmTemplate,
				portletDisplay.getId(), ActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to check permission on ddm template " +
					ddmTemplate.getTemplateId());
		}

		return _showEditTemplateIcon;
	}

	public boolean isShowSelectArticleIcon() throws PortalException {
		if (_showSelectArticleIcon != null) {
			return _showSelectArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_showSelectArticleIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);

		return _showSelectArticleIcon;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentDisplayContext.class);

	private static final Comparator<AssetAddonEntry>
		_assetAddonEntryComparator = new Comparator<AssetAddonEntry>() {

			@Override
			public int compare(
				AssetAddonEntry assetAddonEntry1,
				AssetAddonEntry assetAddonEntry2) {

				return Double.compare(
					assetAddonEntry1.getWeight(), assetAddonEntry2.getWeight());
			}

		};

	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private Long _articleGroupId;
	private String _articleId;
	private List<ContentMetadataAssetAddonEntry>
		_contentMetadataAssetAddonEntries;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private List<DDMTemplate> _ddmTemplates;
	private Boolean _enableViewCountIncrement;
	private Boolean _expired;
	private Boolean _hasViewPermission;
	private final JournalContentPortletInstanceConfiguration
		_journalContentPortletInstanceConfiguration;
	private JournalArticle _latestArticle;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private Boolean _print;
	private Boolean _showAddArticleIcon;
	private Boolean _showArticle;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showSelectArticleIcon;
	private List<UserToolAssetAddonEntry> _userToolAssetAddonEntries;

}