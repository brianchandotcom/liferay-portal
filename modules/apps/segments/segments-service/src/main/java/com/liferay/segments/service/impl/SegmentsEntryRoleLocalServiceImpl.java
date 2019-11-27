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

package com.liferay.segments.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.base.SegmentsEntryRoleLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsEntryRole",
	service = AopService.class
)
public class SegmentsEntryRoleLocalServiceImpl
	extends SegmentsEntryRoleLocalServiceBaseImpl {

	@Override
	public SegmentsEntryRole addSegmentsEntryRole(
			long segmentsEntryId, long roleId, ServiceContext serviceContext)
		throws PortalException {

		// Segments entry role

		User user = userLocalService.getUser(serviceContext.getUserId());

		long segmentsEntryRoleId = counterLocalService.increment();

		SegmentsEntryRole segmentsEntryRole =
			segmentsEntryRolePersistence.create(segmentsEntryRoleId);

		segmentsEntryRole.setCompanyId(user.getCompanyId());
		segmentsEntryRole.setUserId(user.getUserId());
		segmentsEntryRole.setUserName(user.getFullName());
		segmentsEntryRole.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsEntryRole.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntryRole.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryRole.setRoleId(roleId);

		segmentsEntryRolePersistence.update(segmentsEntryRole);

		// Indexer

		_reindex(segmentsEntryId);

		return segmentsEntryRole;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsEntryRole deleteSegmentsEntryRole(
			long segmentsEntryId, long roleId)
		throws PortalException {

		// Segments entry role

		SegmentsEntryRole segmentsEntryRole =
			segmentsEntryRolePersistence.removeByS_R(segmentsEntryId, roleId);

		// Indexer

		_reindex(segmentsEntryId);

		return segmentsEntryRole;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteSegmentsEntryRoles(long segmentsEntryId)
		throws PortalException {

		// Segments entry role

		segmentsEntryRolePersistence.removeBySegmentsEntryId(segmentsEntryId);

		// Indexer

		_reindex(segmentsEntryId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteSegmentsEntryRolesByRoleId(long roleId)
		throws PortalException {

		// Segments entry role

		List<SegmentsEntryRole> segmentsEntryRoles =
			segmentsEntryRolePersistence.findByRoleId(roleId);

		segmentsEntryRolePersistence.removeByRoleId(roleId);

		// Indexer

		for (SegmentsEntryRole segmentsEntryRole : segmentsEntryRoles) {
			_reindex(segmentsEntryRole.getSegmentsEntryId());
		}
	}

	@Override
	public List<SegmentsEntryRole> getSegmentsEntryRoles(long segmentsEntryId) {
		return segmentsEntryRolePersistence.findBySegmentsEntryId(
			segmentsEntryId);
	}

	@Override
	public List<SegmentsEntryRole> getSegmentsEntryRolesByRoleId(long roleId) {
		return segmentsEntryRolePersistence.findByRoleId(roleId);
	}

	@Override
	public int getSegmentsEntryRolesCount(long segmentsEntryId) {
		return segmentsEntryRolePersistence.countBySegmentsEntryId(
			segmentsEntryId);
	}

	@Override
	public int getSegmentsEntryRolesCountByRoleId(long roleId) {
		return segmentsEntryRolePersistence.countByRoleId(roleId);
	}

	@Override
	public boolean hasSegmentEntryRole(long segmentsEntryId, long roleId) {
		if (segmentsEntryRolePersistence.fetchByS_R(segmentsEntryId, roleId) !=
				null) {

			return true;
		}

		return false;
	}

	private void _reindex(long segmentsEntryId) throws PortalException {
		SegmentsEntry segmentsEntry =
			segmentsEntryPersistence.fetchByPrimaryKey(segmentsEntryId);

		if (segmentsEntry == null) {
			return;
		}

		Indexer<SegmentsEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			SegmentsEntry.class);

		indexer.reindex(segmentsEntry);
	}

}