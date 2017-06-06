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

package com.liferay.powwow.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;

/**
 * @author Shinn Lok
 * @generated
 */
public abstract class PowwowMeetingActionableDynamicQuery
	extends BaseActionableDynamicQuery {
	public PowwowMeetingActionableDynamicQuery() throws SystemException {
		setBaseLocalService(PowwowMeetingLocalServiceUtil.getService());
		setClass(PowwowMeeting.class);

		setClassLoader(com.liferay.powwow.service.ClpSerializer.class.getClassLoader());

		setPrimaryKeyPropertyName("powwowMeetingId");
	}
}