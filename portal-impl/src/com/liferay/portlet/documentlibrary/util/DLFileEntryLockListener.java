/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.lock.BaseLockListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;

/**
 * @author Alexander Chow
 */
public class DLFileEntryLockListener extends BaseLockListener {

	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public void onAfterExpire(String key) {
		long fileEntryId = GetterUtil.getLong(key);

		try {
			if (_DL_FILE_ENTRY_LOCK_POLICY == 1) {
				DLFileEntryServiceUtil.checkInFileEntry(
					fileEntryId, true, "Automatic timeout checkin",
					new ServiceContext());

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Lock expired and automatically checked in " +
							fileEntryId);
				}
			}
			else {
				DLFileEntryServiceUtil.cancelCheckOut(fileEntryId);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Lock expired and automatically canceled checkout of " +
							fileEntryId);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final int _DL_FILE_ENTRY_LOCK_POLICY = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.DL_FILE_ENTRY_LOCK_POLICY));

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryLockListener.class);

}