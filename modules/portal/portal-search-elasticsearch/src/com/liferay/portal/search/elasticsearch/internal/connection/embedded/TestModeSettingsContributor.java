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

package com.liferay.portal.search.elasticsearch.internal.connection.embedded;

import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = SettingsContributor.class
)
public class TestModeSettingsContributor extends BaseSettingsContributor {

	public TestModeSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		builder.put("index.refresh_interval", "1ms");
		builder.put("index.store.type", "memory");
		builder.put("index.translog.flush_threshold_ops", "1");
		builder.put("index.translog.interval", "1ms");
	}

}