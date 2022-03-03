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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardGroupUtil;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class BlogsEntryContentDashboardItem
	implements ContentDashboardItem<BlogsEntry> {

	public BlogsEntryContentDashboardItem(
		List<AssetCategory> assetCategories, List<AssetTag> assetTags,
		BlogsEntry blogsEntry,
		ContentDashboardItemActionProviderTracker
			contentDashboardItemActionProviderTracker,
		Group group,
		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider,
		Language language, Portal portal) {

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

		_blogsEntry = blogsEntry;
		_contentDashboardItemActionProviderTracker =
			contentDashboardItemActionProviderTracker;
		_group = group;
		_infoItemFieldValuesProvider = infoItemFieldValuesProvider;
		_language = language;
		_portal = portal;
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
		try {
			return Arrays.asList(
				_portal.getSiteDefaultLocale(_group.getGroupId()));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return Collections.emptyList();
		}
	}

	@Override
	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		HttpServletRequest httpServletRequest,
		ContentDashboardItemAction.Type... types) {

		List<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviders(
						BlogsEntry.class.getName(), types);

		Stream<ContentDashboardItemActionProvider> stream =
			contentDashboardItemActionProviders.stream();

		return stream.map(
			contentDashboardItemActionProvider -> {
				try {
					return Optional.ofNullable(
						contentDashboardItemActionProvider.
							getContentDashboardItemAction(
								_blogsEntry, httpServletRequest));
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
		return null;
	}

	@Override
	public Date getCreateDate() {
		return _blogsEntry.getCreateDate();
	}

	@Override
	public Map<String, Object> getData(Locale locale) {
		return HashMapBuilder.<String, Object>put(
			"display-date", _blogsEntry.getDisplayDate()
		).build();
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
							BlogsEntry.class.getName(),
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
						BlogsEntry.class.getName(),
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
								BlogsEntry.class.getName(),
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
		try {
			return _portal.getSiteDefaultLocale(_blogsEntry.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return LocaleUtil.getDefault();
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return _blogsEntry.getSubtitle();
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return new InfoItemReference(
			BlogsEntry.class.getName(), _blogsEntry.getEntryId());
	}

	@Override
	public Date getModifiedDate() {
		return _blogsEntry.getModifiedDate();
	}

	@Override
	public String getScopeName(Locale locale) {
		return Optional.ofNullable(
			_group
		).map(
			group -> ContentDashboardGroupUtil.getGroupName(group, locale)
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public JSONObject getSpecificInformationJSONObject(Locale locale) {
		return JSONUtil.put(
			"creationDate", _blogsEntry.getCreateDate()
		).put(
			"displayDate", _blogsEntry.getDisplayDate()
		);
	}

	@Override
	public String getTitle(Locale locale) {
		return _blogsEntry.getTitle();
	}

	@Override
	public String getTypeLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = new InfoItemClassDetails(
			BlogsEntry.class.getName());

		return infoItemClassDetails.getLabel(locale);
	}

	@Override
	public long getUserId() {
		return _blogsEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _blogsEntry.getUserName();
	}

	@Override
	public List<Version> getVersions(Locale locale) {
		return Collections.singletonList(
			new Version(
				_language.get(
					locale,
					WorkflowConstants.getStatusLabel(_blogsEntry.getStatus())),
				WorkflowConstants.getStatusStyle(_blogsEntry.getStatus()),
				"1.0"));
	}

	@Override
	public boolean isViewable(HttpServletRequest httpServletRequest) {
		Optional<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviderOptional =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						BlogsEntry.class.getName(),
						ContentDashboardItemAction.Type.VIEW);

		return contentDashboardItemActionProviderOptional.map(
			contentDashboardItemActionProvider ->
				contentDashboardItemActionProvider.isShow(
					_blogsEntry, httpServletRequest)
		).orElse(
			false
		);
	}

	private Version _getLastVersion(Locale locale) {
		List<Version> versions = getVersions(locale);

		return versions.get(versions.size() - 1);
	}

	private ContentDashboardItemAction _toContentDashboardItemAction(
		ContentDashboardItemActionProvider contentDashboardItemActionProvider,
		HttpServletRequest httpServletRequest) {

		try {
			return contentDashboardItemActionProvider.
				getContentDashboardItemAction(_blogsEntry, httpServletRequest);
		}
		catch (ContentDashboardItemActionException
					contentDashboardItemActionException) {

			_log.error(contentDashboardItemActionException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryContentDashboardItem.class);

	private final List<AssetCategory> _assetCategories;
	private final List<AssetTag> _assetTags;
	private final BlogsEntry _blogsEntry;
	private final ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;
	private final Group _group;
	private final InfoItemFieldValuesProvider<BlogsEntry>
		_infoItemFieldValuesProvider;
	private final Language _language;
	private final Portal _portal;

}