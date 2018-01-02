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

package com.liferay.announcements.uad;

import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.user.associated.data.entity.BaseUADEntity;

/**
 * @author William Newbury
 */
public class AnnouncementsUADEntity extends BaseUADEntity {

	public AnnouncementsUADEntity(long userId, String uadEntityId) {
		super(
			userId, uadEntityId,
			AnnouncementsUADConstants.ANNOUNCEMENTS_UAD_ENTITY);
	}

	@Override
	public String getEditURL() {
		return null;
	}

}