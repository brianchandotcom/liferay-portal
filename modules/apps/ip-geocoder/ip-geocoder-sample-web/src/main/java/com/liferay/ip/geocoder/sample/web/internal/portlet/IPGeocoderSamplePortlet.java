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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-ip-geocoder-sample",
		"com.liferay.portlet.display-category=category.tools",
		"javax.portlet.display-name=IP Geocoder Sample",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + IPGeocoderSamplePortletKeys.IP_GEOCODER_SAMPLE,
		"javax.portlet.portlet-info.keywords=IP Geocoder Sample",
		"javax.portlet.portlet-info.short-title=IP Geocoder Sample",
		"javax.portlet.portlet-info.title=IP Geocoder Sample",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class IPGeocoderSamplePortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		ServiceReference<IPGeocoder> serviceReference =
			_bundleContext.getServiceReference(IPGeocoder.class);

		if (serviceReference != null) {
			IPGeocoder ipGeocoder = _bundleContext.getService(serviceReference);

			if (ipGeocoder != null) {
				IPInfo ipInfo = ipGeocoder.getIPInfo(request.getRemoteAddr());

				renderRequest.setAttribute(IPInfo.class.getName(), ipInfo);
			}
		}

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void init() throws PortletException {
		Bundle bundle = FrameworkUtil.getBundle(IPGeocoderSamplePortlet.class);

		_bundleContext = bundle.getBundleContext();

		super.init();
	}

	private static BundleContext _bundleContext;

	@Reference
	private Portal _portal;

}