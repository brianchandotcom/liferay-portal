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

package com.liferay.commerce.frontend.clay.timeline;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetConstants;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;

/**
 * @author Marco Leo
 */
public abstract class ClayTimelineDataSetDisplayView
	implements ClayDataSetDisplayView {

	public String getContentRenderer() {
		return ClayDataSetConstants.CLAY_DATA_SET_CONTENT_RENDERER_TIMELINE;
	}

	public abstract String getDate();

	public abstract String getDescription();

	public String getLabel() {
		return ClayDataSetConstants.CLAY_DATA_SET_CONTENT_RENDERER_TIMELINE;
	}

	public String getThumbnail() {
		return ClayDataSetConstants.CLAY_DATA_SET_CONTENT_RENDERER_TIMELINE;
	}

	public abstract String getTitle();

}