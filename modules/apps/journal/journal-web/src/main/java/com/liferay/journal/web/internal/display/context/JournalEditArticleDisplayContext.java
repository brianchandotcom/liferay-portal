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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.util.JournalUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditArticleDisplayContext {

	public JournalEditArticleDisplayContext(
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse, JournalArticle article) {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;
		_article = article;

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = BeanParamUtil.getString(_article, _request, "articleId");

		return _articleId;
	}

	public Set<Locale> getAvailableLocales() {
		if (_availableLocales != null) {
			return _availableLocales;
		}

		_availableLocales = LanguageUtil.getAvailableLocales(getGroupId());

		return _availableLocales;
	}

	public Map<String, Object> getChangeDefaultLanguageSoyContext() {
		Map<String, Object> context = new HashMap<>();

		context.put("defaultLanguage", getDefaultLanguageId());

		LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<>();

		uniqueLanguageIds.add(getDefaultLanguageId());

		Map<String, Object> strings = new HashMap<>();

		strings.put(
			"change", LanguageUtil.format(_request, "change", "content"));
		strings.put(
			"default", LanguageUtil.format(_request, "default", "content"));

		for (Locale availableLocale : getAvailableLocales()) {
			String curLanguageId = LocaleUtil.toLanguageId(availableLocale);

			strings.put(
				curLanguageId,
				LanguageUtil.format(
					_request, "default-language-x",
					availableLocale.getDisplayName(
						LocaleUtil.fromLanguageId(getDefaultLanguageId())),
					false));

			uniqueLanguageIds.add(curLanguageId);
		}

		List<Map<String, Object>> languages = new ArrayList<>();

		for (String curLanguageId : uniqueLanguageIds) {
			Map<String, Object> language = new HashMap<>();

			language.put(
				"checked",
				Objects.equals(getDefaultLanguageId(), curLanguageId));
			language.put(
				"icon",
				StringUtil.toLowerCase(
					StringUtil.replace(curLanguageId, '_', '-')));
			language.put("label", curLanguageId);

			languages.add(language);
		}

		context.put("languages", languages);
		context.put("namespace", _liferayPortletResponse.getNamespace());
		context.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		context.put("strings", strings);

		return context;
	}

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = BeanParamUtil.getLong(_article, _request, "classNameId");

		return _classNameId;
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = BeanParamUtil.getLong(_article, _request, "classPK");

		return _classPK;
	}

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		if (_ddmFormValues != null) {
			return _ddmFormValues;
		}

		if (_article == null) {
			return _ddmFormValues;
		}

		String content = _article.getContent();

		if (Validator.isNull(content)) {
			return _ddmFormValues;
		}

		JournalConverter journalConverter = _getJournalConverter();

		Fields fields = journalConverter.getDDMFields(ddmStructure, content);

		if (fields == null) {
			return _ddmFormValues;
		}

		_ddmFormValues = journalConverter.getDDMFormValues(
			ddmStructure, fields);

		return _ddmFormValues;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		long ddmStructureId = ParamUtil.getLong(_request, "ddmStructureId");

		if (ddmStructureId > 0) {
			_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				ddmStructureId);
		}
		else if (Validator.isNotNull(getDDMStructureKey())) {
			long groupId = ParamUtil.getLong(
				_request, "groupId", _themeDisplay.getSiteGroupId());

			if (_article != null) {
				groupId = _article.getGroupId();
			}

			_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				groupId, PortalUtil.getClassNameId(JournalArticle.class),
				getDDMStructureKey(), true);
		}

		return _ddmStructure;
	}

	public long getDDMStructureId() {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getStructureId();
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(_request, "ddmStructureKey");

		if (Validator.isNull(_ddmStructureKey) && (_article != null)) {
			_ddmStructureKey = _article.getDDMStructureKey();
		}

		return _ddmStructureKey;
	}

	public DDMTemplate getDDMTemplate() {
		long ddmTemplateId = ParamUtil.getLong(_request, "ddmTemplateId");

		if (ddmTemplateId > 0) {
			_ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
				ddmTemplateId);
		}
		else if (Validator.isNotNull(getDDMTemplateKey())) {
			long groupId = ParamUtil.getLong(
				_request, "groupId", _themeDisplay.getSiteGroupId());

			if (_article != null) {
				groupId = _article.getGroupId();
			}

			_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				groupId, PortalUtil.getClassNameId(DDMStructure.class),
				getDDMTemplateKey(), true);
		}

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_request, "ddmTemplateKey");

		if (Validator.isNull(_ddmTemplateKey) && (_article != null) &&
			Objects.equals(
				_article.getDDMStructureKey(), getDDMStructureKey())) {

			_ddmTemplateKey = _article.getDDMTemplateKey();
		}

		return _ddmTemplateKey;
	}

	public String getDefaultLanguageId() {
		Locale siteDefaultLocale = null;

		try {
			siteDefaultLocale = PortalUtil.getSiteDefaultLocale(getGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			siteDefaultLocale = LocaleUtil.getSiteDefault();
		}

		if (_article == null) {
			return LocaleUtil.toLanguageId(siteDefaultLocale);
		}

		return LocalizationUtil.getDefaultLanguageId(
			_article.getContent(), siteDefaultLocale);
	}

	public String getEditArticleURL() {
		PortletURL editArticleURL = _liferayPortletResponse.createRenderURL();

		editArticleURL.setParameter("redirect", getRedirect());
		editArticleURL.setParameter("mvcPath", "/edit_article.jsp");
		editArticleURL.setParameter("groupId", String.valueOf(getGroupId()));
		editArticleURL.setParameter("articleId", getArticleId());
		editArticleURL.setParameter("version", String.valueOf(getVersion()));

		return editArticleURL.toString();
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			_article, _request, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public String getFriendlyURLBase() {
		StringBundler sb = new StringBundler(4);

		sb.append(_themeDisplay.getPortalURL());

		Group group = _themeDisplay.getScopeGroup();

		boolean privateLayout = false;

		if (_article != null) {
			Layout layout = _article.getLayout();

			if (layout != null) {
				privateLayout = layout.isPrivateLayout();
			}
		}

		sb.append(group.getPathFriendlyURL(privateLayout, _themeDisplay));

		sb.append(group.getFriendlyURL());

		sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);

		return sb.toString();
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = BeanParamUtil.getLong(
			_article, _request, "groupId", _themeDisplay.getScopeGroupId());

		return _groupId;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(_request, "portletResource");

		return _portletResource;
	}

	public String getPreviewContentURL() throws Exception {
		PortletURL previewContentURL =
			_liferayPortletResponse.createLiferayPortletURL(
				JournalUtil.getPreviewPlid(_article, _themeDisplay),
				JournalPortletKeys.JOURNAL, PortletRequest.RENDER_PHASE);

		previewContentURL.setParameter(
			"mvcPath", "/preview_article_content.jsp");

		if (_article == null) {
			return previewContentURL.toString();
		}

		previewContentURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		previewContentURL.setParameter(
			"articleId", String.valueOf(_article.getVersion()));

		String ddmTemplateKey = _article.getDDMTemplateKey();

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			ddmTemplateKey = ddmTemplate.getTemplateKey();
		}

		previewContentURL.setParameter("ddmTemplateKey", ddmTemplateKey);

		return previewContentURL.toString();
	}

	public String getPublishButtonLabel() throws PortalException {
		if (getClassNameId() > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			return "save";
		}

		if (_isWorkflowEnabled()) {
			return "submit-for-publication";
		}

		return "publish";
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		return _redirect;
	}

	public long getRefererPlid() {
		if (_refererPlid != null) {
			return _refererPlid;
		}

		_refererPlid = ParamUtil.getLong(_request, "refererPlid");

		return _refererPlid;
	}

	public String getReferringPortletResource() {
		if (_referringPortletResource != null) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_request, "referringPortletResource");

		return _referringPortletResource;
	}

	public String getSaveButtonLabel() {
		if ((_article == null) || _article.isApproved() || _article.isDraft() ||
			_article.isExpired() || _article.isScheduled()) {

			return "save-as-draft";
		}

		return "save";
	}

	public String getSmallImageSource() {
		if (_article == null) {
			_smallImageSource = "none";

			return _smallImageSource;
		}

		_smallImageSource = ParamUtil.getString(_request, "smallImageSource");

		if (Validator.isNotNull(_smallImageSource)) {
			return _smallImageSource;
		}

		if (!_article.getSmallImage()) {
			_smallImageSource = "none";
		}
		else if (Validator.isNotNull(_article.getSmallImageURL())) {
			_smallImageSource = "url";
		}
		else if ((_article.getSmallImageId() > 0) &&
				 Validator.isNull(_article.getSmallImageURL())) {

			_smallImageSource = "file";
		}

		return _smallImageSource;
	}

	public double getVersion() {
		if (_version != null) {
			return _version;
		}

		_version = BeanParamUtil.getDouble(
			_article, _request, "version",
			JournalArticleConstants.VERSION_DEFAULT);

		return _version;
	}

	public boolean hasSavePermission() throws PortalException {
		if ((_article != null) && !_article.isNew()) {
			return JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), _article,
				ActionKeys.UPDATE);
		}

		return JournalFolderPermission.contains(
			_themeDisplay.getPermissionChecker(), getGroupId(), getFolderId(),
			ActionKeys.ADD_ARTICLE);
	}

	public boolean isApproved() {
		if ((_article != null) && (getVersion() > 0)) {
			return _article.isApproved();
		}

		return false;
	}

	public boolean isChangeStructure() {
		if (_changeStructure != null) {
			return _changeStructure;
		}

		_changeStructure = GetterUtil.getBoolean(
			ParamUtil.getString(_request, "changeStructure"));

		return _changeStructure;
	}

	public boolean isNeverExpire() {
		if (_neverExpire != null) {
			return _neverExpire;
		}

		_neverExpire = ParamUtil.getBoolean(_request, "neverExpire", true);

		if ((_article != null) && (_article.getExpirationDate() != null)) {
			_neverExpire = false;
		}

		return _neverExpire;
	}

	public boolean isNeverReview() {
		if (_neverReview != null) {
			return _neverReview;
		}

		_neverReview = ParamUtil.getBoolean(_request, "neverReview", true);

		if ((_article != null) && (_article.getReviewDate() != null)) {
			_neverReview = false;
		}

		return _neverReview;
	}

	public boolean isPending() throws PortalException {
		if ((_article != null) && (getVersion() > 0) && _isWorkflowEnabled()) {
			return _article.isPending();
		}

		return false;
	}

	private long _getInheritedWorkflowDDMStructuresFolderId()
		throws PortalException {

		if (_inheritedWorkflowDDMStructuresFolderId != null) {
			return _inheritedWorkflowDDMStructuresFolderId;
		}

		_inheritedWorkflowDDMStructuresFolderId =
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				getFolderId());

		return _inheritedWorkflowDDMStructuresFolderId;
	}

	private JournalConverter _getJournalConverter() {
		return (JournalConverter)_request.getAttribute(
			JournalWebKeys.JOURNAL_CONVERTER);
	}

	private String _getTitle() {
		if (getClassNameId() > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			return LanguageUtil.get(_request, "structure-default-values");
		}
		else if ((_article != null) && !_article.isNew()) {
			return _article.getTitle(_themeDisplay.getLocale());
		}

		return LanguageUtil.get(_request, "new-web-content");
	}

	private boolean _hasInheritedWorkflowDefinitionLink()
		throws PortalException {

		if (_getInheritedWorkflowDDMStructuresFolderId() <= 0) {
			return WorkflowDefinitionLinkLocalServiceUtil.
				hasWorkflowDefinitionLink(
					_themeDisplay.getCompanyId(), getGroupId(),
					JournalArticle.class.getName());
		}

		JournalFolder inheritedWorkflowDDMStructuresFolder =
			JournalFolderLocalServiceUtil.getFolder(
				_getInheritedWorkflowDDMStructuresFolderId());

		if (inheritedWorkflowDDMStructuresFolder.getRestrictionType() ==
				JournalFolderConstants.RESTRICTION_TYPE_INHERIT) {

			return true;
		}

		return false;
	}

	private boolean _isShowHeader() {
		if (_showHeader != null) {
			return _showHeader;
		}

		_showHeader = ParamUtil.getBoolean(_request, "showHeader", true);

		return _showHeader;
	}

	private boolean _isWorkflowEnabled() throws PortalException {
		if (_hasInheritedWorkflowDefinitionLink()) {
			return true;
		}

		DDMStructure ddmStructure = getDDMStructure();

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(), getFolderId(),
				ddmStructure.getStructureId())) {

			return true;
		}

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(),
				_getInheritedWorkflowDDMStructuresFolderId(),
				ddmStructure.getStructureId())) {

			return true;
		}

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(),
				_getInheritedWorkflowDDMStructuresFolderId(),
				JournalArticleConstants.DDM_STRUCTURE_ID_ALL)) {

			return true;
		}

		return false;
	}

	private void _setViewAttributes() {
		if (!_isShowHeader()) {
			return;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		if (Validator.isNotNull(getRedirect())) {
			portletDisplay.setURLBack(getRedirect());
		}
		else if ((getClassNameId() ==
					JournalArticleConstants.CLASSNAME_ID_DEFAULT) &&
				 (_article != null)) {

			PortletURL backURL = _liferayPortletResponse.createRenderURL();

			backURL.setParameter(
				"groupId", String.valueOf(_article.getGroupId()));
			backURL.setParameter(
				"folderId", String.valueOf(_article.getFolderId()));

			portletDisplay.setURLBack(backURL.toString());
		}

		if (_liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)_liferayPortletResponse;

			renderResponse.setTitle(_getTitle());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditArticleDisplayContext.class);

	private JournalArticle _article;
	private String _articleId;
	private Set<Locale> _availableLocales;
	private Boolean _changeStructure;
	private Long _classNameId;
	private Long _classPK;
	private DDMFormValues _ddmFormValues;
	private DDMStructure _ddmStructure;
	private String _ddmStructureKey;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private Long _folderId;
	private Long _groupId;
	private Long _inheritedWorkflowDDMStructuresFolderId;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Boolean _neverExpire;
	private Boolean _neverReview;
	private String _portletResource;
	private String _redirect;
	private Long _refererPlid;
	private String _referringPortletResource;
	private final HttpServletRequest _request;
	private Boolean _showHeader;
	private String _smallImageSource;
	private final ThemeDisplay _themeDisplay;
	private Double _version;

}