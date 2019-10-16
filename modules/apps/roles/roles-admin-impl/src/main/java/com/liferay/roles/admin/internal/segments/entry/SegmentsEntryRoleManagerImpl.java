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

package com.liferay.roles.admin.internal.segments.entry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.roles.admin.segments.entry.RoleSegmentsEntryManager;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = RoleSegmentsEntryManager.class)
public class SegmentsEntryRoleManagerImpl implements RoleSegmentsEntryManager {

	@Override
	public void addRoleSegmentsEntries(long roleId, long[] segmentsEntryIds) {
		long classNameId = _classNameLocalService.getClassNameId(Role.class);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (long segmentsEntryId : segmentsEntryIds) {
			try {
				_segmentsEntryRelLocalService.addSegmentsEntryRel(
					segmentsEntryId, classNameId, roleId, serviceContext);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
	}

	@Override
	public void removeRoleSegmentsEntries(
		long roleId, long[] segmentsEntryIds) {

		long classNameId = _classNameLocalService.getClassNameId(Role.class);

		for (long segmentsEntryId : segmentsEntryIds) {
			try {
				_segmentsEntryRelLocalService.deleteSegmentsEntryRel(
					segmentsEntryId, classNameId, roleId);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRoleManagerImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}