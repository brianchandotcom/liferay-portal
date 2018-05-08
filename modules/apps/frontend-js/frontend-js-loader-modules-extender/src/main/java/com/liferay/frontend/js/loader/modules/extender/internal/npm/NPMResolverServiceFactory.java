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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true)
public class NPMResolverServiceFactory implements ServiceFactory<NPMResolver> {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			NPMResolver.class, this, new Hashtable<String, Object>());
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	public NPMResolver getService(
		Bundle bundle, ServiceRegistration<NPMResolver> serviceRegistration) {

		return new NPMResolverImpl(bundle, _jsonFactory, _npmRegistry);
	}

	@Override
	public void ungetService(
		Bundle bundle, ServiceRegistration<NPMResolver> serviceRegistration,
		NPMResolver npmResolver) {
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private JSONFactory _jsonFactory;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private NPMRegistry _npmRegistry;

	private ServiceRegistration<NPMResolver> _serviceRegistration;

}