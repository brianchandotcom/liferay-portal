/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.internal.search;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.UserNotificationEvent",
	service = BaseSearcher.class
)
public class UserNotificationEventSearcher extends BaseSearcher {

	public UserNotificationEventSearcher() {
		setDefaultSelectedFieldNames(Field.GROUP_ID, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return _CLASS_NAME;
	}

	private static final String _CLASS_NAME =
		UserNotificationEvent.class.getName();

}