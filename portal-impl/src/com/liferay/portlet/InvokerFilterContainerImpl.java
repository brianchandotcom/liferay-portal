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
package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Portlet;

import java.io.Closeable;

import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Raymond Augé
 */
public class InvokerFilterContainerImpl
	implements Closeable, InvokerFilterContainer {

	public InvokerFilterContainerImpl(
			Portlet portlet, PortletContext portletContext)
		throws PortletException {

	}

	@Override
	public void close() {
	}

	@Override
	public List<ActionFilter> getActionFilters() {
		return _actionFilters;
	}

	@Override
	public List<EventFilter> getEventFilters() {
		return _eventFilters;
	}

	@Override
	public List<RenderFilter> getRenderFilters() {
		return _renderFilters;
	}

	@Override
	public List<ResourceFilter> getResourceFilters() {
		return _resourceFilters;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvokerFilterContainerImpl.class);

	private List<ActionFilter> _actionFilters;
	private List<EventFilter> _eventFilters;
	private List<RenderFilter> _renderFilters;
	private List<ResourceFilter> _resourceFilters;

}