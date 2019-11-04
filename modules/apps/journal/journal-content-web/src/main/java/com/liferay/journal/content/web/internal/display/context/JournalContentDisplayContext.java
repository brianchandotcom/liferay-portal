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
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.asset.addon.entry.ContentMetadataAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.UserToolAssetAddonEntry;
import com.liferay.journal.content.web.internal.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.content.web.internal.constants.JournalContentWebKeys;
import com.liferay.journal.content.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.util.JournalContent;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.trash.constants.TrashActionKeys;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public static JournalContentDisplayContext create(
			PortletRequest portletRequest, PortletResponse portletResponse,
			long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission)
		throws PortalException {

		JournalContentDisplayContext journalContentDisplayContext =
			(JournalContentDisplayContext)portletRequest.getAttribute(
				JournalContentWebKeys.JOURNAL_CONTENT_DISPLAY_CONTEXT);

		if (journalContentDisplayContext == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						JournalContentPortletInstanceConfiguration.class);

			journalContentDisplayContext = new JournalContentDisplayContext(
				portletRequest, portletResponse, themeDisplay,
				journalContentPortletInstanceConfiguration,
				ddmStructureClassNameId, ddmTemplateModelResourcePermission);

			portletRequest.setAttribute(
				JournalContentWebKeys.JOURNAL_CONTENT_DISPLAY_CONTEXT,
				journalContentDisplayContext);
		}

		return journalContentDisplayContext;
	}

	public void clearCache() throws PortalException {
		if (Validator.isNotNull(getArticleId())) {
			JournalContent journalContent =
				(JournalContent)_portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			journalContent.clearCache(
				getArticleGroupId(), getArticleId(), getDDMTemplateKey());
		}
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = _getArticleByPreviewAssetEntryId();

		if ((_article != null) &&
			JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), _article,
				ActionKeys.UPDATE)) {

			return _article;
		}

		long articleResourcePrimKey = ParamUtil.getLong(
			_portletRequest, "articleResourcePrimKey");

		if (articleResourcePrimKey == -1) {
			return _article;
		}

		if (articleResourcePrimKey == 0) {
			if (Validator.isBlank(getArticleId())) {
				return null;
			}

			JournalArticleResource articleResource =
				JournalArticleResourceLocalServiceUtil.fetchArticleResource(
					getArticleGroupId(), getArticleId());

			if (articleResource != null) {
				articleResourcePrimKey = articleResource.getResourcePrimKey();
			}
		}

		_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
			articleResourcePrimKey, WorkflowConstants.STATUS_ANY, true);

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() throws PortalException {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		String viewMode = ParamUtil.getString(
			_portletRequest, "viewMode", null);
		String languageId = ParamUtil.getString(
			_portletRequest, "languageId", _themeDisplay.getLanguageId());
		int page = ParamUtil.getInteger(_portletRequest, "page", 1);

		if (article.isApproved()) {
			JournalContent journalContent =
				(JournalContent)_portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			if (journalContent == null) {
				return null;
			}

			_articleDisplay = journalContent.getDisplay(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), getDDMTemplateKey(), viewMode, languageId,
				page,
				new PortletRequestModel(_portletRequest, _portletResponse),
				_themeDisplay);
		}
		else {
			try {
				_articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						article, getDDMTemplateKey(), viewMode, languageId,
						page,
						new PortletRequestModel(
							_portletRequest, _portletResponse),
						_themeDisplay);
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
			_articleGroupId = _themeDisplay.getScopeGroupId();
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

	public long getAssetEntryId() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return 0;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(article, 0);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), assetRenderer.getClassPK());

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

		return assetRendererFactory.getAssetRenderer(article, 0);
	}

	public List<ContentMetadataAssetAddonEntry>
		getCommentsContentMetadataAssetAddonEntries() {

		List commentsContentMetadataAssetAddonEntries = new ArrayList();

		ContentMetadataAssetAddonEntry
			enableCommentsContentMetadataAssetAddonEntry =
				getContentMetadataAssetAddonEntry("enableComments");

		if (enableCommentsContentMetadataAssetAddonEntry != null) {
			commentsContentMetadataAssetAddonEntries.add(
				enableCommentsContentMetadataAssetAddonEntry);
		}

		ContentMetadataAssetAddonEntry
			enableCommentRatingsContentMetadataAssetAddonEntry =
				getContentMetadataAssetAddonEntry("enableCommentRatings");

		if (enableCommentRatingsContentMetadataAssetAddonEntry != null) {
			commentsContentMetadataAssetAddonEntries.add(
				enableCommentRatingsContentMetadataAssetAddonEntry);
		}

		return commentsContentMetadataAssetAddonEntries;
	}

	public ContentMetadataAssetAddonEntry getContentMetadataAssetAddonEntry(
		String key) {

		String contentMetadataAssetAddonEntryKeysString =
			_journalContentPortletInstanceConfiguration.
				contentMetadataAssetAddonEntryKeys();

		if (Validator.isNull(contentMetadataAssetAddonEntryKeysString)) {
			return null;
		}

		String[] contentMetadataAssetAddonEntryKeys = StringUtil.split(
			contentMetadataAssetAddonEntryKeysString);

		if (ArrayUtil.contains(contentMetadataAssetAddonEntryKeys, key)) {
			ContentMetadataAssetAddonEntry contentMetadataAssetAddonEntry =
				_contentMetadataAssetAddonEntryMap.getService(key);

			if ((contentMetadataAssetAddonEntry != null) &&
				contentMetadataAssetAddonEntry.isEnabled()) {

				return contentMetadataAssetAddonEntry;
			}
		}

		return null;
	}

	public DDMStructure getDDMStructure() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		return article.getDDMStructure();
	}

	public DDMTemplate getDDMTemplate() throws PortalException {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		_ddmTemplate = _getDDMTemplate(getDDMTemplateKey());

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() throws PortalException {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey",
			_journalContentPortletInstanceConfiguration.ddmTemplateKey());

		if (Validator.isNotNull(ddmTemplateKey)) {
			_ddmTemplateKey = ddmTemplateKey;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return _ddmTemplateKey;
		}

		if (Validator.isNull(_ddmTemplateKey) ||
			_ddmTemplateKey.equals(article.getDDMTemplateKey())) {

			_ddmTemplateKey = article.getDDMTemplateKey();

			return _ddmTemplateKey;
		}

		List<DDMTemplate> ddmTemplates = getDDMTemplates();

		Stream<DDMTemplate> stream = ddmTemplates.stream();

		boolean hasTemplate = stream.anyMatch(
			template -> _ddmTemplateKey.equals(template.getTemplateKey()));

		if (!hasTemplate) {
			_ddmTemplateKey = article.getDDMTemplateKey();
		}

		return _ddmTemplateKey;
	}

	public List<DDMTemplate> getDDMTemplates() throws PortalException {
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
				"Unable to get DDM temmplate for article " + article.getId(),
				pe);
		}

		return _ddmTemplates;
	}

	public DDMTemplate getDefaultDDMTemplate() throws PortalException {
		if (_defaultDDMTemplate != null) {
			return _defaultDDMTemplate;
		}

		JournalArticle article = getArticle();

		_defaultDDMTemplate = _getDDMTemplate(article.getDDMTemplateKey());

		return _defaultDDMTemplate;
	}

	public List<ContentMetadataAssetAddonEntry>
		getEnabledContentMetadataAssetAddonEntries() {

		List<ContentMetadataAssetAddonEntry> contentMetadataAssetAddonEntries =
			ListUtil.filter(
				new ArrayList<>(_contentMetadataAssetAddonEntryMap.values()),
				ContentMetadataAssetAddonEntry::isEnabled);

		return ListUtil.sort(
			contentMetadataAssetAddonEntries, _assetAddonEntryComparator);
	}

	public List<UserToolAssetAddonEntry> getEnabledUserToolAssetAddonEntries() {
		List<UserToolAssetAddonEntry> userToolAssetAddonEntries =
			ListUtil.filter(
				new ArrayList<>(_userToolAssetAddonEntryMap.values()),
				UserToolAssetAddonEntry::isEnabled);

		return ListUtil.sort(
			userToolAssetAddonEntries, _assetAddonEntryComparator);
	}

	public long getGroupId() {
		long groupId = _themeDisplay.getScopeGroupId();

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() &&
			!scopeGroup.isInStagingPortlet(JournalPortletKeys.JOURNAL)) {

			groupId = scopeGroup.getLiveGroupId();
		}

		return groupId;
	}

	public JournalArticle getLatestArticle() throws PortalException {
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

	public JournalArticle getSelectedArticle() {
		PortletPreferences portletPreferences =
			_portletRequest.getPreferences();

		long assetEntryId = GetterUtil.getLong(
			portletPreferences.getValue("assetEntryId", StringPool.BLANK));

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchAssetEntry(
			assetEntryId);

		if (assetEntry == null) {
			return null;
		}

		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			assetEntry.getClassPK());
	}

	public List<ContentMetadataAssetAddonEntry>
			getSelectedContentMetadataAssetAddonEntries()
		throws PortalException {

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
				_contentMetadataAssetAddonEntryMap.getService(
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

	public long[] getSelectedGroupIds() {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup() &&
			!scopeGroup.isInStagingPortlet(JournalPortletKeys.JOURNAL)) {

			return new long[] {scopeGroup.getLiveGroupId()};
		}

		if (_themeDisplay.getScopeGroupId() != _themeDisplay.getSiteGroupId()) {
			return new long[] {_themeDisplay.getScopeGroupId()};
		}

		return null;
	}

	public List<UserToolAssetAddonEntry> getSelectedUserToolAssetAddonEntries()
		throws PortalException {

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
				_userToolAssetAddonEntryMap.getService(
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

			AssetRenderer<JournalArticle> latestArticleAssetRenderer =
				assetRendererFactory.getAssetRenderer(
					getArticle(), AssetRendererFactory.TYPE_LATEST_APPROVED);

			PortletURL portletURL = latestArticleAssetRenderer.getURLEdit(
				PortalUtil.getLiferayPortletRequest(_portletRequest), null,
				LiferayWindowState.NORMAL, _themeDisplay.getURLCurrent());

			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			portletURL.setParameter(
				"portletResource", portletDisplay.getPortletName());

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to get edit URL", e);

			return StringPool.BLANK;
		}
	}

	public String getURLEditTemplate() throws Exception {
		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_portletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_ddm_template.jsp");
		portletURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		portletURL.setParameter(
			"ddmTemplateId", String.valueOf(ddmTemplate.getTemplateId()));
		portletURL.setPortletMode(PortletMode.VIEW);

		return portletURL.toString();
	}

	public String getURLViewHistory() {
		try {
			JournalArticle article = getArticle();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				_portletRequest, JournalPortletKeys.JOURNAL,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/view_article_history.jsp");

			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			portletURL.setParameter(
				"referringPortletResource", portletDisplay.getId());

			portletURL.setParameter(
				"groupId", String.valueOf(article.getGroupId()));
			portletURL.setParameter("articleId", article.getArticleId());
			portletURL.setParameter("showHeader", Boolean.TRUE.toString());
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to get view history URL", e);

			return StringPool.BLANK;
		}
	}

	public boolean hasRestorePermission() throws PortalException {
		JournalArticle selectedArticle = getSelectedArticle();

		if ((selectedArticle == null) || !selectedArticle.isInTrash()) {
			return false;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		TrashEntry trashEntry = selectedArticle.getTrashEntry();

		return trashHandler.hasTrashPermission(
			_themeDisplay.getPermissionChecker(), 0, trashEntry.getClassPK(),
			TrashActionKeys.RESTORE);
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		JournalArticle article = getArticle();

		if (article != null) {
			_hasViewPermission = JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article, ActionKeys.VIEW);
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

	public boolean isDefaultTemplate() {
		String ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey");

		if (Validator.isNotNull(ddmTemplateKey)) {
			return false;
		}

		ddmTemplateKey =
			_journalContentPortletInstanceConfiguration.ddmTemplateKey();

		if (Validator.isNotNull(ddmTemplateKey)) {
			return false;
		}

		return true;
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

	public boolean isExpired() throws PortalException {
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

	public boolean isPreview() {
		if (_preview != null) {
			return _preview;
		}

		JournalArticle article = _getArticleByPreviewAssetEntryId();

		if (article == null) {
			_preview = false;

			return _preview;
		}

		_preview = true;

		return _preview;
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

		if (!hasViewPermission()) {
			_showArticle = false;

			return _showArticle;
		}

		if (isExpired()) {
			_showArticle = false;

			return _showArticle;
		}

		if (article.isScheduled() && !isPreview()) {
			_showArticle = false;

			return _showArticle;
		}

		if (article.isPending() && !isPreview()) {
			_showArticle = false;

			return _showArticle;
		}

		_showArticle = true;

		return _showArticle;
	}

	public boolean isShowEditArticleIcon() throws PortalException {
		if (_showEditArticleIcon != null) {
			return _showEditArticleIcon;
		}

		_showEditArticleIcon = false;

		Group group = _themeDisplay.getScopeGroup();

		if (group.hasStagingGroup() && _STAGING_LIVE_GROUP_LOCKING_ENABLED) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		_showEditArticleIcon = JournalArticlePermission.contains(
			_themeDisplay.getPermissionChecker(), latestArticle,
			ActionKeys.UPDATE);

		return _showEditArticleIcon;
	}

	public boolean isShowEditTemplateIcon() throws PortalException {
		if (_showEditTemplateIcon != null) {
			return _showEditTemplateIcon;
		}

		_showEditTemplateIcon = false;

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return _showEditTemplateIcon;
		}

		try {
			_showEditTemplateIcon =
				_ddmTemplateModelResourcePermission.contains(
					_themeDisplay.getPermissionChecker(), ddmTemplate,
					ActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to check permission on DDM template " +
					ddmTemplate.getTemplateId(),
				pe);
		}

		return _showEditTemplateIcon;
	}

	public boolean isShowSelectArticleLink() {
		if (_showSelectArticleLink != null) {
			return _showSelectArticleLink;
		}

		Layout layout = _themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			_showSelectArticleLink = false;

			return _showSelectArticleLink;
		}

		Group scopeGroup = _themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLocalLiveGroup(scopeGroup) ||
			stagingGroupHelper.isRemoteLiveGroup(scopeGroup)) {

			_showSelectArticleLink = false;

			return _showSelectArticleLink;
		}

		_showSelectArticleLink = true;

		return _showSelectArticleLink;
	}

	private JournalContentDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			ThemeDisplay themeDisplay,
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration,
			long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_themeDisplay = themeDisplay;
		_journalContentPortletInstanceConfiguration =
			journalContentPortletInstanceConfiguration;
		_ddmStructureClassNameId = ddmStructureClassNameId;
		_ddmTemplateModelResourcePermission =
			ddmTemplateModelResourcePermission;

		if (Validator.isNull(getPortletResource()) && !isShowArticle()) {
			portletRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	private JournalArticle _getArticleByPreviewAssetEntryId() {
		long previewClassNameId = ParamUtil.getLong(
			_portletRequest, "previewClassNameId");
		long previewClassPK = ParamUtil.getLong(
			_portletRequest, "previewClassPK");

		if ((previewClassNameId <= 0) || (previewClassPK <= 0)) {
			return null;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			previewClassNameId, previewClassPK);

		if (assetEntry == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetEntry.getAssetRendererFactory();

		if (assetRendererFactory == null) {
			return null;
		}

		int previewType = ParamUtil.getInteger(
			_portletRequest, "previewType",
			AssetRendererFactory.TYPE_LATEST_APPROVED);

		try {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK(), previewType);

			return (JournalArticle)assetRenderer.getAssetObject();
		}
		catch (Exception e) {
		}

		return null;
	}

	private DDMTemplate _getDDMTemplate(String ddmTemplateKey)
		throws PortalException {

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if (articleDisplay == null) {
			return null;
		}

		return DDMTemplateLocalServiceUtil.fetchTemplate(
			articleDisplay.getGroupId(), _ddmStructureClassNameId,
			ddmTemplateKey, true);
	}

	private static final boolean _STAGING_LIVE_GROUP_LOCKING_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.STAGING_LIVE_GROUP_LOCKING_ENABLED));

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

	private static final ServiceTrackerMap
		<String, ContentMetadataAssetAddonEntry>
			_contentMetadataAssetAddonEntryMap;
	private static final ServiceTrackerMap<String, UserToolAssetAddonEntry>
		_userToolAssetAddonEntryMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalContentDisplayContext.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_contentMetadataAssetAddonEntryMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ContentMetadataAssetAddonEntry.class, null,
				(serviceReference, emitter) -> {
					ContentMetadataAssetAddonEntry
						contentMetadataAssetAddonEntry =
							bundleContext.getService(serviceReference);

					emitter.emit(contentMetadataAssetAddonEntry.getKey());
				});

		_userToolAssetAddonEntryMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UserToolAssetAddonEntry.class, null,
				(serviceReference, emitter) -> {
					UserToolAssetAddonEntry userToolAssetAddonEntry =
						bundleContext.getService(serviceReference);

					emitter.emit(userToolAssetAddonEntry.getKey());
				});
	}

	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private Long _articleGroupId;
	private String _articleId;
	private List<ContentMetadataAssetAddonEntry>
		_contentMetadataAssetAddonEntries;
	private final long _ddmStructureClassNameId;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private final ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;
	private List<DDMTemplate> _ddmTemplates;
	private DDMTemplate _defaultDDMTemplate;
	private Boolean _enableViewCountIncrement;
	private Boolean _expired;
	private Boolean _hasViewPermission;
	private final JournalContentPortletInstanceConfiguration
		_journalContentPortletInstanceConfiguration;
	private JournalArticle _latestArticle;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private Boolean _preview;
	private Boolean _showArticle;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showSelectArticleLink;
	private final ThemeDisplay _themeDisplay;
	private List<UserToolAssetAddonEntry> _userToolAssetAddonEntries;

}