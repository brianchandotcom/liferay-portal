/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.OrgLaborServiceBaseImpl;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgLaborServiceImpl extends OrgLaborServiceBaseImpl {

	@Override
	public OrgLabor addOrgLabor(
			long organizationId, int typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		return orgLaborLocalService.addOrgLabor(
			organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
			tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen,
			friClose, satOpen, satClose);
	}

	@Override
	public void deleteOrgLabor(long orgLaborId)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.UPDATE);

		orgLaborLocalService.deleteOrgLabor(orgLaborId);
	}

	@Override
	public OrgLabor getOrgLabor(long orgLaborId)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.VIEW);

		return orgLabor;
	}

	@Override
	public List<OrgLabor> getOrgLabors(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return orgLaborLocalService.getOrgLabors(organizationId);
	}

	@Override
	public OrgLabor updateOrgLabor(
			long orgLaborId, int typeId, int sunOpen, int sunClose, int monOpen,
			int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
			int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
			int satClose)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.UPDATE);

		return orgLaborLocalService.updateOrgLabor(
			orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose, tueOpen,
			tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose,
			satOpen, satClose);
	}

}