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

import org.osgi.framework.Bundle;

/**
 * Provides a builder for constructing absolute URLs pointing to portal
 * resources.
 *
 * Depending on the kind of resource specified (determined by the forXXX()
 * method called) the algorithm to build the URL may be different. See the
 * documentation of each forXXX() method to get such information.
 *
 * In general, all URLs honor the proxy path when present unless the CDN host is
 * being used.
 *
 * @author Iván Zaera Avellón
 * @see    BuildableAbsolutePortalURLBuilder
 * @review
 */
public interface AbsolutePortalURLBuilder {

	/**
	 * Returns URLs for portal images. Image resources live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_IMAGE}.
	 *
	 * Image resources are retrieved from the CDN host when present or from the
	 * Portal otherwise.
	 *
	 * @param  relativeURL the image's relative URL
	 * @return a builder that returns image URLs
	 * @review
	 */
	public ImageAbsolutePortalURLBuilder forImage(String relativeURL);

	/**
	 * Returns URLs for portal's main resources. Main resources live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_MAIN}.
	 *
	 * Main resources are always retrieved from the Portal even if a CDN host
	 * is present.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns main resource URLs
	 * @review
	 */
	public MainAbsolutePortalURLBuilder forMain(String relativeURL);

	/**
	 * Returns URLs for module resources. Module resources live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_MODULE} + bundle's web context
	 * path.
	 *
	 * Module resources are retrieved from the CDN host when present or from the
	 * Portal otherwise.
	 *
	 * @param  bundle the bundle that contains the resource
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns module resource URLs
	 * @review
	 */
	public ModuleAbsolutePortalURLBuilder forModule(
		Bundle bundle, String relativeURL);

	/**
	 * Returns URLs for portlet dependency resources. Portlet dependency
	 * resources live in the portal's root path.
	 *
	 * Portlet dependency resources are retrieved from the configured CSS or JS
	 * URN (see {@link PropsKeys#PORTLET_DEPENDENCY_CSS_URN} and
	 * {@link PropsKeys#PORTLET_DEPENDENCY_JAVASCRIPT_URN}) when present.
	 *
	 * If not present, the resource will be retrieved from the CDN host when
	 * present or the Portal itself otherwise.
	 *
	 * @param  portletDependency the portlet dependency resource
	 * @param  cssURN the URN for CSS portlet dependency resources
	 * @param  javaScriptURN the URN for JavaScript portlet dependency resources
	 * @return a builder that returns portlet dependency resource URLs
	 * @review
	 */
	public PortletDependencyAbsolutePortalURLBuilder forPortletDependency(
		PortletDependency portletDependency, String cssURN,
		String javaScriptURN);

	/**
	 * Returns URLs for arbitrary resources. Arbitrary resources live in the
	 * portal's root path (that can be {@code /} or {@code /something} if the
	 * portal has not been installed as the ROOT webapp). See {@code
	 * com.liferay.portal.spring.context.PortalContextLoaderListener#getPortalServletContextPath(
	 * )} for more details.
	 *
	 * <p>
	 * <b>Warning:</b> Do not use this method unless none of the others serve
	 * your purpose. Otherwise, you may end up hard coding configurable paths.
	 * </p>
	 *
	 * Arbitrary resources are retrieved from the CDN host when present or from
	 * the Portal otherwise.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns arbitrary resource URLs
	 */
	public ResourceAbsolutePortalURLBuilder forResource(String relativeURL);

	/**
	 * Returns URLs for OSGi whiteboard servlet instances. The servlet class
	 * must be annotated with the OSGi {@code @Component} annotation for this
	 * method to work. OSGi whiteboard servlets live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_MODULE}.
	 *
	 * Whiteboard resources are always retrieved from the Portal even if a CDN
	 * host is present.
	 *
	 * @param  servletPattern the value of the {@code
	 *         osgi.http.whiteboard.servlet.pattern} property
	 * @return a builder that returns servlet URLs
	 */
	public WhiteboardAbsolutePortalURLBuilder forWhiteboard(
		String servletPattern);

	/**
	 * Returns absolute URLs without the CDN part. See {@code
	 * com.liferay.portal.kernel.util.Portal#getCDNHost(
	 * javax.servlet.http.HttpServletRequest)} for details.
	 *
	 * @return the same builder
	 */
	public AbsolutePortalURLBuilder ignoreCDNHost();

	/**
	 * Returns absolute URLs without the proxy part. See {@code
	 * com.liferay.portal.kernel.util.Portal#getPathProxy()} for details.
	 *
	 * @return the same builder
	 */
	public AbsolutePortalURLBuilder ignorePathProxy();

}