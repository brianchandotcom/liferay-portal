/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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