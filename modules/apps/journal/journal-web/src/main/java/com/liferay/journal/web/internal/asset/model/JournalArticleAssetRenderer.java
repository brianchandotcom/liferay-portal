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

package com.liferay.journal.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
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
import com.liferay.journal.web.internal.asset.JournalArticleDDMFormValuesReader;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
	public JournalArticle getAssetObject(long versionClassPK) {
		return JournalArticleLocalServiceUtil.fetchArticle(versionClassPK);
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
	public String getDefaultLanguageId() throws Exception {
		return _article.getDefaultLanguageId();
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
			catch (Exception exception) {
				_log.error(exception, exception);

				return null;
			}
		}

		if (_journalServiceConfiguration.articleCommentsEnabled()) {
			return "edit_article_discussion";
		}

		return null;
	}

	@Override
	public long getGroupId() {
		return _article.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

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
			return HtmlUtil.render(HtmlUtil.stripHtml(summary));
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

			String ddmTemplateKey = ParamUtil.getString(
				portletRequest, "ddmTemplateKey");

			JournalArticleDisplay articleDisplay =
				JournalArticleLocalServiceUtil.getArticleDisplay(
					_article, ddmTemplateKey, null,
					LanguageUtil.getLanguageId(locale), 1, portletRequestModel,
					themeDisplay);

			summary = HtmlUtil.render(
				HtmlUtil.stripHtml(articleDisplay.getContent()));
		}
		catch (Exception exception) {
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
	public PortletURL getURLEdit(HttpServletRequest httpServletRequest)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_article.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
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
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return getURLEdit(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));
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
	public String getUrlTitle(Locale locale) {
		try {
			return _article.getUrlTitle(locale);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return getUrlTitle();
	}

	@Override
	public PortletURL getURLViewDiffs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		JournalArticle previousApprovedArticle =
			JournalArticleLocalServiceUtil.getPreviousApprovedArticle(_article);

		if ((previousApprovedArticle.getVersion() == _article.getVersion()) ||
			(_article.getVersion() ==
				JournalArticleConstants.VERSION_DEFAULT)) {

			return null;
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/journal/compare_versions");
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

		Group group = themeDisplay.getScopeGroup();

		if (!_isShowDisplayPage(group.getGroupId(), _article)) {
			String hitLayoutURL = getHitLayoutURL(
				layout.isPrivateLayout(), noSuchEntryRedirect, themeDisplay);

			if (Objects.equals(hitLayoutURL, noSuchEntryRedirect)) {
				hitLayoutURL = getHitLayoutURL(
					!layout.isPrivateLayout(), noSuchEntryRedirect,
					themeDisplay);
			}

			return hitLayoutURL;
		}

		if (group.getGroupId() != _article.getGroupId()) {
			group = GroupLocalServiceUtil.getGroup(_article.getGroupId());
		}

		if (_assetDisplayPageFriendlyURLProvider != null) {
			String friendlyURL =
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					getClassName(), getClassPK(), themeDisplay);

			if (Validator.isNotNull(friendlyURL)) {
				return friendlyURL;
			}
		}

		String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
			LayoutSetLocalServiceUtil.getLayoutSet(
				group.getGroupId(), layout.isPrivateLayout()),
			themeDisplay);

		StringBundler sb = new StringBundler(5);

		sb.append(groupFriendlyURL);
		sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);
		sb.append(_article.getUrlTitle(themeDisplay.getLocale()));

		if (!_article.isApproved()) {
			sb.append(StringPool.SLASH);
			sb.append(_article.getVersion());
		}

		return PortalUtil.addPreservedParameters(themeDisplay, sb.toString());
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, _article);

		httpServletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY,
			getArticleDisplay(httpServletRequest, httpServletResponse));

		return super.include(httpServletRequest, httpServletResponse, template);
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

	public void setAssetDisplayPageFriendlyURLProvider(
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		boolean workflowAssetPreview = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW));

		String ddmTemplateKey = (String)httpServletRequest.getAttribute(
			WebKeys.JOURNAL_TEMPLATE_ID);

		if (Validator.isNull(ddmTemplateKey)) {
			ddmTemplateKey = ParamUtil.getString(
				httpServletRequest, "ddmTemplateKey");
		}

		String viewMode = ParamUtil.getString(
			httpServletRequest, "viewMode", Constants.VIEW);

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if ((themeDisplay != null) &&
			Validator.isNotNull(themeDisplay.getLanguageId())) {

			languageId = themeDisplay.getLanguageId();
		}

		int articlePage = ParamUtil.getInteger(httpServletRequest, "page", 1);
		PortletRequestModel portletRequestModel = getPortletRequestModel(
			httpServletRequest, httpServletResponse);

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

			if (!hitLayout.isSystem() &&
				LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), hitLayout,
					ActionKeys.VIEW)) {

				return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
		}

		return noSuchEntryRedirect;
	}

	protected PortletRequestModel getPortletRequestModel(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return null;
		}

		return new PortletRequestModel(portletRequest, portletResponse);
	}

	private boolean _isShowDisplayPage(long groupId, JournalArticle article)
		throws PortalException {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(), getClassPK());

		boolean hasDisplayPage = AssetDisplayPageHelper.hasAssetDisplayPage(
			groupId, assetEntry);

		if (Validator.isNull(article.getLayoutUuid()) && !hasDisplayPage) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetRenderer.class);

	private final JournalArticle _article;
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private JournalContent _journalContent;
	private JournalConverter _journalConverter;
	private JournalServiceConfiguration _journalServiceConfiguration;

}