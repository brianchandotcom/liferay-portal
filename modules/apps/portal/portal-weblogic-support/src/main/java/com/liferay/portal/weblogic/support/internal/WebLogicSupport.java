/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.weblogic.support.internal;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.weblogic.WebLogicIncludeServletResponseFactory;
import com.liferay.portal.weblogic.support.internal.include.WebLogicIncludeServletResponseFactoryImpl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class WebLogicSupport {

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!ServerDetector.isWebLogic()) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			WebLogicIncludeServletResponseFactory.class,
			new WebLogicIncludeServletResponseFactoryImpl(), null);
	}

	@Deactivate
	protected void deactivate() {
		if (!ServerDetector.isWebLogic()) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private ServiceRegistration<WebLogicIncludeServletResponseFactory>
		_serviceRegistration;

}