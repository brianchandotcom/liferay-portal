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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.monitoring.MonitorNames;
import com.liferay.portal.monitoring.statistics.BaseDataSample;
import com.liferay.portlet.PortletResponseImpl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Karthik Sudarshan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletRequestDataSample extends BaseDataSample {

	public PortletRequestDataSample(
		PortletRequestType requestType, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			(PortletResponseImpl)portletResponse;

		Portlet portlet = portletResponseImpl.getPortlet();

		setCompanyId(portlet.getCompanyId());
		setUser(portletRequest.getRemoteUser());
		setNamespace(MonitorNames.PORTLET);
		setName(portlet.getPortletName());
		_portletId = portlet.getPortletId();
		_displayName = portlet.getDisplayName();
		_requestType = requestType;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getPortletId() {
		return _portletId;
	}

	public PortletRequestType getRequestType() {
		return _requestType;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{displayName=");
		sb.append(_displayName);
		sb.append(", portletId=");
		sb.append(_portletId);
		sb.append(", requestType=");
		sb.append(_requestType);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private final String _displayName;
	private final String _portletId;
	private final PortletRequestType _requestType;

}