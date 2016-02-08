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

import com.liferay.portal.kernel.model.Group;

import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public interface RequestBackedPortletURLFactory {

	public PortletURL createActionURL(String portletId);

	public PortletURL createControlPanelActionURL(
		String portletId, Group group, long refererGroupId, long refererPlid);

	public PortletURL createControlPanelPortletURL(
		String portletId, Group group, long refererGroupId, long refererPlid,
		String lifecycle);

	public PortletURL createControlPanelRenderURL(
		String portletId, Group group, long refererGroupId, long refererPlid);

	public PortletURL createControlPanelResourceURL(
		String portletId, Group group, long refererGroupId, long refererPlid);

	public PortletURL createPortletURL(String portletId, String lifecycle);

	public PortletURL createRenderURL(String portletId);

	public PortletURL createResourceURL(String portletId);

}