/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.diff.DiffVersion;
import com.liferay.diff.DiffVersionsInfo;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Tom Wang
 */
public class JournalUtil {

	public static DiffVersionsInfo getDiffVersionsInfo(
		long groupId, String articleId, double sourceVersion,
		double targetVersion) {

		double previousVersion = 0;
		double nextVersion = 0;

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getArticlesByArticleId(
				groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator(true));

		for (JournalArticle article : articles) {
			if ((article.getVersion() < sourceVersion) &&
				(article.getVersion() > previousVersion)) {

				previousVersion = article.getVersion();
			}

			if ((article.getVersion() > targetVersion) &&
				((article.getVersion() < nextVersion) || (nextVersion == 0))) {

				nextVersion = article.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (JournalArticle article : articles) {
			DiffVersion diffVersion = new DiffVersion(
				article.getUserId(), article.getVersion(),
				article.getModifiedDate());

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	public static boolean getEmailArticleAnyEventEnabled(
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		if (journalGroupServiceConfiguration.emailArticleAddedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalDeniedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalGrantedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalRequestedEnabled() ||
			journalGroupServiceConfiguration.emailArticleReviewEnabled() ||
			journalGroupServiceConfiguration.emailArticleUpdatedEnabled()) {

			return true;
		}

		return false;
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		return getEmailDefinitionTerms(
			portletRequest, emailFromAddress, emailFromName, StringPool.BLANK);
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName, String emailType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fromAddress = HtmlUtil.escape(emailFromAddress);
		String fromName = HtmlUtil.escape(emailFromName);
		String toAddress = LanguageUtil.get(
			themeDisplay.getLocale(), "the-address-of-the-email-recipient");
		String toName = LanguageUtil.get(
			themeDisplay.getLocale(), "the-name-of-the-email-recipient");

		if (emailType.equals("requested")) {
			toName = fromName;
			toAddress = fromAddress;

			fromName = LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-sender");
			fromAddress = LanguageUtil.get(
				themeDisplay.getLocale(), "the-address-of-the-email-sender");
		}

		return LinkedHashMapBuilder.put(
			"[$ARTICLE_CONTENT]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content")
		).put(
			"[$ARTICLE_DIFFS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-web-content-compared-with-the-previous-version-web-" +
					"content")
		).put(
			"[$ARTICLE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content-id")
		).put(
			"[$ARTICLE_TITLE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content-title")
		).put(
			"[$ARTICLE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content-url")
		).put(
			"[$ARTICLE_VERSION$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-web-content-version")
		).put(
			"[$FROM_ADDRESS$]", fromAddress
		).put(
			"[$FROM_NAME$]", fromName
		).put(
			"[$PORTAL_URL$]",
			() -> {
				Company company = themeDisplay.getCompany();

				return company.getVirtualHostname();
			}
		).put(
			"[$PORTLET_NAME$]",
			() -> {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				return HtmlUtil.escape(portletDisplay.getTitle());
			}
		).put(
			"[$TO_ADDRESS$]", toAddress
		).put(
			"[$TO_NAME$]", toName
		).build();
	}

	public static long getPreviewPlid(
			JournalArticle article, ThemeDisplay themeDisplay)
		throws Exception {

		if (article != null) {
			Layout layout = article.getLayout();

			if (layout != null) {
				return layout.getPlid();
			}
		}

		Layout layout = LayoutLocalServiceUtil.fetchFirstLayout(
			themeDisplay.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchFirstLayout(
				themeDisplay.getScopeGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
		}

		if ((layout != null) &&
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout, ActionKeys.VIEW)) {

			layout = _getViewableLayout(false, themeDisplay);

			if (layout == null) {
				layout = _getViewableLayout(true, themeDisplay);
			}
		}

		if (layout != null) {
			return layout.getPlid();
		}

		return themeDisplay.getPlid();
	}

	public static boolean hasWorkflowDefinitionsLinks(
		ThemeDisplay themeDisplay) {

		int count =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					JournalFolder.class.getName());

		if (count > 0) {
			return true;
		}

		count =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					JournalArticle.class.getName());

		if (count > 0) {
			return true;
		}

		count =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(),
					GroupConstants.DEFAULT_PARENT_GROUP_ID,
					JournalArticle.class.getName());

		if (count > 0) {
			return true;
		}

		return false;
	}

	public static boolean isIncludeVersionHistory() {
		try {
			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return journalServiceConfiguration.
				singleAssetPublishIncludeVersionHistory();
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to retrieve journal service configuration",
					configurationException);
			}

			return false;
		}
	}

	public static boolean isSubscribedToArticle(
		long companyId, long groupId, long userId, long articleId) {

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, JournalArticle.class.getName(), articleId);
	}

	public static boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId)
		throws PortalException {

		return isSubscribedToFolder(companyId, groupId, userId, folderId, true);
	}

	public static boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId,
			boolean recursive)
		throws PortalException {

		List<Long> ancestorFolderIds = new ArrayList<>();

		if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
				folderId);

			ancestorFolderIds.add(folderId);

			if (recursive) {
				ancestorFolderIds.addAll(folder.getAncestorFolderIds());

				ancestorFolderIds.add(groupId);
			}
		}
		else {
			ancestorFolderIds.add(groupId);
		}

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, JournalFolder.class.getName(),
			ArrayUtil.toLongArray(ancestorFolderIds));
	}

	public static boolean isSubscribedToStructure(
		long companyId, long groupId, long userId, long ddmStructureId) {

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, DDMStructure.class.getName(), ddmStructureId);
	}

	private static Layout _getViewableLayout(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		for (int end = 0, interval = 20, start = 0;;) {
			end = start + interval;

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				themeDisplay.getScopeGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false, start, end);

			for (Layout layout : layouts) {
				try {
					if (LayoutPermissionUtil.contains(
							themeDisplay.getPermissionChecker(), layout,
							ActionKeys.VIEW)) {

						return layout;
					}
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}
			}

			start = start + interval;

			if (layouts.size() < interval) {
				break;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(JournalUtil.class);

}