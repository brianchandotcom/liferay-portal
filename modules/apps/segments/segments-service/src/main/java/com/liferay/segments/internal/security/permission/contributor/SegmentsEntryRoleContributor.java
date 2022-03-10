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

package com.liferay.segments.internal.security.permission.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.contributor.RoleCollection;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	service = RoleContributor.class
)
public class SegmentsEntryRoleContributor implements RoleContributor {

	@Override
	public void contribute(RoleCollection roleCollection) {
		try {
			if (!_segmentsConfigurationProvider.isRoleSegmentationEnabled(
					roleCollection.getCompanyId())) {

				return;
			}
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (permissionChecker != null) {
				PermissionThreadLocal.setPermissionChecker(
					_liberalPermissionCheckerFactory.create(
						permissionChecker.getUser()));
			}

			for (long segmentsEntryId : _getSegmentsEntryIds(roleCollection)) {
				List<SegmentsEntryRole> segmentsEntryRoles =
					_segmentsEntryRoleLocalService.getSegmentsEntryRoles(
						segmentsEntryId);

				for (SegmentsEntryRole segmentsEntryRole : segmentsEntryRoles) {
					roleCollection.addRoleId(segmentsEntryRole.getRoleId());
				}
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private long[] _getSegmentsEntryIds(RoleCollection roleCollection) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return new long[0];
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return new long[0];
		}

		User user = roleCollection.getUser();

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			roleCollection.getGroupId(), user.getUserId(),
			_requestContextMapper.map(httpServletRequest));

		if ((segmentsEntryIds.length > 0) && _log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Found segments ", segmentsEntryIds, " for user ",
					user.getUserId(), " in group ",
					roleCollection.getGroupId()));
		}

		return segmentsEntryIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRoleContributor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference(target = "(permission.checker.type=liberal)")
	private PermissionCheckerFactory _liberalPermissionCheckerFactory;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

}