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

package com.liferay.announcements.uad.aggregator;

import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.user.associated.data.aggregator.DynamicQueryUADAggregator;

/**
 * @author Drew Brokke
 */
public abstract class BaseAnnouncementsUADAggregator<T extends BaseModel>
	extends DynamicQueryUADAggregator<T> {

	@Override
	public String getApplicationName() {
		return AnnouncementsUADConstants.APPLICATION_NAME;
	}

}