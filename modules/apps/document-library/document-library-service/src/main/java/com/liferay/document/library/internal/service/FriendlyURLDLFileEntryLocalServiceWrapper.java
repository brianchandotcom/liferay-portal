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

package com.liferay.document.library.internal.service;

import com.liferay.document.library.configuration.FFFriendlyURLEntryFileEntryConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceWrapper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.FFFriendlyURLEntryFileEntryConfiguration",
	service = ServiceWrapper.class
)
public class FriendlyURLDLFileEntryLocalServiceWrapper
	extends DLFileEntryLocalServiceWrapper {

	@Override
	public DLFileEntry addFileEntry(
			String externalReferenceCode, long userId, long groupId,
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
			File file, InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = super.addFileEntry(
			externalReferenceCode, userId, groupId, repositoryId, folderId,
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, ddmFormValuesMap, file, inputStream, size,
			expirationDate, reviewDate, serviceContext);

		if (_ffFriendlyURLEntryFileEntryConfiguration.enabled()) {
			_addFriendlyURLEntry(dlFileEntry);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry deleteFileEntry(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = super.deleteFileEntry(fileEntryId);

		if (_ffFriendlyURLEntryFileEntryConfiguration.enabled()) {
			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				dlFileEntry.getGroupId(),
				_classNameLocalService.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
			File file, InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, fileEntryTypeId,
			ddmFormValuesMap, file, inputStream, size, expirationDate,
			reviewDate, serviceContext);

		if (_ffFriendlyURLEntryFileEntryConfiguration.enabled()) {
			_updateFriendlyURL(dlFileEntry, title);
		}

		return dlFileEntry;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffFriendlyURLEntryFileEntryConfiguration =
			ConfigurableUtil.createConfigurable(
				FFFriendlyURLEntryFileEntryConfiguration.class, properties);
	}

	private void _addFriendlyURLEntry(DLFileEntry dlFileEntry)
		throws PortalException {

		String urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			dlFileEntry.getGroupId(),
			_classNameLocalService.getClassNameId(FileEntry.class),
			dlFileEntry.getFileEntryId(), dlFileEntry.getTitle(),
			LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			dlFileEntry.getGroupId(),
			_classNameLocalService.getClassNameId(FileEntry.class),
			dlFileEntry.getFileEntryId(), urlTitle,
			ServiceContextThreadLocal.getServiceContext());
	}

	private Map<String, String> _getUniqueUrlTitleMap(
		long groupId, long fileEntryId, String title,
		Map<String, String> urlTitleMap) {

		Map<String, String> newUrlTitleMap = new HashMap<>();

		for (Map.Entry<String, String> entry : urlTitleMap.entrySet()) {
			String languageId = entry.getKey();

			String urlTitle = urlTitleMap.get(languageId);

			if (Validator.isNotNull(urlTitle) ||
				((urlTitle != null) && urlTitle.equals(StringPool.BLANK))) {

				urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
					groupId,
					_classNameLocalService.getClassNameId(FileEntry.class),
					fileEntryId, title, languageId);

				newUrlTitleMap.put(languageId, urlTitle);
			}
		}

		return newUrlTitleMap;
	}

	private void _updateFriendlyURL(DLFileEntry dlFileEntry, String title)
		throws PortalException {

		try {
			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_classNameLocalService.getClassNameId(FileEntry.class),
					dlFileEntry.getFileEntryId());

			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId(),
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(),
				friendlyURLEntry.getDefaultLanguageId(),
				_getUniqueUrlTitleMap(
					dlFileEntry.getGroupId(), dlFileEntry.getFileEntryId(),
					title, friendlyURLEntry.getLanguageIdToUrlTitleMap()));
		}
		catch (NoSuchModelException noSuchModelException) {
			_addFriendlyURLEntry(dlFileEntry);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile FFFriendlyURLEntryFileEntryConfiguration
		_ffFriendlyURLEntryFileEntryConfiguration;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}