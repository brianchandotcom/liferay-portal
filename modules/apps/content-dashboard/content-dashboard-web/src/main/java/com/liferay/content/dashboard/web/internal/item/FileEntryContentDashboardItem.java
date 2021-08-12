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
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class FileEntryContentDashboardItem
	extends ContentDashboardBaseItem<FileEntry> {

	public FileEntryContentDashboardItem(
		List<AssetCategory> assetCategories, List<AssetTag> assetTags,
		ContentDashboardItemActionProviderTracker
			contentDashboardItemActionProviderTracker,
		ContentDashboardItemSubtype contentDashboardItemSubtype,
		FileEntry fileEntry, Group group,
		InfoItemFieldValuesProvider<FileEntry> infoItemFieldValuesProvider,
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

		_contentDashboardItemActionProviderTracker =
			contentDashboardItemActionProviderTracker;
		_contentDashboardItemSubtype = contentDashboardItemSubtype;
		_fileEntry = fileEntry;
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
				_portal.getSiteDefaultLocale(_fileEntry.getGroupId()));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

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
						FileEntry.class.getName(), types);

		Stream<ContentDashboardItemActionProvider> stream =
			contentDashboardItemActionProviders.stream();

		return stream.map(
			contentDashboardItemActionProvider -> {
				try {
					return Optional.ofNullable(
						contentDashboardItemActionProvider.
							getContentDashboardItemAction(
								_fileEntry, httpServletRequest));
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
	public ContentDashboardItemSubtype getContentDashboardItemSubtype() {
		return _contentDashboardItemSubtype;
	}

	@Override
	public Date getCreateDate() {
		return _fileEntry.getCreateDate();
	}

	@Override
	public Map<String, Object> getData(Locale locale) {
		return Collections.emptyMap();
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
							FileEntry.class.getName(),
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
						FileEntry.class.getName(),
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
								FileEntry.class.getName(),
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
			return _portal.getSiteDefaultLocale(_fileEntry.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return LocaleUtil.getDefault();
		}
	}

	@Override
	public Object getDisplayFieldValue(String fieldName, Locale locale) {
		InfoFieldValue<Object> infoFieldValue =
			_infoItemFieldValuesProvider.getInfoFieldValue(
				_fileEntry, fieldName);

		if (infoFieldValue == null) {
			return null;
		}

		return infoFieldValue.getValue(locale);
	}

	@Override
	public FileEntry getInfoItem() {
		return _fileEntry;
	}

	@Override
	public InfoItemFieldValuesProvider getInfoItemFieldValuesProvider() {
		return _infoItemFieldValuesProvider;
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return new InfoItemReference(
			FileEntry.class.getName(), _fileEntry.getFileEntryId());
	}

	@Override
	public Date getModifiedDate() {
		return _fileEntry.getModifiedDate();
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
	public JSONObject getSpecificInformationJSONObject(
		String backURL, LiferayPortletResponse liferayPortletResponse,
		Locale locale, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"description", getDescription(locale)
		).put(
			"downloadURL", _getDownloadURL()
		).put(
			"extension", _getExtension()
		).put(
			"fileName", _getFileName()
		).put(
			"previewImageURL", _getPreviewImageURL()
		).put(
			"previewURL", _getPreviewURL(themeDisplay)
		).put(
			"size", _getSize(locale)
		).put(
			"viewURL", _getViewURL(liferayPortletResponse, backURL)
		);
	}

	@Override
	public String getTitle(Locale locale) {
		return _fileEntry.getTitle();
	}

	@Override
	public String getTypeLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = new InfoItemClassDetails(
			FileEntry.class.getName());

		return infoItemClassDetails.getLabel(locale);
	}

	@Override
	public long getUserId() {
		return _fileEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _fileEntry.getUserName();
	}

	@Override
	public List<Version> getVersions(Locale locale) {
		try {
			FileVersion latestFileVersion = _fileEntry.getLatestFileVersion();
			FileVersion latestTrustedFileVersion =
				_fileEntry.getLatestFileVersion(true);

			List<FileVersion> fileVersions = new ArrayList<>();

			fileVersions.add(latestTrustedFileVersion);

			if (!latestFileVersion.equals(latestTrustedFileVersion)) {
				fileVersions.add(latestFileVersion);
			}

			Stream<FileVersion> stream = fileVersions.stream();

			return stream.map(
				fileVersion -> _toVersionOptional(fileVersion, locale)
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
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Collections.emptyList();
		}
	}

	@Override
	public boolean isViewable(HttpServletRequest httpServletRequest) {
		if (ListUtil.isEmpty(
				_fileEntry.getFileVersions(
					WorkflowConstants.STATUS_APPROVED))) {

			return false;
		}

		Optional<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviderOptional =
				_contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						FileEntry.class.getName(),
						ContentDashboardItemAction.Type.VIEW);

		return contentDashboardItemActionProviderOptional.map(
			contentDashboardItemActionProvider ->
				contentDashboardItemActionProvider.isShow(
					_fileEntry, httpServletRequest)
		).orElse(
			false
		);
	}

	private String _getDownloadURL() {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(_fileEntry);

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("downloadURL");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object downloadURL = infoFieldValue.getValue();

		return downloadURL.toString();
	}

	private String _getExtension() {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(_fileEntry);

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("fileName");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object fileName = infoFieldValue.getValue();

		return FileUtil.getExtension(fileName.toString());
	}

	private String _getFileName() {
		return _fileEntry.getFileName();
	}

	private Version _getLastVersion(Locale locale) {
		List<Version> versions = getVersions(locale);

		return versions.get(versions.size() - 1);
	}

	private String _getPreviewImageURL() {
		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(_fileEntry);

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("previewImage");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		return String.valueOf(infoFieldValue.getValue());
	}

	private String _getPreviewURL(ThemeDisplay themeDisplay) {
		try {
			return DLURLHelperUtil.getPreviewURL(
				_fileEntry, _fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, true);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private String _getSize(Locale locale) {
		return LanguageUtil.formatStorageSize(_fileEntry.getSize(), locale);
	}

	private String _getViewURL(
		LiferayPortletResponse liferayPortletResponse, String redirect) {

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
		).setMVCRenderCommandName(
			"/document_library/view_file_entry"
		).setRedirect(
			redirect
		).setParameter(
			"fileEntryId", _fileEntry.getFileEntryId()
		).buildString();
	}

	private ContentDashboardItemAction _toContentDashboardItemAction(
		ContentDashboardItemActionProvider contentDashboardItemActionProvider,
		HttpServletRequest httpServletRequest) {

		try {
			return contentDashboardItemActionProvider.
				getContentDashboardItemAction(_fileEntry, httpServletRequest);
		}
		catch (ContentDashboardItemActionException
					contentDashboardItemActionException) {

			_log.error(
				contentDashboardItemActionException,
				contentDashboardItemActionException);

			return null;
		}
	}

	private Optional<Version> _toVersionOptional(
		FileVersion fileVersion, Locale locale) {

		return Optional.ofNullable(
			fileVersion
		).map(
			curFileVersion -> new Version(
				_language.get(
					locale,
					WorkflowConstants.getStatusLabel(
						curFileVersion.getStatus())),
				WorkflowConstants.getStatusStyle(curFileVersion.getStatus()),
				curFileVersion.getVersion())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryContentDashboardItem.class);

	private final List<AssetCategory> _assetCategories;
	private final List<AssetTag> _assetTags;
	private final ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;
	private final ContentDashboardItemSubtype _contentDashboardItemSubtype;
	private final FileEntry _fileEntry;
	private final Group _group;
	private final InfoItemFieldValuesProvider<FileEntry>
		_infoItemFieldValuesProvider;
	private final Language _language;
	private final Portal _portal;

}