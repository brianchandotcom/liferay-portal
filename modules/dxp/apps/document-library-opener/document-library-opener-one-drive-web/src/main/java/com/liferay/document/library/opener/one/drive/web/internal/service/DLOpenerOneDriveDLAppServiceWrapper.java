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

package com.liferay.document.library.opener.one.drive.web.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.opener.one.drive.web.internal.DLOpenerOneDriveManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a service wrapper responsible for uploading, updating, or deleting
 * the One Drive file linked to a Documents and Media file entry.
 *
 * @author Cristina González
 * @review
 */
@Component(service = ServiceWrapper.class)
public class DLOpenerOneDriveDLAppServiceWrapper extends DLAppServiceWrapper {

	public DLOpenerOneDriveDLAppServiceWrapper() {
		super(null);
	}

	public DLOpenerOneDriveDLAppServiceWrapper(DLAppService dlAppService) {
		super(dlAppService);
	}

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		super.cancelCheckOut(fileEntryId);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) &&
			_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			_dlOpenerOneDriveManager.deleteFile(_getUserId(), fileEntry);

			if (DLFileEntryConstants.VERSION_DEFAULT.equals(
					fileEntry.getVersion())) {

				deleteFileEntry(fileEntryId);
			}
		}
	}

	private long _getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLOpenerOneDriveDLAppServiceWrapper.class);

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

}