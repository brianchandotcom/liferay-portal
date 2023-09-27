/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperience",
	service = ModelResourcePermission.class
)
public class SegmentsExperienceModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<SegmentsExperience> {

	@Override
	protected ModelResourcePermission<SegmentsExperience>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			SegmentsExperience.class,
			SegmentsExperience::getSegmentsExperienceId,
			_segmentsExperienceLocalService::getSegmentsExperience,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelResourcePermissionLogic(
					_layoutPermission, _stagingPermission)));
	}

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference(
		target = "(resource.name=" + SegmentsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private StagingPermission _stagingPermission;

	private static class StagedModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<SegmentsExperience> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				SegmentsExperience segmentsExperience, String actionId)
			throws PortalException {

			if (_layoutPermission.containsLayoutRestrictedUpdatePermission(
					permissionChecker, segmentsExperience.getPlid())) {

				return true;
			}

			return _stagingPermission.hasPermission(
				permissionChecker, segmentsExperience.getGroupId(),
				SegmentsExperience.class.getName(),
				segmentsExperience.getSegmentsExperienceId(),
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