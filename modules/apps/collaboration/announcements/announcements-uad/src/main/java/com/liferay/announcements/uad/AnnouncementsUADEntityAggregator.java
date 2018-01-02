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
import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_UAD_ENTITY
	},
	service = UADEntityAggregator.class
)
public class AnnouncementsUADEntityAggregator extends BaseUADEntityAggregator {

	@Override
	public List<UADEntity> getUADEntities(long userId) {
		List<UADEntity> announcementsUADEntities = new ArrayList<>();

		UADEntity announcementsUADEntity = new AnnouncementsUADEntity(
			0, "testEntityId");

		announcementsUADEntities.add(announcementsUADEntity);

		return announcementsUADEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) {
		return new AnnouncementsUADEntity(0, uadEntityId);
	}

}