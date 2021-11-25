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

package com.liferay.frontend.data.set.view.timeline;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;

/**
 * @author Marco Leo
 */
public abstract class BaseTimelineFDSView implements FDSView {

	@Override
	public String getContentRenderer() {
		return FDSConstants.TIMELINE;
	}

	public abstract String getDate();

	public abstract String getDescription();

	@Override
	public String getLabel() {
		return FDSConstants.TIMELINE;
	}

	@Override
	public String getName() {
		return FDSConstants.TIMELINE;
	}

	@Override
	public String getThumbnail() {
		return FDSConstants.TIMELINE;
	}

	public abstract String getTitle();

}