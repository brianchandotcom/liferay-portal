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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.change.tracking.display.CTDisplay;
import com.liferay.change.tracking.display.CTPortletDisplay;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.web.internal.display.adapter.CTAssetRendererDisplayAdapter;
import com.liferay.change.tracking.web.internal.display.adapter.CTModelDisplayAdapter;
import com.liferay.change.tracking.web.internal.display.adapter.CTPortletDisplayAdapter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.MapUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRegistry.class)
public class CTDisplayRegistry {

	public <T extends CTModel<T>> void renderCTEntry(
			HttpServletRequest request, HttpServletResponse response,
			CTEntry ctEntry, long ctCollectionId)
		throws Exception {

		CTService<T> ctService = _ctServiceServiceTrackerMap.getService(
			ctEntry.getModelClassNameId());

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			T ctModel = ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKey(
					ctEntry.getModelClassPK()));

			CTDisplay<T> ctDisplay = _ctDisplayServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

			if (ctDisplay == null) {
				ctDisplay = CTModelDisplayAdapter.getInstance();
			}

			ctDisplay.render(request, response, ctModel);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_assetRendererFactoryServiceTracker = new ServiceTracker<>(
			bundleContext, AssetRendererFactory.class,
			new AssetRendererFactoryServiceTrackerCustomizer(bundleContext));

		_assetRendererFactoryServiceTracker.open();

		_ctPortletDisplayFactoryServiceTracker = new ServiceTracker<>(
			bundleContext, CTPortletDisplay.class,
			new CTPortletDisplayServiceTrackerCustomizer(bundleContext));

		_ctPortletDisplayFactoryServiceTracker.open();

		_ctDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CTDisplay.class, null,
				(serviceReference, emitter) -> {
					CTDisplay<?> ctDisplay = bundleContext.getService(
						serviceReference);

					try {
						emitter.emit(
							_classNameLocalService.getClassNameId(
								ctDisplay.getModelClass()));
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});

		_ctServiceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CTService.class, null,
				(serviceReference, emitter) -> {
					CTService<?> ctService = bundleContext.getService(
						serviceReference);

					emitter.emit(
						_classNameLocalService.getClassNameId(
							ctService.getModelClass()));
				});
	}

	@Deactivate
	protected void deactivate() {
		_assetRendererFactoryServiceTracker.close();
		_ctPortletDisplayFactoryServiceTracker.close();
		_ctDisplayServiceTrackerMap.close();
		_ctServiceServiceTrackerMap.close();
	}

	private ServiceTracker<?, ?> _assetRendererFactoryServiceTracker;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, CTDisplay> _ctDisplayServiceTrackerMap;
	private ServiceTracker<?, ?> _ctPortletDisplayFactoryServiceTracker;
	private ServiceTrackerMap<Long, CTService> _ctServiceServiceTrackerMap;

	@Reference
	private PortletLocalService _portletLocalService;

	private static class AssetRendererFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AssetRendererFactory, ServiceRegistration> {

		public AssetRendererFactoryServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration addingService(
			ServiceReference<AssetRendererFactory> serviceReference) {

			AssetRendererFactory<?> assetRendererFactory =
				_bundleContext.getService(serviceReference);

			return _bundleContext.registerService(
				CTDisplay.class,
				new CTAssetRendererDisplayAdapter(assetRendererFactory),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -200));
		}

		@Override
		public void modifiedService(
			ServiceReference<AssetRendererFactory> serviceReference,
			ServiceRegistration serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<AssetRendererFactory> serviceReference,
			ServiceRegistration serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

	private class CTPortletDisplayServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<CTPortletDisplay, ServiceRegistration> {

		public CTPortletDisplayServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration addingService(
			ServiceReference<CTPortletDisplay> serviceReference) {

			CTPortletDisplay<?> ctPortletDisplay = _bundleContext.getService(
				serviceReference);

			return _bundleContext.registerService(
				CTDisplay.class,
				new CTPortletDisplayAdapter(
					ctPortletDisplay, _portletLocalService),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));
		}

		@Override
		public void modifiedService(
			ServiceReference<CTPortletDisplay> serviceReference,
			ServiceRegistration serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<CTPortletDisplay> serviceReference,
			ServiceRegistration serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}