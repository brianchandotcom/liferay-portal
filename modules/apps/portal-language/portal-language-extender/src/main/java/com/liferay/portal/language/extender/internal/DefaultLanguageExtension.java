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

package com.liferay.portal.language.extender.internal;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.language.LanguageResources;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class DefaultLanguageExtension implements LanguageExtension {

	public DefaultLanguageExtension(
		Bundle bundle, BundleContext bundleContext) {

		_bundle = bundle;
		_bundleContext = bundleContext;
	}

	@Override
	public void destroy() {
		_serviceRegistration.unregister();
	}

	@Override
	public void start() {
		_serviceRegistration = _bundleContext.registerService(
			ResourceBundleLoader.class,
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER,
			HashMapDictionaryBuilder.<String, Object>put(
				"bundle.symbolic.name", _bundle.getSymbolicName()
			).put(
				"service.ranking", Integer.MIN_VALUE
			).put(
				"resource.bundle.base.name", "content.Language"
			).build());
	}

	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private ServiceRegistration<?> _serviceRegistration;

}