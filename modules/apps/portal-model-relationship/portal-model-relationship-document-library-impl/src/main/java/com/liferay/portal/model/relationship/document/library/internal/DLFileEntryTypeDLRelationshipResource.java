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

package com.liferay.portal.model.relationship.document.library.internal;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = RelationshipResource.class
)
public class DLFileEntryTypeDLRelationshipResource
	implements RelationshipResource<DLFileEntryType> {

	@Override
	public Relationship<DLFileEntryType> relationship(
		Relationship.Builder<DLFileEntryType> builder) {

		return builder.modelSupplier(
			fileEntryTypeId -> _dlFileEntryTypeLocalService.fetchFileEntryType(
				fileEntryTypeId)
		).outboundSingleRelationship(
			this::_getFileEntry
		).outboundMultiRelationship(
			this::_getFolders
		).build();
	}

	private FileEntry _getFileEntry(DLFileEntryType fileEntryType) {
		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getFileEntries(-1, -1);

		Stream<DLFileEntry> stream = dlFileEntries.parallelStream();

		return stream.filter(
			dlFileEntry ->
				dlFileEntry.getFileEntryTypeId() ==
					fileEntryType.getFileEntryTypeId()
		).findFirst(
		).map(
			dlFileEntry -> {
				try {
					return _dlAppLocalService.getFileEntry(
						dlFileEntry.getFileEntryId());
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe.getMessage(), pe);
					}

					return null;
				}
			}
		).get();
	}

	private List<Folder> _getFolders(DLFileEntryType fileEntryType) {
		List<DLFolder> dlFolders =
			_dlFolderLocalService.getDLFileEntryTypeDLFolders(
				fileEntryType.getFileEntryTypeId());

		Stream<DLFolder> stream = dlFolders.stream();

		return stream.map(
			dlFolder -> {
				try {
					return _dlAppLocalService.getFolder(dlFolder.getFolderId());
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe.getMessage(), pe);
					}

					return null;
				}
			}
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeDLRelationshipResource.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

}