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

package com.liferay.info.internal.list.provider;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.sort.Sort;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProviderTracker.class)
public class InfoListProviderTrackerImpl implements InfoListProviderTracker {

	@Override
	public InfoListProvider<?> getInfoListProvider(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoListProvider.class, key);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders() {
		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(Class<?> itemClass) {
		return getInfoListProviders(itemClass.getName());
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(
		String itemClassName) {

		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class, itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<InfoListProvider<?>>)(Class<?>)InfoListProvider.class,
			new InfoListProviderAdapterServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<InfoCollectionProvider<?>>
				serviceRegistration : _serviceRegistrations.values()) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(illegalStateException, illegalStateException);
			}
		}

		_serviceRegistrations.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoListProviderTrackerImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private final Map
		<ServiceReference<InfoListProvider<?>>,
		 ServiceRegistration<InfoCollectionProvider<?>>> _serviceRegistrations =
			new ConcurrentHashMap<>();
	private ServiceTracker<InfoListProvider<?>, InfoCollectionProvider<?>>
		_serviceTracker;

	@Reference
	private UserLocalService _userLocalService;

	private class InfoListProviderAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InfoListProvider<?>, InfoCollectionProvider<?>> {

		public InfoListProviderAdapterServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map
				<ServiceReference<InfoListProvider<?>>,
				 ServiceRegistration<InfoCollectionProvider<?>>>
					serviceRegistrations) {

			_bundleContext = bundleContext;
			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public InfoCollectionProvider<?> addingService(
			ServiceReference<InfoListProvider<?>> serviceReference) {

			InfoListProvider<?> infoListProvider = _bundleContext.getService(
				serviceReference);

			InfoCollectionProvider<?>
				infoListProviderInfoCollectionProviderAdapter =
					new InfoListProviderInfoCollectionProviderWrapper(
						infoListProvider);

			ServiceRegistration<InfoCollectionProvider<?>> serviceRegistration =
				_bundleContext.registerService(
					(Class<InfoCollectionProvider<?>>)
						(Class<?>)InfoCollectionProvider.class,
					infoListProviderInfoCollectionProviderAdapter,
					_getServiceReferenceProperties(serviceReference));

			_serviceRegistrations.put(serviceReference, serviceRegistration);

			return infoListProviderInfoCollectionProviderAdapter;
		}

		@Override
		public void modifiedService(
			ServiceReference<InfoListProvider<?>> serviceReference,
			InfoCollectionProvider<?> infoCollectionProvider) {

			removedService(serviceReference, infoCollectionProvider);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<InfoListProvider<?>> serviceReference,
			InfoCollectionProvider<?> infoCollectionProvider) {

			ServiceRegistration<InfoCollectionProvider<?>> serviceRegistration =
				_serviceRegistrations.remove(serviceReference);

			serviceRegistration.unregister();
		}

		private Dictionary<String, Object> _getServiceReferenceProperties(
			ServiceReference<InfoListProvider<?>> serviceReference) {

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				dictionary.put(key, serviceReference.getProperty(key));
			}

			InfoListProvider<?> infoListProvider = _bundleContext.getService(
				serviceReference);

			try {
				dictionary.put(
					"item.class.name",
					GenericUtil.getGenericClassName(infoListProvider));
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}

			return dictionary;
		}

		private final BundleContext _bundleContext;
		private final Map
			<ServiceReference<InfoListProvider<?>>,
			 ServiceRegistration<InfoCollectionProvider<?>>>
				_serviceRegistrations;

	}

	private class InfoListProviderInfoCollectionProviderWrapper
		implements InfoCollectionProvider {

		public InfoListProviderInfoCollectionProviderWrapper(
			InfoListProvider<?> infoListProvider) {

			_infoListProvider = infoListProvider;
		}

		@Override
		public InfoPage<?> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			InfoListProviderContext infoListProviderContext =
				_getInfoListProviderContext();

			Optional<Sort> sortOptional = collectionQuery.getSortOptional();

			return InfoPage.of(
				_infoListProvider.getInfoList(
					infoListProviderContext, collectionQuery.getPagination(),
					sortOptional.orElse(null)),
				collectionQuery.getPagination(),
				_infoListProvider.getInfoListCount(infoListProviderContext));
		}

		@Override
		public Class<?> getCollectionItemClass() {
			return GenericUtil.getGenericClass(_infoListProvider);
		}

		@Override
		public String getKey() {
			return _infoListProvider.getKey();
		}

		@Override
		public String getLabel(Locale locale) {
			return _infoListProvider.getLabel(locale);
		}

		@Override
		public boolean isAvailable() {
			return _infoListProvider.isAvailable(_getInfoListProviderContext());
		}

		private InfoListProviderContext _getInfoListProviderContext() {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Group group = _groupLocalService.fetchGroup(
				serviceContext.getScopeGroupId());
			User user = _userLocalService.fetchUser(serviceContext.getUserId());

			DefaultInfoListProviderContext defaultInfoListProviderContext =
				new DefaultInfoListProviderContext(group, user);

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			Layout layout = themeDisplay.getLayout();

			if (layout.isTypeControlPanel()) {
				layout = _layoutLocalService.fetchLayout(
					themeDisplay.getRefererPlid());
			}

			defaultInfoListProviderContext.setLayout(layout);

			return defaultInfoListProviderContext;
		}

		private final InfoListProvider<?> _infoListProvider;

	}

}