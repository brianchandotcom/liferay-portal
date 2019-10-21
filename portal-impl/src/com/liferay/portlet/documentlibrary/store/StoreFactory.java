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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.store.CTStoreFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Manuel de la Peña
 * @author Edward C. Han
 */
public class StoreFactory {

	public static StoreFactory getInstance() {
		if (_storeFactory == null) {
			_storeFactory = new StoreFactory();
		}

		return _storeFactory;
	}

	public void checkProperties() {
		if (_warned) {
			return;
		}

		String dlHookImpl = PropsUtil.get("dl.hook.impl");

		if (Validator.isNull(dlHookImpl)) {
			_warned = true;

			return;
		}

		boolean found = false;

		for (String key : StoreServiceTrackerMapHolder.keySet()) {
			Store store = getStore(key);

			Class<?> clazz = store.getClass();

			String className = clazz.getName();

			if (dlHookImpl.equals(className)) {
				PropsValues.DL_STORE_IMPL = className;

				found = true;

				break;
			}
		}

		if (!found) {
			PropsValues.DL_STORE_IMPL = dlHookImpl;
		}

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(11);

			sb.append("Liferay is configured with the legacy property ");
			sb.append("\"dl.hook.impl=");
			sb.append(dlHookImpl);
			sb.append("\" in portal-ext.properties. Please reconfigure to ");
			sb.append("use the new property \"");
			sb.append(PropsKeys.DL_STORE_IMPL);
			sb.append("\". Liferay will attempt to temporarily set \"");
			sb.append(PropsKeys.DL_STORE_IMPL);
			sb.append("=");
			sb.append(PropsValues.DL_STORE_IMPL);
			sb.append("\".");

			_log.warn(sb.toString());
		}

		_warned = true;
	}

	public Store getStore() {
		Store store = StoreServiceTrackerMapHolder.getService(
			PropsValues.DL_STORE_IMPL);

		if (store == null) {
			throw new IllegalStateException("Store is not available");
		}

		return store;
	}

	public Store getStore(String key) {
		return StoreServiceTrackerMapHolder.getService(key);
	}

	public String[] getStoreTypes() {
		Set<String> storeTypes = StoreServiceTrackerMapHolder.keySet();

		return storeTypes.toArray(new String[0]);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void setStore(String key) {
	}

	private static final Log _log = LogFactoryUtil.getLog(StoreFactory.class);

	private static StoreFactory _storeFactory;
	private static boolean _warned;

	private static class StoreServiceTrackerMapHolder {

		public static Store getService(String key) {
			return _storeServiceTrackerMap.getService(key);
		}

		public static Set<String> keySet() {
			return _storeServiceTrackerMap.keySet();
		}

		private static final ServiceTrackerMap<String, Store>
			_storeServiceTrackerMap;

		static {
			Registry registry = RegistryUtil.getRegistry();

			_storeServiceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					Store.class, "store.type",
					new ServiceTrackerCustomizer<Store, Store>() {

						@Override
						public Store addingService(
							ServiceReference<Store> serviceReference) {

							Store store = registry.getService(serviceReference);

							if (!GetterUtil.getBoolean(
									serviceReference.getProperty("ct.aware"))) {

								store = CTStoreFactoryUtil.createCTStore(
									store,
									GetterUtil.getString(
										serviceReference.getProperty(
											"store.type")));
							}

							return store;
						}

						@Override
						public void modifiedService(
							ServiceReference<Store> serviceReference,
							Store service) {
						}

						@Override
						public void removedService(
							ServiceReference<Store> serviceReference,
							Store service) {

							registry.ungetService(serviceReference);
						}

					});
		}

	}

}