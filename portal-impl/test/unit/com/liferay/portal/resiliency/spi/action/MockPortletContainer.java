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

package com.liferay.portal.resiliency.spi.action;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class MockPortletContainer implements PortletContainer {

	public static final String MOCK_LAYOUT_TYPE_SETTINGS =
		"MOCK_LAYOUT_TYPE_SETTINGS";

	@Override
	public void preparePortlet(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		prepared = true;
	}

	@Override
	public ActionResult processAction(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet) {

		request = httpServletRequest;
		response = httpServletResponse;
		this.portlet = portlet;

		if (modifyLayoutTypeSettings) {
			Layout requestLayout = (Layout)httpServletRequest.getAttribute(
				WebKeys.LAYOUT);

			requestLayout.setTypeSettings(MOCK_LAYOUT_TYPE_SETTINGS);
		}

		actionResult = new ActionResult(null, null);

		return actionResult;
	}

	@Override
	public List<Event> processEvent(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet, Layout layout,
		Event event) {

		request = httpServletRequest;
		response = httpServletResponse;
		this.portlet = portlet;
		this.layout = layout;
		this.event = event;

		if (modifyLayoutTypeSettings) {
			Layout requestLayout = (Layout)httpServletRequest.getAttribute(
				WebKeys.LAYOUT);

			requestLayout.setTypeSettings(MOCK_LAYOUT_TYPE_SETTINGS);
		}

		events = new ArrayList<>();

		return events;
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout) {
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {
	}

	@Override
	public void render(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet) {

		request = httpServletRequest;
		response = httpServletResponse;
		this.portlet = portlet;
	}

	@Override
	public void renderHeaders(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet) {

		request = httpServletRequest;
		response = httpServletResponse;
		this.portlet = portlet;
	}

	@Override
	public void serveResource(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet) {

		request = httpServletRequest;
		response = httpServletResponse;
		this.portlet = portlet;
	}

	public ActionResult actionResult;
	public Event event;
	public List<Event> events;
	public Layout layout;
	public boolean modifyLayoutTypeSettings;
	public Portlet portlet;
	public boolean prepared;
	public HttpServletRequest request;
	public HttpServletResponse response;

}