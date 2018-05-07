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

package com.liferay.commerce.user.segment.test.util;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceUserSegmentEntryTestUtil {

	public static CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			long groupId, boolean system)
		throws PortalException {

		return addCommerceUserSegmentEntry(
			groupId, system, RandomTestUtil.randomBoolean(), -1, null);
	}

	public static CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			long groupId, boolean active, boolean system, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext == null) {
			serviceContext = ServiceContextTestUtil.getServiceContext(groupId);
		}

		if (userId > 0) {
			serviceContext.setUserId(userId);
		}

		return CommerceUserSegmentEntryLocalServiceUtil.
			addCommerceUserSegmentEntry(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), active, system,
				RandomTestUtil.randomDouble(), serviceContext);
	}

}