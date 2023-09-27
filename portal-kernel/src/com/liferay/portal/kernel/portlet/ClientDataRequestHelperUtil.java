/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.IOException;

import java.util.Collection;

import javax.portlet.PortletException;

import javax.servlet.http.Part;

/**
 * @author Jiefeng Wu
 */
public class ClientDataRequestHelperUtil {

	public static Part getPart(String name, Object request, Portlet portlet)
		throws IOException, PortletException {

		return _clientDataRequestHelper.getPart(name, request, portlet);
	}

	public static Collection<Part> getParts(Object request, Portlet portlet)
		throws IOException, PortletException {

		return _clientDataRequestHelper.getParts(request, portlet);
	}

	private static volatile ClientDataRequestHelper _clientDataRequestHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			ClientDataRequestHelper.class, ClientDataRequestHelperUtil.class,
			"_clientDataRequestHelper", false);

}