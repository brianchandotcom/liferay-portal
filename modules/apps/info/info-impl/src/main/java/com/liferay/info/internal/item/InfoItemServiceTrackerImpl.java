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

package com.liferay.info.internal.item;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.formatter.InfoTextFormatter;
import com.liferay.info.internal.util.ItemClassNameServiceReferenceMapper;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.selector.InfoItemSelector;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.Keyed;
import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemServiceTracker.class)
public class InfoItemServiceTrackerImpl implements InfoItemServiceTracker {

	@Override
	public <P> List<P> getAllInfoItemServices(Class<P> serviceClass) {
		ServiceTrackerMap<String, ?> serviceTrackerMap =
			_keyedInfoItemServiceTrackerMap.get(serviceClass.getName());

		return new ArrayList<>(
			(Collection<? extends P>)serviceTrackerMap.values());
	}

	@Override
	public <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter) {

		ServiceTrackerMap
			<String, ? extends List<ServiceReferenceServiceTuple<P, P>>>
				infoItemServiceTrackerMap =
					(ServiceTrackerMap)
						_itemClassNameInfoItemServiceTrackerMap.get(
							serviceClass.getName());

		List<ServiceReferenceServiceTuple<P, P>> serviceReferenceServiceTuples =
			infoItemServiceTrackerMap.getService(itemClassName);

		if (serviceReferenceServiceTuples != null) {
			Stream<ServiceReferenceServiceTuple<P, P>> stream =
				serviceReferenceServiceTuples.stream();

			if (infoItemServiceFilter != null) {
				try {
					Filter filter = FrameworkUtil.createFilter(
						infoItemServiceFilter.getFilterString());

					stream = stream.filter(
						srst -> filter.match(srst.getServiceReference()));
				}
				catch (InvalidSyntaxException invalidSyntaxException) {
					throw new RuntimeException(
						"Incorrect filter string", invalidSyntaxException);
				}
			}

			return stream.map(
				ServiceReferenceServiceTuple::getService
			).collect(
				Collectors.toList()
			);
		}

		return Collections.emptyList();
	}

	@Override
	public <P> P getFirstInfoItemService(
		Class<P> serviceClass, String itemClassName,
		InfoItemServiceFilter infoItemServiceFilter) {

		List<?> infoItemServices = getAllInfoItemServices(
			serviceClass, itemClassName, infoItemServiceFilter);

		if (ListUtil.isEmpty(infoItemServices)) {
			return null;
		}

		return (P)infoItemServices.get(0);
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities(
		String itemClassName) {

		InfoItemCapabilitiesProvider infoItemCapabilitiesProvider =
			getFirstInfoItemService(
				InfoItemCapabilitiesProvider.class, itemClassName, null);

		return infoItemCapabilitiesProvider.getInfoItemCapabilities();
	}

	@Override
	public InfoItemCapability getInfoItemCapability(
		String infoItemCapabilityKey) {

		return _infoItemCapabilityServiceTrackerMap.getService(
			infoItemCapabilityKey);
	}

	@Override
	public <P> List<InfoItemClassDetails> getInfoItemClassDetails(
		Class<P> serviceClass) {

		List<String> infoItemClassNames = getInfoItemClassNames(serviceClass);

		Stream<String> infoItemClassNamesStream = infoItemClassNames.stream();

		return infoItemClassNamesStream.map(
			itemClassName -> _getInfoItemClassDetails(itemClassName)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<InfoItemClassDetails> getInfoItemClassDetails(
			InfoItemCapability infoItemCapability)
		throws CapabilityVerificationException {

		List<InfoItemClassDetails> infoItemClassDetailsList = new ArrayList<>();

		for (InfoItemClassDetails curInfoItemClassDetails :
				getInfoItemClassDetails(InfoItemCapabilitiesProvider.class)) {

			InfoItemCapabilitiesProvider infoItemCapabilitiesProvider =
				getFirstInfoItemService(
					InfoItemCapabilitiesProvider.class,
					curInfoItemClassDetails.getClassName(), null);

			List<InfoItemCapability> infoItemCapabilities =
				infoItemCapabilitiesProvider.getInfoItemCapabilities();

			if (infoItemCapabilities.contains(infoItemCapability)) {
				infoItemCapability.verify(
					curInfoItemClassDetails.getClassName());
			}

			infoItemClassDetailsList.add(curInfoItemClassDetails);
		}

		return infoItemClassDetailsList;
	}

	@Override
	public List<InfoItemClassDetails> getInfoItemClassDetails(
			String infoItemCapabilityKey)
		throws CapabilityVerificationException {

		InfoItemCapability infoItemCapability =
			_infoItemCapabilityServiceTrackerMap.getService(
				infoItemCapabilityKey);

		if (infoItemCapability == null) {
			throw new RuntimeException(
				"Unable to find info item capability with key " +
					infoItemCapabilityKey);
		}

		return getInfoItemClassDetails(infoItemCapability);
	}

	@Override
	public <P> List<String> getInfoItemClassNames(Class<P> serviceClass) {
		ServiceTrackerMap<String, ?> infoItemServiceTrackerMap =
			_itemClassNameInfoItemServiceTrackerMap.get(serviceClass.getName());

		return new ArrayList<>(infoItemServiceTrackerMap.keySet());
	}

	@Override
	public <P> P getInfoItemService(Class<P> serviceClass, String serviceKey) {
		if (Validator.isNull(serviceKey)) {
			return null;
		}

		ServiceTrackerMap<String, ?> infoItemServiceTrackerMap =
			_keyedInfoItemServiceTrackerMap.get(serviceClass.getName());

		return (P)infoItemServiceTrackerMap.getService(serviceKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemCapabilityServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoItemCapability.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(service, emitter) -> emitter.emit(service.getKey())));

		Class<?>[] serviceClasses = new Class<?>[] {
			InfoCollectionTextFormatter.class, InfoTextFormatter.class,
			InfoItemCapabilitiesProvider.class, InfoItemDetailsProvider.class,
			InfoItemFieldValuesProvider.class, InfoItemFieldValuesUpdater.class,
			InfoItemFormProvider.class, InfoItemFormVariationsProvider.class,
			InfoItemLanguagesProvider.class, InfoItemObjectProvider.class,
			InfoItemPermissionProvider.class, InfoItemRenderer.class,
			InfoItemSelector.class, InfoListRenderer.class,
			InfoListProvider.class
		};

		for (Class<?> serviceClass : serviceClasses) {
			ServiceTrackerMap
				<String, ? extends List<ServiceReferenceServiceTuple<?, ?>>>
					itemClassNameInfoItemServiceTrackerMap =
						ServiceTrackerMapFactory.openMultiValueMap(
							bundleContext, serviceClass, null,
							new ItemClassNameServiceReferenceMapper(
								bundleContext),
							(ServiceTrackerCustomizer)
								new ServiceReferenceServiceTupleServiceTrackerCustomizer(
									bundleContext));

			_itemClassNameInfoItemServiceTrackerMap.put(
				serviceClass.getName(), itemClassNameInfoItemServiceTrackerMap);

			ServiceTrackerMap<String, ?> infoItemServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, serviceClass, null,
					ServiceReferenceMapperFactory.create(
						bundleContext,
						(service, emitter) -> {
							String key = serviceClass.getName();

							if (service instanceof Keyed) {
								Keyed keyedService = (Keyed)service;

								key = keyedService.getKey();
							}

							emitter.emit(key);
						}),
					new PropertyServiceReferenceComparator<>(
						"service.ranking"));

			_keyedInfoItemServiceTrackerMap.put(
				serviceClass.getName(), infoItemServiceTrackerMap);
		}
	}

	private InfoItemClassDetails _getInfoItemClassDetails(
		String itemClassName) {

		InfoItemDetailsProvider infoItemDetailsProvider =
			getFirstInfoItemService(
				InfoItemDetailsProvider.class, itemClassName, null);

		InfoItemClassDetails infoItemClassDetails = null;

		if (infoItemDetailsProvider != null) {
			infoItemClassDetails =
				infoItemDetailsProvider.getInfoItemClassDetails();
		}
		else {
			infoItemClassDetails = new InfoItemClassDetails(
				itemClassName, InfoLocalizedValue.modelResource(itemClassName));
		}

		return infoItemClassDetails;
	}

	private ServiceTrackerMap<String, InfoItemCapability>
		_infoItemCapabilityServiceTrackerMap;
	private final Map
		<String,
		 ServiceTrackerMap
			 <String, ? extends List<ServiceReferenceServiceTuple<?, ?>>>>
				_itemClassNameInfoItemServiceTrackerMap = new HashMap<>();
	private final Map<String, ServiceTrackerMap<String, ?>>
		_keyedInfoItemServiceTrackerMap = new HashMap<>();

	private static class ServiceReferenceServiceTupleServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<Object, ServiceReferenceServiceTuple<Object, Object>> {

		public ServiceReferenceServiceTupleServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceReferenceServiceTuple<Object, Object> addingService(
			ServiceReference<Object> serviceReference) {

			Object service = _bundleContext.getService(serviceReference);

			if (service == null) {
				return null;
			}

			return new ServiceReferenceServiceTuple<>(
				serviceReference, service);
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			ServiceReferenceServiceTuple<Object, Object>
				objectObjectServiceReferenceServiceTuple) {

			//nothing to do here since servicereference is mutable and has

			// already been mutated by the framework

		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			ServiceReferenceServiceTuple<Object, Object>
				serviceReferenceServiceTuple) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}