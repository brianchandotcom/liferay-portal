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
 * A URL builder that can control caching of underlying resource using a URL
 * parameter (`t` or `mac`, for example) to invalidate browser caches.
 *
 * <p>
 * By default, cacheable resources use the {@link CachePolicy#UNTIL_CHANGED}
 * policy unless noted otherwise in the documentation.
 * </p>
 *
 * <p>
 * The {@link com.liferay.portal.url.builder.AbsolutePortalURLBuilder} framework
 * guarantees that the returned URLs will trigger a browser cache invalidation
 * when needed. This may be implemented by looking at the contents or metadata
 * of files in the server or using other similar techniques.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface CacheAwareAbsolutePortalURLBuilder<T> {

	/**
	 * Control the cache policy of this URL builder.
	 *
	 * @return a version of this URL builder that applies the requested policy
	 */
	public T cache(CachePolicy cachePolicy);

	public enum CachePolicy {

		/**
		 * Returns a version of this URL builder that returns the same URL each
		 * time it is built so that it can be cached by the browser forever (if
		 * the Cache-Control headers allow it).
		 */
		FOREVER,

		/**
		 * Returns a version of this URL builder that changes the URL each time
		 * it is built so that it can never be cached by the browser.
		 */
		NEVER,

		/**
		 * Returns a version of this URL builder that changes the URL each time
		 * the resource changes.
		 */
		UNTIL_CHANGED,

	}

}