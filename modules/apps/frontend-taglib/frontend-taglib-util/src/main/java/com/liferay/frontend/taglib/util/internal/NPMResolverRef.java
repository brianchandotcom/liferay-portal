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

package com.liferay.frontend.taglib.util.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.util.TagAccessor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolverRef implements AutoCloseable {

	public NPMResolverRef(TagAccessor tagAccessor) {
		Bundle bundle = FrameworkUtil.getBundle(tagAccessor.getClass());

		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			NPMResolver.class);

		_npmResolver = _bundleContext.getService(_serviceReference);
	}

	@Override
	public void close() {
		_bundleContext.ungetService(_serviceReference);
	}

	public NPMResolver getNPMResolver() {
		return _npmResolver;
	}

	private final BundleContext _bundleContext;
	private final NPMResolver _npmResolver;
	private final ServiceReference<NPMResolver> _serviceReference;

}