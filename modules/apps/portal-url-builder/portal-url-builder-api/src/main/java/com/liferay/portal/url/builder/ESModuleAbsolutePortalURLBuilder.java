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
import com.liferay.portal.url.builder.facet.CDNAwareAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.CacheAwareAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.PathProxyAwareAbsolutePortalURLBuilder;

/**
 * Builds a URL to retrieve an ECMAScript Module file living inside a bundle.
 *
 * <p>
 * ESM scripts sometimes have additional parameters to account for RTL
 * support, language, etc. Those are automagically inferred from the request.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface ESModuleAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder,
			CacheAwareAbsolutePortalURLBuilder
				<ESModuleAbsolutePortalURLBuilder>,
			CDNAwareAbsolutePortalURLBuilder<ESModuleAbsolutePortalURLBuilder>,
			PathProxyAwareAbsolutePortalURLBuilder
				<ESModuleAbsolutePortalURLBuilder> {
}