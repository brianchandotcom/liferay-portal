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

import com.liferay.user.associated.data.model.UADEntity;
import com.liferay.user.associated.data.model.UADEntityAggregator;
import com.liferay.user.associated.data.model.impl.UADEntityAggregatorImpl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.announcements.uad.AnnouncementsUADEntityImpl"},
	service = UADEntityAggregator.class
)
public class AnnouncementsUADEntityAggregatorImpl
	extends UADEntityAggregatorImpl {

	@Override
	public List<UADEntity> getUADEntities(long userId) {
		List<UADEntity> announcementsUADEntities = new ArrayList<>();

		AnnouncementsUADEntityImpl announcementsUADEntity =
			new AnnouncementsUADEntityImpl(
				0, "testEntityId", AnnouncementsUADEntityImpl.class.getName(),
				new ArrayList<UADEntity>());

		announcementsUADEntities.add(announcementsUADEntity);

		return announcementsUADEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) {
		return new AnnouncementsUADEntityImpl(
			0, uadEntityId,
			"com.liferay.announcements.uad.AnnouncementsUADEntityImpl",
			new ArrayList<UADEntity>());
	}

}