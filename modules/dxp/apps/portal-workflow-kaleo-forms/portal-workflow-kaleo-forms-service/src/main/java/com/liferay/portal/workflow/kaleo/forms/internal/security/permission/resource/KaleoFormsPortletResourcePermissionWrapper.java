/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsConstants;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	property = "resource.name=" + KaleoFormsConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class KaleoFormsPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			KaleoFormsConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN));
	}

	@Reference
	private StagingPermission _stagingPermission;

}