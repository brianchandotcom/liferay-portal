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

package com.liferay.info.internal.display.contributor;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayContributorTracker.class)
public class InfoDisplayContributorTrackerImpl
	implements InfoDisplayContributorTracker {

	@Override
	public InfoDisplayContributor<?> getInfoDisplayContributor(
		String className) {

		return _infoDisplayContributorMap.getService(className);
	}

	@Override
	public InfoDisplayContributor<?> getInfoDisplayContributorByURLSeparator(
		String urlSeparator) {

		return _infoDisplayContributorByURLSeparatorMap.getService(
			urlSeparator);
	}

	@Override
	public List<InfoDisplayContributor<?>> getInfoDisplayContributors() {
		return new ArrayList(_infoDisplayContributorMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoDisplayContributorMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<InfoDisplayContributor<?>>)
					(Class<?>)InfoDisplayContributor.class,
				null,
				(serviceReference, emitter) -> {
					InfoDisplayContributor<?> infoDisplayContributor =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(infoDisplayContributor.getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});
		_infoDisplayContributorByURLSeparatorMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<InfoDisplayContributor<?>>)
					(Class<?>)InfoDisplayContributor.class,
				null,
				(serviceReference, emitter) -> {
					InfoDisplayContributor<?> infoDisplayContributor =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							infoDisplayContributor.getInfoURLSeparator());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});

		_infoDisplayContributorServiceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<InfoDisplayContributor<?>>)
				(Class<?>)InfoDisplayContributor.class,
			new ServiceTrackerCustomizer
				<InfoDisplayContributor<?>,
				 ServiceRegistration<InfoDisplayContributor<?>>>() {

				@Override
				public ServiceRegistration<InfoDisplayContributor<?>>
					addingService(
						ServiceReference<InfoDisplayContributor<?>>
							serviceReference) {

					InfoDisplayContributor<Object> infoDisplayContributor =
						(InfoDisplayContributor<Object>)
							bundleContext.getService(serviceReference);

					try {
						InfoDisplayContributorWrapper
							infoDisplayContributorWrapper =
								new InfoDisplayContributorWrapper(
									infoDisplayContributor,
									ListUtil.fromArray(
										_displayPageInfoItemCapability));

						return (ServiceRegistration<InfoDisplayContributor<?>>)
							bundleContext.registerService(
								new String[] {
									InfoItemCapabilitiesProvider.class.
										getName(),
									InfoItemDetailsProvider.class.getName(),
									InfoItemFieldValuesProvider.class.getName(),
									InfoItemFormProvider.class.getName(),
									InfoItemFormVariationsProvider.class.
										getName(),
									InfoItemObjectProvider.class.getName()
								},
								infoDisplayContributorWrapper,
								_getServiceReferenceProperties(
									bundleContext, serviceReference));
					}
					catch (Exception exception) {
						bundleContext.ungetService(serviceReference);

						throw exception;
					}
				}

				@Override
				public void modifiedService(
					ServiceReference<InfoDisplayContributor<?>>
						serviceReference,
					ServiceRegistration<InfoDisplayContributor<?>>
						serviceRegistration) {

					serviceRegistration.setProperties(
						_getServiceReferenceProperties(
							bundleContext, serviceReference));
				}

				@Override
				public void removedService(
					ServiceReference<InfoDisplayContributor<?>>
						serviceReference,
					ServiceRegistration<InfoDisplayContributor<?>>
						serviceRegistration) {

					bundleContext.ungetService(serviceReference);

					serviceRegistration.unregister();
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_infoDisplayContributorServiceTracker.close();
	}

	private Dictionary<String, Object> _getServiceReferenceProperties(
		BundleContext bundleContext,
		ServiceReference<InfoDisplayContributor<?>> serviceReference) {

		Dictionary<String, Object> dictionary = new Hashtable<>();

		for (String key : serviceReference.getPropertyKeys()) {
			dictionary.put(key, serviceReference.getProperty(key));
		}

		InfoDisplayContributor<?> infoDisplayContributor =
			bundleContext.getService(serviceReference);

		try {
			dictionary.put(
				"item.class.name", infoDisplayContributor.getClassName());
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}

		return dictionary;
	}

	@Reference
	private DisplayPageInfoItemCapability _displayPageInfoItemCapability;

	private ServiceTrackerMap<String, InfoDisplayContributor<?>>
		_infoDisplayContributorByURLSeparatorMap;
	private ServiceTrackerMap<String, InfoDisplayContributor<?>>
		_infoDisplayContributorMap;
	private ServiceTracker
		<InfoDisplayContributor<?>,
		 ServiceRegistration<InfoDisplayContributor<?>>>
			_infoDisplayContributorServiceTracker;

}