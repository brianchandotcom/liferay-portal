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

package com.liferay.portal.url.builder.internal;

import com.liferay.portal.url.builder.BundleScriptAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.CacheHelper;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class BundleScriptAbsolutePortalURLBuilderImpl
	extends BundleResourceAbsolutePortalURLBuilderImplBase
		<BundleScriptAbsolutePortalURLBuilder>
	implements BundleScriptAbsolutePortalURLBuilder {

	public BundleScriptAbsolutePortalURLBuilderImpl(
		Bundle bundle, CacheHelper cacheHelper, String cdnHost,
		String pathModule, String pathProxy, String relativeURL) {

		super(bundle, cacheHelper, cdnHost, pathModule, pathProxy, relativeURL);
	}

}