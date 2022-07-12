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

package com.liferay.frontend.icons.web.internal.model;

import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.frontend.icons.web.internal.util.SVGUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Bryce Osterhaus
 */
@Component(immediate = true, service = {})
public class FrontendIconsResourcePackTracker {

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", ServletContext.class.getName(),
					")(osgi.web.symbolicname=*))")),
			new FrontendIconsResourcePackServiceTrackerCustomizer(
				bundleContext, _companyLocalService,
				_frontendIconsResourcePackRepository));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	private ServiceTracker
		<ServletContext, ServiceRegistration<FrontendIconsResourcePack>>
			_serviceTracker;

	private static class FrontendIconsResourcePackServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContext, ServiceRegistration<FrontendIconsResourcePack>> {

		@Override
		public ServiceRegistration<FrontendIconsResourcePack> addingService(
			ServiceReference<ServletContext> serviceReference) {

			Bundle bundle = serviceReference.getBundle();

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			if (headers == null) {
				return null;
			}

			String name = headers.get("Liferay-Icons-Pack-Name");
			String path = headers.get("Liferay-Icons-Path");

			if (Validator.isBlank(name) || Validator.isBlank(path)) {
				return null;
			}

			FrontendIconsResourcePack frontendIconsResourcePack =
				_createFrontendIconsResourcePack(bundle, name, path);

			if (frontendIconsResourcePack == null) {
				return null;
			}

			return _bundleContext.registerService(
				FrontendIconsResourcePack.class, frontendIconsResourcePack,
				MapUtil.singletonDictionary("service.ranking", 0));
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContext> serviceReference,
			ServiceRegistration<FrontendIconsResourcePack>
				serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletContext> serviceReference,
			ServiceRegistration<FrontendIconsResourcePack>
				serviceRegistration) {

			Bundle bundle = serviceReference.getBundle();

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			if (headers == null) {
				return;
			}

			String name = headers.get("Liferay-Icons-Pack-Name");

			if (Validator.isBlank(name)) {
				return;
			}

			_companyLocalService.forEachCompanyId(
				companyId ->
					_frontendIconsResourcePackRepository.
						deleteTransientFrontendIconsResourcePack(
							companyId, name));

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private FrontendIconsResourcePackServiceTrackerCustomizer(
			BundleContext bundleContext,
			CompanyLocalService companyLocalService,
			FrontendIconsResourcePackRepository
				frontendIconsResourcePackRepository) {

			_bundleContext = bundleContext;
			_companyLocalService = companyLocalService;
			_frontendIconsResourcePackRepository =
				frontendIconsResourcePackRepository;
		}

		private FrontendIconsResourcePack _createFrontendIconsResourcePack(
			Bundle bundle, String name, String path) {

			Enumeration<URL> entriesEnumeration = bundle.findEntries(
				"/META-INF/resources" + path, "*.svg", true);

			if (entriesEnumeration == null) {
				return null;
			}

			while (entriesEnumeration.hasMoreElements()) {
				URL url = entriesEnumeration.nextElement();

				try {
					FrontendIconsResourcePack frontendIconsResourcePack =
						new FrontendIconsResourcePack(false, name);

					InputStream urlInputStream = url.openStream();

					frontendIconsResourcePack.addFrontendIconsResources(
						SVGUtil.getFrontendIconsResources(
							StringUtil.read(urlInputStream)));

					_companyLocalService.forEachCompanyId(
						companyId -> {
							try {
								_frontendIconsResourcePackRepository.
									addTransientFrontendIconsResourcePack(
										companyId, frontendIconsResourcePack);
							}
							catch (UndeployedExternalRepositoryException
										undeployedExternalRepositoryException) {

								if (_log.isDebugEnabled()) {
									_log.debug(
										undeployedExternalRepositoryException);
								}
							}
						});

					return frontendIconsResourcePack;
				}
				catch (IOException ioException) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioException);
					}
				}
			}

			return null;
		}

		private static final Log _log = LogFactoryUtil.getLog(
			FrontendIconsResourcePackTracker.class);

		private final BundleContext _bundleContext;
		private final CompanyLocalService _companyLocalService;
		private final FrontendIconsResourcePackRepository
			_frontendIconsResourcePackRepository;

	}

}