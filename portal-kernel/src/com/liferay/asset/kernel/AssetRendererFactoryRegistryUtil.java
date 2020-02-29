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

package com.liferay.asset.kernel;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRankingUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryUtil {

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return ListUtil.fromMapValues(
			_filterAssetRendererFactories(
				companyId, _classNameAssetRenderFactoriesServiceTrackerMap,
				false));
	}

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		return ListUtil.fromMapValues(
			_filterAssetRendererFactories(
				companyId, _classNameAssetRenderFactoriesServiceTrackerMap,
				filterSelectable));
	}

	public static <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return (AssetRendererFactory<T>)
			_classNameAssetRenderFactoriesServiceTrackerMap.getService(
				clazz.getName());
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		return _classNameAssetRenderFactoriesServiceTrackerMap.getService(
			className);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _classNameAssetRenderFactoriesServiceTrackerMap.getService(
			PortalUtil.getClassName(classNameId));
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByType(
		String type) {

		return _typeAssetRenderFactoriesServiceTrackerMap.getService(type);
	}

	public static long[] getClassNameIds(long companyId) {
		return getClassNameIds(companyId, false);
	}

	public static long[] getClassNameIds(
		long companyId, boolean filterSelectable) {

		if (companyId > 0) {
			Map<String, AssetRendererFactory<?>> assetRenderFactories =
				_filterAssetRendererFactories(
					companyId, _classNameAssetRenderFactoriesServiceTrackerMap,
					filterSelectable);

			long[] classNameIds = new long[assetRenderFactories.size()];

			int i = 0;

			for (AssetRendererFactory<?> assetRendererFactory :
					assetRenderFactories.values()) {

				classNameIds[i] = assetRendererFactory.getClassNameId();

				i++;
			}

			return classNameIds;
		}

		Set<String> classNames =
			_classNameAssetRenderFactoriesServiceTrackerMap.keySet();

		Stream<String> stream = classNames.stream();

		return stream.map(
			_classNameAssetRenderFactoriesServiceTrackerMap::getService
		).map(
			AssetRendererFactory::getClassNameId
		).mapToLong(
			classNameId -> classNameId
		).toArray();
	}

	public static void register(AssetRendererFactory<?> assetRendererFactory) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
			registry.registerService(
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				assetRendererFactory);

		_serviceRegistrations.put(assetRendererFactory, serviceRegistration);
	}

	public static void register(
		List<AssetRendererFactory<?>> assetRendererFactories) {

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			register(assetRendererFactory);
		}
	}

	public static void unregister(
		AssetRendererFactory<?> assetRendererFactory) {

		ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
			_serviceRegistrations.remove(assetRendererFactory);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	public static void unregister(
		List<AssetRendererFactory<?>> assetRendererFactories) {

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			unregister(assetRendererFactory);
		}
	}

	private static Map<String, AssetRendererFactory<?>>
		_filterAssetRendererFactories(
			long companyId,
			ServiceTrackerMap<String, AssetRendererFactory>
				assetRendererFactories,
			boolean filterSelectable) {

		Map<String, AssetRendererFactory<?>> filteredAssetRendererFactories =
			new ConcurrentHashMap<>();

		for (String key : assetRendererFactories.keySet()) {
			AssetRendererFactory<?> assetRendererFactory =
				assetRendererFactories.getService(key);

			if (assetRendererFactory.isActive(companyId) &&
				(!filterSelectable || assetRendererFactory.isSelectable())) {

				filteredAssetRendererFactories.put(key, assetRendererFactory);
			}
		}

		return filteredAssetRendererFactories;
	}

	private static Comparator<ServiceReference<AssetRendererFactory>>
		_getServiceReferenceComparator() {

		Comparator<ServiceReference<AssetRendererFactory>>
			serviceReferenceComparator = ServiceRankingUtil::compare;

		return serviceReferenceComparator.reversed();
	}

	private AssetRendererFactoryRegistryUtil() {
	}

	private static ServiceTrackerMap<String, AssetRendererFactory>
		_classNameAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerCollections.openSingleValueMap(
				AssetRendererFactory.class, null,
				(serviceReference, emitter) -> {
					Registry registry = RegistryUtil.getRegistry();

					AssetRendererFactory assetRendererFactory =
						registry.getService(serviceReference);

					emitter.emit(assetRendererFactory.getClassName());
				},
				_getServiceReferenceComparator());

	private static final ServiceRegistrationMap<AssetRendererFactory<?>>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();

	private static ServiceTrackerMap<String, AssetRendererFactory>
		_typeAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerCollections.openSingleValueMap(
				AssetRendererFactory.class, null,
				(serviceReference, emitter) -> {
					Registry registry = RegistryUtil.getRegistry();

					AssetRendererFactory assetRendererFactory =
						registry.getService(serviceReference);

					emitter.emit(assetRendererFactory.getType());
				},
				_getServiceReferenceComparator());

}