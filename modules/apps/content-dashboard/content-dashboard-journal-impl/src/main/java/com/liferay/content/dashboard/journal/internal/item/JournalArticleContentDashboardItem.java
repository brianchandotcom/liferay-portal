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

package com.liferay.content.dashboard.journal.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.VersionableContentDashboardItem;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItem
	implements VersionableContentDashboardItem<JournalArticle> {

	public JournalArticleContentDashboardItem(
		List<AssetCategory> assetCategories, List<AssetTag> assetTags,
		ContentDashboardItemActionProviderTracker
			contentDashboardItemActionProviderTracker,
		ContentDashboardItemSubtype contentDashboardItemSubtype, Group group,
		InfoItemFieldValuesProvider<JournalArticle> infoItemFieldValuesProvider,
		JournalArticle journalArticle,
		JournalArticleService journalArticleService, Language language,
		JournalArticle latestApprovedJournalArticle, Portal portal) {

		if (ListUtil.isEmpty(assetCategories)) {
			_assetCategories = Collections.emptyList();
		}
		else {
			_assetCategories = Collections.unmodifiableList(assetCategories);
		}

		if (ListUtil.isEmpty(assetTags)) {
			_assetTags = Collections.emptyList();
		}
		else {
			_assetTags = Collections.unmodifiableList(assetTags);
		}

		_contentDashboardItemActionProviderTracker =
			contentDashboardItemActionProviderTracker;
		_contentDashboardItemSubtype = contentDashboardItemSubtype;
		_group = group;
		_infoItemFieldValuesProvider = infoItemFieldValuesProvider;
		_journalArticle = journalArticle;
		_journalArticleService = journalArticleService;
		_language = language;

		if (!journalArticle.equals(latestApprovedJournalArticle)) {
			_latestApprovedJournalArticle = latestApprovedJournalArticle;
		}
		else {
			_latestApprovedJournalArticle = null;
		}

		_portal = portal;
	}

	@Override
	public List<Version> getAllVersions(ThemeDisplay themeDisplay) {
		int status = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		User user = themeDisplay.getUser();

		if ((user.getUserId() == _journalArticle.getUserId()) ||
			permissionChecker.isContentReviewer(
				user.getCompanyId(), themeDisplay.getScopeGroupId())) {

			status = WorkflowConstants.STATUS_ANY;
		}

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticlesByArticleId(
				_journalArticle.getGroupId(), _journalArticle.getArticleId(),
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator());

		return ListUtil.toList(
			journalArticles,
			journalArticle -> new Version(
				_language.get(
					themeDisplay.getLocale(),
					WorkflowConstants.getStatusLabel(
						journalArticle.getStatus())),
				WorkflowConstants.getStatusStyle(journalArticle.getStatus()),
				String.valueOf(journalArticle.getVersion()), null,
				journalArticle.getUserName(), journalArticle.getStatusDate()));
	}

	@Override
	public List<AssetCategory> getAssetCategories() {
		return _assetCategories;
	}

	@Override
	public List<AssetCategory> getAssetCategories(long assetVocabularyId) {
		Stream<AssetCategory> stream = _assetCategories.stream();

		return stream.filter(
			assetCategory ->
				assetCategory.getVocabularyId() == assetVocabularyId
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<AssetTag> getAssetTags() {
		return _assetTags;
	}

	@Override
	public List<Locale> getAvailableLocales() {
		return Stream.of(
			_journalArticle.getAvailableLanguageIds()
		).map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		HttpServletRequest httpServletRequest,
		ContentDashboardItemAction.Type... types) {

		List<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviders(
						JournalArticle.class.getName(), types);

		Stream<ContentDashboardItemActionProvider> stream =
			contentDashboardItemActionProviders.stream();

		return stream.map(
			contentDashboardItemActionProvider -> {
				try {
					return Optional.ofNullable(
						contentDashboardItemActionProvider.
							getContentDashboardItemAction(
								_journalArticle, httpServletRequest));
				}
				catch (ContentDashboardItemActionException
							contentDashboardItemActionException) {

					_log.error(contentDashboardItemActionException);
				}

				return Optional.<ContentDashboardItemAction>empty();
			}
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public ContentDashboardItemSubtype getContentDashboardItemSubtype() {
		return _contentDashboardItemSubtype;
	}

	@Override
	public Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	@Override
	public ContentDashboardItemAction getDefaultContentDashboardItemAction(
		HttpServletRequest httpServletRequest) {

		long userId = _portal.getUserId(httpServletRequest);

		Locale locale = _portal.getLocale(httpServletRequest);

		Version version = _getLastVersion(locale);

		if ((getUserId() == userId) &&
			Objects.equals(
				version.getLabel(),
				_language.get(
					locale,
					WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_DRAFT)))) {

			Optional<ContentDashboardItemActionProvider>
				contentDashboardItemActionProviderOptional =
					_contentDashboardItemActionProviderTracker.
						getContentDashboardItemActionProviderOptional(
							JournalArticle.class.getName(),
							ContentDashboardItemAction.Type.EDIT);

			return contentDashboardItemActionProviderOptional.map(
				contentDashboardItemActionProvider ->
					_toContentDashboardItemAction(
						contentDashboardItemActionProvider, httpServletRequest)
			).orElse(
				null
			);
		}

		Optional<ContentDashboardItemActionProvider>
			viewContentDashboardItemActionProviderOptional =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						JournalArticle.class.getName(),
						ContentDashboardItemAction.Type.VIEW);

		return viewContentDashboardItemActionProviderOptional.map(
			contentDashboardItemActionProvider -> _toContentDashboardItemAction(
				contentDashboardItemActionProvider, httpServletRequest)
		).orElseGet(
			() -> {
				Optional<ContentDashboardItemActionProvider>
					editContentDashboardItemActionProviderOptional =
						_contentDashboardItemActionProviderTracker.
							getContentDashboardItemActionProviderOptional(
								JournalArticle.class.getName(),
								ContentDashboardItemAction.Type.EDIT);

				return editContentDashboardItemActionProviderOptional.map(
					contentDashboardItemActionProvider ->
						_toContentDashboardItemAction(
							contentDashboardItemActionProvider,
							httpServletRequest)
				).orElse(
					null
				);
			}
		);
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.fromLanguageId(
			_journalArticle.getDefaultLanguageId());
	}

	@Override
	public String getDescription(Locale locale) {
		return _getStringValue("description", locale);
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return new InfoItemReference(
			JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey());
	}

	@Override
	public List<Version> getLatestVersions(Locale locale) {
		return Stream.of(
			_toVersionOptional(_journalArticle, locale),
			_toVersionOptional(_latestApprovedJournalArticle, locale)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).sorted(
			Comparator.comparing(Version::getVersion)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	@Override
	public String getScopeName(Locale locale) {
		return Optional.ofNullable(
			_group
		).map(
			group -> {
				try {
					return Optional.ofNullable(
						group.getDescriptiveName(locale)
					).orElseGet(
						() -> group.getName(locale)
					);
				}
				catch (PortalException portalException) {
					_log.error(portalException);

					return group.getName(locale);
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public Map<String, Object> getSpecificInformation(Locale locale) {
		return HashMapBuilder.<String, Object>put(
			"display-date", _journalArticle.getDisplayDate()
		).put(
			"expiration-date", _journalArticle.getExpirationDate()
		).put(
			"review-date", _journalArticle.getReviewDate()
		).build();
	}

	@Override
	public String getTitle(Locale locale) {
		return _journalArticle.getTitle(locale);
	}

	@Override
	public String getTypeLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = new InfoItemClassDetails(
			JournalArticle.class.getName());

		return infoItemClassDetails.getLabel(locale);
	}

	@Override
	public long getUserId() {
		if (_latestApprovedJournalArticle != null) {
			return _latestApprovedJournalArticle.getUserId();
		}

		return _journalArticle.getUserId();
	}

	@Override
	public String getUserName() {
		if (_latestApprovedJournalArticle != null) {
			return _latestApprovedJournalArticle.getUserName();
		}

		return _journalArticle.getUserName();
	}

	@Override
	public String getViewVersionsURL(HttpServletRequest httpServletRequest) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				httpServletRequest,
				GroupLocalServiceUtil.fetchGroup(_journalArticle.getGroupId()),
				JournalPortletKeys.JOURNAL, 0, 0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/view_article_history.jsp"
		).setBackURL(
			() -> {
				LiferayPortletResponse liferayPortletResponse =
					PortalUtil.getLiferayPortletResponse(
						(PortletResponse)httpServletRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE));

				return liferayPortletResponse.createRenderURL();
			}
		).setParameter(
			"articleId", _journalArticle.getArticleId()
		).buildString();
	}

	@Override
	public boolean isViewable(HttpServletRequest httpServletRequest) {
		if (!_journalArticle.hasApprovedVersion()) {
			return false;
		}

		Optional<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviderOptional =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						JournalArticle.class.getName(),
						ContentDashboardItemAction.Type.VIEW);

		return contentDashboardItemActionProviderOptional.map(
			contentDashboardItemActionProvider ->
				contentDashboardItemActionProvider.isShow(
					_journalArticle, httpServletRequest)
		).orElse(
			false
		);
	}

	private Version _getLastVersion(Locale locale) {
		List<Version> versions = getLatestVersions(locale);

		return versions.get(versions.size() - 1);
	}

	private String _getStringValue(String infoFieldName, Locale locale) {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(
				_journalArticle);

		return Optional.ofNullable(
			infoItemFieldValues.getInfoFieldValue(infoFieldName)
		).map(
			infoFieldValue -> infoFieldValue.getValue(locale)
		).orElse(
			StringPool.BLANK
		).toString();
	}

	private ContentDashboardItemAction _toContentDashboardItemAction(
		ContentDashboardItemActionProvider contentDashboardItemActionProvider,
		HttpServletRequest httpServletRequest) {

		try {
			return contentDashboardItemActionProvider.
				getContentDashboardItemAction(
					_journalArticle, httpServletRequest);
		}
		catch (ContentDashboardItemActionException
					contentDashboardItemActionException) {

			_log.error(contentDashboardItemActionException);

			return null;
		}
	}

	private Optional<Version> _toVersionOptional(
		JournalArticle journalArticle, Locale locale) {

		return Optional.ofNullable(
			journalArticle
		).map(
			curJournalArticle -> new Version(
				_language.get(
					locale,
					WorkflowConstants.getStatusLabel(
						curJournalArticle.getStatus())),
				WorkflowConstants.getStatusStyle(curJournalArticle.getStatus()),
				String.valueOf(curJournalArticle.getVersion()), null,
				curJournalArticle.getUserName(),
				curJournalArticle.getCreateDate())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleContentDashboardItem.class);

	private final List<AssetCategory> _assetCategories;
	private final List<AssetTag> _assetTags;
	private final ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;
	private final ContentDashboardItemSubtype _contentDashboardItemSubtype;
	private final Group _group;
	private final InfoItemFieldValuesProvider<JournalArticle>
		_infoItemFieldValuesProvider;
	private final JournalArticle _journalArticle;
	private final JournalArticleService _journalArticleService;
	private final Language _language;
	private final JournalArticle _latestApprovedJournalArticle;
	private final Portal _portal;

}