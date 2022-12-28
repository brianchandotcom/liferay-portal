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