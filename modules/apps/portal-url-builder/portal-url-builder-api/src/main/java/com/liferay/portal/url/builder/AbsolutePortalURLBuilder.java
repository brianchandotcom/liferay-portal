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

/**
 * A builder for constructing absolute URLs pointing to portal resources.
 * @author Iván Zaera Avellón
 * @review
 */
public interface AbsolutePortalURLBuilder {

	/**
	 * Configure this builder to return URLs for portal images. Image resources
	 * live in {@link com.liferay.portal.kernel.util.Portal#getPathImage()}.
	 * @param relativeURL the relative URL of the image
	 * @return a builder that returns image URLs
	 * @see BuildableAbsolutePortalURLBuilder
	 * @review
	 */
	public ImageAbsolutePortalURLBuilder forImage(String relativeURL);

	/**
	 * Configure this builder to return URLs for portal main resources. Main
	 * resources live in {@link com.liferay.portal.kernel.util.Portal#getPathMain()}.
	 * @param relativeURL the relative URL of the resource
	 * @return a builder that returns main resource URLs
	 * @see BuildableAbsolutePortalURLBuilder
	 * @review
	 */
	public MainAbsolutePortalURLBuilder forMain(String relativeURL);

	/**
	 * Configure this builder to return URLs for module resources. Module
	 * resources live in {@link com.liferay.portal.kernel.util.Portal#getPathModule()}.
	 * @param relativeURL the relative URL of the resource
	 * @return a builder that returns module resource URLs
	 * @see BuildableAbsolutePortalURLBuilder
	 * @review
	 */
	public ModuleAbsolutePortalURLBuilder forModule(String relativeURL);

	/**
	 * Configure this builder to return URLs for arbitrary resources. Arbitrary
	 * resources live in the root path of the portal.
	 * @param relativeURL the relative URL of the resource
	 * @return a builder that returns arbitrary resource URLs
	 * @see BuildableAbsolutePortalURLBuilder
	 * @review
	 */
	public ResourceAbsolutePortalURLBuilder forResource(String relativeURL);

	/**
	 * Configure this builder to return absolute URLs without the CDN part.
	 * @return the same builder
	 * @see com.liferay.portal.kernel.util.Portal#getCDNHost(javax.servlet.http.HttpServletRequest)
	 * @review
	 */
	public AbsolutePortalURLBuilder ignoreCDNHost();

	/**
	 * Configure this builder to return absolute URLs without the proxy part.
	 * @return the same builder
	 * @see com.liferay.portal.kernel.util.Portal#getPathProxy()
	 * @review
	 */
	public AbsolutePortalURLBuilder ignorePathProxy();

}