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

package com.liferay.portal.search.elasticsearch6.internal.settings;

import com.liferay.portal.search.elasticsearch6.settings.IndexSettingsContributor;
import com.liferay.portal.search.elasticsearch6.settings.IndexSettingsHelper;
import com.liferay.portal.search.elasticsearch6.settings.TypeMappingsHelper;

/**
 * @author André de Oliveira
 */
public class BaseIndexSettingsContributor implements IndexSettingsContributor {

	public BaseIndexSettingsContributor(int priority) {
		_priority = priority;
	}

	@Override
	public int compareTo(IndexSettingsContributor indexSettingsContributor) {
		if (_priority > indexSettingsContributor.getPriority()) {
			return 1;
		}
		else if (_priority == indexSettingsContributor.getPriority()) {
			return 0;
		}

		return -1;
	}

	@Override
	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper) {
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void populate(IndexSettingsHelper indexSettingsHelper) {
	}

	private final int _priority;

}