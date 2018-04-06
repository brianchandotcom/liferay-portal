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

package com.liferay.portal.search.elasticsearch.shield.internal;

import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true, property = "operation.mode=EMBEDDED",
	service = SettingsContributor.class
)
public class ShieldEmbeddedSettingsContributor extends BaseSettingsContributor {

	public ShieldEmbeddedSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ClientSettingsHelper clientSettingsHelper) {
		clientSettingsHelper.put("shield.enabled", "false");
	}

}