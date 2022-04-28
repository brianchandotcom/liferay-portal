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

package com.liferay.portal.url.builder.facet;

/**
 * A URL builder that controls serving by CDN/Portal of underlying resource.
 *
 * <p>
 * By default, CDN aware resources are served from CDN if it is configured.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface CDNAwareAbsolutePortalURLBuilder<T> {

	/**
	 * Returns a version of this URL builder that ignores the CDN part. See
	 * {@code
	 * com.liferay.portal.kernel.util.Portal#getCDNHost(
	 * javax.servlet.http.HttpServletRequest)} for details.
	 *
	 * @return a version of this URL builder that ignores the CDN part
	 */
	public T ignoreCDNHost();

}