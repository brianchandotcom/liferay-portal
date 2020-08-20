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
public interface AbsolutePortalURLBuilder {

	/**
	 * Returns a URL builder for Portal images. Image resources live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_IMAGE}.
	 *
	 * <p>
	 * Image resources are retrieved from a CDN host if present or from Portal
	 * otherwise.
	 * </p>
	 *
	 * @param  relativeURL the image's relative URL
	 * @return a URL builder for Portal images
	 */
	public ImageAbsolutePortalURLBuilder forImage(String relativeURL);

	/**
	 * Returns a URL builder for Portal's main resources. Main resources live in
	 * {@code com.liferay.portal.kernel.util.Portal#PATH_MAIN}.
	 *
	 * <p>
	 * Main resources are always retrieved from the Portal, even if a CDN host
	 * is present.
	 * </p>
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a URL builder for Portal's main resources
	 */
	public MainAbsolutePortalURLBuilder forMain(String relativeURL);

	/**
	 * Returns a URL builder for module resources. Module resources live in
	 * {@code com.liferay.portal.kernel.util.Portal#PATH_MODULE} + the bundle's
	 * web context path.
	 *
	 * <p>
	 * Module resources are retrieved from a CDN host if present or from the
	 * Portal otherwise.
	 * </p>
	 *
	 * @param  bundle the bundle that contains the resource
	 * @param  relativeURL the resource's relative URL
	 * @return a URL builder for module resources
	 */
	public ModuleAbsolutePortalURLBuilder forModule(
		Bundle bundle, String relativeURL);

	/**
	 * Returns a URL builder for portlet dependency resources. Portlet
	 * dependency resources live in the portal's root path.
	 *
	 * <p>
	 * Portlet dependency resources are retrieved from a configured CSS URN or
	 * JS URN if present. (See
	 * <code>com.liferay.portal.kernel.util.PropsKeys#PORTLET_DEPENDENCY_CSS_URN</code>
	 * and <code>PropsKeys#PORTLET_DEPENDENCY_JAVASCRIPT_URN</code>).
	 * </p>
	 *
	 * <p>
	 * If neither are present, the resource is retrieved from a CDN host if
	 * present, or Portal otherwise.
	 * </p>
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
	 * Returns a URL builder for arbitrary resources. Arbitrary resources live
	 * in Portal's root path. It can be {@code /} if Portal installed as the
	 * root web app, or {@code /some-other-path} based on its context). See
	 * {@code
	 * com.liferay.portal.spring.context.PortalContextLoaderListener#getPortalServletContextPath(
	 * )} for more details.
	 *
	 * <p>
	 * <b>Warning:</b> Only use this method if none of the other methods meet
	 * your needs. Otherwise, you may end up hard coding configurable paths.
	 * </p>
	 *
	 * <p>
	 * Arbitrary resources are retrieved from a CDN host if present or from the
	 * Portal otherwise.
	 * </p>
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a URL builder for arbitrary resources
	 */
	public ResourceAbsolutePortalURLBuilder forResource(String relativeURL);

	/**
	 * Returns a URL builder for OSGi whiteboard servlet instances. This method
	 * requires the servlet class to be annotated with the OSGi {@code
	 * @Component}. OSGi whiteboard servlets live in {@code
	 * com.liferay.portal.kernel.util.Portal#PATH_MODULE}.
	 *
	 * <p>
	 * Whiteboard resources are always retrieved from Portal, even if a CDN host
	 * is present.
	 * </p>
	 *
	 * @param  servletPattern the value of the {@code
	 *         osgi.http.whiteboard.servlet.pattern} property
	 * @return a URL builder for OSGi whiteboard servlet instances
	 */
	public WhiteboardAbsolutePortalURLBuilder forWhiteboard(
		String servletPattern);

	/**
	 * Returns a version of this URL builder that ignores the CDN part. See
	 * {@code
	 * com.liferay.portal.kernel.util.Portal#getCDNHost(
	 * javax.servlet.http.HttpServletRequest)} for details.
	 *
	 * @return a version of this URL builder that ignores the CDN part
	 */
	public AbsolutePortalURLBuilder ignoreCDNHost();

	/**
	 * Returns a version of this URL builder that ignores the path proxy part.
	 * See {@code com.liferay.portal.kernel.util.Portal#getPathProxy()} for
	 * details.
	 *
	 * @return a version of this URL builder that ignores the path proxy part
	 */
	public AbsolutePortalURLBuilder ignorePathProxy();

}