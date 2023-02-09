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

package com.liferay.portal.osgi.web.portlet.container.error.test;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Lourdes Fernández Besada
 */
public class UncontrolledExceptionErrorPortlet extends MVCPortlet {

	public static final String PORTLET_NAME =
		"com_liferay_portal_portlet_container_error_test_" +
			"UncontrolledExceptionErrorPortlet";

	public UncontrolledExceptionErrorPortlet() {
		_title = RandomTestUtil.randomString();
	}

	public boolean isCalledRender() {
		return _calledRender;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		super.render(renderRequest, renderResponse);

		_calledRender = true;

		throw new RuntimeException();
	}

	protected String getTitle() {
		return _title;
	}

	@Override
	protected String getTitle(RenderRequest renderRequest) {
		return getTitle();
	}

	private boolean _calledRender;
	private final String _title;

}