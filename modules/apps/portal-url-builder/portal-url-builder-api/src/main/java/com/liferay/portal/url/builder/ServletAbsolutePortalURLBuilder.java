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
 * Builds a URL that connects to a dynamic servlet.
 *
 * <p>
 * API requests are, by definition, not cacheable and should not be stored in
 * CDNs.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface ServletAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder {
}