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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRendererRegistry.class)
public class CTDisplayRendererRegistry {

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

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctEntry.getCtCollectionId())) {

			T ctModel = ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKey(
					ctEntry.getModelClassPK()));

			if (ctModel == null) {
				return null;
			}

			return ctDisplayRenderer.getEditURL(httpServletRequest, ctModel);
		}
	}

	public <T extends CTModel<T>> String getTypeName(
		Locale locale, CTEntry ctEntry) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

		String name = null;

		if (ctDisplayRenderer != null) {
			CTService<T> ctService = _ctServiceServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

			long ctCollectionId = ctEntry.getCtCollectionId();

			if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

				ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
			}

			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

				T ctModel = ctService.updateWithUnsafeFunction(
					ctPersistence -> ctPersistence.fetchByPrimaryKey(
						ctEntry.getModelClassPK()));

				if (ctModel == null) {
					name = ctDisplayRenderer.getTypeName(locale);
				}
				else {
					try {
						name = ctDisplayRenderer.getTypeName(locale, ctModel);
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException, portalException);
						}

						name = ctDisplayRenderer.getTypeName(locale);
					}
				}
			}
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
				ctPersistence -> ctPersistence.fetchByPrimaryKey(
					ctEntry.getModelClassPK()));

			if (ctModel == null) {
				return;
			}

			CTDisplayRenderer<T> ctDisplayRenderer =
				_ctDisplayServiceTrackerMap.getService(
					ctEntry.getModelClassNameId());

			if (ctDisplayRenderer == null) {
				ctDisplayRenderer = CTModelDisplayRendererAdapter.getInstance();

				ctDisplayRenderer.render(
					httpServletRequest, httpServletResponse, ctModel);

				return;
			}

			try (UnsyncStringWriter unsyncStringWriter =
					new UnsyncStringWriter()) {

				PipingServletResponse pipingServletResponse =
					new PipingServletResponse(
						httpServletResponse, unsyncStringWriter);

				ctDisplayRenderer.render(
					httpServletRequest, pipingServletResponse, ctModel);

				StringBundler sb = unsyncStringWriter.getStringBundler();

				sb.writeTo(httpServletResponse.getWriter());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}

				ctDisplayRenderer = CTModelDisplayRendererAdapter.getInstance();

				ctDisplayRenderer.render(
					httpServletRequest, httpServletResponse, ctModel);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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
		_ctDisplayServiceTrackerMap.close();
		_ctServiceServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDisplayRendererRegistry.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, CTDisplayRenderer>
		_ctDisplayServiceTrackerMap;
	private ServiceTrackerMap<Long, CTService> _ctServiceServiceTrackerMap;

	@Reference
	private ResourceActions _resourceActions;

}