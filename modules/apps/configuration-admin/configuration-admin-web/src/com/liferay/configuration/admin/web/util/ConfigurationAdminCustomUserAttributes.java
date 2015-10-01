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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portlet.tracker.internal.PortletTracker;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.DefaultCustomUserAttributes;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
			"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN
	},
	service = CustomUserAttributes.class
)
public class ConfigurationAdminCustomUserAttributes
	extends DefaultCustomUserAttributes {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle();

		PortletContextBag portletContextBag = PortletContextBagPool.get(
			getServletContextName(bundle));

		if (portletContextBag == null) {
			portletContextBag = new PortletContextBag(
				getServletContextName(bundle));
		}

		portletContextBag.getCustomUserAttributes().put(
			DefaultCustomUserAttributes.class.getName(), this);
	}

	protected String getServletContextName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String header = headers.get("Servlet-Context-Name");

		if (Validator.isNotNull(header)) {
			return header;
		}

		String symbolicName = bundle.getSymbolicName();

		return symbolicName.replaceAll("[^a-zA-Z0-9]", "");
	}

	@Reference(unbind = "-")
	protected void setPortletTracker(PortletTracker portletTracker) {
		_portletTracker = portletTracker;
	}

	private PortletTracker _portletTracker;

}