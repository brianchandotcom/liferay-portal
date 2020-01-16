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

package com.liferay.document.library.opener.google.drive.web.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class DLFileEntryModelListener extends BaseModelListener<DLFileEntry> {

	@Override
	public void onAfterRemove(DLFileEntry dlFileEntry)
		throws ModelListenerException {

		try {
			super.onAfterRemove(dlFileEntry);

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			DLOpenerFileEntryReference dlOpenerFileEntryReference =
				_dlOpenerFileEntryReferenceLocalService.
					fetchDLOpenerFileEntryReference(
						DLOpenerGoogleDriveConstants.
							GOOGLE_DRIVE_REFERENCE_TYPE,
						fileEntry);

			if (dlOpenerFileEntryReference != null) {
				_dlOpenerFileEntryReferenceLocalService.
					deleteDLOpenerFileEntryReference(
						DLOpenerGoogleDriveConstants.
							GOOGLE_DRIVE_REFERENCE_TYPE,
						fileEntry);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

}