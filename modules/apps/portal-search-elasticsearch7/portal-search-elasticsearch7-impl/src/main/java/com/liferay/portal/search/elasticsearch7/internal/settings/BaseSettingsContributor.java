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

package com.liferay.portal.search.elasticsearch7.internal.settings;

import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

/**
 * @author Michael C. Han
 */
public abstract class BaseSettingsContributor implements SettingsContributor {

	public BaseSettingsContributor(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(SettingsContributor settingsContributor) {
		if (priority > settingsContributor.getPriority()) {
			return 1;
		}
		else if (priority == settingsContributor.getPriority()) {
			return 0;
		}

		return -1;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	protected int priority;

}