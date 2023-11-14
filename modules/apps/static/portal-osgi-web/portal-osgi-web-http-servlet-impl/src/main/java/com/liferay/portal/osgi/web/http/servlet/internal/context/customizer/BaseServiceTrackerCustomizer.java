/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context.customizer;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Dante Wang
 */
public abstract class BaseServiceTrackerCustomizer<S, T>
	implements ServiceTrackerCustomizer<S, T> {

	public BaseServiceTrackerCustomizer(
		BundleContext bundleContext, ContextController contextController,
		HttpServletEndpointController httpServletEndpointController) {

		this.bundleContext = bundleContext;
		this.contextController = contextController;
		this.httpServletEndpointController = httpServletEndpointController;
	}

	protected BundleContext bundleContext;
	protected ContextController contextController;
	protected HttpServletEndpointController httpServletEndpointController;

}