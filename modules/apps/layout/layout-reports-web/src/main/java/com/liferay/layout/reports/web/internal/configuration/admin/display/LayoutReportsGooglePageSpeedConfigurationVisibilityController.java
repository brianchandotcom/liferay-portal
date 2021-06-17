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

package com.liferay.layout.reports.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"configuration.pid=com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedCompanyConfiguration",
		"configuration.pid=com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedGroupConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class LayoutReportsGooglePageSpeedConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (ExtendedObjectClassDefinition.Scope.GROUP.equals(scope)) {
			try {
				Group group = _groupLocalService.getGroup((long)scopePK);

				return _layoutReportsGooglePageSpeedConfigurationProvider.
					isEnabled(group.getCompanyId());
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return _layoutReportsGooglePageSpeedConfigurationProvider.isEnabled();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsGooglePageSpeedConfigurationVisibilityController.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

}