/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;

/**
 * @author Shuyang Zhou
 */
public class PortletFriendlyURLMapperMatch {

	public PortletFriendlyURLMapperMatch(
		Portlet portlet, FriendlyURLMapper friendlyURLMapper, int position) {

		_portlet = portlet;
		_friendlyURLMapper = friendlyURLMapper;
		_position = position;
	}

	public FriendlyURLMapper getFriendlyURLMapper() {
		return _friendlyURLMapper;
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public int getPosition() {
		return _position;
	}

	private final FriendlyURLMapper _friendlyURLMapper;
	private final Portlet _portlet;
	private final int _position;

}