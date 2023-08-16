/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BaseSingleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.EmptyBulkSelection;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class SingleUserNotificationEventBulkSelection
	extends BaseSingleEntryBulkSelection<UserNotificationEvent> {

	public SingleUserNotificationEventBulkSelection(
		long userNotificationEventId, Map<String, String[]> parameterMap,
		UserNotificationEventLocalService userNotificationEventLocalService) {

		super(userNotificationEventId, parameterMap);

		_userNotificationEventId = userNotificationEventId;
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
	protected UserNotificationEvent getEntry() throws PortalException {
		return _userNotificationEventLocalService.getUserNotificationEvent(
			_userNotificationEventId);
	}

	@Override
	protected String getEntryName() throws PortalException {
		return StringPool.BLANK;
	}

	private final long _userNotificationEventId;
	private final UserNotificationEventLocalService
		_userNotificationEventLocalService;

}