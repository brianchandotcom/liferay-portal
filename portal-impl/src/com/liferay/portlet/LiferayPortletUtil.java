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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.internal.PortletRequestImpl;
import com.liferay.portlet.internal.PortletResponseImpl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.filter.PortletRequestWrapper;
import javax.portlet.filter.PortletResponseWrapper;

/**
 * @author Neil Griffin
 */
public class LiferayPortletUtil {

	public static LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest) {

		if (portletRequest == null) {
			return null;
		}

		while (!(portletRequest instanceof PortletRequestImpl)) {
			if (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper =
					(PortletRequestWrapper)portletRequest;

				portletRequest = portletRequestWrapper.getRequest();
			}
			else {
				throw new RuntimeException(
					"Unable to unwrap the portlet request from " +
						portletRequest.getClass());
			}
		}

		return (PortletRequestImpl)portletRequest;
	}

	public static LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		if (portletResponse == null) {
			return null;
		}

		while (!(portletResponse instanceof PortletResponseImpl)) {
			if (portletResponse instanceof PortletResponseWrapper) {
				PortletResponseWrapper portletResponseWrapper =
					(PortletResponseWrapper)portletResponse;

				portletResponse = portletResponseWrapper.getResponse();
			}
			else {
				throw new RuntimeException(
					"Unable to unwrap the portlet response from " +
						portletResponse.getClass());
			}
		}

		return (PortletResponseImpl)portletResponse;
	}

}