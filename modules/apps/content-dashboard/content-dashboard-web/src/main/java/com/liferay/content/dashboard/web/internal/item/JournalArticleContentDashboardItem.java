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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItem
	implements ContentDashboardItem<JournalArticle> {

	public JournalArticleContentDashboardItem(
		List<AssetCategory> assetCategories, List<AssetTag> assetTags,
		ContentDashboardItemActionProviderTracker
			contentDashboardItemActionProviderTracker,
		ContentDashboardItemType contentDashboardItemType, Group group,
		InfoDisplayContributor<JournalArticle> infoDisplayContributor,
		JournalArticle journalArticle, Language language,
		JournalArticle latestApprovedJournalArticle) {

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
		_contentDashboardItemType = contentDashboardItemType;
		_group = group;
		_infoDisplayContributor = infoDisplayContributor;
		_journalArticle = journalArticle;
		_language = language;

		if (!journalArticle.equals(latestApprovedJournalArticle)) {
			_latestApprovedJournalArticle = latestApprovedJournalArticle;
		}
		else {
			_latestApprovedJournalArticle = null;
		}
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
					if (!contentDashboardItemActionProvider.isShow(
							_journalArticle, httpServletRequest)) {

						return Optional.<ContentDashboardItemAction>empty();
					}

					return Optional.of(
						contentDashboardItemActionProvider.
							getContentDashboardItemAction(
								_journalArticle, httpServletRequest));
				}
				catch (ContentDashboardItemActionException
							contentDashboardItemActionException) {

					_log.error(
						contentDashboardItemActionException,
						contentDashboardItemActionException);
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
	public ContentDashboardItemType getContentDashboardItemType() {
		return _contentDashboardItemType;
	}

	@Override
	public Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	@Override
	public Map<String, Object> getData(Locale locale) {
		return HashMapBuilder.<String, Object>put(
			"display-date", _journalArticle.getDisplayDate()
		).put(
			"expiration-date", _journalArticle.getExpirationDate()
		).put(
			"review-date", _journalArticle.getReviewDate()
		).build();
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.fromLanguageId(
			_journalArticle.getDefaultLanguageId());
	}

	@Override
	public Object getDisplayFieldValue(String fieldName, Locale locale) {
		try {
			return _infoDisplayContributor.getInfoDisplayFieldValue(
				_journalArticle, fieldName, locale);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return StringPool.BLANK;
		}
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return new InfoItemReference(
			JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey());
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
					return group.getDescriptiveName(locale);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return StringPool.BLANK;
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public String getTitle(Locale locale) {
		return _journalArticle.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _journalArticle.getUserId();
	}

	@Override
	public List<Version> getVersions(Locale locale) {
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
				curJournalArticle.getVersion())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleContentDashboardItem.class);

	private final List<AssetCategory> _assetCategories;
	private final List<AssetTag> _assetTags;
	private final ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;
	private final ContentDashboardItemType _contentDashboardItemType;
	private final Group _group;
	private final InfoDisplayContributor<JournalArticle>
		_infoDisplayContributor;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final JournalArticle _latestApprovedJournalArticle;

}