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

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ServiceWrapper.class)
public class TrashEntryDLFileShortcutLocalServiceWrapper
	extends DLFileShortcutLocalServiceWrapper {

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException {

		super.deleteFileShortcut(fileShortcut);

		// Trash

		if (_trashHelper.isInTrashExplicitly(fileShortcut)) {
			_trashEntryLocalService.deleteEntry(
				DLFileShortcutConstants.getClassName(),
				fileShortcut.getFileShortcutId());
		}
		else {
			_trashVersionLocalService.deleteTrashVersion(
				DLFileShortcutConstants.getClassName(),
				fileShortcut.getFileShortcutId());
		}
	}

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}