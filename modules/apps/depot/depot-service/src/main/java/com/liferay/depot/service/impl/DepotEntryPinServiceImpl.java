/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryPin;
import com.liferay.depot.service.base.DepotEntryPinServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=depot",
		"json.web.service.context.path=DepotEntryPin"
	},
	service = AopService.class
)
public class DepotEntryPinServiceImpl extends DepotEntryPinServiceBaseImpl {

	@Override
	public DepotEntryPin addDepotEntryPin(long userId, long depotEntryId)
		throws PortalException {

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.VIEW);

		return depotEntryPinLocalService.addDepotEntryPin(userId, depotEntryId);
	}

	@Override
	public DepotEntryPin deleteDepotEntryPin(long userId, long depotEntryId)
		throws PortalException {

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.VIEW);

		return depotEntryPinLocalService.deleteDepotEntryPin(
			userId, depotEntryId);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.depot.model.DepotEntry)"
	)
	private volatile ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;

}