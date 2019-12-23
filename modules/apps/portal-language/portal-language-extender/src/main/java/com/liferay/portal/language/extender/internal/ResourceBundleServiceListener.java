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

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;

import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(immediate = true, service = ResourceBundleServiceListener.class)
public class ResourceBundleServiceListener implements ServiceListener {

	@Override
	public void serviceChanged(ServiceEvent serviceEvent) {
		CacheResourceBundleLoader.notifyResourceBundleModification();
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		bundleContext.removeServiceListener(this);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		target = ModuleServiceLifecycle.PORTAL_INITIALIZED
	)
	protected void setModuleServiceLifecycle(
			ModuleServiceLifecycle moduleServiceLifecycle)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ResourceBundleServiceListener.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.addServiceListener(
			this,
			"(&(!(javax.portlet.name=*))(language.id=*)(objectClass=" +
				ResourceBundle.class.getName() + "))");
	}

	protected void unsetModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		_bundleContext.removeServiceListener(this);
	}

	private BundleContext _bundleContext;

}