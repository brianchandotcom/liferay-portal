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

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.base.DepotEntryGroupRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the depot entry group rel remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.depot.service.DepotEntryGroupRelService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=depot",
		"json.web.service.context.path=DepotEntryGroupRel"
	},
	service = AopService.class
)
public class DepotEntryGroupRelServiceImpl
	extends DepotEntryGroupRelServiceBaseImpl {

	@Override
	public List<DepotEntryGroupRel> getDepotEntryGroupRels(long groupId)
		throws PortalException {

		_groupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return depotEntryGroupRelLocalService.getDepotEntryGroupRels(groupId);
	}

	@Reference
	private GroupPermission _groupPermission;

}