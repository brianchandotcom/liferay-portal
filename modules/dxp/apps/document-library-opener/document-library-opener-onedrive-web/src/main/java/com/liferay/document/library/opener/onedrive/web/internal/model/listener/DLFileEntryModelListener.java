/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveConstants;
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
						DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
						fileEntry);

			if (dlOpenerFileEntryReference != null) {
				_dlOpenerFileEntryReferenceLocalService.
					deleteDLOpenerFileEntryReference(
						DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
						fileEntry);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

}