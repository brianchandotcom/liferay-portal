/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.uad.anonymizer;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADAnonymizer.class)
public class DLFileEntryUADAnonymizer extends BaseDLFileEntryUADAnonymizer {

	@Override
	public void autoAnonymize(
			DLFileEntry dlFileEntry, long userId, User anonymousUser)
		throws PortalException {

		List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			_anonymizeDLFileVersion(
				dlFileVersion, userId, anonymousUser.getUserId(),
				anonymousUser.getFullName());
		}

		super.autoAnonymize(dlFileEntry, userId, anonymousUser);
	}

	@Override
	public void delete(DLFileEntry dlFileEntry) throws PortalException {
		_dlAppLocalService.deleteFileEntry(dlFileEntry.getFileEntryId());
	}

	private void _anonymizeDLFileVersion(
		DLFileVersion dlFileVersion, long userId, long anonymousUserId,
		String anonymousUserFullName) {

		if (userId == dlFileVersion.getUserId()) {
			dlFileVersion.setUserId(anonymousUserId);
			dlFileVersion.setUserName(anonymousUserFullName);
		}

		if (userId == dlFileVersion.getStatusByUserId()) {
			dlFileVersion.setStatusByUserId(anonymousUserId);
			dlFileVersion.setStatusByUserName(anonymousUserFullName);
		}

		_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}