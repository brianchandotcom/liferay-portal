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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
@ProviderType
public interface PortletURLFactory {

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, Portlet portlet, Layout layout,
		String lifecycle);

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy);

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, Portlet portlet,
		String lifecycle);

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, String portletId, Layout layout,
		String lifecycle);

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, String portletId, long plid,
		String lifecycle);

	public LiferayPortletURL create(
		HttpServletRequest httpServletRequest, String portletId,
		String lifecycle);

	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, Layout layout,
		String lifecycle);

	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, long plid,
		String lifecycle);

	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, long plid,
		String lifecycle, MimeResponse.Copy copy);

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, Layout layout,
		String lifecycle);

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle);

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle, MimeResponse.Copy copy);

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, String lifecycle);

}