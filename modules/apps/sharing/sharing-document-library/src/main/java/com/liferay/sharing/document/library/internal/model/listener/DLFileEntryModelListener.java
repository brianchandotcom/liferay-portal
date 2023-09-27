/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.document.library.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.service.SharingEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = ModelListener.class)
public class DLFileEntryModelListener extends BaseModelListener<DLFileEntry> {

	@Override
	public void onBeforeRemove(DLFileEntry dlFileEntry) {
		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());

		int count = _sharingEntryLocalService.getSharingEntriesCount(
			classNameId, dlFileEntry.getFileEntryId());

		if (count > 0) {
			_sharingEntryLocalService.deleteSharingEntries(
				classNameId, dlFileEntry.getFileEntryId());
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}