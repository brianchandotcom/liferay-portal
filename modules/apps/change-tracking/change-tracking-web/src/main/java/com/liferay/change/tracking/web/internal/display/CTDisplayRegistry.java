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
import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.web.internal.display.adapter.CTDisplayRendererAssetRendererAdapter;
import com.liferay.change.tracking.web.internal.display.adapter.CTModelDisplayRendererAdapter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

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

	public <T extends CTModel<T>> String getEditURL(
			HttpServletRequest httpServletRequest, CTEntry ctEntry)
		throws Exception {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

		if (ctDisplayRenderer == null) {
			return null;
		}

		CTService<T> ctService = _ctServiceServiceTrackerMap.getService(
			ctEntry.getModelClassNameId());

		T ctModel = ctService.updateWithUnsafeFunction(
			ctPersistence -> ctPersistence.findByPrimaryKey(
				ctEntry.getModelClassPK()));

		return ctDisplayRenderer.getEditURL(httpServletRequest, ctModel);
	}

	public String getTypeName(Locale locale, CTEntry ctEntry) {
		CTDisplayRenderer<?> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

		String name = null;

		if (ctDisplayRenderer != null) {
			name = ctDisplayRenderer.getTypeName(locale);
		}

		if (name == null) {
			ClassName className = _classNameLocalService.fetchClassName(
				ctEntry.getModelClassNameId());

			if (className != null) {
				name = _resourceActions.getModelResource(
					locale, className.getClassName());
			}
		}

		return name;
	}

	public <T extends CTModel<T>> void renderCTEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, CTEntry ctEntry,
			long ctCollectionId)
		throws Exception {

		CTService<T> ctService = _ctServiceServiceTrackerMap.getService(
			ctEntry.getModelClassNameId());

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			T ctModel = ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.findByPrimaryKey(
					ctEntry.getModelClassPK()));

			CTDisplayRenderer<T> ctDisplayRenderer =
				_ctDisplayServiceTrackerMap.getService(
					ctEntry.getModelClassNameId());

			if (ctDisplayRenderer == null) {
				ctDisplayRenderer = CTModelDisplayRendererAdapter.getInstance();
			}

			ctDisplayRenderer.render(
				httpServletRequest, httpServletResponse, ctModel);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_assetRendererFactoryServiceTracker = new ServiceTracker<>(
			bundleContext, AssetRendererFactory.class,
			new AssetRendererFactoryServiceTrackerCustomizer(bundleContext));

		_assetRendererFactoryServiceTracker.open();

		_ctDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CTDisplayRenderer.class, null,
				(serviceReference, emitter) -> {
					CTDisplayRenderer<?> ctDisplayRenderer =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							_classNameLocalService.getClassNameId(
								ctDisplayRenderer.getModelClass()));
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
		_ctDisplayServiceTrackerMap.close();
		_ctServiceServiceTrackerMap.close();
	}

	private ServiceTracker<?, ?> _assetRendererFactoryServiceTracker;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, CTDisplayRenderer>
		_ctDisplayServiceTrackerMap;
	private ServiceTrackerMap<Long, CTService> _ctServiceServiceTrackerMap;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourceActions _resourceActions;

	private static class AssetRendererFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AssetRendererFactory, ServiceRegistration> {

		@Override
		public ServiceRegistration addingService(
			ServiceReference<AssetRendererFactory> serviceReference) {

			AssetRendererFactory<?> assetRendererFactory =
				_bundleContext.getService(serviceReference);

			Class<?> clazz = assetRendererFactory.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			try {
				clazz = classLoader.loadClass(
					assetRendererFactory.getClassName());
			}
			catch (ClassNotFoundException cnfe) {
				return null;
			}

			if (!CTModel.class.isAssignableFrom(clazz)) {
				return null;
			}

			return _registerService(assetRendererFactory, clazz);
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

		private AssetRendererFactoryServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@SuppressWarnings("unchecked")
		private <T extends CTModel<T>> ServiceRegistration _registerService(
			AssetRendererFactory<?> assetRendererFactory, Class<?> clazz) {

			return _bundleContext.registerService(
				CTDisplayRenderer.class,
				new CTDisplayRendererAssetRendererAdapter<>(
					(AssetRendererFactory<T>)assetRendererFactory,
					(Class<T>)clazz),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));
		}

		private final BundleContext _bundleContext;

	}

}