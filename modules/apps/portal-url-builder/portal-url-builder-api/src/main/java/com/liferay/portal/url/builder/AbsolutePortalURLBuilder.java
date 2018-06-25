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

/**
 * Provides a builder for constructing absolute URLs pointing to portal
 * resources.
 *
 * @author Iván Zaera Avellón
 * @see    BuildableAbsolutePortalURLBuilder
 */
public interface AbsolutePortalURLBuilder {

	/**
	 * Returns URLs for portal images. Image resources live in {@link
	 * com.liferay.portal.kernel.util.Portal#getPathImage()}.
	 *
	 * @param  relativeURL the image's relative URL
	 * @return a builder that returns image URLs
	 */
	public ImageAbsolutePortalURLBuilder forImage(String relativeURL);

	/**
	 * Returns URLs for portal's main resources. Main resources live in {@link
	 * com.liferay.portal.kernel.util.Portal#getPathMain()}.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns main resource URLs
	 */
	public MainAbsolutePortalURLBuilder forMain(String relativeURL);

	/**
	 * Returns URLs for module resources. Module resources live in {@link
	 * com.liferay.portal.kernel.util.Portal#getPathModule()}.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns module resource URLs
	 */
	public ModuleAbsolutePortalURLBuilder forModule(String relativeURL);

	/**
	 * Returns URLs for portlet dependency resources. Portlet dependency
	 * resources live in the portal's root path.
	 *
	 * @param  portletDependency the portlet dependency resource
	 * @param  cssURN the URN for CSS portlet dependency resources
	 * @param  javaScriptURN the URN for JavaScript portlet dependency resources
	 * @return a builder that returns portlet dependency resource URLs
	 */
	public PortletDependencyAbsolutePortalURLBuilder forPortletDependency(
		PortletDependency portletDependency, String cssURN,
		String javaScriptURN);

	/**
	 * Returns URLs for arbitrary resources. Arbitrary resources live in the
	 * portal's root path.
	 *
	 * @param  relativeURL the resource's relative URL
	 * @return a builder that returns arbitrary resource URLs
	 */
	public ResourceAbsolutePortalURLBuilder forResource(String relativeURL);

	/**
	 * Returns absolute URLs without the CDN part.
	 *
	 * @return the same builder
	 * @see    com.liferay.portal.kernel.util.Portal#getCDNHost(
	 *         javax.servlet.http.HttpServletRequest)
	 */
	public AbsolutePortalURLBuilder ignoreCDNHost();

	/**
	 * Returns absolute URLs without the proxy part.
	 *
	 * @return the same builder
	 * @see    com.liferay.portal.kernel.util.Portal#getPathProxy()
	 */
	public AbsolutePortalURLBuilder ignorePathProxy();

}