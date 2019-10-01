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

package com.liferay.document.library.opener.onedrive.web.internal.lock;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.BaseLockListener;
import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "service.ranking:Integer=100", service = LockListener.class
)
public class DLFileEntryLockListener extends BaseLockListener {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public void onAfterExpire(String key) {
		long fileEntryId = GetterUtil.getLong(key);

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			if (!_dlOpenerOneDriveManager.isConfigured(
					fileEntry.getCompanyId()) ||
				!_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

				_lockListener.onAfterExpire(key);

				return;
			}

			if (PropsValues.DL_FILE_ENTRY_LOCK_POLICY == 1) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUserId(fileEntry.getUserId());

				_dlAppService.checkInFileEntry(
					fileEntryId, DLVersionNumberIncrease.fromMajorVersion(true),
					"Automatic timeout checkin", serviceContext);

				if (_log.isDebugEnabled()) {
					_log.debug("Lock expired and checked in " + fileEntryId);
				}
			}
			else {
				_dlAppService.cancelCheckOut(fileEntryId);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Lock expired and canceled check out of " +
							fileEntryId);
				}
			}
		}
		catch (PortalException pe) {
			_log.error("Error trying to execute onAfterExpire on " + key, pe);
		}
	}

	@Override
	public void onAfterRefresh(String key) {
		_lockListener.onAfterRefresh(key);
	}

	@Override
	public void onBeforeExpire(String key) {
		_lockListener.onBeforeExpire(key);
	}

	@Override
	public void onBeforeRefresh(String key) {
		_lockListener.onBeforeRefresh(key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryLockListener.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference(
		target = "(component.name=com.liferay.document.library.internal.lock.DLFileEntryLockListener)"
	)
	private LockListener _lockListener;

}