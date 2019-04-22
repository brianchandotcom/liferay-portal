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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class DLFileEntryInfoDisplayContributor
	implements InfoDisplayContributor<FileEntry> {

	@Override
	public String getClassName() {
		return _dlFileEntryAssetInfoDisplayContributor.getClassName();
	}

	@Override
	public List<InfoDisplayField> getClassTypeInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		return _dlFileEntryAssetInfoDisplayContributor.
			getClassTypeInfoDisplayFields(classTypeId, locale);
	}

	@Override
	public List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		return _dlFileEntryAssetInfoDisplayContributor.getClassTypes(
			groupId, locale);
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		return _dlFileEntryAssetInfoDisplayContributor.getInfoDisplayFields(
			classTypeId, locale);
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			FileEntry fileEntry, Locale locale)
		throws PortalException {

		AssetEntry assetEntry = _getAssetEntry(fileEntry);

		return _dlFileEntryAssetInfoDisplayContributor.
			getInfoDisplayFieldsValues(assetEntry, locale);
	}

	@Override
	public Object getInfoDisplayFieldValue(
			FileEntry fileEntry, String fieldName, Locale locale)
		throws PortalException {

		AssetEntry assetEntry = _getAssetEntry(fileEntry);

		return _dlFileEntryAssetInfoDisplayContributor.getInfoDisplayFieldValue(
			assetEntry, fieldName, locale);
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(classPK);

		return new InfoDisplayObjectProvider<FileEntry>() {

			@Override
			public long getClassNameId() {
				return _classNameLocalService.getClassNameId(getClassName());
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

			@Override
			public long getClassTypeId() {
				try {
					InfoDisplayObjectProvider infoDisplayObjectProvider =
						_dlFileEntryAssetInfoDisplayContributor.
							getInfoDisplayObjectProvider(classPK);

					return infoDisplayObjectProvider.getClassTypeId();
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe, pe);
					}

					return 0;
				}
			}

			@Override
			public String getDescription(Locale locale) {
				return fileEntry.getDescription();
			}

			@Override
			public FileEntry getDisplayObject() {
				return fileEntry;
			}

			@Override
			public long getGroupId() {
				return fileEntry.getGroupId();
			}

			@Override
			public String getKeywords(Locale locale) {
				try {
					InfoDisplayObjectProvider infoDisplayObjectProvider =
						_dlFileEntryAssetInfoDisplayContributor.
							getInfoDisplayObjectProvider(classPK);

					return infoDisplayObjectProvider.getKeywords(locale);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe, pe);
					}

					return StringPool.BLANK;
				}
			}

			@Override
			public String getTitle(Locale locale) {
				return fileEntry.getTitle();
			}

			@Override
			public String getURLTitle(Locale locale) {
				return String.valueOf(classPK);
			}

		};
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		return getInfoDisplayObjectProvider(Long.valueOf(urlTitle));
	}

	@Override
	public String getInfoURLSeparator() {
		return _dlFileEntryAssetInfoDisplayContributor.getInfoURLSeparator();
	}

	@Override
	public String getLabel(Locale locale) {
		return _dlFileEntryAssetInfoDisplayContributor.getLabel(locale);
	}

	private AssetEntry _getAssetEntry(FileEntry fileEntry) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			getClassName(), fileEntry.getFileEntryId());

		if (assetEntry == null) {
			assetEntry = _assetEntryLocalService.createAssetEntry(
				fileEntry.getFileEntryId());

			assetEntry.setGroupId(fileEntry.getGroupId());
			assetEntry.setCompanyId(fileEntry.getCompanyId());
			assetEntry.setUserId(fileEntry.getUserId());
			assetEntry.setCreateDate(fileEntry.getCreateDate());
			assetEntry.setClassNameId(
				_classNameLocalService.getClassNameId(getClassName()));
			assetEntry.setClassPK(fileEntry.getFileEntryId());
			assetEntry.setPublishDate(fileEntry.getModifiedDate());
			assetEntry.setTitle(fileEntry.getTitle());
			assetEntry.setDescription(fileEntry.getDescription());
		}

		return assetEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryInfoDisplayContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private final DLFileEntryAssetInfoDisplayContributor
		_dlFileEntryAssetInfoDisplayContributor =
			new DLFileEntryAssetInfoDisplayContributor();

	private class DLFileEntryAssetInfoDisplayContributor
		extends BaseAssetInfoDisplayContributor<FileEntry> {

		@Override
		public String getClassName() {
			return DLFileEntryConstants.getClassName();
		}

		@Override
		public String getInfoURLSeparator() {
			return "/d/";
		}

		@Override
		protected Map<String, Object> getClassTypeValues(
			FileEntry fileEntry, Locale locale) {

			return new HashMap<>();
		}

	}

}