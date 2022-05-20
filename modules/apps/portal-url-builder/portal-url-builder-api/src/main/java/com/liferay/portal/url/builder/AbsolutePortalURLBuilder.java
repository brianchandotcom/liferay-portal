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

package com.liferay.portal.url.builder;

import com.liferay.portal.kernel.model.portlet.PortletDependency;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;

/**
 * Provides builders for constructing absolute URLs pointing to portal
 * resources.
 *
 * <p>
 * Each <code>for[Resource]</code> method returns a URL builder for the named
 * resource. Algorithms may differ between builders. In general, the builders
 * construct URLs that honor existing proxy paths unless a CDN host is being
 * used.
 * </p>
 *
 * @author Iván Zaera Avellón
 * @see    BuildableAbsolutePortalURLBuilder
 */
@ProviderType
public interface AbsolutePortalURLBuilder {

	/**
	 * Returns a URL builder for AMD JavaScript files.
	 *
	 * @param  browserModulePath the browser module path (e.g.
	 *         /o/js/resolved-module/... or a legacy config generator module
	 *         path too)
	 * @return a URL builder for AMD JavaScript files
	 * @review
	 */
	public BrowserModuleAbsolutePortalURLBuilder forBrowserModule(
		String browserModulePath);

	/**
	 * Returns a URL builder for bundle JavaScript files.
	 *
	 * @param  bundle the bundle that contains the resource
	 * @param  relativeURL the JavaScript file relative URL
	 * @return a URL builder for module scripts
	 * @review
	 */
	public BundleScriptAbsolutePortalURLBuilder forBundleScript(
		Bundle bundle, String relativeURL);

	/**
	 * Returns a URL builder for bundle stylesheets.
	 *
	 * @param  bundle the bundle that contains the resource
	 * @param  relativeURL the stylesheets relative URL
	 * @return a URL builder for module stylesheets
	 * @review
	 */
	public BundleStylesheetAbsolutePortalURLBuilder forBundleStylesheet(
		Bundle bundle, String relativeURL);

	/**
	 * Returns a URL builder for JavaScript combo servlet requests.
	 *
	 * @return a URL builder for combo servlet requests
	 */
	public ComboRequestAbsolutePortalURLBuilder forComboRequest();

	/**
	 * Returns a URL builder for an ECMAScript module.
	 *
	 * @param  webContextPath the context path where the module lives
	 * @param  esModulePath the module path (e.g. /js/index.js)
	 * @return a URL builder for ESM scripts
	 * @review
	 */
	public ESModuleAbsolutePortalURLBuilder forESModule(
		String webContextPath, String esModulePath);

	/**
	 * Returns a URL builder for portal images. Image resources live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_IMAGE}.
	 *
	 * @param  relativeURL the image's relative URL
	 * @return a URL builder for portal images
	 */
	public PortalImageAbsolutePortalURLBuilder forPortalImage(
		String relativeURL);

	/**
	 * Returns a URL builder for portal's main resources. Main resources live in
	 * {@code com.liferay.portal.kernel.util.Portal#PATH_MAIN}.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a URL builder for portal's main resources
	 */
	public PortalMainResourceAbsolutePortalURLBuilder forPortalMainResource(
		String relativeURL);

	/**
	 * Returns a URL builder for portlet dependency resources. Portlet
	 * dependency resources live in the portal's root path.
	 *
	 * @param  portletDependency the portlet dependency resource
	 * @param  cssURN the URN for CSS portlet dependency resources
	 * @param  javaScriptURN the URN for JavaScript portlet dependency resources
	 * @return a URL builder for portlet dependency resources
	 */
	public PortletDependencyAbsolutePortalURLBuilder forPortletDependency(
		PortletDependency portletDependency, String cssURN,
		String javaScriptURN);

	/**
	 * Returns a URL builder for requests to dynamic content returned by
	 * servlets.
	 *
	 * @param  requestURL the API's request URL
	 * @return a URL builder for API requests
	 */
	public ServletAbsolutePortalURLBuilder forServlet(String requestURL);

}