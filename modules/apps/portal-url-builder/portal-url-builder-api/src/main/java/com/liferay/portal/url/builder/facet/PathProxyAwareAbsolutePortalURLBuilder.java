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
 * A URL builder that controls prepending the proxy path ({@link
 * com.liferay.portal.util.PropsValues#PORTAL_PROXY_PATH}) to the URL.
 *
 * <p>
 * By default, the proxy path is always used. However, there are scenarios where
 * the developer may need to strip it from the generated URL (for example, when
 * wanting to retrieve the internal URL of a resource).
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface PathProxyAwareAbsolutePortalURLBuilder<T> {

	/**
	 * Returns a version of this URL builder that ignores the proxy path part.
	 *
	 * @return a version of this URL builder that ignores the proxy path part
	 */
	public T ignorePathProxy();

}