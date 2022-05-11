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

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener
	extends BaseAnalyticsDXPEntityModelListener<Group> {

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		if (!analyticsConfigurationTracker.isActive() || !isTracked(group)) {
			return;
		}

		updateConfigurationProperties(
			group.getCompanyId(), "syncedGroupIds",
			String.valueOf(group.getGroupId()), "liferayAnalyticsGroupIds");
	}

	@Override
	protected boolean isTracked(Group group) {
		if (group.isSite()) {
			return true;
		}

		return false;
	}

}