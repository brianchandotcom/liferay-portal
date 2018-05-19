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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portlet.PortletURLImpl;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Neil Griffin
 */
public class RenderURLImpl extends PortletURLImpl implements RenderURL {

	public RenderURLImpl(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle) {

		super(request, portlet, layout, lifecycle);
	}

	public RenderURLImpl(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		super(request, portlet, layout, lifecycle, copy);
	}

	public RenderURLImpl(
		PortletRequest portletRequest, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		super(portletRequest, portlet, layout, lifecycle, copy);
	}

	@Override
	public String getFragmentIdentifier() {

		// TODO

		throw new UnsupportedOperationException();
	}

	@Override
	public void setFragmentIdentifier(String fragment) {

		// TODO

		throw new UnsupportedOperationException();
	}

}