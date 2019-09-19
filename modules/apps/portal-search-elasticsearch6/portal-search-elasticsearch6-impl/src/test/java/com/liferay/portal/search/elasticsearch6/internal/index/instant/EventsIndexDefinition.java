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

package com.liferay.portal.search.elasticsearch6.internal.index.instant;

import com.liferay.portal.search.spi.index.IndexDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		IndexDefinition.PROPERTY_KEY_INDEX_NAME + "=" + EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
		IndexDefinition.PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME + "=" + EventsIndexDefinition.TYPE_MAPPING_FILE_NAME_WORKFLOW_EVENTS
	},
	service = IndexDefinition.class
)
public class EventsIndexDefinition implements IndexDefinition {

	public static final String INDEX_NAME_WORKFLOW_EVENTS = "workflow-events";

	public static final String TYPE_MAPPING_FILE_NAME_WORKFLOW_EVENTS =
		"workflow-events-type-mappings.json";

}