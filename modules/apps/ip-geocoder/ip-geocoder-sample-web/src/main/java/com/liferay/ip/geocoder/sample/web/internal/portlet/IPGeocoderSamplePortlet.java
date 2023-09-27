/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder.sample.web.internal.portlet;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;
import com.liferay.ip.geocoder.sample.web.internal.constants.IPGeocoderSamplePortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-ip-geocoder-sample",
		"com.liferay.portlet.display-category=category.tools",
		"javax.portlet.display-name=IP Geocoder Sample",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + IPGeocoderSamplePortletKeys.IP_GEOCODER_SAMPLE,
		"javax.portlet.portlet-info.keywords=IP Geocoder Sample",
		"javax.portlet.portlet-info.short-title=IP Geocoder Sample",
		"javax.portlet.portlet-info.title=IP Geocoder Sample",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class IPGeocoderSamplePortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		renderRequest.setAttribute(
			IPInfo.class.getName(), _ipGeocoder.getIPInfo(httpServletRequest));

		super.doView(renderRequest, renderResponse);
	}

	@Reference
	private IPGeocoder _ipGeocoder;

	@Reference
	private Portal _portal;

}