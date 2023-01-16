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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryUtil {

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return ListUtil.fromMapValues(
			_filterAssetRendererFactories(companyId, false));
	}

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		return ListUtil.fromMapValues(
			_filterAssetRendererFactories(companyId, filterSelectable));
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
				_filterAssetRendererFactories(companyId, filterSelectable);

			long[] classNameIds = new long[assetRenderFactories.size()];

			int i = 0;

			for (AssetRendererFactory<?> assetRendererFactory :
					assetRenderFactories.values()) {

				classNameIds[i] = assetRendererFactory.getClassNameId();

				i++;
			}

			return classNameIds;
		}

		int i = 0;

		Set<String> classNames =
			_classNameAssetRenderFactoriesServiceTrackerMap.keySet();

		long[] classNameIds = new long[classNames.size()];

		for (String className : classNames) {
			AssetRendererFactory<?> assetRendererFactory =
				_classNameAssetRenderFactoriesServiceTrackerMap.getService(
					className);

			classNameIds[i++] = assetRendererFactory.getClassNameId();
		}

		return classNameIds;
	}

	public static long[] getIndexableClassNameIds(
		long companyId, boolean filterSelectable) {

		return ArrayUtil.filter(
			getClassNameIds(companyId, filterSelectable),
			classNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					PortalUtil.getClassName(classNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});
	}

	private static Map<String, AssetRendererFactory<?>>
		_filterAssetRendererFactories(
			long companyId, boolean filterSelectable) {

		Map<String, AssetRendererFactory<?>> filteredAssetRendererFactories =
			new ConcurrentHashMap<>();

		for (String key :
				_companyAssetRenderFactoriesServiceTrackerMap.keySet()) {

			AssetRendererFactory<?> assetRendererFactory =
				_companyAssetRenderFactoriesServiceTrackerMap.getService(key);

			if (assetRendererFactory.isActive(companyId) &&
				(!filterSelectable || assetRendererFactory.isSelectable())) {

				if (key.startsWith(
						"com.liferay.object.model.ObjectDefinition#")) {

					if (key.split("#")[2].equals(String.valueOf(companyId))) {
						filteredAssetRendererFactories.put(
							key, assetRendererFactory);
					}
				}
				else {
					filteredAssetRendererFactories.put(
						key, assetRendererFactory);
				}
			}
		}

		return filteredAssetRendererFactories;
	}

	private AssetRendererFactoryRegistryUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, AssetRendererFactory<?>>
		_classNameAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext,
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				null,
				(serviceReference, emitter) -> {
					AssetRendererFactory<?> assetRendererFactory =
						_bundleContext.getService(serviceReference);

					emitter.emit(assetRendererFactory.getClassName());
				});

	private static final ServiceTrackerMap<String, AssetRendererFactory<?>>
		_companyAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext,
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				null,
				(serviceReference, emitter) -> {
					AssetRendererFactory<?> assetRendererFactory =
						_bundleContext.getService(serviceReference);

					String className = assetRendererFactory.getClassName();

					if (className.startsWith(
							"com.liferay.object.model.ObjectDefinition#")) {

						emitter.emit(
							className + "#" +
								GetterUtil.getLong(
									serviceReference.getProperty(
										"company.id")));
					}
					else {
						emitter.emit(className);
					}
				});

	private static final ServiceTrackerMap<String, AssetRendererFactory<?>>
		_typeAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext,
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				null,
				(serviceReference, emitter) -> {
					AssetRendererFactory<?> assetRendererFactory =
						_bundleContext.getService(serviceReference);

					emitter.emit(assetRendererFactory.getType());
				});

}