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

package com.liferay.portal.license.deployer.internal;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.license.deployer.internal.installer.LicenseInstaller;

import org.apache.felix.fileinstall.ArtifactInstaller;
import org.apache.felix.fileinstall.ArtifactListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true, service = {})
public class LicenseDeployerActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		ArtifactInstaller artifactInstaller = new LicenseInstaller();

		_artifactListenerServiceRegistration = registerArtifactListener(
			bundleContext, artifactInstaller);

		_lpkgLicensedBundleTracker = new BundleTracker<>(
			bundleContext, ~Bundle.UNINSTALLED,
			new LPKGLicensedBundleTrackerCustomizer(artifactInstaller));

		_lpkgLicensedBundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_artifactListenerServiceRegistration.unregister();
	}

	protected ServiceRegistration<?> registerArtifactListener(
		BundleContext bundleContext, ArtifactInstaller artifactInstaller) {

		return bundleContext.registerService(
			new String[] {
				ArtifactInstaller.class.getName(),
				ArtifactListener.class.getName()
			},
			artifactInstaller, null);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private ServiceRegistration<?> _artifactListenerServiceRegistration;
	private BundleTracker<Bundle> _lpkgLicensedBundleTracker;

}