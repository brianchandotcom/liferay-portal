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

package com.liferay.segments.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = {})
public class SegmentsExperimentModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			(Class<ModelResourcePermission<SegmentsExperiment>>)
				(Class<?>)ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				SegmentsExperiment.class,
				SegmentsExperiment::getSegmentsExperimentId,
				_segmentsExperimentLocalService::getSegmentsExperiment,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> consumer.accept(
					new StagedModelResourcePermissionLogic(
						_layoutPermission, _stagingPermission))),
			HashMapDictionaryBuilder.<String, Object>put(
				"model.class.name", SegmentsExperiment.class.getName()
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference(
		target = "(resource.name=" + SegmentsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	private ServiceRegistration<ModelResourcePermission<SegmentsExperiment>>
		_serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	private static class StagedModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<SegmentsExperiment> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				SegmentsExperiment segmentsExperiment, String actionId)
			throws PortalException {

			if (_layoutPermission.containsLayoutBasicUpdatePermission(
					permissionChecker, segmentsExperiment.getClassPK())) {

				return true;
			}

			return _stagingPermission.hasPermission(
				permissionChecker, segmentsExperiment.getGroupId(),
				SegmentsExperiment.class.getName(),
				segmentsExperiment.getSegmentsExperimentId(),
				SegmentsPortletKeys.SEGMENTS, actionId);
		}

		private StagedModelResourcePermissionLogic(
			LayoutPermission layoutPermission,
			StagingPermission stagingPermission) {

			_layoutPermission = layoutPermission;
			_stagingPermission = stagingPermission;
		}

		private final LayoutPermission _layoutPermission;
		private final StagingPermission _stagingPermission;

	}

}