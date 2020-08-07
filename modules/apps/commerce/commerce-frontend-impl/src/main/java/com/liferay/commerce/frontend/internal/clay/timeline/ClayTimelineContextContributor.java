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

package com.liferay.commerce.frontend.internal.clay.timeline;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.timeline.ClayTimelineDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "commerce.data.set.content.renderer.name=timeline",
	service = ClayDataSetContentRendererContextContributor.class
)
public class ClayTimelineContextContributor
	implements ClayDataSetContentRendererContextContributor {

	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof ClayTimelineDataSetDisplayView) {
			return _serialize(
				(ClayTimelineDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		ClayTimelineDataSetDisplayView clayTimelineDataSetDisplayView) {

		JSONObject schemaJSONObject = _jsonFactory.createJSONObject();

		schemaJSONObject.put(
			"date", clayTimelineDataSetDisplayView.getDate()
		).put(
			"description", clayTimelineDataSetDisplayView.getDescription()
		).put(
			"title", clayTimelineDataSetDisplayView.getTitle()
		);

		return HashMapBuilder.<String, Object>put(
			"schema", schemaJSONObject
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}