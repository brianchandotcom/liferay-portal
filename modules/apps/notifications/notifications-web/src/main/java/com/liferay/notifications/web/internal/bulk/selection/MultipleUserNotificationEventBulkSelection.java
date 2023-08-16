/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BaseMultipleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.EmptyBulkSelection;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class MultipleUserNotificationEventBulkSelection
	extends BaseMultipleEntryBulkSelection<UserNotificationEvent> {

	public MultipleUserNotificationEventBulkSelection(
		long[] entryIds, Map<String, String[]> parameterMap,
		UserNotificationEventLocalService userNotificationEventLocalService) {

		super(entryIds, parameterMap);

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return UserNotificationEventBulkSelectionFactory.class;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new EmptyBulkSelection<>();
	}

	@Override
	protected UserNotificationEvent fetchEntry(long entryId) {
		try {
			return _userNotificationEventLocalService.getUserNotificationEvent(
				entryId);
		}
		catch (NoSuchUserNotificationEventException
					noSuchUserNotificationEventException) {

			if (_log.isWarnEnabled()) {
				_log.warn(noSuchUserNotificationEventException);
			}

			return null;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultipleUserNotificationEventBulkSelection.class);

	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}