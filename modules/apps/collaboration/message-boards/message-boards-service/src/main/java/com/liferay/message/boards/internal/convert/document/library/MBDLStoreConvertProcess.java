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

package com.liferay.message.boards.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Shay
 */
@Component(service = DLStoreConvertProcess.class)
public class MBDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _mbMessageLocalService.getMBMessagesCount();

		MaintenanceUtil.appendStatus(
			"Migrating message boards attachments in " + count + " messages");

		ActionableDynamicQuery actionableDynamicQuery =
			_mbMessageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MBMessage>() {

				@Override
				public void performAction(MBMessage mbMessage)
					throws PortalException {

					for (FileEntry fileEntry :
							mbMessage.getAttachmentsFileEntries()) {

						DLFileEntry dlFileEntry =
							(DLFileEntry)fileEntry.getModel();

						dlStoreConverter.migrateDLFileEntry(
							mbMessage.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								dlFileEntry.getRepositoryId(),
								dlFileEntry.getFolderId()),
							new LiferayFileEntry(dlFileEntry));
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;

}