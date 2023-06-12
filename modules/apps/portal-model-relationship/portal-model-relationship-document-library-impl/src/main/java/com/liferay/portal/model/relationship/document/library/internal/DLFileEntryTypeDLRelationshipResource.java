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
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.List;
import java.util.NoSuchElementException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 *
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = RelationshipResource.class
)
@Deprecated
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
		for (DLFileEntry dlFileEntry :
				_dlFileEntryLocalService.getFileEntries(-1, -1)) {

			if (dlFileEntry.getFileEntryTypeId() !=
					fileEntryType.getFileEntryTypeId()) {

				continue;
			}

			try {
				return _dlAppLocalService.getFileEntry(
					dlFileEntry.getFileEntryId());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException);
				}

				throw new NoSuchElementException();
			}
		}

		throw new NoSuchElementException();
	}

	private List<Folder> _getFolders(DLFileEntryType fileEntryType) {
		return TransformUtil.transform(
			_dlFolderLocalService.getDLFileEntryTypeDLFolders(
				fileEntryType.getFileEntryTypeId()),
			dlFolder -> {
				try {
					return _dlAppLocalService.getFolder(dlFolder.getFolderId());
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}

					return null;
				}
			});
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