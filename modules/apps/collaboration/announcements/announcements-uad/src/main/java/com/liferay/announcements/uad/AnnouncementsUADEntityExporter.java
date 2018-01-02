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
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.BaseUADEntityExporter;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_UAD_ENTITY
	},
	service = UADEntityExporter.class
)
public class AnnouncementsUADEntityExporter extends BaseUADEntityExporter {

	@Override
	public void export(UADEntity uadEntity) {
	}

	@Override
	protected List<UADEntity> getUADEntities(long userId) {
		return _announcementsUADEntityAggregator.getUADEntities(userId);
	}

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_UAD_ENTITY + ")",
		unbind = "-"
	)
	private UADEntityAggregator _announcementsUADEntityAggregator;

}