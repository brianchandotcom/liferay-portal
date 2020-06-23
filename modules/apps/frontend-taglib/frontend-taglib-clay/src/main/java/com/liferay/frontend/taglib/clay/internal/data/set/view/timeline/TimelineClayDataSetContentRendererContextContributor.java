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

package com.liferay.frontend.taglib.clay.internal.data.set.view.timeline;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.timeline.BaseTimelineClayDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.CONTENT_RENDERER_TIMELINE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class TimelineClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof
				BaseTimelineClayDataSetDisplayView) {

			return _serialize(
				(BaseTimelineClayDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTimelineClayDataSetDisplayView timelineClayDataSetDisplayView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"date", timelineClayDataSetDisplayView.getDate()
			).put(
				"description", timelineClayDataSetDisplayView.getDescription()
			).put(
				"title", timelineClayDataSetDisplayView.getTitle()
			)
		).build();
	}

}