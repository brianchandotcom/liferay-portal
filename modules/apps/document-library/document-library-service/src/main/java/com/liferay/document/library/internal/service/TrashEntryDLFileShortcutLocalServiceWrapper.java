/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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