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

package com.liferay.frontend.data.set.internal.view.timeline;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.timeline.BaseTimelineFDSView;
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
	property = "frontend.data.set.view.name=" + FDSConstants.TIMELINE,
	service = FDSViewContextContributor.class
)
public class TimelineFDSViewContextContributor
	implements FDSViewContextContributor {

	@Override
	public Map<String, Object> getFDSViewContext(
		FDSView fdsView, Locale locale) {

		if (fdsView instanceof BaseTimelineFDSView) {
			return _serialize((BaseTimelineFDSView)fdsView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTimelineFDSView baseTimelineFDSView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"date", baseTimelineFDSView.getDate()
			).put(
				"description", baseTimelineFDSView.getDescription()
			).put(
				"title", baseTimelineFDSView.getTitle()
			)
		).build();
	}

}