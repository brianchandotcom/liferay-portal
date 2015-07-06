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

import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.BaseJSPAssetRenderer;
import com.liferay.portlet.asset.model.DDMFormValuesReader;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	extends BaseJSPAssetRenderer implements TrashRenderer {

	public static final String TYPE = "journal_article";

	public static long getClassPK(JournalArticle article) {
		if ((article.isDraft() || article.isPending()) &&
			(article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {

			return article.getPrimaryKey();
		}
		else {
			return article.getResourcePrimKey();
		}
	}

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public JournalArticle getArticle() {
		return _article;
	}

	@Override
	public Object getAsset() {
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
		return new JournalArticleDDMFormValuesReader(_article);
	}

	@Override
	public String getDiscussionPath() {
		if (JournalServiceConfigurationValues.
				JOURNAL_ARTICLE_COMMENTS_ENABLED) {

			return "edit_article_discussion";
		}
		else {
			return null;
		}
	}

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
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
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
			return StringUtil.shorten(summary, 200);
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

			summary = StringUtil.shorten(
				HtmlUtil.stripHtml(articleDisplay.getContent()), 200);
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

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/article.png";
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

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL, 0,
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

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL, 0,
			PortletRequest.RESOURCE_PHASE);

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
			liferayPortletRequest, JournalPortletKeys.JOURNAL, 0,
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
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

		if (Validator.isNotNull(_article.getLayoutUuid()) &&
			Validator.isNull(linkToLayoutUuid)) {

			Group group = themeDisplay.getScopeGroup();

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

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				_article.getGroupId(), layout.isPrivateLayout(),
				_article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				_article.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}

		return noSuchEntryRedirect;
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

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private final JournalArticle _article;

}