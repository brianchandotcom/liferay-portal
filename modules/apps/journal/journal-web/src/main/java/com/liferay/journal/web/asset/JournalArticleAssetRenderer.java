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

package com.liferay.journal.web.asset;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 * @author Raymond Augé
 */
public class JournalArticleAssetRenderer
	extends BaseJSPAssetRenderer<JournalArticle> implements TrashRenderer {

	public static final String TYPE = "journal_article";

	public static long getClassPK(JournalArticle article) {
		if ((article.isDraft() || article.isPending()) &&
			(article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {

			return article.getPrimaryKey();
		}

		return article.getResourcePrimKey();
	}

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public JournalArticle getArticle() {
		return _article;
	}

	@Override
	public JournalArticle getAssetObject() {
		return _article;
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _article.getAvailableLanguageIds();
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public long getClassPK() {
		return getClassPK(_article);
	}

	@Override
	public DDMFormValuesReader getDDMFormValuesReader() {
		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(_article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		return journalArticleDDMFormValuesReader;
	}

	@Override
	public String getDiscussionPath() {
		if (_journalServiceConfiguration == null) {
			try {
				_journalServiceConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						JournalServiceConfiguration.class,
						_article.getCompanyId());
			}
			catch (Exception e) {
				_log.error(e, e);

				return null;
			}
		}

		if (_journalServiceConfiguration.articleCommentsEnabled()) {
			return "edit_article_discussion";
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Date getDisplayDate() {
		return _article.getDisplayDate();
	}

	@Override
	public long getGroupId() {
		return _article.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (_article.isInTrash() && template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/trash/" + template + ".jsp";
		}

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Override
	public int getStatus() {
		return _article.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Locale locale = getLocale(portletRequest);

		String summary = _article.getDescription(locale);

		if (Validator.isNotNull(summary)) {
			return HtmlUtil.stripHtml(summary);
		}

		try {
			PortletRequestModel portletRequestModel = null;
			ThemeDisplay themeDisplay = null;

			if ((portletRequest != null) && (portletResponse != null)) {
				portletRequestModel = new PortletRequestModel(
					portletRequest, portletResponse);
				themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);
			}

			JournalArticleDisplay articleDisplay =
				JournalArticleLocalServiceUtil.getArticleDisplay(
					_article, null, null, LanguageUtil.getLanguageId(locale), 1,
					portletRequestModel, themeDisplay);

			summary = HtmlUtil.unescape(
				HtmlUtil.stripHtml(articleDisplay.getContent()));
		}
		catch (Exception e) {
		}

		return summary;
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String thumbnailSrc = _article.getArticleImageURL(themeDisplay);

		if (Validator.isNotNull(thumbnailSrc)) {
			return thumbnailSrc;
		}

		return super.getThumbnailPath(portletRequest);
	}

	@Override
	public String getTitle(Locale locale) {
		return _article.getTitle(locale);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_article.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_article.jsp");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter(
			"version", String.valueOf(_article.getVersion()));

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			themeDisplay.getPlid(), PortletRequest.RESOURCE_PHASE);

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		liferayPortletURL.setParameter("articleId", _article.getArticleId());
		liferayPortletURL.setResourceID("exportArticle");

		return liferayPortletURL;
	}

	@Override
	public String getUrlTitle() {
		return _article.getUrlTitle();
	}

	@Override
	public PortletURL getURLViewDiffs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		JournalArticle previousApprovedArticle =
			JournalArticleLocalServiceUtil.getPreviousApprovedArticle(_article);

		if (previousApprovedArticle.getVersion() == _article.getVersion()) {
			return null;
		}

		portletURL.setParameter("mvcPath", "/compare_versions.jsp");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter(
			"sourceVersion",
			String.valueOf(previousApprovedArticle.getVersion()));
		portletURL.setParameter(
			"targetVersion", String.valueOf(_article.getVersion()));

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = _article.getLayout();

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		String portletId = (String)liferayPortletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				layout, portletId);

		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(), getClassPK());

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				getClassPK());

		Group group = themeDisplay.getScopeGroup();

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					group.getGroupId(), assetEntry.getClassNameId(),
					assetEntry.getClassTypeId());

		if (_isShowDisplayPage(
				_article, assetDisplayPageEntry,
				defaultLayoutPageTemplateEntry) &&
			Validator.isNull(linkToLayoutUuid)) {

			if (group.getGroupId() != _article.getGroupId()) {
				group = GroupLocalServiceUtil.getGroup(_article.getGroupId());
			}

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				LayoutSetLocalServiceUtil.getLayoutSet(
					group.getGroupId(), layout.isPrivateLayout()),
				themeDisplay);

			return PortalUtil.addPreservedParameters(
				themeDisplay,
				groupFriendlyURL.concat(
					JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
						_article.getUrlTitle()));
		}

		String hitLayoutURL = getHitLayoutURL(
			layout.isPrivateLayout(), noSuchEntryRedirect, themeDisplay);

		if (Objects.equals(hitLayoutURL, noSuchEntryRedirect)) {
			hitLayoutURL = getHitLayoutURL(
				!layout.isPrivateLayout(), noSuchEntryRedirect, themeDisplay);
		}

		return hitLayoutURL;
	}

	@Override
	public long getUserId() {
		return _article.getUserId();
	}

	@Override
	public String getUserName() {
		return _article.getUserName();
	}

	@Override
	public String getUuid() {
		return _article.getUuid();
	}

	@Override
	public String getViewInContextMessage() {
		return "view[action]";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.JOURNAL_ARTICLE, _article);

		request.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY,
			getArticleDisplay(request, response));

		return super.include(request, response, template);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isDisplayable() {
		Date now = new Date();

		Date displayDate = _article.getDisplayDate();

		if ((displayDate != null) && displayDate.after(now)) {
			return false;
		}

		Date expirationDate = _article.getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(now)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isLocalizable() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	public void setFieldsToDDMFormValuesConverter(
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter) {

		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
	}

	public void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	public void setJournalConverter(JournalConverter journalConverter) {
		_journalConverter = journalConverter;
	}

	protected JournalArticleDisplay getArticleDisplay(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		boolean workflowAssetPreview = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW));

		String ddmTemplateKey = (String)request.getAttribute(
			WebKeys.JOURNAL_TEMPLATE_ID);
		String viewMode = ParamUtil.getString(
			request, "viewMode", Constants.VIEW);
		String languageId = LanguageUtil.getLanguageId(request);
		int articlePage = ParamUtil.getInteger(request, "page", 1);
		PortletRequestModel portletRequestModel = getPortletRequestModel(
			request, response);
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!workflowAssetPreview && _article.isApproved()) {
			return _journalContent.getDisplay(
				_article.getGroupId(), _article.getArticleId(),
				_article.getVersion(), ddmTemplateKey, viewMode, languageId,
				articlePage, portletRequestModel, themeDisplay);
		}

		return JournalArticleLocalServiceUtil.getArticleDisplay(
			_article, ddmTemplateKey, viewMode, languageId, articlePage,
			portletRequestModel, themeDisplay);
	}

	protected String getHitLayoutURL(
			boolean privateLayout, String noSuchEntryRedirect,
			ThemeDisplay themeDisplay)
		throws PortalException {

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				_article.getGroupId(), privateLayout, _article.getArticleId());

		for (Long hitLayoutId : hitLayoutIds) {
			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				_article.getGroupId(), privateLayout, hitLayoutId.longValue());

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), hitLayout,
					ActionKeys.VIEW)) {

				return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
		}

		return noSuchEntryRedirect;
	}

	protected PortletRequestModel getPortletRequestModel(
		HttpServletRequest request, HttpServletResponse response) {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return null;
		}

		return new PortletRequestModel(portletRequest, portletResponse);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void setJournalServiceConfiguration() {
	}

	private boolean _isShowDisplayPage(
		JournalArticle article, AssetDisplayPageEntry assetDisplayPageEntry,
		LayoutPageTemplateEntry defaultAssetDisplayPageEntry) {

		if (Validator.isNull(article.getLayoutUuid()) &&
			(assetDisplayPageEntry == null)) {

			return false;
		}

		if ((assetDisplayPageEntry != null) &&
			(Objects.equals(
				assetDisplayPageEntry.getType(),
				AssetDisplayPageConstants.TYPE_SPECIFIC) ||
			 (defaultAssetDisplayPageEntry != null))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetRenderer.class);

	private final JournalArticle _article;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private JournalContent _journalContent;
	private JournalConverter _journalConverter;
	private JournalServiceConfiguration _journalServiceConfiguration;

}