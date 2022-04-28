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

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;

/**
 * Builds a JavaScript combo request URL.
 *
 * <p>
 * Combo requests are cacheable but don't implement the {@link
 * com.liferay.portal.url.builder.facet.CacheAwareAbsolutePortalURLBuilder}
 * interface because there was an old mechanism in place that doesn't conform
 * exactly to the new semantics.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface ComboRequestAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder {

	/**
	 * Add a file to the combo request.
	 *
	 * @param filePath the absolute URL of the file to gather
	 */
	public ComboRequestAbsolutePortalURLBuilder addFile(String filePath);

	/**
	 * Set the value to use for the `t` parameter that controls caching.
	 */
	public ComboRequestAbsolutePortalURLBuilder setTimestamp(long timestamp);

}