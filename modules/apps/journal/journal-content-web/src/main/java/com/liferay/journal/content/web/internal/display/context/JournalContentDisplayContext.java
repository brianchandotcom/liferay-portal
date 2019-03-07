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
import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionModel;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.asset.addon.entry.ContentMetadataAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.UserToolAssetAddonEntry;
import com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration;
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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
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
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public static JournalContentDisplayContext create(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortletDisplay portletDisplay, long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission)
		throws PortalException {

		JournalContentDisplayContext journalContentDisplayContext =
			(JournalContentDisplayContext)portletRequest.getAttribute(
				JournalContentWebKeys.JOURNAL_CONTENT_DISPLAY_CONTEXT);

		if (journalContentDisplayContext == null) {
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						JournalContentPortletInstanceConfiguration.class);

			journalContentDisplayContext = new JournalContentDisplayContext(
				portletDisplay.getPortletSetup(), portletRequest,
				portletResponse, journalContentPortletInstanceConfiguration,
				ddmStructureClassNameId, ddmTemplateModelResourcePermission);

			portletRequest.setAttribute(
				JournalContentWebKeys.JOURNAL_CONTENT_DISPLAY_CONTEXT,
				journalContentDisplayContext);
		}

		return journalContentDisplayContext;
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

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		long previewArticleId = ParamUtil.getLong(
			_portletRequest, "previewArticleId");

		if (previewArticleId > 0) {
			_article = JournalArticleLocalServiceUtil.fetchArticle(
				previewArticleId);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((_article != null) &&
			JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), _article,
				ActionKeys.UPDATE)) {

			return _article;
		}

		long articleResourcePrimKey = ParamUtil.getLong(
			_portletRequest, "articleResourcePrimKey");

		if (articleResourcePrimKey == -1) {
			return _article;
		}

		if (articleResourcePrimKey == 0) {
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String viewMode = ParamUtil.getString(
			_portletRequest, "viewMode", null);
		String languageId = ParamUtil.getString(
			_portletRequest, "languageId", themeDisplay.getLanguageId());
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
				themeDisplay);
		}
		else {
			try {
				_articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						article, getDDMTemplateKey(), viewMode, languageId,
						page,
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

		long companyId = GetterUtil.getLong(
			_portletRequest.getAttribute(WebKeys.COMPANY_ID));

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			_articleId = ParamUtil.getString(
				_portletRequest, "articleId",
				_journalContentPortletInstanceConfiguration.articleId());

			return _articleId;
		}

		long userId = GetterUtil.getLong(
			_portletRequest.getAttribute(WebKeys.USER_ID));

		Optional<CTCollection> activeCTCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(userId);

		String ctCollectionArticleIdKey = activeCTCollectionOptional.map(
			CTCollectionModel::getCtCollectionId
		).map(
			ctCollectionId -> "ctCollectionArticleId" + ctCollectionId
		).orElse(
			"articleId"
		);

		String preferencesArticleId = _portletPreferences.getValue(
			ctCollectionArticleIdKey,
			_journalContentPortletInstanceConfiguration.articleId());

		_articleId = ParamUtil.getString(
			_portletRequest, ctCollectionArticleIdKey, preferencesArticleId);

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

		long classPK = assetRenderer.getClassPK();

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

		return assetRendererFactory.getAssetRenderer(article, 0);
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

		_ddmTemplateKey =
			_journalContentPortletInstanceConfiguration.ddmTemplateKey();

		String ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey");

		if (Validator.isNotNull(ddmTemplateKey)) {
			_ddmTemplateKey = ddmTemplateKey;
		}

		JournalArticle article = getArticle();

		if (article == null) {
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
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (!scopeGroup.isStaged() ||
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
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup() &&
			!scopeGroup.isInStagingPortlet(JournalPortletKeys.JOURNAL)) {

			return new long[] {scopeGroup.getLiveGroupId()};
		}

		if (themeDisplay.getScopeGroupId() != themeDisplay.getSiteGroupId()) {
			return new long[] {themeDisplay.getScopeGroupId()};
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

			JournalArticle article = getArticle();

			AssetRenderer<JournalArticle> latestArticleAssetRenderer =
				assetRendererFactory.getAssetRenderer(
					article, AssetRendererFactory.TYPE_LATEST_APPROVED);

			LiferayPortletResponse liferayPortletResponse =
				PortalUtil.getLiferayPortletResponse(_portletResponse);

			PortletURL portletURL = latestArticleAssetRenderer.getURLEdit(
				PortalUtil.getLiferayPortletRequest(_portletRequest), null,
				LiferayWindowState.MAXIMIZED,
				liferayPortletResponse.createRenderURL());

			portletURL.setParameter(
				"hideDefaultSuccessMessage", Boolean.TRUE.toString());

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

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_portletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_ddm_template.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		portletURL.setParameter(
			"ddmTemplateId", String.valueOf(ddmTemplate.getTemplateId()));
		portletURL.setPortletMode(PortletMode.VIEW);

		return portletURL.toString();
	}

	public boolean hasRestorePermission() throws PortalException {
		JournalArticle selectedArticle = getSelectedArticle();

		if ((selectedArticle == null) || !selectedArticle.isInTrash()) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		TrashEntry trashEntry = selectedArticle.getTrashEntry();

		return trashHandler.hasTrashPermission(
			themeDisplay.getPermissionChecker(), 0, trashEntry.getClassPK(),
			TrashActionKeys.RESTORE);
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
				themeDisplay.getPermissionChecker(), article, ActionKeys.VIEW);
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

		long previewArticleId = ParamUtil.getLong(
			_portletRequest, "previewArticleId");

		if (previewArticleId <= 0) {
			_preview = false;

			return _preview;
		}

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			previewArticleId);

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

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.hasStagingGroup() && _STAGING_LIVE_GROUP_LOCKING_ENABLED) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		_showEditArticleIcon = JournalArticlePermission.contains(
			themeDisplay.getPermissionChecker(), latestArticle,
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_showEditTemplateIcon =
				_ddmTemplateModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), ddmTemplate,
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			_showSelectArticleLink = false;

			return _showSelectArticleLink;
		}

		Group scopeGroup = themeDisplay.getScopeGroup();

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
			PortletPreferences portletPreferences,
			PortletRequest portletRequest, PortletResponse portletResponse,
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration,
			long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission)
		throws PortalException {

		_portletPreferences = portletPreferences;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
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
	private static CTEngineManager _ctEngineManager;
	private static CTManager _ctManager;
	private static final ServiceTrackerMap<String, UserToolAssetAddonEntry>
		_userToolAssetAddonEntryMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalContentDisplayContext.class);

		ServiceTracker<CTEngineManager, CTEngineManager>
			ctEngineManagerServiceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(), CTEngineManager.class, null);

		ctEngineManagerServiceTracker.open();

		_ctEngineManager = ctEngineManagerServiceTracker.getService();

		ServiceTracker<CTManager, CTManager>
			ctManagerServiceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(), CTManager.class, null);

		ctManagerServiceTracker.open();

		_ctManager = ctManagerServiceTracker.getService();

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
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private Boolean _preview;
	private Boolean _showArticle;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showSelectArticleLink;
	private List<UserToolAssetAddonEntry> _userToolAssetAddonEntries;

}