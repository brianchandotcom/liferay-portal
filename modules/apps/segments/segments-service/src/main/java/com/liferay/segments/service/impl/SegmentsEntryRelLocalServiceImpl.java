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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.base.SegmentsEntryRelLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Eduardo Garcia
 */
public class SegmentsEntryRelLocalServiceImpl
	extends SegmentsEntryRelLocalServiceBaseImpl {

	@Override
	public SegmentsEntryRel addSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long segmentsEntryRelId = counterLocalService.increment();

		SegmentsEntryRel segmentsEntryRel = segmentsEntryRelPersistence.create(
			segmentsEntryRelId);

		segmentsEntryRel.setGroupId(groupId);
		segmentsEntryRel.setCompanyId(user.getCompanyId());
		segmentsEntryRel.setUserId(user.getUserId());
		segmentsEntryRel.setUserName(user.getFullName());
		segmentsEntryRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsEntryRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntryRel.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryRel.setClassNameId(classNameId);
		segmentsEntryRel.setClassPK(classPK);

		segmentsEntryRelPersistence.update(segmentsEntryRel);

		return segmentsEntryRel;
	}

	@Override
	public void deleteSegmentsEntryRels(long segmentsEntryId) {
		segmentsEntryRelPersistence.removeBySegmentsEntryId(segmentsEntryId);
	}

	@Override
	public void deleteSegmentsEntryRels(long classNameId, long classPK) {
		segmentsEntryRelPersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentEntryRels(long segmentEntryId) {
		return segmentsEntryRelPersistence.findBySegmentsEntryId(
			segmentEntryId);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentEntryRels(
		long classNameId, long classPK) {

		return segmentsEntryRelPersistence.findByCN_CPK(classNameId, classPK);
	}

}