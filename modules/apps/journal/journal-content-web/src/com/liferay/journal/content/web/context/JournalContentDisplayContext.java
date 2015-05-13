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

package com.liferay.journal.content.web.context;

import com.liferay.journal.content.web.util.ContentMetadataAssetAddonEntry;
import com.liferay.journal.content.web.util.ContentMetadataAssetAddonEntryTracker;
import com.liferay.journal.content.web.util.UserToolAssetAddonEntry;
import com.liferay.journal.content.web.util.UserToolAssetAddonEntryTracker;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.util.PropertyComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public JournalContentDisplayContext(
			HttpServletRequest request, PortletPreferences portletPreferences)
		throws PortalException {

		_request = request;
		_portletPreferences = portletPreferences;

		String portletId = PortalUtil.getPortletId(request);

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			return;
		}

		JournalArticle article = getArticle();
		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((article == null) || !hasViewPermission() ||
			(articleDisplay == null) || isExpired()) {

			request.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public JournalArticle getArticle() {
		if (_article != null) {
			return _article;
		}

		_article = (JournalArticle)_request.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		if (_article != null) {
			return _article;
		}

		_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
			getArticleGroupId(), getArticleId(), WorkflowConstants.STATUS_ANY);

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		_articleDisplay = (JournalArticleDisplay)_request.getAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY);

		return _articleDisplay;
	}

	public long getArticleGroupId() {
		if (_articleGroupId != null) {
			return _articleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_articleGroupId = PrefsParamUtil.getLong(
			_portletPreferences, _request, "groupId",
			themeDisplay.getScopeGroupId());

		return _articleGroupId;
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = PrefsParamUtil.getString(
			_portletPreferences, _request, "articleId");

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

	public DDMTemplate getDDMTemplate() throws PortalException {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((articleDisplay == null) ||
			Validator.isNull(articleDisplay.getDDMTemplateKey())) {

			return null;
		}

		_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			articleDisplay.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class),
			articleDisplay.getDDMTemplateKey(), true);

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() throws PortalException {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = PrefsParamUtil.getString(
			_portletPreferences, _request, "ddmTemplateKey");

		if (getDDMTemplates().isEmpty()) {
			return _ddmTemplateKey;
		}

		if (Validator.isNull(_ddmTemplateKey)) {
			JournalArticle article = getArticle();

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

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		_ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(
			article.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(), true);

		return _ddmTemplates;
	}

	public List<ContentMetadataAssetAddonEntry>
	getEnabledContentMetadataAssetAddonEntries() {

		List<ContentMetadataAssetAddonEntry> contentMetadataEntries =
			ListUtil.filter(
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntries(),

				new PredicateFilter<ContentMetadataAssetAddonEntry>() {

					@Override
					public boolean filter(
						ContentMetadataAssetAddonEntry contentMetadataEntry) {
							return contentMetadataEntry.isEnabled();
					}

				});

		return ListUtil.sort(
			contentMetadataEntries,
			new PropertyComparator("weight", true, false));
	}

	public List<UserToolAssetAddonEntry> getEnabledUserToolAssetAddonEntries() {
		List<UserToolAssetAddonEntry> userToolEntries = ListUtil.filter(
			UserToolAssetAddonEntryTracker.getUserToolAddonEntries(),
			new PredicateFilter<UserToolAssetAddonEntry>() {

				@Override
				public boolean filter(UserToolAssetAddonEntry userToolEntry) {
					return userToolEntry.isEnabled();
				}

			});

		return ListUtil.sort(
			userToolEntries, new PropertyComparator("weight", true, false));
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

		_portletResource = ParamUtil.getString(_request, "portletResource");

		return _portletResource;
	}

	public List<ContentMetadataAssetAddonEntry>
	getSelectedContentMetadataAssetAddonEntries() {

		if (_contentMetadataAssetAddonEntries != null) {
			return _contentMetadataAssetAddonEntries;
		}

		_contentMetadataAssetAddonEntries = new ArrayList<>();

		String contentMetadataAssetAddonEntriesPref =
			_portletPreferences.getValue(
				"contentMetadataAssetAddonEntries", null);

		if (Validator.isNull(contentMetadataAssetAddonEntriesPref)) {
			return _contentMetadataAssetAddonEntries;
		}

		String[] contentMetadataAssetAddonEntryKeys = StringUtil.split(
			contentMetadataAssetAddonEntriesPref);

		for (String contentMetadataEntryKey :
				contentMetadataAssetAddonEntryKeys) {

			ContentMetadataAssetAddonEntry contentMetadataEntry =
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntry(contentMetadataEntryKey);

			if (contentMetadataEntry != null) {
				_contentMetadataAssetAddonEntries.add(contentMetadataEntry);
			}
		}

		_request.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_request.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _contentMetadataAssetAddonEntries;
	}

	public List<UserToolAssetAddonEntry>
	getSelectedUserToolAssetAddonEntries() {

		if (_userToolAssetAddonEntries != null) {
			return _userToolAssetAddonEntries;
		}

		_userToolAssetAddonEntries = new ArrayList<>();

		String userToolAssetAddonEntriesPref = _portletPreferences.getValue(
			"userToolAssetAddonEntries", null);

		if (Validator.isNull(userToolAssetAddonEntriesPref)) {
			return _userToolAssetAddonEntries;
		}

		String[] userToolEntryKeys = StringUtil.split(
			userToolAssetAddonEntriesPref);

		for (String userToolEntryKey : userToolEntryKeys) {
			UserToolAssetAddonEntry userToolEntry =
				UserToolAssetAddonEntryTracker.getUserToolAddonEntry(
					userToolEntryKey);

			if (userToolEntry != null) {
				_userToolAssetAddonEntries.add(userToolEntry);
			}
		}

		_request.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_request.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _userToolAssetAddonEntries;
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		JournalArticle article = getArticle();

		if (article != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

		_enableViewCountIncrement = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableViewCountIncrement", null),
			PropsValues.ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED);

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

		String viewMode = ParamUtil.getString(_request, "viewMode");

		if (viewMode.equals(Constants.PRINT)) {
			_print = true;
		}

		return _print;
	}

	public boolean isShowAddArticleIcon() throws PortalException {
		if (_showAddArticleIcon != null) {
			return _showAddArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

	public boolean isShowEditArticleIcon() throws PortalException {
		if (_showEditArticleIcon != null) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		_showEditArticleIcon = false;

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_showEditArticleIcon = JournalArticlePermission.contains(
			themeDisplay.getPermissionChecker(), latestArticle.getGroupId(),
			latestArticle.getArticleId(), ActionKeys.UPDATE);

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

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_showEditTemplateIcon = DDMTemplatePermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ddmTemplate, portletDisplay.getId(), ActionKeys.UPDATE);

		return _showEditTemplateIcon;
	}

	public boolean isShowIconsActions() throws PortalException {
		if (_showIconsActions != null) {
			return _showIconsActions;
		}

		_showIconsActions = false;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn() || isPrint() || !hasViewPermission()) {
			return _showIconsActions;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			return _showIconsActions;
		}

		Group group = themeDisplay.getSiteGroup();

		if (group.hasLocalOrRemoteStagingGroup()) {
			return _showIconsActions;
		}

		if (isShowAddArticleIcon() || isShowEditArticleIcon() ||
			isShowEditTemplateIcon() || isShowSelectArticleIcon()) {

			_showIconsActions = true;
		}

		return _showIconsActions;
	}

	public boolean isShowSelectArticleIcon() throws PortalException {
		if (_showSelectArticleIcon != null) {
			return _showSelectArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_showSelectArticleIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);

		return _showSelectArticleIcon;
	}

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
	private JournalArticle _latestArticle;
	private final PortletPreferences _portletPreferences;
	private String _portletResource;
	private Boolean _print;
	private final HttpServletRequest _request;
	private Boolean _showAddArticleIcon;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showIconsActions;
	private Boolean _showSelectArticleIcon;
	private List<UserToolAssetAddonEntry> _userToolAssetAddonEntries;

}