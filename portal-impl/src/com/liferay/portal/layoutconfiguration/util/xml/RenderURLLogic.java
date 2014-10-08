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

package com.liferay.portal.layoutconfiguration.util.xml;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class RenderURLLogic extends ActionURLLogic {

	public static final String CLOSE_1_TAG = "</runtime-render-url>";

	public static final String CLOSE_2_TAG = "/>";

	public static final String OPEN_TAG = "<runtime-render-url";

	public RenderURLLogic(RenderResponse renderResponse) {
		super(renderResponse);
	}

	@Override
	public String getClose1Tag() {
		return CLOSE_1_TAG;
	}

	@Override
	public String getLifecycle() {
		return _LIFECYCLE;
	}

	@Override
	public String getOpenTag() {
		return OPEN_TAG;
	}

	private static final String _LIFECYCLE = PortletRequest.RENDER_PHASE;

}