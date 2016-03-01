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

package com.liferay.marketplace.deployer;

import com.liferay.marketplace.deployer.handler.LiferayPackageStreamHandlerService;
import com.liferay.marketplace.deployer.installer.LiferayPackageInstaller;
import com.liferay.marketplace.deployer.transformer.LiferayPackageURLTransformer;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class LiferayPackageDeployer {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_urlStreamHandlerServiceServiceRegistration = registerHandlerService(
			bundleContext);
		_artifactUrlTransformerServiceRegistration =
			registerArtifactUrlTransformer(bundleContext);

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new LiferayPackageInstaller(
				bundleContext, _appLocalService, _moduleLocalService));

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_urlStreamHandlerServiceServiceRegistration.unregister();
		_artifactUrlTransformerServiceRegistration.unregister();

		_bundleTracker.close();
	}

	protected ServiceRegistration<?> registerArtifactUrlTransformer(
		BundleContext bundleContext) {

		return bundleContext.registerService(
			ArtifactUrlTransformer.class, new LiferayPackageURLTransformer(),
			null);
	}

	protected ServiceRegistration<?> registerHandlerService(
		BundleContext bundleContext) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"lpkg"});

		return bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new LiferayPackageStreamHandlerService(), properties);
	}

	@Reference(unbind = "-")
	protected void setAppLocalService(AppLocalService appLocalService) {
		_appLocalService = appLocalService;
	}

	@Reference(unbind = "-")
	protected void setModuleLocalService(
		ModuleLocalService moduleLocalService) {

		_moduleLocalService = moduleLocalService;
	}

	private AppLocalService _appLocalService;
	private ServiceRegistration<?> _artifactUrlTransformerServiceRegistration;
	private BundleTracker<Bundle> _bundleTracker;
	private ModuleLocalService _moduleLocalService;
	private ServiceRegistration<?> _urlStreamHandlerServiceServiceRegistration;

}