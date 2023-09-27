/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;

/**
 * Builds a JavaScript combo request URL.
 *
 * <p>
 * Combo requests are cacheable but do not implement the {@link
 * com.liferay.portal.url.builder.facet.CacheAwareAbsolutePortalURLBuilder}
 * interface because there was an old mechanism in place that does not conform
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
	 * Set the value to use for the "t" parameter that controls caching.
	 */
	public ComboRequestAbsolutePortalURLBuilder setTimestamp(long timestamp);

}