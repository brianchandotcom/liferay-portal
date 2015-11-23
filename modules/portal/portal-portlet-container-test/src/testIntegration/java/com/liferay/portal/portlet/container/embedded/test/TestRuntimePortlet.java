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

package com.liferay.portal.portlet.container.embedded.test;

import com.liferay.portal.portlet.container.test.TestPortlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Manuel de la Peña
 */
public class TestRuntimePortlet extends TestPortlet {

	public boolean isCalledRuntime() {
		return _calledRuntime;
	}

	/**
	 * @throws IOException
	 * @throws PortletException
	 */
	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_calledRuntime = true;

		super.render(renderRequest, renderResponse);
	}

	private boolean _calledRuntime;

}